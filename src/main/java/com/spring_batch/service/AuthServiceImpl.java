package com.spring_batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring_batch.model.dto.AuthResponseDTO;
import com.spring_batch.model.dto.LoginCredentialDTO;
import com.spring_batch.security.JWTTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseDTO login(LoginCredentialDTO loginCredential) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredential.getUsername(), loginCredential.getPassword()));
            String token = jwtTokenProvider.createToken(authentication);
            AuthResponseDTO response = new AuthResponseDTO();
            response.setToken(token);
            return response;
        } catch (JsonProcessingException e) {
            throw new BadCredentialsException("Bad Login Credentials");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
