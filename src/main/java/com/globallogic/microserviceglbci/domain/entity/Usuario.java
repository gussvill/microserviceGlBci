package com.globallogic.microserviceglbci.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private String token;

    private boolean isActive;

    private String name;

    @Column(nullable = false)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @Column(nullable = false)
//    @Pattern(regexp = "^(?=.*\\d.*\\d)(?=.*[A-Z])(?=.*[a-z]).{8,12}$", message = "La password debe tener una mayúscula y dos números, y tener un largo mínimo de 8 y un máximo de 12 caracteres.")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private List<Phone> phones;

}
