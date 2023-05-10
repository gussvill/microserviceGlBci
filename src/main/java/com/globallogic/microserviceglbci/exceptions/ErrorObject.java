package com.globallogic.microserviceglbci.exceptions;

import java.time.LocalDateTime;

//  Esta representación en formato de cadena es útil para imprimir información de depuración o para enviar información de error a un registro de errores.
public class ErrorObject {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;

    public ErrorObject(LocalDateTime timestamp, int codigo, String detail) {
        this.timestamp = timestamp;
        this.codigo = codigo;
        this.detail = detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ErrorObject{" +
                "timestamp=" + timestamp +
                ", codigo=" + codigo +
                ", detail='" + detail + '\'' +
                '}';
    }

}

/*
    La clase `ErrorObject` es una clase que encapsula información sobre un error en una aplicación. Tiene tres propiedades: `timestamp`, `codigo` y `detail`.
    La propiedad `timestamp` es de tipo `LocalDateTime` y representa la fecha y hora en que se produjo el error. La propiedad `codigo` es un entero que representa el código de error,
    y la propiedad `detail` es una cadena de texto que proporciona detalles adicionales sobre el error.

    La clase tiene un constructor que toma como argumentos un objeto `LocalDateTime`, un entero y una cadena de texto, que se asignan a las propiedades correspondientes.
    También tiene métodos getters y setters para cada propiedad, lo que permite acceder y modificar los valores de las propiedades.

    Además, la clase tiene un método `toString` que devuelve una cadena de texto que representa el objeto `ErrorObject`. Este método se utiliza comúnmente para la depuración y el registro de errores en una aplicación.

    La clase `ErrorObject` se utiliza comúnmente en aplicaciones web para encapsular información sobre los errores que ocurren en la aplicación. Se puede utilizar en conjunto con otros mecanismos de manejo de errores,
    como controladores de excepciones, para proporcionar información detallada sobre los errores que ocurren en la aplicación.

    En resumen, la clase `ErrorObject` es una clase que encapsula información sobre un error en una aplicación. Sus propiedades `timestamp`, `codigo` y `detail` se utilizan para proporcionar información detallada sobre el error,
    y sus métodos getters y setters permiten acceder y modificar los valores de las propiedades.
 */