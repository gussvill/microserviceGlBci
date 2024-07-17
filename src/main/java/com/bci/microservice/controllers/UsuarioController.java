package com.bci.microservice.controllers;

import com.bci.microservice.entities.Usuario;
import com.bci.microservice.enums.ErrorMessages;
import com.bci.microservice.exceptions.InputValidationException;
import com.bci.microservice.responses.UsuarioResponse;
import com.bci.microservice.responses.UsuarioSignUpResponse;
import com.bci.microservice.services.RevokedTokenService;
import com.bci.microservice.services.UsuarioService;
import com.bci.microservice.tokens.RevokedTokenFactory;
import com.bci.microservice.tokens.TokenProperties;
import com.bci.microservice.tokens.TokenUtils;
import com.bci.microservice.utils.DateUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtParser jwtParser;
    private final RevokedTokenService revokedTokenService;
    private final TokenProperties tokenProperties;

    public UsuarioController(UsuarioService usuarioService, JwtParser jwtParser, RevokedTokenService revokedTokenService, TokenProperties tokenProperties) {
        this.usuarioService = usuarioService;
        this.jwtParser = jwtParser;
        this.revokedTokenService = revokedTokenService;
        this.tokenProperties = tokenProperties;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se encontraron los usuarios", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))}),
            @ApiResponse(responseCode = "404", description = "Usuarios no encontrados", content = @Content)})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> getUsers() {
        List<Usuario> users = usuarioService.getUsers().stream()
                .sorted(Comparator.comparing(Usuario::getEmail))
                .collect(Collectors.toList());
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
    public ResponseEntity<UsuarioResponse> login(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_TOKEN.getMessage());
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String emailByToken = TokenUtils.getEmailByToken(token);
        String passwordByToken = TokenUtils.getPasswordByToken(token);
        Optional<Usuario> usuarioList = usuarioService.getUserByEmail(emailByToken);

        try {
            return usuarioList.map(usuario -> {
                jwtParser.parseClaimsJws(token).getBody();
                usuarioService.updateLastLogin(emailByToken, DateUtils.formattedDate(tokenProperties.getFormatDate()));
                revokedTokenService.save(RevokedTokenFactory.createRevokedToken(token));

                Usuario updatedUser = usuarioService.getUserByEmail(emailByToken, null);
                String newToken = TokenUtils.createToken(emailByToken, passwordByToken, tokenProperties.getExpirationTokenMs());
                usuarioService.updateToken(emailByToken, newToken);
                updatedUser.setToken(newToken);
                UsuarioResponse usuarioResponse = UsuarioResponse.convertToUsuarioResponse(updatedUser);

                return ResponseEntity.accepted().body(usuarioResponse);
            }).orElseThrow(() -> new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_USER_NOT_EXISTS.getMessage()));
        } catch (JwtException e) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_TOKEN.getMessage());
        }
    }

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioSignUpResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Datos inválidos ingresados o el usuario ya existe", content = @Content)})
    @PostMapping("/sign-up")
    public ResponseEntity<UsuarioSignUpResponse> signUp(@Valid @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioList = usuarioService.getUserByEmail(usuario.getEmail());

        if (usuarioList.isPresent()) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_USER_EXISTS.getMessage());
        }

        if (!usuario.getPassword().matches("^(?=.*[A-Z])(?=.*\\d.*\\d)[a-zA-Z\\d]{8,12}$")) {
            throw new InputValidationException(HttpStatus.BAD_REQUEST.value(), ErrorMessages.INVALID_PASSWORD.getMessage());
        }

        usuario.setPassword(usuarioService.passwordEncoder(usuario.getPassword()));
        usuario.setCreated(DateUtils.formattedDate(tokenProperties.getFormatDate()));
        usuario.setLastLogin(ErrorMessages.INVALID_LAST_LOGIN.getMessage());
        usuario.setToken(TokenUtils.createToken(usuario.getEmail(), usuario.getPassword(), tokenProperties.getExpirationTokenMs()));
        usuario.setActive(true);
        usuario.setName(StringUtils.isNotBlank(usuario.getName()) ? usuario.getName() : "");
        usuario.setPhonesAsJson(usuario.getPhones());
        Usuario usuarioSave = usuarioService.save(usuario);
        UsuarioSignUpResponse usuarioSignUpResponse = UsuarioSignUpResponse.convertToUsuarioSignUpResponse(usuarioSave);

        return new ResponseEntity<>(usuarioSignUpResponse, HttpStatus.CREATED);
    }
}
