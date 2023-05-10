package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// esta implementación se encarga de proporcionar una capa de abstracción para acceder a los datos de usuario en la base de datos, y ofrece una interfaz simple y coherente para hacerlo.
@Service
public class UsuarioQueryServiceImpl implements UsuarioQueryService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> getUserByEmail(String email) {
        return usuarioRepository.findByEmailContaining(email);
    }

    @Override
    public Usuario getUserByEmail(String email, String nullValue) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> getUsers() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void updateToken(String email, String newToken) {
        usuarioRepository.updateToken(email, newToken);
    }

    @Override
    public void updateLastLogin(String email, String date) {
        usuarioRepository.updateLastLogin(email, date);
    }
}

/*
La clase `UsuarioQueryServiceImpl` es una implementación de la interfaz `UsuarioQueryService`, que define métodos para realizar consultas y operaciones en una base de datos que almacena objetos `Usuario`.

La anotación `@Service` indica que esta clase es un componente de servicio en una arquitectura basada en Spring Framework, y permite que esta clase sea detectada y manejada por el contenedor de Spring.

La clase `UsuarioQueryServiceImpl` utiliza la inyección de dependencias a través de la anotación `@Autowired` para obtener una instancia del repositorio de `Usuario` (`UsuarioRepository`)
en el cual se realizan las operaciones de consulta y actualización en la base de datos.

Los métodos implementados en `UsuarioQueryServiceImpl` son los siguientes:

- `getUserByEmail(String email)`: devuelve un `Optional` que contiene un objeto `Usuario` si se encuentra un usuario con la dirección de correo electrónico especificada, y un objeto vacío si no se encuentra ningún usuario.
- `getUserByEmail(String email, String nullValue)`: devuelve un objeto `Usuario` si se encuentra un usuario con la dirección de correo electrónico especificada, o el valor `nullValue` si no se encuentra ningún usuario.
- `getUsers()`: devuelve una lista de todos los usuarios almacenados en la base de datos.
- `save(Usuario usuario)`: guarda un objeto `Usuario` en la base de datos y devuelve el objeto guardado.
- `updateToken(String email, String newToken)`: actualiza el token de seguridad para un usuario con la dirección de correo electrónico especificada.
- `updateLastLogin(String email, String date)`: actualiza la fecha y hora del último inicio de sesión para un usuario con la dirección de correo electrónico especificada.

En resumen, la clase `UsuarioQueryServiceImpl` es una clase de servicio que encapsula las operaciones de consulta y actualización en una base de datos para objetos `Usuario`,
y se utiliza para interactuar con la capa de persistencia en una aplicación basada en Spring Framework.
 */
