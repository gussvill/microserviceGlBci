package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.Phone;
import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.UsuarioRepository;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
import com.globallogic.microserviceglbci.response.UserResponse;
import com.globallogic.microserviceglbci.security.TokenUtils;
import com.globallogic.microserviceglbci.service.UsuarioQueryService;
import com.globallogic.microserviceglbci.utils.JavaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/user")
@Validated
public class UserQueryController {

    private static final String INVALID_USER = "El usuario ingresado ya existe, favor ingresar un usuario diferente.";
    private static final String INVALID_DATA = "Revise los datos ingresados en el contrato.";
    private static final String INVALID_PASSWORD = "Contraseña inválida.";

    private final UsuarioQueryService usuarioQueryService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserQueryController(UsuarioQueryService usuarioQueryService) {
        this.usuarioQueryService = usuarioQueryService;
    }

    @GetMapping("/{email}")
    public List<Usuario> getUserByEmail(@PathVariable(value = "email") String email) {
        List<Usuario> usuarioList = usuarioQueryService.getUserByEmail(email);
        return usuarioList;
    }

    @GetMapping("/users")
    public List<Usuario> getUsers() {
        return usuarioQueryService.getUsers();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable UUID id) {
        Optional<Usuario> userData = usuarioRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody Usuario usuario) {

        try {
            List<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());

            if (usuarioList.isEmpty()) {
                if (!usuario.getPassword().matches("^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$")) {
                    throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_PASSWORD);
                }

                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setCreated(JavaUtils.generateDate());
                usuario.setLastLogin(JavaUtils.generateDate());
                usuario.setToken(TokenUtils.createToken(usuario.getEmail(), usuario.getPassword()));
                usuario.setActive(true);
                usuario.setName(StringUtils.isNotBlank(usuario.getName()) ? usuario.getName() : "");
//                usuario.setPhones(usuario.getPhones() == null || usuario.getPhones().isEmpty() ? usuario.getPhones() : new ArrayList<>());
                Usuario _usuario = usuarioQueryService.save(usuario);

                UserResponse userResponse = new UserResponse();
                userResponse.setId(_usuario.getId());
                userResponse.setCreated(_usuario.getCreated());
                userResponse.setLastLogin(_usuario.getLastLogin());
                userResponse.setToken(_usuario.getToken());
                userResponse.setActive(_usuario.isActive());

                return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER);
            }
        } catch (Exception e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_DATA);
        }

    }

}