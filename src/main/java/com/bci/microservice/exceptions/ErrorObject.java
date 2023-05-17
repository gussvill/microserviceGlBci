package com.bci.microservice.exceptions;

import java.time.LocalDateTime;

/**
 * La clase `ErrorObject` es una clase que encapsula información sobre un error en una aplicación.
 */
public class ErrorObject {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;

    /**
     * Instantiates a new Error object.
     *
     * @param timestamp the timestamp
     * @param codigo    the codigo
     * @param detail    the detail
     */
    public ErrorObject(LocalDateTime timestamp, int codigo, String detail) {
        this.timestamp = timestamp;
        this.codigo = codigo;
        this.detail = detail;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets codigo.
     *
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets detail.
     *
     * @param detail the detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ErrorObject{" +
                "timestamp=" + timestamp +
                ", codigo=" + codigo +
                ", detail='" + detail + '\'' +
                '}';
    }

}