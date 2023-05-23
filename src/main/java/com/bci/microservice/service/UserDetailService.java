package com.bci.microservice.service;

import com.bci.microservice.entity.Usuario;
import com.bci.microservice.repository.UsuarioJpaRepository;
import com.bci.microservice.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Esta clase proporciona una implementación del método `loadUserByUsername` para cargar los detalles del usuario desde una base de datos y crear un objeto `UserDetails` para su uso en la autenticación del usuario.
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioJpaRepository UsuarioJpaRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = UsuarioJpaRepository
                .findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con userName " + name + " no existe."));

        return new UserDetailsImpl(usuario);


    }
}
