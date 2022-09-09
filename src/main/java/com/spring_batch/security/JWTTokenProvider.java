package com.spring_batch.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_batch.model.User;
import com.spring_batch.utils.AppUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;


@Slf4j
@Component
public class JWTTokenProvider {

    private static final String HEADER_AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_TOKEN = "Bearer ";

    @Value("${app.jwt.token.secret-key}")
    private String secretKey;
    @Value("${app.jwt.token.expire-seconds}")
    private long tokenExpirationInSeconds;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Authentication authentication) throws JsonProcessingException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());

        User user = customUserDetails.getUser();
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("username", user.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        claims.put("roles", objectMapper.writeValueAsString(user.getRoles()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (tokenExpirationInSeconds)))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public String getTokenRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN)) {
            return bearerToken.split(" ")[1];
        }
        return null;
    }

    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired: JWT Token");
            request.setAttribute("Expired", "Expired: JWT Token");
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid: JWT Token");
            request.setAttribute("Invalid", "Invalid: JWT Token");
            e.printStackTrace();
        }
        return false;
    }

    public Authentication getAuthenticationToken(String token, HttpServletRequest request) throws JsonProcessingException {
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        User user = new User();
        Long id = Long.valueOf(String.valueOf(body.get("userId")));
        user.setId(id);
        user.setUsername((String) body.get("username"));
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> userRoles = objectMapper.readValue(body.get("roles").toString(), Set.class);
        user.setRoles(AppUtils.convertStringRolesSetToEnumSet(userRoles));
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        return authentication;
    }
}
