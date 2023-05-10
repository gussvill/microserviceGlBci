package com.globallogic.microserviceglbci.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//  se utiliza para cargar y acceder a las propiedades de configuración de la aplicación.
@Component
public class MyAppProperties {

    @Value("${myapp.formatDate}")
    private String formatDate;

    @Value("${myapp.expirationTokenMs}")
    private int expirationTokenMs;

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public int getExpirationTokenMs() {
        return expirationTokenMs;
    }

    public void setExpirationTokenMs(int expirationTokenMs) {
        this.expirationTokenMs = expirationTokenMs;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                "formatDate='" + formatDate + '\'' +
                ", expirationDateMs=" + expirationTokenMs +
                '}';
    }
}
/*
    Este código define una clase llamada `MyAppProperties`, que se utiliza para cargar y exponer propiedades de configuración en una aplicación de Spring.
    La anotación `@Component` indica que esta clase es un componente de Spring y se puede inyectar en otras partes de la aplicación.
    Las propiedades de configuración se definen mediante las anotaciones `@Value`, que leen los valores de una fuente de configuración, como un archivo `.properties` o `.yml`.
    En este caso, se definen dos propiedades: `formatDate` y `expirationTokenMs`, que se almacenan en las variables de instancia correspondientes.
    La clase también tiene métodos getter y setter para acceder a estas propiedades desde otras partes de la aplicación.
    Además, se sobrescribe el método `toString()` para proporcionar una representación legible de la clase, que incluye los valores de las propiedades de configuración.
    En resumen, esta clase permite a la aplicación de Spring leer y exponer propiedades de configuración específicas, como el formato de fecha y el tiempo de expiración de un token.
 */