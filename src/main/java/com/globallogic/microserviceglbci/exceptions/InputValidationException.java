package com.globallogic.microserviceglbci.exceptions;

/**
 * La clase `InputValidationException` es una clase de excepción que se utiliza para representar errores de validación de datos de entrada en una aplicación Java.
 */
public class InputValidationException extends RuntimeException {
    private final int codigo;
    private final String detail;

    /**
     * Instantiates a new Input validation exception.
     *
     * @param codigo the codigo
     * @param detail the detail
     */
    public InputValidationException(int codigo, String detail) {
        this.codigo = codigo;
        this.detail = detail;
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
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }
}
