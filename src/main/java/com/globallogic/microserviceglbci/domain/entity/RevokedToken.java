package com.globallogic.microserviceglbci.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

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

    public RevokedToken(String token) {
        this.token = token;
    }

    public RevokedToken() {

    }
}
