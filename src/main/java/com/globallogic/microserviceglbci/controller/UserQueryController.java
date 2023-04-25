package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.User;
import com.globallogic.microserviceglbci.domain.repository.UserRepository;
import com.globallogic.microserviceglbci.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/user")
public class UserQueryController {

    private final UserQueryService userQueryService;

    @Autowired
    UserRepository userRepository;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") Long userId) {
        Optional<User> userOpt = userQueryService.getUserById(userId);
        return userOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public List<User> getAccounts() {
        return userQueryService.getUsers();
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllTutorials(@RequestParam(required = false) String name) {
        try {
            List<User> users = new ArrayList<User>();

            if (name == null)
                userRepository.findAll().forEach(users::add);
            else
                userRepository.findByNameContaining(name).forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User _user = userRepository.save(new User(user.getName(), user.getEmail(), user.getPassword(), user.getPhones()));
            return new ResponseEntity<>(_user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());
            _user.setPhones(user.getPhones());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userQueryService.deleteUsers();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}