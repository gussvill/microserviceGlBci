package com.globallogic.microserviceglbci.security;

import lombok.Data;

// la clase AuthCredentials es una clase simple de POJO que se utiliza para encapsular las credenciales de autenticación de un usuario en una aplicación web.
// La anotación @Data de Lombok se utiliza para generar automáticamente los métodos getter y setter para los campos de datos de la clase.
@Data
public class AuthCredentials {
    private String email;
    private String password;
}

/*
    POJO es un acrónimo de "Plain Old Java Object" que se refiere a una clase de Java simple que no sigue ningún patrón de diseño particular o marco de trabajo y que contiene solo los campos de datos (propiedades)
        y los métodos getter y setter para acceder y modificar los campos de datos.
    En otras palabras, un POJO es una clase Java que se utiliza para encapsular los datos y no contiene ninguna lógica de negocio compleja o dependencias externas. Es una clase simple y
        sin complicaciones que puede ser fácilmente serializada y deserializada en diferentes formatos de datos como XML, JSON, etc.
    Las clases POJO se utilizan comúnmente en aplicaciones de la capa de presentación, como aplicaciones web y de escritorio, donde se utilizan para almacenar datos de formularios, datos de sesión, datos de respuesta de la API, etc.
    Las clases POJO son un enfoque sencillo y efectivo para la programación orientada a objetos en Java, ya que facilitan la comprensión y el mantenimiento del código al mismo tiempo que reducen la complejidad del mismo.
 */

/*

    La anotación `@Data` es una etiqueta utilizada en el lenguaje de programación Java y es parte del proyecto Lombok.
    Esta anotación permite que Lombok genere automáticamente los métodos getters y setters, toString, equals y hashCode para las propiedades de la clase, lo que simplifica la creación de clases de modelo.

    En la clase `AuthCredentials`, la anotación `@Data` indica que la clase es un modelo de datos que contiene dos propiedades: `email` y `password`. Estas propiedades son de tipo `String` y son privadas,
    lo que significa que solo se pueden acceder a ellas a través de los métodos getters y setters generados automáticamente por Lombok.

    Esta clase se utiliza para almacenar las credenciales de autenticación de un usuario en una aplicación web. En particular, se utiliza en el método `attemptAuthentication` de la clase `JWTAuthenticationFilter`
     leer las credenciales proporcionadas por el usuario y crear un `UsernamePasswordAuthenticationToken` que se pasa al administrador de autenticación de Spring para su validación.

    En resumen, la clase `AuthCredentials` es un modelo de datos simple que se utiliza para almacenar las credenciales de autenticación de un usuario en una aplicación web y que se beneficia de la anotación `@Data`
    para simplificar la creación de getters y setters y otros métodos comunes.

 */