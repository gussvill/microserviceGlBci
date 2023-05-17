package com.bci.microservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

/**
 * La clase `RevokedToken` es una entidad que representa un token revocado en la aplicación.
 */
@Entity
@Table(name = "RevokedToken")
@Data
@AllArgsConstructor
@Builder
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    /**
     * Instantiates a new Revoked token.
     *
     * @param token the token
     */
    public RevokedToken(String token) {
        this.token = token;
    }

    /**
     * Instantiates a new Revoked token.
     */
    public RevokedToken() {

    }
}
