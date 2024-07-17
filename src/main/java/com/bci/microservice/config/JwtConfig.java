package com.bci.microservice.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de Spring que se utiliza para configurar la funcionalidad de generación y validación de tokens JWT (JSON Web Token).
 */
@Configuration
public class JwtConfig {

    private static final String ACCESS_TOKEN_SECRET = "5e05050c0acc4df6be982f5d41ff18da";

    /**
     * Método `jwtParser()` que devuelve un objeto `JwtParser`. Este objeto se utiliza para analizar y validar los tokens JWT.
     *
     * @return the jwt parser
     */
    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .build();
    }
}
