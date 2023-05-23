package com.bci.microservice.services;

import com.bci.microservice.entities.RevokedToken;
import com.bci.microservice.interfaces.repositories.IRevokedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevokedTokenService {

    private final IRevokedTokenRepository revokedTokenJpaRepository;

    @Autowired
    public RevokedTokenService(IRevokedTokenRepository revokedTokenJpaRepository) {
        this.revokedTokenJpaRepository = revokedTokenJpaRepository;
    }

    public boolean existsByToken(String token) {
        return revokedTokenJpaRepository.existsByToken(token);
    }

    public void save(RevokedToken revokedToken) {
        revokedTokenJpaRepository.save(revokedToken);
    }

}
