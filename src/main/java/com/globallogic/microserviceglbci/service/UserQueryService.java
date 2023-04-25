package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.User;
import com.globallogic.microserviceglbci.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long userId) {
        return  userRepository.findById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
