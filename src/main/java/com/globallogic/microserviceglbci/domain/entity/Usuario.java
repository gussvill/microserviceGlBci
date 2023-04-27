package com.globallogic.microserviceglbci.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String created;

    private String lastLogin;

    private String token;

    private Boolean isActive;

    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

//    @Embedded
//    private Phones phones;

    public Usuario(String created, String lastLogin, String name, String email, String password) {
        this.created = created;
        this.lastLogin = lastLogin;
        this.isActive = false;
        this.name = name != null ? name : "";;
        this.email = email;
        this.password = password;
//        this.phones = phones;
    }
}
