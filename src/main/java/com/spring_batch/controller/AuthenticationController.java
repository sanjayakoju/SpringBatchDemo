package com.spring_batch.controller;

import com.spring_batch.model.dto.AuthResponseDTO;
import com.spring_batch.model.dto.LoginCredentialDTO;
import com.spring_batch.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentialDTO loginCredential) {
        log.info("Authentication : Login requested by User: " +loginCredential.getUsername());
        AuthResponseDTO responseDTO = authService.login(loginCredential);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
