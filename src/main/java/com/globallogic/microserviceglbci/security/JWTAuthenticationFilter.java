package com.globallogic.microserviceglbci.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.microserviceglbci.utils.MyAppProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

//  la clase JWTAuthenticationFilter es responsable de autenticar a los usuarios que intentan iniciar sesión en una aplicación web mediante un token JWT.
//  La clase utiliza el objeto MyAppProperties para obtener información sobre la duración de expiración del token.
//  Si la autenticación es exitosa, la clase genera y devuelve un nuevo token JWT en la respuesta HTTP.
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MyAppProperties myAppProperties;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, MyAppProperties myAppProperties) {
        this.myAppProperties = myAppProperties;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {

        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getEmail(), userDetails.getPassword(), myAppProperties.getExpirationTokenMs());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
/*
    La anotación `@Component` es una etiqueta utilizada en el lenguaje de programación Java y es parte del framework de Spring.
    Indica que la clase anotada es un componente de Spring y que debe ser registrada en el contexto de la aplicación. En este caso,
    la clase `JWTAuthenticationFilter` es un filtro de autenticación que extiende la clase `UsernamePasswordAuthenticationFilter` de Spring Security.

    En la clase `JWTAuthenticationFilter`, se utiliza el método `attemptAuthentication` para intentar autenticar al usuario con las credenciales proporcionadas.
    Este método lee las credenciales del objeto `AuthCredentials` y las convierte en un `UsernamePasswordAuthenticationToken`, que se pasa al administrador de autenticación de Spring para su validación.

    Si la autenticación es exitosa, el método `successfulAuthentication` crea un token JWT (JSON Web Token) utilizando la información del usuario autenticado y lo agrega a la respuesta HTTP.
    El token se utiliza para autenticar futuras solicitudes del usuario. Además, este método también llama al método `successfulAuthentication` de la superclase `UsernamePasswordAuthenticationFilter`.

    En resumen, la clase `JWTAuthenticationFilter` es un componente de Spring que se encarga de la autenticación de los usuarios utilizando tokens JWT en una aplicación web.
 */