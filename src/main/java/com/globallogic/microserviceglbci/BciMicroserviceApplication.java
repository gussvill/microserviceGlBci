package com.globallogic.microserviceglbci;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@EnableEncryptableProperties // esta anotación habilita la encriptación de propiedades en la aplicación. En otras palabras, permite que las propiedades sensibles, como contraseñas
public class BciMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BciMicroserviceApplication.class, args);
    }

}

/*
    Este código es la clase principal de una aplicación de microservicios de BCI (Banco de Crédito e Inversiones), escrita en el lenguaje de programación Java utilizando el framework Spring Boot.
    En particular, el código define una clase llamada `BciMicroserviceApplication` que tiene un método `main()` que sirve como punto de entrada para la aplicación.
    Dentro del método `main()`, se llama al método `run()` de la clase `SpringApplication`, que inicializa la aplicación Spring Boot y lanza el servidor web integrado de la aplicación. El primer argumento pasado al método `run()` es la clase `BciMicroserviceApplication` y el segundo argumento es una lista de argumentos de línea de comando que se pueden utilizar para configurar la aplicación.
    En resumen, este código inicializa y lanza una aplicación de microservicios de BCI utilizando el framework Spring Boot.
 */