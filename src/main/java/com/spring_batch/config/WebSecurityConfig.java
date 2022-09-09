package com.spring_batch.config;

import com.spring_batch.security.CustomUserDetailsService;
import com.spring_batch.security.JWTTokenFilter;
import com.spring_batch.security.JWTTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final JWTTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(JWTTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return http.csrf().disable()
              .authorizeHttpRequests((auth) -> auth
                              .antMatchers("/auth/**").permitAll()
                              .antMatchers("/job/**").hasAnyAuthority("ADMIN")
                              .anyRequest().authenticated()
                      ).httpBasic(Customizer.withDefaults())
              .exceptionHandling(eh -> eh
                      .authenticationEntryPoint((request, response, authException) ->
                              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
              )
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and().addFilterAfter(new JWTTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
              .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
