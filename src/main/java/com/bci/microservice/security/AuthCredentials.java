package com.bci.microservice.security;

import lombok.Data;

/**
 * La clase `AuthCredentials` es un modelo de datos simple que se utiliza para almacenar las credenciales de autenticación de un usuario en una aplicación web y que se beneficia de la anotación `@Data`
 * para simplificar la creación de getters y setters y otros métodos comunes.
 */
@Data
public class AuthCredentials {
    private String email;
    private String password;
}
