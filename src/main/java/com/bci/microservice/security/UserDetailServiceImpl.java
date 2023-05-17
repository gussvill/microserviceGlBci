package com.bci.microservice.security;

import com.bci.microservice.domain.entity.Usuario;
import com.bci.microservice.domain.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Esta clase proporciona una implementación del método `loadUserByUsername` para cargar los detalles del usuario desde una base de datos y crear un objeto `UserDetails` para su uso en la autenticación del usuario.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository IUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = IUsuarioRepository
                .findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con userName " + name + " no existe."));

        return new UserDetailsImpl(usuario);


    }
}
