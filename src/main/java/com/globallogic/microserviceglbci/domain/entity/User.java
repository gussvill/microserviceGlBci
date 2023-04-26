package com.globallogic.microserviceglbci.domain.entity;

import com.globallogic.microserviceglbci.utils.JavaUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
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

    private String email;

    private String password;

//    @Embedded
//    private Phones phones;

    public User(String created, String lastLogin, String name, String email, String password) {
        this.created = created;
        this.lastLogin = lastLogin;
        this.name = name;
        this.email = email;
        this.password = password;
//        this.phones = phones;
    }
}
