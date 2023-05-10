package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;
// define el contrato que deben cumplir las implementacion.
public interface UsuarioQueryService {
    Optional<Usuario> getUserByEmail(String email);

    Usuario getUserByEmail(String email, String nullValue);

    List<Usuario> getUsers();

    Usuario save(Usuario usuario);

    void updateToken(String email, String newToken);

    void updateLastLogin(String email, String date);
}

/*
    Este código define una interfaz llamada `UsuarioQueryService` que define un conjunto de métodos que se pueden utilizar para realizar consultas y actualizaciones relacionadas con los usuarios de una aplicación.
    La interfaz no proporciona una implementación concreta de estos métodos, sino que define la firma de los mismos.

    Los métodos que se definen en esta interfaz son los siguientes:

    - `Optional<Usuario> getUserByEmail(String email)`: este método toma una dirección de correo electrónico como parámetro y devuelve un objeto `Optional` que contiene un objeto `Usuario`
    que tiene la dirección de correo electrónico dada. Si no se encuentra ningún usuario con la dirección de correo electrónico dada, devuelve un objeto `Optional` vacío.

    - `Usuario getUserByEmail(String email, String nullValue)`: este método es similar al anterior, pero en lugar de devolver un objeto `Optional`, devuelve un objeto `Usuario` que tiene la dirección de correo electrónico dada.
    Si no se encuentra ningún usuario con la dirección de correo electrónico dada, devuelve un valor predeterminado especificado por el segundo parámetro.

    - `List<Usuario> getUsers()`: este método devuelve una lista de todos los usuarios en la aplicación.

    - `Usuario save(Usuario usuario)`: este método se utiliza para guardar un nuevo usuario en la aplicación. Toma un objeto `Usuario` como parámetro y devuelve el mismo objeto después de haber sido guardado en la aplicación.

    - `void updateToken(String email, String newToken)`: este método actualiza el token de seguridad de un usuario existente en la aplicación. Toma una dirección de correo electrónico y un nuevo token como parámetros.

    - `void updateLastLogin(String email, String date)`: este método actualiza la fecha de último inicio de sesión de un usuario existente en la aplicación. Toma una dirección de correo electrónico y una fecha como parámetros.

    En resumen, esta interfaz define un conjunto de métodos que se pueden utilizar para consultar y actualizar usuarios en una aplicación. La implementación concreta de estos métodos se define en una clase que implementa esta interfaz.
 */