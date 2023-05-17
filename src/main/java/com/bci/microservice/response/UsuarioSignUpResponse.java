package com.bci.microservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * The type Usuario sign up response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSignUpResponse {
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
}
