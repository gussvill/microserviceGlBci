package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.Phones;
import com.globallogic.microserviceglbci.domain.entity.User;
import com.globallogic.microserviceglbci.domain.repository.UserRepository;
import com.globallogic.microserviceglbci.utils.JavaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MockUserGenerateService {

    @Autowired
    private UserRepository userRepository;

    public void generateUsers() {

        User account1 = User.builder()
                .created(JavaUtils.generateDate())
                .lastLogin("test2")
                .token("test3")
                .isActive(true)
                .name("Julio Gonzalez")
                .email("julio@testssw.cl")
                .password("testPass1")
                .phones(new Phones(87650009, 7, "25"))
                .build();
        userRepository.save(account1);

        User account2 = User.builder()
                .created(JavaUtils.generateDate())
                .lastLogin("test3")
                .token("test4")
                .isActive(false)
                .name("Roger Federer")
                .email("roger.federer@gmail.com")
                .password("testPass2")
                .phones(new Phones(52555766, 2, "56"))
                .build();

        userRepository.save(account2);
    }
}
