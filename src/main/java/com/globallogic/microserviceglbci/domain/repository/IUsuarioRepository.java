package com.globallogic.microserviceglbci.domain.repository;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailContaining(String email);

    Optional<Usuario> findOneByName(String usuario);

    Usuario findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.token = :newToken WHERE u.email = :email")
    void updateToken(@Param("email") String email, @Param("newToken") String newToken);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.lastLogin = :date WHERE u.email = :email")
    void updateLastLogin(@Param("email") String email, @Param("date") String date);

}

/*
    La anotación `@Repository` es una etiqueta utilizada en el lenguaje de programación Java y es parte del framework de Spring. Esta anotación indica que la interfaz anotada es un componente de repositorio,
    que se utiliza para interactuar con una fuente de datos, como una base de datos, en una aplicación.

    La interfaz `UsuarioRepository` es una interfaz de repositorio que extiende la interfaz `JpaRepository`. `JpaRepository` es una interfaz proporcionada por Spring Data JPA que proporciona una serie de métodos
    predefinidos para realizar operaciones comunes en la base de datos, como guardar, eliminar, actualizar y buscar registros.

    Además de los métodos predefinidos proporcionados por `JpaRepository`, la interfaz `UsuarioRepository` tiene varios métodos personalizados para interactuar con los datos de usuario de la aplicación. Por ejemplo,
    `findByEmailContaining` busca un usuario por correo electrónico, `findOneByName` busca un usuario por nombre de usuario, `findByEmail` busca un usuario por correo electrónico y los métodos `updateToken` y `updateLastLogin`
    actualizan los valores de token y última fecha de inicio de sesión de un usuario, respectivamente.

    Estos métodos personalizados utilizan la anotación `@Modifying`, que indica que la consulta JPA asociada modificará los datos en la base de datos. También utilizan la anotación `@Transactional`,
    que indica que la consulta se ejecutará dentro de una transacción, lo que garantiza la integridad de los datos.

    En resumen, la interfaz `UsuarioRepository` es una interfaz de repositorio que se utiliza para interactuar con los datos de usuario en una aplicación. Utiliza la anotación `@Repository` para indicar que es un componente
    de repositorio de Spring, y extiende la interfaz `JpaRepository` para proporcionar una serie de métodos predefinidos para interactuar con los datos. También tiene varios métodos personalizados para interactuar
    con los datos de usuario, que utilizan las anotaciones `@Modifying` y `@Transactional` para garantizar la integridad de los datos.
 */