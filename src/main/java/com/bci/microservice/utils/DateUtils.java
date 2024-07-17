package com.bci.microservice.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Clase de utilidad que proporciona métodos estáticos para trabajar con fechas y tiempos.
 */
public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Date Utility class");
    }

    /**
     * Devuelve una cadena formateada que representa la fecha y la hora actuales en el formato especificado.
     *
     * @param format el formato de la fecha, por ejemplo, "yyyy-MM-dd HH:mm:ss".
     * @return la fecha y hora actuales formateadas como cadena.
     */
    public static String formattedDate(String format) {
        DateTimeFormatter formatter = (format != null && !format.isEmpty())
                ? DateTimeFormatter.ofPattern(format)
                : DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Devuelve una instancia de la clase `Date` que representa la fecha y hora de expiración basada en la duración especificada.
     *
     * @param expirationDuration la duración hasta la expiración en milisegundos.
     * @return la fecha y hora de expiración.
     */
    public static Date expirationDate(long expirationDuration) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plus(Duration.ofMillis(expirationDuration));
        Instant instant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
