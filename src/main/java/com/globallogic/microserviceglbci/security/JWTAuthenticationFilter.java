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

/**
 * La clase `JWTAuthenticationFilter` es un componente de Spring que se encarga de la autenticación de los usuarios utilizando tokens JWT en una aplicación web.
 */
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MyAppProperties myAppProperties;

    /**
     * Instantiates a new Jwt authentication filter.
     *
     * @param authenticationManager the authentication manager
     * @param myAppProperties       the my app properties
     */
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, MyAppProperties myAppProperties) {
        this.myAppProperties = myAppProperties;
        super.setAuthenticationManager(authenticationManager);
    }

    /**
     * Se utiliza el método `attemptAuthentication` para intentar autenticar al usuario con las credenciales proporcionadas.
     * Este método lee las credenciales del objeto `AuthCredentials` y las convierte en un `UsernamePasswordAuthenticationToken`, que se pasa al administrador de autenticación de Spring para su validación.
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OpenID).
     * @return
     * @throws AuthenticationException
     */
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

    /**
     * Si la autenticación es exitosa, el método `successfulAuthentication` crea un token JWT (JSON Web Token) utilizando la información del usuario autenticado y lo agrega a la respuesta HTTP.
     * El token se utiliza para autenticar futuras solicitudes del usuario.
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     * method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getEmail(), userDetails.getPassword(), myAppProperties.getExpirationTokenMs());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
