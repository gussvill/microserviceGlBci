package com.globallogic.microserviceglbci.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "RevokedToken")
@Data
@AllArgsConstructor
@Builder
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    public RevokedToken(String token) {
        this.token = token;
    }

    public RevokedToken() {

    }
}

/*
    La anotación `@Entity` se utiliza en Java para indicar que una clase es una entidad que se puede almacenar en una base de datos relacional. La anotación `@Table` se utiliza para especificar el nombre de la
    tabla en la base de datos que corresponde a la entidad.

    La clase `RevokedToken` es una entidad que representa un token revocado en la aplicación. Tiene dos propiedades: `id` y `token`.

    La propiedad `id` se utiliza como identificador único del token revocado en la base de datos. La propiedad `token` representa el valor del token revocado. Esta propiedad es única y no puede ser nula,
    lo que garantiza que no se puedan almacenar varios tokens revocados con el mismo valor en la base de datos.

    La clase `RevokedToken` tiene dos constructores: uno sin argumentos y otro que toma como argumento una cadena de texto `token`. También tiene métodos getters y setters para acceder y modificar los valores de sus propiedades.

    Además, la clase utiliza la anotación `@Id` para indicar que la propiedad `id` es el identificador único de la entidad en la base de datos, y la anotación `@GeneratedValue` para especificar cómo se generará
    el valor de la propiedad `id`. También utiliza la anotación `@Column` para especificar que la propiedad `token` se mapeará a una columna en la tabla de la base de datos, y para especificar que la columna es única y no nula.

    En resumen, la clase `RevokedToken` es una entidad que representa un token revocado en la aplicación. Utiliza las anotaciones `@Entity` y `@Table` para indicar que es una entidad de base de datos y
    para especificar el nombre de la tabla en la base de datos que corresponde a la entidad. Tiene propiedades para representar los datos del token revocado, métodos getters y setters para acceder y
    modificar los valores de las propiedades, y constructores para crear instancias de la clase. También utiliza las anotaciones `@Id`, `@GeneratedValue` y `@Column` para especificar detalles adicionales
    sobre cómo se mapean las propiedades de la entidad a la tabla de la base de datos.

 */