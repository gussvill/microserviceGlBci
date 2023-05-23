package com.bci.microservice;

import com.bci.microservice.interfaces.IUsuario;
import com.bci.microservice.interfaces.repositories.IUsuarioRepository;
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
public class IUsuarioTest {

    @Autowired
    private IUsuario IUsuario;

    @MockBean
    private IUsuarioRepository IUsuarioRepository;

    @Test
    public void testGetUserByEmailOptional() {

        // Crear los datos de prueba
        com.bci.microservice.entity.Usuario usuario1 = new com.bci.microservice.entity.Usuario();
        usuario1.setEmail("gussvill@example.com");
        usuario1.setPassword("Top2023");

        com.bci.microservice.entity.Usuario usuario2 = new com.bci.microservice.entity.Usuario();
        usuario2.setEmail("gussvill78@example.com");
        usuario2.setPassword("Top2024");
        List<com.bci.microservice.entity.Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        // Establecer el comportamiento esperado del mock
        when(IUsuarioRepository.findByEmailContaining("test")).thenReturn(Optional.of(usuario1));
        when(IUsuarioRepository.findByEmailContaining("no-existe")).thenReturn(Optional.empty());
        when(IUsuarioRepository.findByEmailContaining("")).thenReturn(Optional.ofNullable(null));
        when(IUsuarioRepository.findByEmailContaining("multiple")).thenReturn(Optional.of(usuario1)).thenReturn(Optional.of(usuario2));

        // Llamar al método del servicio y verificar los resultados
        assertEquals(Optional.of(usuario1), IUsuario.getUserByEmail("test"));
        assertEquals(Optional.empty(), IUsuario.getUserByEmail("no-existe"));
        assertEquals(Optional.ofNullable(null), IUsuario.getUserByEmail(""));
        assertEquals(Optional.of(usuario1), IUsuario.getUserByEmail("multiple"));
        assertEquals(Optional.of(usuario2), IUsuario.getUserByEmail("multiple"));
    }

    @Test
    public void testGetUserByEmail() {
        // Datos de prueba
        String email = "gussvill@example.com";
        com.bci.microservice.entity.Usuario expectedUsuario = new com.bci.microservice.entity.Usuario();
        expectedUsuario.setEmail(email);

        // Configurar el comportamiento del mock
        Mockito.when(IUsuarioRepository.findByEmail(email)).thenReturn(expectedUsuario);

        // Invocar al método a probar
        com.bci.microservice.entity.Usuario actualUsuario = IUsuario.getUserByEmail(email, null);

        // Verificar el resultado
        Assertions.assertEquals(expectedUsuario, actualUsuario);

    }

    @Test
    public void testGetUsers() {

        com.bci.microservice.entity.Usuario usuario = new com.bci.microservice.entity.Usuario();
        usuario.setEmail("gussvill@example.com");
        usuario.setPassword("Top2023");

        com.bci.microservice.entity.Usuario usuario2 = new com.bci.microservice.entity.Usuario();
        usuario.setEmail("gussvill78@example.com");
        usuario.setPassword("Top2024");

        // Given
        List<com.bci.microservice.entity.Usuario> expectedUsuarios = Arrays.asList(usuario, usuario2);
        when(IUsuarioRepository.findAll()).thenReturn(expectedUsuarios);

        // When
        List<com.bci.microservice.entity.Usuario> actualUsuarios = usuario.getUsers();

        // Then
        assertEquals(expectedUsuarios, actualUsuarios);
        verify(IUsuarioRepository).findAll();
    }

    @Test
    public void testSaveUser() {
        // crea un usuario de prueba
        com.bci.microservice.entity.Usuario usuario = new com.bci.microservice.entity.Usuario();
        usuario.setEmail("gussvill@example.com");
        usuario.setPassword("Top2023");

        // define el comportamiento del repositorio
        when(IUsuarioRepository.save(any(com.bci.microservice.entity.Usuario.class))).thenReturn(usuario);

        // llama al método del servicio y verifica el resultado
        com.bci.microservice.entity.Usuario result = usuario.save(usuario);
        verify(IUsuarioRepository, times(1)).save(usuario);
        assertEquals(usuario, result);
    }

    @Test
    public void testUpdateToken() {
        // define los parámetros de prueba
        String email = "gussvill@example.com";
        String newToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndXNzdmlsbEBnbWFpbC5jb20iLCJleHAiOjE2ODQ1MTAwMjAsInBhc3N3b3JkIjoiJDJhJDEwJEhaT2kvVlVyWW9QdVBJSmZlUHl4VGU2QmFjRU1uaEkyQ1pWaHJ6dkg0UGNEWXkxdjU1QlNPIn0.o-GEWRSjeAhKsI2k_xfWyEX_5r27-YLPCs8XYc2z8ao";

        // llama al método del servicio
        IUsuario.updateToken(email, newToken);

        // verifica si el método del repositorio se llamó una vez con los parámetros adecuados
        verify(IUsuarioRepository, times(1)).updateToken(email, newToken);
    }

    @Test
    public void testUpdateLastLogin() {
        // Given
        String email = "gussvill@example.com";
        String date = "may 19, 2023 11:22:00 AM";

        // When
        IUsuario.updateLastLogin(email, date);

        // Then
        verify(IUsuarioRepository).updateLastLogin(email, date);
    }

}
