package com.globallogic.microserviceglbci.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private static final String ACCESS_TOKEN_SECRET = "5e05050c0acc4df6be982f5d41ff18da";

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .build();
    }
}

/*
    Esta es una clase de configuración de Spring que se utiliza para configurar la funcionalidad de generación y validación de tokens JWT (JSON Web Token).
    La anotación `@Configuration` indica que esta clase contiene métodos de configuración de Spring que se utilizan para crear y configurar beans.

    La clase define un método `jwtParser()` que devuelve un objeto `JwtParser`. Este objeto se utiliza para analizar y validar los tokens JWT.
    El método `setSigningKey()` se utiliza para establecer la clave de firma utilizada para generar los tokens JWT. En este caso, se utiliza una clave secreta específica definida en la constante `ACCESS_TOKEN_SECRET`.

    El método `getBytes()` se utiliza para convertir la cadena de clave secreta en una matriz de bytes, que es la entrada requerida para el método `hmacShaKeyFor()` de la clase `Keys`.
    Este método devuelve una clave secreta que se utiliza para firmar los tokens JWT.

    En resumen, esta clase configura la clave secreta utilizada para generar y validar los tokens JWT utilizando la biblioteca `jjwt` de Java.
 */