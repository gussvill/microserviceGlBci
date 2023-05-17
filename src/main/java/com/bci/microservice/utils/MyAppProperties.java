package com.bci.microservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * clase MyAppProperties permite a la aplicación de Spring leer y exponer propiedades de configuración específicas, como el formato de fecha y el tiempo de expiración de un token.
 */
@Component
public class MyAppProperties {

    @Value("${myapp.formatDate}")
    private String formatDate;

    @Value("${myapp.expirationTokenMs}")
    private int expirationTokenMs;

    /**
     * Gets format date.
     *
     * @return the format date
     */
    public String getFormatDate() {
        return formatDate;
    }

    /**
     * Sets format date.
     *
     * @param formatDate the format date
     */
    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    /**
     * Gets expiration token ms.
     *
     * @return the expiration token ms
     */
    public int getExpirationTokenMs() {
        return expirationTokenMs;
    }

    /**
     * Sets expiration token ms.
     *
     * @param expirationTokenMs the expiration token ms
     */
    public void setExpirationTokenMs(int expirationTokenMs) {
        this.expirationTokenMs = expirationTokenMs;
    }

    /**
     * se sobrescribe el método `toString()` para proporcionar una representación legible de la clase, que incluye los valores de las propiedades de configuración.
     * @return String
     */
    @Override
    public String toString() {
        return "MyAppProperties{" +
                "formatDate='" + formatDate + '\'' +
                ", expirationDateMs=" + expirationTokenMs +
                '}';
    }
}
