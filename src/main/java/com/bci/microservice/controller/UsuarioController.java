package com.bci.microservice.controller;

import com.bci.microservice.entity.RevokedToken;
import com.bci.microservice.entity.Usuario;
import com.bci.microservice.enums.ErrorMessages;
import com.bci.microservice.exceptions.InputValidationException;
import com.bci.microservice.repository.RevokedTokenJpaRepository;
import com.bci.microservice.repository.UsuarioJpaRepository;
import com.bci.microservice.request.LoginUsuarioRequest;
import com.bci.microservice.response.UsuarioResponse;
import com.bci.microservice.response.UsuarioSignUpResponse;
import com.bci.microservice.security.TokenUtils;
import com.bci.microservice.service.UsuarioService;
import com.bci.microservice.tokens.RevokedTokenFactory;
import com.bci.microservice.utils.DateUtils;
import com.bci.microservice.utils.TokenProperties;
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

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtParser jwtParser;
    private final RevokedTokenJpaRepository revokedTokenJpaRepository;
    private final TokenProperties tokenProperties;

    @Autowired
    public UsuarioController(UsuarioJpaRepository usuarioJpaRepository, UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtParser jwtParser, RevokedTokenJpaRepository revokedTokenJpaRepository, TokenProperties tokenProperties) {
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtParser = jwtParser;
        this.revokedTokenJpaRepository = revokedTokenJpaRepository;
        this.tokenProperties = tokenProperties;
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
            @ApiResponse(responseCode = "202", description = "Inicio de sesión exitoso", content = @Content(schema = @Schema(implementation = UsuarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Token inválido o usuario no existente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/login")
    public ResponseEntity<UsuarioResponse> login(@Parameter(description = "Usuario para iniciar sesión") @Valid @RequestBody LoginUsuarioRequest loginRequest, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Optional<Usuario> usuarioList = usuarioService.getUserByEmail(loginRequest.getEmail());

        try {
            return usuarioList.map(usuario -> {
                jwtParser.parseClaimsJws(token).getBody();
                usuarioService.updateLastLogin(loginRequest.getEmail(), DateUtils.formattedDate(tokenProperties.getFormatDate()));
                revokedTokenJpaRepository.save(RevokedTokenFactory.createRevokedToken(token));

                Usuario updatedUser = usuarioService.getUserByEmail(loginRequest.getEmail(), null);
                String newToken = TokenUtils.createToken(loginRequest.getEmail(), loginRequest.getPassword(), tokenProperties.getExpirationTokenMs());
                usuarioService.updateToken(loginRequest.getEmail(), newToken);
                updatedUser.setToken(newToken);
                UsuarioResponse usuarioResponse = UsuarioResponse.convertToUsuarioResponse(updatedUser);

                return ResponseEntity.accepted().body(usuarioResponse);
            }).orElseThrow(() -> new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_USER_NOT_EXISTS.getMessage()));

        } catch (JwtException e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_TOKEN.getMessage());
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
                    throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_PASSWORD.getMessage());
                }

                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuario.setCreated(DateUtils.formattedDate(tokenProperties.getFormatDate()));
                usuario.setLastLogin(ErrorMessages.INVALID_LAST_LOGIN.getMessage());
                usuario.setToken(TokenUtils.createToken(usuario.getEmail(), usuario.getPassword(), tokenProperties.getExpirationTokenMs()));
                usuario.setActive(true);
                usuario.setName(StringUtils.isNotBlank(usuario.getName()) ? usuario.getName() : "");
                usuario.setPhonesAsJson(usuario.getPhones());
                Usuario usuarioSave = usuarioJpaRepository.save(usuario);
                UsuarioSignUpResponse usuarioSignUpResponse = UsuarioSignUpResponse.convertToUsuarioSignUpResponse(usuarioSave);

                return new ResponseEntity<>(usuarioSignUpResponse, HttpStatus.CREATED);
            } else {
                throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_USER_EXISTS.getMessage());
            }
        } catch (Exception e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_DATA.getMessage());
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
