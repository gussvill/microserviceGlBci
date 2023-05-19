package com.bci.microservice;

import com.bci.microservice.domain.repository.UsuarioRepository;
import com.bci.microservice.persistence.jpa.UsuarioJpaRepository;
import com.bci.microservice.persistence.entity.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @MockBean
    private UsuarioJpaRepository UsuarioJpaRepository;

    @Test
    public void testGetUserByEmailOptional() {

        // Crear los datos de prueba
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("johndoe@example.com");
        usuario1.setPassword("Guss2023");

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("guss@example.com");
        usuario2.setPassword("Top2023");
        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        // Establecer el comportamiento esperado del mock
        when(UsuarioJpaRepository.findByEmailContaining("test")).thenReturn(Optional.of(usuario1));
        when(UsuarioJpaRepository.findByEmailContaining("no-existe")).thenReturn(Optional.empty());
        when(UsuarioJpaRepository.findByEmailContaining("")).thenReturn(Optional.ofNullable(null));
        when(UsuarioJpaRepository.findByEmailContaining("multiple"))
                .thenReturn(Optional.of(usuario1))
                .thenReturn(Optional.of(usuario2));

        // Llamar al método del servicio y verificar los resultados
        assertEquals(Optional.of(usuario1), usuarioRepository.getUserByEmail("test"));
        assertEquals(Optional.empty(), usuarioRepository.getUserByEmail("no-existe"));
        assertEquals(Optional.ofNullable(null), usuarioRepository.getUserByEmail(""));
        assertEquals(Optional.of(usuario1), usuarioRepository.getUserByEmail("multiple"));
        assertEquals(Optional.of(usuario2), usuarioRepository.getUserByEmail("multiple"));
    }

    @Test
    public void testGetUserByEmail() {
        // Datos de prueba
        String email = "usuario@example.com";
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setEmail(email);

        // Configurar el comportamiento del mock
        Mockito.when(UsuarioJpaRepository.findByEmail(email)).thenReturn(expectedUsuario);

        // Invocar al método a probar
        Usuario actualUsuario = usuarioRepository.getUserByEmail(email, null);

        // Verificar el resultado
        Assertions.assertEquals(expectedUsuario, actualUsuario);

    }


    @Test
    public void testGetUsers() {

        Usuario usuario = new Usuario();
        usuario.setEmail("johndoe@example.com");
        usuario.setPassword("Guss2023");

        Usuario usuario2 = new Usuario();
        usuario.setEmail("guss@example.com");
        usuario.setPassword("Top2023");

        // Given
        List<Usuario> expectedUsuarios = Arrays.asList(usuario, usuario2);
        when(UsuarioJpaRepository.findAll()).thenReturn(expectedUsuarios);

        // When
        List<Usuario> actualUsuarios = usuarioRepository.getUsers();

        // Then
        assertEquals(expectedUsuarios, actualUsuarios);
        verify(UsuarioJpaRepository).findAll();
    }


    @Test
    public void testSaveUser() {
        // crea un usuario de prueba
        Usuario usuario = new Usuario();
        usuario.setEmail("johndoe@example.com");
        usuario.setPassword("Guss2023");

        // define el comportamiento del repositorio
        when(UsuarioJpaRepository.save(any(Usuario.class))).thenReturn(usuario);

        // llama al método del servicio y verifica el resultado
        Usuario result = usuarioRepository.save(usuario);
        verify(UsuarioJpaRepository, times(1)).save(usuario);
        assertEquals(usuario, result);
    }

    @Test
    public void testUpdateToken() {
        // define los parámetros de prueba
        String email = "johndoe@example.com";
        String newToken = "1234567890";

        // llama al método del servicio
        usuarioRepository.updateToken(email, newToken);

        // verifica si el método del repositorio se llamó una vez con los parámetros adecuados
        verify(UsuarioJpaRepository, times(1)).updateToken(email, newToken);
    }

    @Test
    public void testUpdateLastLogin() {
        // Given
        String email = "test@example.com";
        String date = "2023-05-01";

        // When
        usuarioRepository.updateLastLogin(email, date);

        // Then
        verify(UsuarioJpaRepository).updateLastLogin(email, date);
    }


}