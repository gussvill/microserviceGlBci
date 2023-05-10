package com.globallogic.microserviceglbci.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// la clase GlobalExceptionHandler maneja excepciones en una aplicación web Spring Boot. El método handleInputValidationException se encarga de manejar la excepción InputValidationException,
// creando un objeto ErrorObject y devolviendo una respuesta HTTP con un código de estado "400 Bad Request" y la lista de ErrorObject como cuerpo de respuesta.
// Esto permite una respuesta clara y estructurada a los errores de validación en la entrada del usuario.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<List<ErrorObject>> handleInputValidationException(InputValidationException ex) {

        /*
            El método handleInputValidationException es un método de manejo de excepciones que se encarga de manejar la excepción InputValidationException.
            Este método toma como parámetro un objeto InputValidationException, que se lanza cuando se detecta un error de validación en la entrada del usuario en una aplicación.

            El método handleInputValidationException crea un objeto ErrorObject utilizando los valores del código de error y la descripción detallada de la excepción InputValidationException.
            El objeto ErrorObject es una clase personalizada que encapsula información sobre un error en la aplicación, como la fecha y hora en que se produjo el error, el código de error y una descripción detallada del mismo.

            El método handleInputValidationException crea una lista de ErrorObject y agrega el objeto ErrorObject creado anteriormente a la lista.
            Luego, devuelve una respuesta HTTP con un código de estado "400 Bad Request" y la lista de ErrorObject como cuerpo de respuesta.
         */

        ErrorObject errorObject = new ErrorObject(LocalDateTime.now(), ex.getCodigo(), ex.getDetail());
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(errorObject);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }
}
/*
    La anotación `@RestControllerAdvice` es una etiqueta utilizada en el lenguaje de programación Java y es parte del framework de Spring.
    Esta anotación indica que la clase anotada es un controlador global de excepciones que se utiliza para manejar todas las excepciones lanzadas por los controladores de la aplicación.

    La clase `GlobalExceptionHandler` es una clase anotada con `@RestControllerAdvice` que implementa un método de manejo de excepciones llamado `handleInputValidationException`.
    Este método maneja la excepción `InputValidationException`, que se lanza cuando se detecta un error de validación en la entrada del usuario en una aplicación.

    Dentro del método `handleInputValidationException`, se crea un objeto `ErrorObject` utilizando los valores del código de error y la descripción detallada de la excepción `InputValidationException`.
    El objeto `ErrorObject` es una clase personalizada que encapsula información sobre un error en la aplicación, como la fecha y hora en que se produjo el error, el código de error y una descripción detallada del mismo.

    Luego, se crea una lista de `ErrorObject` y se agrega el objeto `ErrorObject` creado anteriormente a la lista. Finalmente, se devuelve una respuesta HTTP con un código de estado "400 Bad Request" y la lista de `ErrorObject`
    como cuerpo de respuesta.

    En resumen, la clase `GlobalExceptionHandler` es un controlador global de excepciones que utiliza el método `handleInputValidationException` para manejar las excepciones `InputValidationException`.
    El método crea un objeto `ErrorObject` para encapsular información sobre el error y devuelve una respuesta HTTP con un código de estado "400 Bad Request" y la lista de `ErrorObject` como cuerpo de respuesta.
 */