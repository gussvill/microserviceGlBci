package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.RevokedToken;
import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.RevokedTokenRepository;
import com.globallogic.microserviceglbci.domain.repository.UsuarioRepository;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
import com.globallogic.microserviceglbci.response.UserResponse;
import com.globallogic.microserviceglbci.security.TokenUtils;
import com.globallogic.microserviceglbci.service.UsuarioQueryService;
import com.globallogic.microserviceglbci.utils.JavaUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Validated
/**
 * Optional: Optional is a new class in Java 1.8 that allows to represent values that may or may not be present.
 * Date and Time API: The Date and Time API provides classes to represent dates, times and periods in Java 1.8.
 */
public class UserQueryController {

    private static final String INVALID_USER_EXISTS = "The entered user already exists, please enter a different user.";
    private static final String INVALID_USER_NOT_EXISTS = "The entered user does not exist, please register a new user.";
    private static final String INVALID_DATA = "Check the data entered in the contract.";
    private static final String INVALID_PASSWORD = "Invalid password.";
    private static final String INVALID_TOKEN = "Invalid or expired token";
    private static final String INVALID_LAST_LOGIN = "The user is not logged in yet. No Data!";

    private final UsuarioQueryService usuarioQueryService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtParser jwtParser;

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    public UserQueryController(UsuarioQueryService usuarioQueryService) {
        this.usuarioQueryService = usuarioQueryService;
    }

    @GetMapping("/users")
    public List<Usuario> getUsers() {
        return usuarioQueryService.getUsers();
    }

    @GetMapping("/login")
    public Usuario getUserByEmail(@Valid @RequestBody Usuario usuario, @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Optional<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());


        try {
            if (usuarioList.isPresent()) {
                jwtParser.parseClaimsJws(token).getBody();
                usuarioQueryService.updateLastLogin(usuario.getEmail(), JavaUtils.formattedDate());
                revokedTokenRepository.save(new RevokedToken(token));
                usuarioQueryService.updateToken(usuario.getEmail(), TokenUtils.createToken(usuario.getEmail(), usuario.getPassword()));

                Usuario usuarioActualizado = usuarioQueryService.getUserByEmail(usuario.getEmail(), null);

                return usuarioActualizado;

            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_NOT_EXISTS);
            }

        } catch (JwtException e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_TOKEN);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody Usuario usuario) {

        try {

            Optional<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());

            if (!usuarioList.isPresent()) {
                if (!usuario.getPassword().matches("^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$")) {
                    throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_PASSWORD);
                }

                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setCreated(JavaUtils.formattedDate());
                usuario.setLastLogin(INVALID_LAST_LOGIN);
                usuario.setToken(TokenUtils.createToken(usuario.getEmail(), usuario.getPassword()));
                usuario.setActive(true);
                usuario.setName(StringUtils.isNotBlank(usuario.getName()) ? usuario.getName() : "");
                Usuario _usuario = usuarioQueryService.save(usuario);

                UserResponse userResponse = new UserResponse();
                userResponse.setId(_usuario.getId());
                userResponse.setCreated(_usuario.getCreated());
                userResponse.setLastLogin(_usuario.getLastLogin());
                userResponse.setToken(_usuario.getToken());
                userResponse.setActive(_usuario.isActive());

                return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_EXISTS);
            }
        } catch (Exception e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_DATA);
        }

    }

}