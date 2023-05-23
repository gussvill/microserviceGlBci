package com.bci.microservice.enums;

public enum ErrorMessages {
    INVALID_USER_EXISTS("The entered user already exists, please enter a different user."),
    INVALID_USER_NOT_EXISTS("The entered user does not exist, please register a new user."),
    INVALID_DATA("Check the data entered in the contract."),
    INVALID_PASSWORD("Invalid password."),
    INVALID_TOKEN("Invalid or expired token."),
    INVALID_LAST_LOGIN("The user is not logged in yet. No Data!");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
