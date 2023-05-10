package com.globallogic.microserviceglbci.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

// utilidades de fecha y hora en el proyecto
public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Date Utility class");
    }

    public static String formattedDate(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static Date expirationDate(int expirationDateMs) {
        return new Date(new Date().getTime() + expirationDateMs);
    }

}

/*
La clase `DateUtils` es una clase de utilidad que proporciona dos métodos estáticos para trabajar con fechas y tiempos:

1. El primer método, `formattedDate()`, toma una cadena que especifica el formato de fecha y devuelve una cadena formateada que representa la fecha y la hora actuales en el formato especificado.
Este método utiliza la clase `LocalDateTime` y `DateTimeFormatter` de la API de fecha y hora de Java 8 para formatear la fecha y hora.

2. El segundo método, `expirationDate()`, toma una duración de tiempo en milisegundos como argumento y devuelve una instancia de la clase `Date` que representa la fecha y hora de expiración.
Este método utiliza la clase `Date` de la API de fecha y hora de Java para crear una instancia de fecha y hora actual y luego agregar la duración de tiempo especificada en milisegundos para calcular la fecha y hora de expiración.

La clase `DateUtils` tiene un constructor privado que arroja una excepción `IllegalStateException` si se intenta crear una instancia de esta clase.
Este constructor privado se utiliza para evitar que se creen instancias de la clase `DateUtils`, ya que los métodos de esta clase son estáticos y no requieren una instancia de la clase para ser utilizados.
 */