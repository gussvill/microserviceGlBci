package com.bci.microservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * The type Usuarios response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosResponse {
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    @JsonProperty("isActive")
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private String phones;
}
