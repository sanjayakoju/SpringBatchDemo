package com.spring_batch.model.dto;

import com.spring_batch.enums.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class AuthResponseDTO {

    private String token;
    private LocalDateTime timestamp;
    private Set<UserRole> roles;
}
