package com.globallogic.microserviceglbci.service;

import com.globallogic.microserviceglbci.domain.entity.User;
import com.globallogic.microserviceglbci.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQueryService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUserByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUsers() {
        userRepository.deleteAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
