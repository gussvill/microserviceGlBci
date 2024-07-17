package com.bci.microservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de una aplicación de microservicios, escrita en el lenguaje de programación Java utilizando el framework Spring Boot.
 * Habilita la encriptación de propiedades sensibles como contraseñas.
 * <p>
 * La anotación @EnableEncryptableProperties permite que las propiedades encriptadas en los archivos de configuración sean desencriptadas automáticamente.
 * </p>
 *
 * <p>Autor: Gustavo Villegas</p>
 */
@Slf4j
@SpringBootApplication
@EnableEncryptableProperties
public class BciMicroserviceApplication {

    /**
     * Método que inicializa la aplicación Spring Boot y lanza el servidor web integrado de la aplicación.
     *
     * @param args argumentos de entrada
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(BciMicroserviceApplication.class, args);
            log.info("Aplicación iniciada correctamente.");
        } catch (Exception e) {
            handleStartupException(e);
        }
    }

    private static void handleStartupException(Exception e) {
        if (isSilentExitException(e)) {
            log.info("Aplicación reiniciada por DevTools.");
        } else {
            log.error("Error al iniciar la aplicación: ", e);
        }
    }

    private static boolean isSilentExitException(Throwable e) {
        // Verificar la causa de la excepción para detectar reinicio silencioso
        while (e != null) {
            if (e.getClass().getName().equals("org.springframework.boot.devtools.restart.SilentExitExceptionHandler$SilentExitException")) {
                return true;
            }
            e = e.getCause();
        }
        return false;
    }
}
