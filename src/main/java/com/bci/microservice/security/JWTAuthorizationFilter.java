package com.bci.microservice.security;

import com.bci.microservice.domain.repository.IRevokedTokenRepository;
import com.bci.microservice.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Este filtro se utiliza para autenticar y autorizar a los usuarios en cada solicitud y para verificar si el token de autenticación está revocado. Si el token está revocado, se lanza una excepción personalizada.
 * Si la autenticación es exitosa y el token no está revocado, el filtro continúa con la cadena de filtros.
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private IRevokedTokenRepository IRevokedTokenRepository;


    /**
     * 1. Obtiene el token de autenticación del encabezado de la solicitud.
     * 2. Si el token está presente y tiene el prefijo "Bearer", intenta autenticar al usuario utilizando el método `TokenUtils.getAuthentication(token)`.
     * 3. Si la autenticación tiene éxito, establece la autenticación del usuario en el contexto de seguridad de Spring utilizando el objeto `SecurityContextHolder`.
     * 4. Verifica si el token de autenticación está revocado verificando si existe en la base de datos a través del objeto `revokedTokenRepository`.
     * 5. Si el token está revocado, lanza una excepción personalizada `CustomException` con un mensaje de error.
     * 6. Si la autenticación es exitosa y el token no está revocado, el filtro continúa con la cadena de filtros mediante `filterChain.doFilter(request, response)`.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        try {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

                String token = bearerToken.replace("Bearer ", "");
                UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(usernamePAT);

                String tokenB = request.getHeader("Authorization").substring(7);
                if (IRevokedTokenRepository.existsByToken(tokenB)) {
                    throw new CustomException("Token has been revoked", "{\"error\": \"Token has been revoked\"}");
                }

            }
        } catch (CustomException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(e.getMessage());
            return;
        }


        filterChain.doFilter(request, response);

    }
}
