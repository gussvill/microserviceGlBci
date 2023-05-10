package com.globallogic.microserviceglbci.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long number;
    private int cityCode;
    private String countryCode;

    public Phone() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}

/*
    La anotación `@Entity` indica que la clase `Phone` es una entidad de base de datos que puede ser mapeada a una tabla en una base de datos relacional. En otras palabras,
    `@Entity` es una anotación de Hibernate que se utiliza para indicar que la clase debe ser persistida en la base de datos.

    Los campos de la clase `Phone` (como `id`, `number`, `cityCode`, `countryCode`) corresponden a columnas en la tabla de base de datos que representa la entidad `Phone`.
    Las anotaciones `@Id` y `@GeneratedValue` se utilizan para indicar que el campo `id` es la clave principal de la tabla y que se genera automáticamente un valor para la clave principal, respectivamente.

    Además, los métodos `getId()`, `setId()`, `getNumber()`, `setNumber()`, `getCityCode()`, `setCityCode()`, `getCountryCode()`, y `setCountryCode()`
    son métodos de acceso (getters y setters) que se utilizan para acceder a los campos de la entidad `Phone`.
 */