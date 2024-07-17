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
 * The type Usuarios response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private static ModelMapper modelMapper = new ModelMapper();
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

    public static UsuarioResponse convertToUsuarioResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }
}
