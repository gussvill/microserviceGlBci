package com.globallogic.microserviceglbci.exceptions;

import java.time.LocalDateTime;

public class ErrorObject {
    private LocalDateTime timestamp;
    private int codigo;
    private String detail;

    public ErrorObject(LocalDateTime timestamp, int codigo, String detail) {
        this.timestamp = timestamp;
        this.codigo = codigo;
        this.detail = detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDetail() {
        return detail;
    }

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