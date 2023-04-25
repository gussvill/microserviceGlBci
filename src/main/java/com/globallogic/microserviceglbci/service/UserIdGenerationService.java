package com.globallogic.microserviceglbci.service;

import org.springframework.stereotype.Service;

@Service
public class UserIdGenerationService {
    public Long newUserId() {
        return System.nanoTime();
    }
}
