package com.globallogic.microserviceglbci.exceptions;

public class CustomException extends RuntimeException {

    private final String json;

    public CustomException(String message, String json) {
        super(message);
        this.json = json;
    }

    public String getJson() {
        return json;
    }
}
