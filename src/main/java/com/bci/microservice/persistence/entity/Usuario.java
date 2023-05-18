package com.bci.microservice.persistence.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

/**
 * La clase `Usuario` es una entidad que representa a un usuario en la aplicación.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(hidden = true)
    @Column(name = "id_usuario")
    private UUID id;

    @Schema(hidden = true)
    private String created;

    @Schema(hidden = true)
    private String lastLogin;

    @Schema(hidden = true)
    private String token;

    @Schema(hidden = true)
    private boolean isActive;

    @Schema(example = "Gustavo Villegas")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no es válido")
    @Schema(example = "gussvill@gmail.com")
    private String email;

    @Schema(example = "Guss2020")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Schema(example = "[\n" +
            "        {\n" +
            "            \"number\": 5255766,\n" +
            "            \"cityCode\": 2,\n" +
            "            \"countryCode\": \"56\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"number\": 93099285,\n" +
            "            \"cityCode\": 9,\n" +
            "            \"countryCode\": \"56\"\n" +
            "        }\n" +
            "    ]")
    private Set<Phone> phones;

    private String listPhones;

    public String getListPhones() {
        return listPhones;
    }

    public void setListPhones(String listPhones) {
        this.listPhones = listPhones;
    }

    public void setPhonesAsJson(Set<Phone> phones) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonPhones = objectMapper.writeValueAsString(phones);
            this.listPhones = jsonPhones;
        } catch (JsonProcessingException e) {
            // Manejar la excepción si ocurre algún error durante la conversión
            e.printStackTrace();
        }
    }


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
    @Schema(hidden = true)
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
    public Set<Phone> getPhones() {
        return phones;
    }

    /**
     * Sets phones.
     *
     * @param phones the phones
     */
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Usuario(UUID id, String created, String lastLogin, String token, boolean isActive, String name, String email, String password) {
        this.id = id;
        this.created = created;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
