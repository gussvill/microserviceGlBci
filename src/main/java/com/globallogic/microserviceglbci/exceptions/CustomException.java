package com.globallogic.microserviceglbci.exceptions;

public class CustomException extends RuntimeException {

    private final String json;

    public CustomException(String message, String json) {
        super(message);
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}

/*
    La clase `CustomException` es una clase de excepción personalizada que extiende la clase `RuntimeException` de Java. Se utiliza para lanzar excepciones personalizadas en una aplicación cuando se produce un error
    que no puede ser manejado de forma normal.

    La clase tiene dos propiedades, `message` y `json`, que se utilizan para proporcionar información sobre el error. La propiedad `message` es una cadena de texto que proporciona una descripción detallada del error,
    mientras que la propiedad `json` es una cadena de texto que representa el objeto JSON asociado con el error.

    El constructor de la clase `CustomException` toma como argumentos una cadena de texto `message` y una cadena de texto `json`, que se asignan a las propiedades correspondientes. El constructor también llama al
    constructor de la superclase `RuntimeException` con la cadena de texto `message`.

    El método `getJson()` se utiliza para recuperar el valor de la propiedad `json`.

    Esta clase se utiliza comúnmente en aplicaciones web para manejar errores personalizados y proporcionar información detallada sobre el error al usuario o al desarrollador.

    En resumen, la clase `CustomException` es una clase de excepción personalizada que se utiliza para lanzar excepciones personalizadas en una aplicación. Sus propiedades `message` y `json`
    se utilizan para proporcionar información detallada sobre el error, y su método `getJson()` se utiliza para recuperar el valor de la propiedad `json`.
 */