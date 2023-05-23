package com.bci.microservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * The type Phone.
 */
@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(hidden = true)
    @Column(name = "id_phone")
    private Long id;
    @Schema(example = "5255766")
    private long number;
    @Schema(example = "2")
    private int cityCode;
    @Schema(example = "56")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    /**
     * Instantiates a new Phone.
     */
    public Phone() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Gets city code.
     *
     * @return the city code
     */
    public int getCityCode() {
        return cityCode;
    }

    /**
     * Sets city code.
     *
     * @param cityCode the city code
     */
    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * Gets country code.
     *
     * @return the country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets country code.
     *
     * @param countryCode the country code
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
