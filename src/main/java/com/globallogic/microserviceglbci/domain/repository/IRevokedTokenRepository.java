package com.globallogic.microserviceglbci.domain.repository;

import com.globallogic.microserviceglbci.domain.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * La interfaz `RevokedTokenRepository` es una interfaz de repositorio que se utiliza para interactuar con los tokens revocados en una aplicación.
 */
public interface IRevokedTokenRepository extends JpaRepository<RevokedToken, Long> {

    /**
     * Exists by token boolean.
     *
     * @param token the token
     * @return the boolean
     */
    boolean existsByToken(String token);

}
