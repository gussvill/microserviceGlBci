package com.bci.microservice.tokens;

import com.bci.microservice.entities.RevokedToken;

public class RevokedTokenFactory {
    public static RevokedToken createRevokedToken(String token) {
        return new RevokedToken(token);
    }
}
