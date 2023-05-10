package com.globallogic.microserviceglbci.security;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//  se utiliza para cargar los detalles de un usuario desde una base de datos utilizando
//  Spring Data JPA y crear un objeto UserDetails de Spring Security que contenga la información necesaria para autenticar al usuario en un sistema de Spring Security.
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository IUsuarioRepository;

    /*
         Si se encuentra el usuario, se crea un nuevo objeto UserDetailsImpl que implementa la interfaz UserDetails de Spring Security,
         y se inicializa con el objeto Usuario cargado desde la base de datos.
         Luego, se devuelve este objeto UserDetails al sistema de autenticación de Spring Security para su uso en la autenticación del usuario.
     */

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = IUsuarioRepository
                .findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con userName " + name + " no existe."));

        return new UserDetailsImpl(usuario);


    }
}
/*
    La anotación `@Service` indica que la clase `UserDetailServiceImpl` es un componente de servicio de Spring y que debe ser administrada por el contenedor de Spring.
    Esto significa que se puede inyectar en otras clases utilizando la anotación `@Autowired`.

    La clase `UserDetailServiceImpl` implementa la interfaz `UserDetailsService` de Spring Security, que proporciona un método para cargar los detalles del usuario por nombre de usuario.

    En el método `loadUserByUsername`, se utiliza el repositorio de `Usuario` para buscar un objeto `Usuario` por nombre de usuario. Si se encuentra, se crea un nuevo objeto `UserDetailsImpl`
   s con los detalles del usuario obtenidos del objeto `Usuario`, y se devuelve a Spring Security para su uso en la autenticación del usuario.

    Si no se encuentra el usuario, se lanza una excepción `UsernameNotFoundException`, que indica que el nombre de usuario no existe en la base de datos.

    En resumen, esta clase proporciona una implementación del método `loadUserByUsername` para cargar los detalles del usuario desde una base de datos y crear un objeto `UserDetails` para su uso en la autenticación del usuario.
 */