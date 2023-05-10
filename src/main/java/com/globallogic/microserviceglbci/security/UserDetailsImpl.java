package com.globallogic.microserviceglbci.security;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//  implementa la interfaz UserDetails de Spring Security framework
// La interfaz UserDetails se utiliza para representar los detalles de un usuario en un sistema de autenticación de Spring Security
// implementación básica de la interfaz UserDetails que se puede utilizar en un sistema de autenticación de Spring Security donde no se requieren roles o autoridades
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    public String getEmail() {
        return usuario.getEmail();
    }

    @Override
    public String getUsername() {
        return usuario.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
/*
    Esta es una implementación de la interfaz `UserDetails` de Spring Security para una clase llamada `Usuario`.
    Representa los detalles de un usuario en un sistema y proporciona los métodos necesarios para autenticar y autorizar al usuario.

    La clase `UserDetailsImpl` tiene un constructor que toma un objeto `Usuario` y ​​implementa todos los métodos de la interfaz `UserDetails`.

    El método `getAuthorities()` devuelve una lista vacía de objetos `GrantedAuthority`, lo que indica que este usuario no tiene roles ni permisos.

    El método `getPassword()` devuelve la contraseña del usuario, que se obtiene del objeto `Usuario`.

    El método `getUsername()` devuelve el nombre del usuario, que también se obtiene del objeto `Usuario`.

    Los métodos `isAccountNonExpired()`, `isAccountNonLocked()` y `isCredentialsNonExpired()` todos devuelven `true`,
    lo que indica que la cuenta del usuario no ha expirado, no está bloqueada y las credenciales no han expirado.

    El método `isEnabled()` también devuelve `true`, lo que indica que la cuenta del usuario está habilitada.

    En general, esta implementación proporciona un conjunto básico de detalles de usuario que se pueden utilizar para la autenticación y autorización en una aplicación habilitada para Spring Security.
    Sin embargo, puede ser necesario personalizarlo según los requisitos específicos del sistema.
 */
