package com.bci.microservice.service;

import com.bci.microservice.entity.Usuario;
import com.bci.microservice.interfaces.repositories.IUsuarioRepository;
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
    private IUsuarioRepository IUsuarioRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = IUsuarioRepository
                .findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con userName " + name + " no existe."));

        return new UserDetailsImpl(usuario);


    }
}
