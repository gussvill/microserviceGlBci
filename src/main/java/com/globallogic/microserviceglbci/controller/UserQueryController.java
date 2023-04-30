package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.Usuario;
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
 * Optional: Optional es una nueva clase en Java 1.8 que permite representar valores que pueden o no estar presentes
 * Fecha y hora API: La API de fecha y hora proporciona clases para representar fechas, tiempos y períodos en Java 1.8.
 */
public class UserQueryController {

    private static final String INVALID_USER = "El usuario ingresado ya existe, favor ingresar un usuario diferente.";
    private static final String INVALID_DATA = "Revise los datos ingresados en el contrato.";
    private static final String INVALID_PASSWORD = "Contraseña inválida.";
    private static final String INVALID_TOKEN = "Invalid or expired token";

    private final UsuarioQueryService usuarioQueryService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtParser jwtParser;

    public UserQueryController(UsuarioQueryService usuarioQueryService) {
        this.usuarioQueryService = usuarioQueryService;
    }

    @GetMapping("/users")
    public List<Usuario> getUsers() {
        return usuarioQueryService.getUsers();
    }

    @GetMapping("/login")
    public Optional<Usuario> getUserByEmail(@Valid @RequestBody Usuario usuario, @RequestHeader("Authorization") String authHeader) {
        Optional<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        try {
            jwtParser.parseClaimsJws(token).getBody();
            return usuarioList;
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
                usuario.setLastLogin(JavaUtils.formattedDate());
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
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER);
            }
        } catch (Exception e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_DATA);
        }

    }

}