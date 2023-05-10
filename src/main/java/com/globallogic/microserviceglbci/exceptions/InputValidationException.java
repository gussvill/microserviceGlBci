package com.globallogic.microserviceglbci.exceptions;

//  Esta clase se utiliza para representar una excepción que se produce cuando se detecta un error de validación en la entrada de un usuario en una aplicación.
//  se utiliza para representar una excepción que se produce cuando se detecta un error de validación en la entrada de un usuario en una aplicación.
//  La clase almacena el código de error y una descripción detallada del error, y proporciona métodos para acceder a estos valores.
public class InputValidationException extends RuntimeException {
    private final int codigo;
    private final String detail;

    public InputValidationException(int codigo, String detail) {
        this.codigo = codigo;
        this.detail = detail;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDetail() {
        return detail;
    }
}

/*
    La clase `InputValidationException` es una clase de excepción que extiende la clase `RuntimeException` de Java. Se utiliza para representar una excepción lanzada cuando se produce un error de validación en los datos de entrada.

    La clase tiene dos propiedades, `codigo` y `detail`, que se utilizan para proporcionar información sobre el error de validación.
    La propiedad `codigo` es un valor entero que representa un código de error específico, mientras que la propiedad `detail` es una cadena de texto que proporciona detalles adicionales sobre el error.

    El constructor de la clase `InputValidationException` toma como argumentos los valores para `codigo` y `detail`, que se asignan a las propiedades correspondientes. Luego, los métodos `getCodigo()` y `getDetail()`
    se utilizan para recuperar los valores de las propiedades `codigo` y `detail`, respectivamente.

    Esta clase se utiliza comúnmente en aplicaciones web para manejar errores de validación de datos de entrada. Por ejemplo, si un usuario intenta enviar un formulario con datos incorrectos,
    se puede lanzar una instancia de esta clase para indicar que se ha producido un error de validación y proporcionar información detallada sobre el problema.

    En resumen, la clase `InputValidationException` es una clase de excepción que se utiliza para representar errores de validación de datos de entrada en una aplicación Java. Sus propiedades `codigo` y `detail`
    se utilizan para proporcionar información detallada sobre el error y se pueden recuperar utilizando los métodos `getCodigo()` y `getDetail()`.
 */