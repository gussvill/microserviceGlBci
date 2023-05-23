package com.bci.microservice.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * La interfaz `UsuarioQueryService` define un conjunto de métodos que se pueden utilizar para realizar consultas y actualizaciones relacionadas con los usuarios de una aplicación.
 * La interfaz no proporciona una implementación concreta de estos métodos, sino que define la firma de los mismos.
 */
// define el contrato que deben cumplir las implementacion.
public interface IUsuario {
    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    Optional<com.bci.microservice.entity.Usuario> getUserByEmail(String email);

    /**
     * Gets user by email.
     *
     * @param email     the email
     * @param nullValue the null value
     * @return the user by email
     */
    com.bci.microservice.entity.Usuario getUserByEmail(String email, String nullValue);

    /**
     * Gets users.
     *
     * @return the users
     */
    List<com.bci.microservice.entity.Usuario> getUsers();

    /**
     * Save usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    com.bci.microservice.entity.Usuario save(com.bci.microservice.entity.Usuario usuario);

    /**
     * Update token.
     *
     * @param email    the email
     * @param newToken the new token
     */
    void updateToken(String email, String newToken);

    /**
     * Update last login.
     *
     * @param email the email
     * @param date  the date
     */
    void updateLastLogin(String email, String date);

    String passwordEncoder(String userPassword);
}