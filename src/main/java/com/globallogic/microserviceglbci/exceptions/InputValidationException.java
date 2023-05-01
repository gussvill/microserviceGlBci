package com.globallogic.microserviceglbci.exceptions;

public class InputValidationException extends RuntimeException {
    private final int codigo;
    private final String detail;

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