package com.globallogic.microserviceglbci.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@Embeddable
public class Phones {

    @Column(name = "phones")
    private long number;
    private int cityCode;
    private String countryCode;

    public Phones() {
    }

}