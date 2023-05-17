package com.bci.microservice.domain.repository;

import com.bci.microservice.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * La interfaz `UsuarioRepository` es una interfaz de repositorio que se utiliza para interactuar con los datos de usuario en una aplicación.
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Find by email containing optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Usuario> findByEmailContaining(String email);

    /**
     * Find one by name optional.
     *
     * @param usuario the usuario
     * @return the optional
     */
    Optional<Usuario> findOneByName(String usuario);

    /**
     * Find by email usuario.
     *
     * @param email the email
     * @return the usuario
     */
    Usuario findByEmail(String email);

    /**
     * Update token.
     *
     * @param email    the email
     * @param newToken the new token
     */
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.token = :newToken WHERE u.email = :email")
    void updateToken(@Param("email") String email, @Param("newToken") String newToken);

    /**
     * Update last login.
     *
     * @param email the email
     * @param date  the date
     */
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.lastLogin = :date WHERE u.email = :email")
    void updateLastLogin(@Param("email") String email, @Param("date") String date);

}
