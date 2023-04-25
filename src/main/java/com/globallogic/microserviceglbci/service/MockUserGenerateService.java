package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.User;
import com.globallogic.microserviceglbci.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MockUserGenerateService {

    @Autowired
    private UserRepository userRepository;

    public void generateUsers() {
        User account1 = User.builder()
                .balance(new BigDecimal(100))
                .name("Berkay account")
                .build();
        userRepository.save(account1);

        User account2 = User.builder()
                .balance(new BigDecimal(100))
                .name("Test account")
                .build();

        userRepository.save(account2);
    }
}
