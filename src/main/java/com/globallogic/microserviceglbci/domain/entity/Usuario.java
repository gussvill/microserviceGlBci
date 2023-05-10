package com.globallogic.microserviceglbci.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String created;

    private String lastLogin;

    private String token;

    private boolean isActive;

    private String name;

    @Column(nullable = false)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private List<Phone> phones;

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}

/*
    La anotación `@Entity` se utiliza en Java para indicar que una clase es una entidad que se puede almacenar en una base de datos relacional.
    En el contexto de la programación de aplicaciones web, una entidad representa un objeto de negocio que se puede guardar, actualizar o eliminar en la base de datos.

    La clase `Usuario` es una entidad que representa a un usuario en la aplicación. Tiene varias propiedades que representan los datos del usuario, como `id`, `created`, `lastLogin`, `token`, `isActive`, `name`, `email`, `password`
     y `phones`.

    La propiedad `id` se utiliza como identificador único del usuario en la base de datos. La propiedad `created` representa la fecha y hora en que se creó el usuario en la base de datos. La propiedad `lastLogin`
    representa la fecha y hora en que el usuario inició sesión por última vez en la aplicación. La propiedad `token` representa el token de autenticación del usuario en la aplicación. La propiedad `isActive`
    es un indicador booleano que indica si el usuario está activo o no en la aplicación. La propiedad `name` representa el nombre del usuario. La propiedad `email` representa la dirección de correo electrónico del usuario.
    La propiedad `password` representa la contraseña del usuario. Finalmente, la propiedad `phones` es una lista de objetos `Phone` que representa los números de teléfono asociados con el usuario.

    La clase `Usuario` también tiene varios métodos getters y setters para acceder y modificar los valores de sus propiedades. También tiene dos constructores: uno sin argumentos y
    otro que toma como argumentos una cadena de texto `email` y una cadena de texto `password`. Además, la clase tiene la anotación `@Id`, que indica que la propiedad `id` es el identificador único de la entidad en la base de datos,
    y la anotación `@GeneratedValue`, que especifica cómo se generará el valor de la propiedad `id`.

    En resumen, la clase `Usuario` es una entidad que representa a un usuario en la aplicación. Utiliza la anotación `@Entity` para indicar que es una entidad de base de datos,
    y tiene varias propiedades que representan los datos del usuario. También tiene métodos getters y setters para acceder y modificar los valores de sus propiedades, y constructores para crear instancias de la clase.
 */