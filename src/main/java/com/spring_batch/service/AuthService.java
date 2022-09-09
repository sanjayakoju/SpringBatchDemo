package com.spring_batch.service;

import com.spring_batch.model.dto.AuthResponseDTO;
import com.spring_batch.model.dto.LoginCredentialDTO;

public interface AuthService {

    AuthResponseDTO login(LoginCredentialDTO loginCredential);
}
