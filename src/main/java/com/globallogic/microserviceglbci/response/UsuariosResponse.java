package com.globallogic.microserviceglbci.response;

import com.globallogic.microserviceglbci.domain.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosResponse {
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}

/*
    La anotación `@Data` es una etiqueta utilizada en el lenguaje de programación Java y es parte del proyecto Lombok.
    Esta anotación permite que Lombok genere automáticamente los métodos getters y setters, toString, equals y hashCode para las propiedades de la clase, lo que simplifica la creación de clases de modelo.

    Las anotaciones `@NoArgsConstructor` y `@AllArgsConstructor` son también parte de Lombok y se utilizan para generar automáticamente constructores sin argumentos y con todos los argumentos respectivamente.
    El constructor sin argumentos es útil para la creación de instancias vacías de la clase, mientras que el constructor con argumentos permite crear instancias de la clase con valores específicos para sus propiedades.

    La anotación `@Builder` es otra etiqueta de Lombok que se utiliza para generar un patrón de diseño de constructor de objeto inmutable,
    donde se construye un objeto paso a paso mediante la asignación de valores a cada propiedad individualmente. Esto proporciona una forma más clara y segura de construir objetos complejos.

    En la clase `UsuariosResponse`, estas anotaciones se utilizan en conjunto para generar una clase de modelo que representa la respuesta de la API de un servicio de usuarios.
    La clase tiene varias propiedades, como `id`, `created`, `lastLogin`, `token`, `isActive`, `name`, `email`, `password` y `phones`. Estas propiedades contienen información relevante sobre el usuario y sus dispositivos.

    En resumen, la clase `UsuariosResponse` es una clase de modelo generada automáticamente por Lombok que se utiliza para representar la respuesta de la API de un servicio de usuarios.
    Las anotaciones `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` y `@Builder` son utilizadas para simplificar la creación de la clase y generar automáticamente los métodos y constructores necesarios.
 */