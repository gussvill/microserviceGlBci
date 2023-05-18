package com.bci.microservice.response;

import com.bci.microservice.domain.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
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
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private Set<Phone> phones;
}
