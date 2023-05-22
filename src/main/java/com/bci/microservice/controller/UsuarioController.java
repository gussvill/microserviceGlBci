package com.bci.microservice.controller;

import com.bci.microservice.domain.service.UsuarioService;
import com.bci.microservice.exceptions.InputValidationException;
import com.bci.microservice.persistence.jpa.RevokedTokenJpaRepository;
import com.bci.microservice.persistence.jpa.UsuarioJpaRepository;
import com.bci.microservice.persistence.entity.RevokedToken;
import com.bci.microservice.persistence.entity.Usuario;
import com.bci.microservice.request.LoginUsuarioRequest;
import com.bci.microservice.response.UsuarioSignUpResponse;
import com.bci.microservice.response.UsuariosResponse;
import com.bci.microservice.security.TokenUtils;
import com.bci.microservice.utils.DateUtils;
import com.bci.microservice.utils.MyAppProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Este es el controlador que maneja solicitudes HTTP relacionadas con usuarios.
 */
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Validated
/**
 * Optional: Optional is a new class in Java 1.8 that allows to represent values that may or may not be present.
 * Date and Time API: The Date and Time API provides classes to represent dates, times and periods in Java 1.8.
 */
public class UsuarioController {

    private static final String INVALID_USER_EXISTS = "The entered user already exists, please enter a different user.";
    private static final String INVALID_USER_NOT_EXISTS = "The entered user does not exist, please register a new user.";
    private static final String INVALID_DATA = "Check the data entered in the contract.";
    private static final String INVALID_PASSWORD = "Invalid password.";
    private static final String INVALID_TOKEN = "Invalid or expired token";
    private static final String INVALID_LAST_LOGIN = "The user is not logged in yet. No Data!";

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtParser jwtParser;
    private final RevokedTokenJpaRepository revokedTokenJpaRepository;
    private final MyAppProperties myAppProperties;

    @Autowired
    public UsuarioController(UsuarioJpaRepository usuarioJpaRepository, UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtParser jwtParser, RevokedTokenJpaRepository revokedTokenJpaRepository, MyAppProperties myAppProperties) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtParser = jwtParser;
        this.revokedTokenJpaRepository = revokedTokenJpaRepository;
        this.myAppProperties = myAppProperties;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontraron los usuarios", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "404", description = "Usuarios no encontrados", content = @Content)})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsers() {
        List<Usuario> users = usuarioService.getUsers().stream().sorted(Comparator.comparing(Usuario::getEmail)).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Iniciar sesión de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Inicio de sesión exitoso", content = @Content(schema = @Schema(implementation = UsuariosResponse.class))),
            @ApiResponse(responseCode = "400", description = "Token inválido o usuario no existente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/login")
    public ResponseEntity<UsuariosResponse> login(@Parameter(description = "Usuario para iniciar sesión") @Valid @RequestBody LoginUsuarioRequest loginRequest, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Optional<Usuario> usuarioList = usuarioService.getUserByEmail(loginRequest.getEmail());

        try {
            return usuarioList.map(usuario -> {
                jwtParser.parseClaimsJws(token).getBody();
                usuarioService.updateLastLogin(loginRequest.getEmail(), DateUtils.formattedDate(myAppProperties.getFormatDate()));
                revokedTokenJpaRepository.save(new RevokedToken(token));

                Usuario updatedUser = usuarioService.getUserByEmail(loginRequest.getEmail(), null);
                String newToken = TokenUtils.createToken(loginRequest.getEmail(), loginRequest.getPassword(), myAppProperties.getExpirationTokenMs());
                usuarioService.updateToken(loginRequest.getEmail(), newToken);
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
                usuariosResponse.setPhones(updatedUser.getListPhones());

                return ResponseEntity.accepted().body(usuariosResponse);
            }).orElseThrow(() -> new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_USER_NOT_EXISTS));

        } catch (JwtException e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), INVALID_TOKEN);
        }
    }

    /**
     * Sign up response entity.
     *
     * @param usuario the usuario
     * @return the response entity
     */
    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioSignUpResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Datos inválidos ingresados o el usuario ya existe", content = @Content)})
    @PostMapping("/sign-up")
    public ResponseEntity<UsuarioSignUpResponse> signUp(@Valid @RequestBody Usuario usuario) {
        try {

            Optional<Usuario> usuarioList = usuarioService.getUserByEmail(usuario.getEmail());

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
                usuario.setPhonesAsJson(usuario.getPhones());
                Usuario usuarioSave = usuarioJpaRepository.save(usuario);

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
- `UsuariosResponse`: es una clase que se utiliza para devolver información sobre el usuariodespués del inicio de sesión.
- `UsuarioSignUpResponse`: es una clase que se utiliza para devolver información sobre el usuario después del registro.
 */
