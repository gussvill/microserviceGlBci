package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.domain.entity.RevokedToken;
import com.globallogic.microserviceglbci.domain.entity.Usuario;
import com.globallogic.microserviceglbci.domain.repository.IRevokedTokenRepository;
import com.globallogic.microserviceglbci.domain.repository.IUsuarioRepository;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
import com.globallogic.microserviceglbci.response.UsuarioSignUpResponse;
import com.globallogic.microserviceglbci.response.UsuariosResponse;
import com.globallogic.microserviceglbci.security.TokenUtils;
import com.globallogic.microserviceglbci.service.IUsuarioQueryService;
import com.globallogic.microserviceglbci.service.UsuarioQueryServiceImpl;
import com.globallogic.microserviceglbci.utils.DateUtils;
import com.globallogic.microserviceglbci.utils.MyAppProperties;
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
public class UserController {

    private static final String INVALID_USER_EXISTS = "The entered user already exists, please enter a different user.";
    private static final String INVALID_USER_NOT_EXISTS = "The entered user does not exist, please register a new user.";
    private static final String INVALID_DATA = "Check the data entered in the contract.";
    private static final String INVALID_PASSWORD = "Invalid password.";
    private static final String INVALID_TOKEN = "Invalid or expired token";
    private static final String INVALID_LAST_LOGIN = "The user is not logged in yet. No Data!";

    private final IUsuarioQueryService usuarioQueryService;

    @Autowired
    IUsuarioRepository IUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtParser jwtParser;

    @Autowired
    private IRevokedTokenRepository IRevokedTokenRepository;

    @Autowired
    private MyAppProperties myAppProperties;

    public UserController(UsuarioQueryServiceImpl usuarioQueryService) {
        this.usuarioQueryService = usuarioQueryService;
    }

    @GetMapping("/users")
    public List<Usuario> getUsers() {
        return IUsuarioRepository.findAll();
    }

    @GetMapping("/login")
    public ResponseEntity<UsuariosResponse> login(@Valid @RequestBody Usuario usuario, @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Optional<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());

        try {
            if (usuarioList.isPresent()) {
                jwtParser.parseClaimsJws(token).getBody();
                usuarioQueryService.updateLastLogin(usuario.getEmail(), DateUtils.formattedDate(myAppProperties.getFormatDate()));
                IRevokedTokenRepository.save(new RevokedToken(token));

                Usuario updatedUser = usuarioQueryService.getUserByEmail(usuario.getEmail(), null);
                String newToken = TokenUtils.createToken(usuario.getEmail(), usuario.getPassword(), myAppProperties.getExpirationTokenMs());
                usuarioQueryService.updateToken(usuario.getEmail(), newToken);
                updatedUser.setToken(newToken);

                UsuariosResponse usuariosResponse = new UsuariosResponse();
                usuariosResponse.setId(updatedUser.getId());
                usuariosResponse.setCreated(updatedUser.getCreated());
                usuariosResponse.setLastLogin(updatedUser.getLastLogin());
                usuariosResponse.setToken(updatedUser.getToken());
                usuariosResponse.setActive(updatedUser.isActive());
                usuariosResponse.setName(updatedUser.getName());
                usuariosResponse.setEmail(updatedUser.getEmail());
                usuariosResponse.setPassword(updatedUser.getPassword());
                usuariosResponse.setPhones(updatedUser.getPhones());

                return new ResponseEntity<>(usuariosResponse, HttpStatus.ACCEPTED);

            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_NOT_EXISTS);
            }

        } catch (JwtException e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_TOKEN);
        }

    }

    @PostMapping("/sign-up")
    public ResponseEntity<UsuarioSignUpResponse> signUp(@Valid @RequestBody Usuario usuario) {

        try {

            Optional<Usuario> usuarioList = usuarioQueryService.getUserByEmail(usuario.getEmail());

            if (!usuarioList.isPresent()) {
                if (!usuario.getPassword().matches("^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$")) {
                    throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_PASSWORD);
                }

                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setCreated(DateUtils.formattedDate(myAppProperties.getFormatDate()));
                usuario.setLastLogin(INVALID_LAST_LOGIN);
                usuario.setToken(TokenUtils.createToken(usuario.getEmail(), usuario.getPassword(), myAppProperties.getExpirationTokenMs()));
                usuario.setActive(true);
                usuario.setName(StringUtils.isNotBlank(usuario.getName()) ? usuario.getName() : "");
                usuario.setPhones(usuario.getPhones());
                Usuario usuarioSave = usuarioQueryService.save(usuario);

                UsuarioSignUpResponse usuarioSignUpResponse = new UsuarioSignUpResponse();
                usuarioSignUpResponse.setId(usuarioSave.getId());
                usuarioSignUpResponse.setCreated(usuarioSave.getCreated());
                usuarioSignUpResponse.setLastLogin(usuarioSave.getLastLogin());
                usuarioSignUpResponse.setToken(usuarioSave.getToken());
                usuarioSignUpResponse.setActive(usuarioSave.isActive());

                return new ResponseEntity<>(usuarioSignUpResponse, HttpStatus.CREATED);
            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_EXISTS);
            }
        } catch (Exception e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_DATA);
        }

    }

}

/*
Este es un controlador de Spring que maneja solicitudes HTTP relacionadas con usuarios en una aplicación web. Veamos algunas de las anotaciones y métodos utilizados en el controlador:

- `@CrossOrigin(origins = "http://localhost:8081")`: permite solicitudes HTTP de origen cruzado (CORS) desde el origen especificado (en este caso, http://localhost:8081).
- `@RestController`: indica que esta clase es un controlador de Spring que maneja solicitudes HTTP y devuelve objetos JSON.
- `@Validated`: se utiliza para validar los objetos de entrada de las solicitudes HTTP.
- `@GetMapping("/users")`: mapea la solicitud GET a la ruta "/users" y devuelve una lista de usuarios.
- `@GetMapping("/login")`: mapea la solicitud GET a la ruta "/login" y maneja el inicio de sesión del usuario.
- `@PostMapping("/sign-up")`: mapea la solicitud POST a la ruta "/sign-up" y maneja el registro de un nuevo usuario.
- `@Autowired`: se utiliza para inyectar dependencias de Spring en la clase (por ejemplo, `UsuarioRepository`, `PasswordEncoder`, etc.).
- `UsuarioQueryServiceImpl`: es una implementación de servicio que se utiliza para realizar consultas de usuario en la base de datos.
- `UsuarioRepository`: es una interfaz que se utiliza para acceder a la base de datos de usuarios.
- `JwtParser`: se utiliza para analizar y validar tokens JWT (JSON Web Token).
- `RevokedTokenRepository`: es una interfaz que se utiliza para acceder a la base de datos de tokens revocados.
- `MyAppProperties`: es una clase que contiene propiedades de configuración de la aplicación (por ejemplo, formato de fecha, tiempo de expiración del token, etc.).
- `Usuario`: es una clase de modelo que representa a un usuario en la aplicación.
- `UsuariosResponse`: es una clase que se utiliza para devolver información sobre el usuario (por ejemplo, id, nombre, token, etc.) después del inicio de sesión.
- `UsuarioSignUpResponse`: es una clase que se utiliza para devolver información sobre el usuario (por ejemplo, id, fecha de creación, token, etc.) después del registro.

En resumen, este controlador maneja solicitudes HTTP relacionadas con el inicio de sesión y el registro de usuarios en una aplicación web utilizando Spring.
 */