package com.bci.microservice;

import com.bci.microservice.controllers.UsuarioController;
import com.bci.microservice.entities.Usuario;
import com.bci.microservice.enums.ErrorMessages;
import com.bci.microservice.exceptions.InputValidationException;
import com.bci.microservice.responses.UsuarioResponse;
import com.bci.microservice.responses.UsuarioSignUpResponse;
import com.bci.microservice.services.RevokedTokenService;
import com.bci.microservice.services.UsuarioService;
import com.bci.microservice.tokens.TokenProperties;
import com.bci.microservice.tokens.TokenUtils;
import com.bci.microservice.utils.DateUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private JwtParser jwtParser;

    @Mock
    private RevokedTokenService revokedTokenService;

    @Mock
    private TokenProperties tokenProperties;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("Password123");
    }

    @Test
    public void testSignUpSuccess() {
        when(usuarioService.getUserByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);
        when(tokenProperties.getFormatDate()).thenReturn("yyyy-MM-dd HH:mm:ss");

        ResponseEntity<UsuarioSignUpResponse> response = usuarioController.signUp(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testSignUpUserExists() {
        String expectedMessage = ErrorMessages.INVALID_USER_EXISTS.getMessage();

        Usuario existingUser = new Usuario();
        existingUser.setEmail("test@example.com");

        when(usuarioService.getUserByEmail(anyString())).thenReturn(Optional.of(existingUser));

        InputValidationException exception = assertThrows(InputValidationException.class, () -> {
            usuarioController.signUp(existingUser);
        });

        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getCodigo());
        assertEquals(expectedMessage, exception.getDetail());
    }

    @Test
    public void testLoginSuccess() {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String email = "test@example.com";
        String password = "Password123";
        String formattedDate = "2023-07-15 12:34:56";

        try (MockedStatic<TokenUtils> mockedTokenUtils = mockStatic(TokenUtils.class);
             MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            mockedTokenUtils.when(() -> TokenUtils.getEmailByToken(anyString())).thenReturn(email);
            mockedTokenUtils.when(() -> TokenUtils.getPasswordByToken(anyString())).thenReturn(password);
            mockedTokenUtils.when(() -> TokenUtils.createToken(anyString(), anyString(), anyInt())).thenReturn(token);

            when(usuarioService.getUserByEmail(email)).thenReturn(Optional.of(usuario));
            when(jwtParser.parseClaimsJws(anyString())).thenReturn(Mockito.mock(io.jsonwebtoken.Jws.class));
            when(tokenProperties.getFormatDate()).thenReturn("yyyy-MM-dd HH:mm:ss");
            mockedDateUtils.when(() -> DateUtils.formattedDate(anyString())).thenReturn(formattedDate);

            Usuario updatedUser = new Usuario();
            updatedUser.setEmail(email);
            updatedUser.setToken(token);
            when(usuarioService.getUserByEmail(email, null)).thenReturn(updatedUser);

            doNothing().when(usuarioService).updateLastLogin(eq(email), eq(formattedDate));
            doNothing().when(usuarioService).updateToken(eq(email), eq(token));
            doNothing().when(revokedTokenService).save(any());

            ResponseEntity<UsuarioResponse> response = usuarioController.login(token);

            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            verify(revokedTokenService, times(1)).save(any());
            verify(usuarioService).updateLastLogin(eq(email), eq(formattedDate));
            verify(usuarioService).updateToken(eq(email), eq(token));
        }
    }

    @Test
    public void testLoginInvalidToken() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0.invalid_signature";
        String email = "test@example.com";

        try (MockedStatic<TokenUtils> mockedTokenUtils = mockStatic(TokenUtils.class)) {
            mockedTokenUtils.when(() -> TokenUtils.getEmailByToken(anyString())).thenReturn(email);
            mockedTokenUtils.when(() -> TokenUtils.getPasswordByToken(anyString())).thenReturn("Password123");

            when(usuarioService.getUserByEmail(email)).thenReturn(Optional.of(usuario));
            doThrow(new JwtException("Invalid token")).when(jwtParser).parseClaimsJws(anyString());

            InputValidationException exception = assertThrows(InputValidationException.class, () -> {
                usuarioController.login(token);
            });

            assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getCodigo());
            assertEquals(ErrorMessages.INVALID_TOKEN.getMessage(), exception.getDetail());
        }
    }

    @Test
    public void testGetUsers() {
        Usuario user1 = new Usuario();
        user1.setEmail("user1@example.com");
        Usuario user2 = new Usuario();
        user2.setEmail("user2@example.com");

        List<Usuario> users = Arrays.asList(user1, user2);
        when(usuarioService.getUsers()).thenReturn(users);

        ResponseEntity<List<Usuario>> response = usuarioController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("user1@example.com", response.getBody().get(0).getEmail());
        assertEquals("user2@example.com", response.getBody().get(1).getEmail());

        verify(usuarioService, times(1)).getUsers();
    }
}
