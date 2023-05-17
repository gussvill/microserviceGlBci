package com.bci.microservice.exceptions;

/**
 * La clase `CustomException` es una clase de excepción personalizada que se utiliza para lanzar excepciones personalizadas en una aplicación.
 */
public class CustomException extends RuntimeException {

    private final String json;

    /**
     * Instantiates a new Custom exception.
     *
     * @param message the message
     * @param json    the json
     */
    public CustomException(String message, String json) {
        super(message);
        this.json = json;
    }

    /**
     * Gets json.
     *
     * @return the json
     */
    public String getJson() {
        return json;
    }
}
