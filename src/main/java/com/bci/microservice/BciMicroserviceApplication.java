package com.bci.microservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de una aplicación de microservicios, escrita en el lenguaje de programación Java utilizando el framework Spring Boot.
 *
 * @author Gustavo Villegas
 */
@Slf4j
@SpringBootApplication
@EnableEncryptableProperties
// esta anotación habilita la encriptación de propiedades en la aplicación. En otras palabras, permite que las propiedades sensibles, como contraseñas
public class BciMicroserviceApplication {

    /**
     * Metodo que inicializa la aplicación Spring Boot y lanza el servidor web integrado de la aplicación
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BciMicroserviceApplication.class, args);
    }

}