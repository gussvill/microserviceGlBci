package com.globallogic.microserviceglbci.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/*
    En resumen, la clase UsuarioSignUpResponse es una clase simple de POJO que representa la respuesta que se devuelve al cliente después de que un usuario se registra exitosamente en una aplicación.
    La clase utiliza varias anotaciones de Lombok para generar automáticamente los constructores, los métodos getter y setter, y otros métodos útiles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSignUpResponse {
    private UUID id;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;
}

/*
Las anotaciones de Lombok son utilizadas en conjunción con el framework Spring Boot para simplificar el código de las clases Java. A continuación, se describe cada una de las anotaciones mencionadas en la pregunta:

@Data: Esta anotación es utilizada para generar automáticamente los métodos getter y setter, así como los métodos equals(), hashCode(), y toString() para todos los campos de la clase.
Esto evita la necesidad de escribir código redundante para cada campo de la clase, lo que simplifica el código y reduce la posibilidad de errores.

@NoArgsConstructor: Esta anotación es utilizada para generar un constructor sin argumentos para la clase. Esto es útil cuando se necesita crear una instancia de la clase sin proporcionar ningún valor inicial.
Por ejemplo, cuando se utiliza un framework de serialización para deserializar objetos a partir de una representación de texto.

@AllArgsConstructor: Esta anotación es utilizada para generar un constructor que toma todos los campos de la clase como argumentos.
Esto evita la necesidad de escribir manualmente un constructor para cada clase y reduce la cantidad de código necesario.

@Builder: Esta anotación es utilizada para generar un constructor de estilo "Builder" para la clase. El patrón de diseño Builder se utiliza para crear objetos complejos paso a paso,
proporcionando una sintaxis más clara y legible. Con esta anotación, se pueden construir objetos complejos utilizando una sintaxis similar a la de los métodos encadenados.

En resumen, las anotaciones de Lombok mencionadas se utilizan en conjunto con el framework Spring Boot para simplificar el código de las clases Java, generando automáticamente constructores,
métodos getter y setter, y otros métodos útiles. Esto permite una mayor productividad y reduce la posibilidad de errores en el código.
 */