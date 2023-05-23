package com.bci.microservice.tokens;

import com.bci.microservice.entity.RevokedToken;

public class RevokedTokenFactory {
    public static RevokedToken createRevokedToken(String token) {
        return new RevokedToken(token);
    }
}
