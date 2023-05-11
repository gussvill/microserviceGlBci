package com.globallogic.microserviceglbci.response;

import com.globallogic.microserviceglbci.domain.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private List<Phone> phones;
}
