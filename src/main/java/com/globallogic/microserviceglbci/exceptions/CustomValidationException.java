package com.globallogic.microserviceglbci.exceptions;

public class CustomValidationException extends RuntimeException {

    private final String json;

    public CustomValidationException(String message, String json) {
        super(message);
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}
