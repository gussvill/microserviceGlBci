package com.globallogic.microserviceglbci.security;

import com.globallogic.microserviceglbci.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * La clase `TokenUtils` proporciona una forma de crear y validar tokens de autenticación JWT.
 */
public class TokenUtils {

    private static final String ACCESS_TOKEN_SECRET = "5e05050c0acc4df6be982f5d41ff18da";

    /**
     * El método `createToken` toma como entrada el correo electrónico del usuario, su contraseña y la duración de expiración del token en milisegundos.
     * Luego, genera un token JWT con los datos proporcionados utilizando la biblioteca JJWT. El token incluye el correo electrónico del usuario como "subject" y la fecha de expiración del token.
     * Además, agrega una reclamación personalizada con la contraseña del usuario. El método devuelve una cadena que representa el token JWT generado.
     *
     * @param email             the email
     * @param password          the password
     * @param expirationTokenMs the expiration token ms
     * @return the string
     */
    public static String createToken(String email, String password, int expirationTokenMs) {

        Map<String, Object> extra = new HashMap<>();
        extra.put("password", password);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(DateUtils.expirationDate(expirationTokenMs))
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

    }

    /**
     * El método `getAuthentication` toma como entrada un token JWT y lo valida utilizando la biblioteca JJWT. Si el token es válido,
     * extrae el correo electrónico del usuario del "subject" del token y devuelve un objeto `UsernamePasswordAuthenticationToken` para autenticar al usuario.
     * Si el token no es válido o ha expirado, el método devuelve `null`.
     *
     * @param token the token
     * @return the authentication
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }

}
