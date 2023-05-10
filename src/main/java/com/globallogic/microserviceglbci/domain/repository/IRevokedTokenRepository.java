package com.globallogic.microserviceglbci.domain.repository;

import com.globallogic.microserviceglbci.domain.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRevokedTokenRepository extends JpaRepository<RevokedToken, Long> {

    boolean existsByToken(String token);

}

/*
    La interfaz `RevokedTokenRepository` es una interfaz de repositorio que extiende la interfaz `JpaRepository`. `JpaRepository` es una interfaz proporcionada por Spring Data JPA
    que proporciona una serie de métodos predefinidos para realizar operaciones comunes en la base de datos, como guardar, eliminar, actualizar y buscar registros.

    La interfaz `RevokedTokenRepository` tiene dos métodos, uno de los cuales es personalizado. El método personalizado, `existsByToken`, comprueba si existe un token revocado en la base de datos.
    Este método toma como argumento una cadena de texto `token` y devuelve un valor booleano que indica si existe o no un token revocado en la base de datos que coincida con el valor de `token`.

    Este método personalizado utiliza una convención de nomenclatura específica de Spring Data JPA para generar automáticamente la consulta JPA asociada a este método. En este caso,
    la consulta generada comprobará si existe una entidad `RevokedToken` en la base de datos que tenga un atributo `token` igual al valor de `token`.

    En resumen, la interfaz `RevokedTokenRepository` es una interfaz de repositorio que se utiliza para interactuar con los tokens revocados en una aplicación. Utiliza la anotación `@Repository`
    para indicar que es un componente de repositorio de Spring, y extiende la interfaz `JpaRepository` para proporcionar una serie de métodos predefinidos para interactuar con los datos. También tiene un método personalizado, `existsByToken`, que utiliza la convención de nomenclatura de Spring Data JPA para generar automáticamente una consulta JPA que comprueba si existe un token revocado en la base de datos que coincida con el valor de `token`.
 */