package com.bci.microservice.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

/**
 * La clase `Usuario` es una entidad que representa a un usuario en la aplicación.
 */
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

    /**
     * Instantiates a new Usuario.
     *
     * @param email    the email
     * @param password the password
     */
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Instantiates a new Usuario.
     */
    public Usuario() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Gets last login.
     *
     * @return the last login
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets phones.
     *
     * @return the phones
     */
    public List<Phone> getPhones() {
        return phones;
    }

    /**
     * Sets phones.
     *
     * @param phones the phones
     */
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }
}
