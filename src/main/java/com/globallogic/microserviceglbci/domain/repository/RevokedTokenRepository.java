package com.globallogic.microserviceglbci.domain.repository;

import com.globallogic.microserviceglbci.domain.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {

    boolean existsByToken(String token);

}

