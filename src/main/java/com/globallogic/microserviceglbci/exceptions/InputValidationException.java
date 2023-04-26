package com.globallogic.microserviceglbci.exceptions;

public class InputValidationException extends RuntimeException {
    private int codigo;
    private String detail;

    public InputValidationException(int codigo, String detail) {
        this.codigo = codigo;
        this.detail = detail;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDetail() {
        return detail;
    }
}