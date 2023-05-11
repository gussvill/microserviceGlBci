package com.globallogic.microserviceglbci.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * clase de utilidad que proporciona dos métodos estáticos para trabajar con fechas y tiempos
 */

public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Date Utility class");
    }

    /**
     * Formatted date string. El primer método, `formattedDate()`, toma una cadena que especifica el formato de fecha y devuelve una cadena formateada que representa la fecha y la hora actuales en el formato especificado.
     *
     * @param format the format
     * @return the string
     */
    public static String formattedDate(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Expiration date date. El segundo método, `expirationDate()`, toma una duración de tiempo en milisegundos como argumento y devuelve una instancia de la clase `Date` que representa la fecha y hora de expiración.
     *
     * @param expirationDateMs the expiration date ms
     * @return the date
     */
    public static Date expirationDate(int expirationDateMs) {
        return new Date(new Date().getTime() + expirationDateMs);
    }

}