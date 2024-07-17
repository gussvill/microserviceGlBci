package com.bci.microservice.responses;

import com.bci.microservice.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

/**
 * The type Usuario sign up response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSignUpResponse {
    private static ModelMapper modelMapper = new ModelMapper();
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    @JsonProperty("isActive")
    private boolean isActive;
    private String message;

    public static UsuarioSignUpResponse convertToUsuarioSignUpResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioSignUpResponse.class);
    }
}
