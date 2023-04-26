package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.UsuarioRepository;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
import com.globallogic.microserviceglbci.service.UsuarioQueryService;
import com.globallogic.microserviceglbci.utils.JavaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/user")
public class UserQueryController {

    private static final String INVALID_USER = "El usuario ingresado ya existe, favor ingresar un usuario diferente.";

    private final UsuarioQueryService usuarioQueryService;

    @Autowired
    UsuarioRepository usuarioRepository;

    public UserQueryController(UsuarioQueryService usuarioQueryService) {
        this.usuarioQueryService = usuarioQueryService;
    }

    @GetMapping("/{name}")
    public List<Usuario> getUserByName(@PathVariable(value = "name") String name) {
        List<Usuario> usuarioList = usuarioQueryService.getUserByName(name);
        return usuarioList;
    }

    @GetMapping("/list")
    public List<Usuario> getAccounts() {
        return usuarioQueryService.getUsers();
    }


    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getAllTutorials(@RequestParam(required = false) String name) {
        try {
            List<Usuario> usuarios = new ArrayList<Usuario>();

            if (name == null)
                usuarioRepository.findAll().forEach(usuarios::add);
            else
                usuarioRepository.findByNameContaining(name).forEach(usuarios::add);

            if (usuarios.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("id") long id) {
        Optional<Usuario> userData = usuarioRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {

        List<Usuario> usuarioList = usuarioQueryService.getUserByName(usuario.getName());

        if (usuarioList.isEmpty()) {
            Usuario _usuario = usuarioQueryService.save(new Usuario(JavaUtils.generateDate(), JavaUtils.generateDate(), usuario.getName(), usuario.getEmail(), new BCryptPasswordEncoder().encode(usuario.getPassword())));
            return new ResponseEntity<>(_usuario, HttpStatus.CREATED);
        } else {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER);
        }

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable("id") long id, @RequestBody Usuario usuario) {
        Optional<Usuario> userData = usuarioRepository.findById(id);

        if (userData.isPresent()) {
            Usuario _usuario = userData.get();
            _usuario.setName(usuario.getName());
            _usuario.setEmail(usuario.getEmail());
            _usuario.setPassword(usuario.getPassword());
//            _user.setPhones(user.getPhones());
            return new ResponseEntity<>(usuarioRepository.save(_usuario), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            usuarioRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}