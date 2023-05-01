package com.globallogic.microserviceglbci.security;

import com.globallogic.microserviceglbci.domain.repository.RevokedTokenRepository;
import com.globallogic.microserviceglbci.exceptions.CustomException;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
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

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private RevokedTokenRepository revokedTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");

        try
        {
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

                String token = bearerToken.replace("Bearer ", "");
                UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(usernamePAT);

                String tokenB = request.getHeader("Authorization").substring(7);
                if (revokedTokenRepository.existsByToken(tokenB)) {
                    throw new CustomException("Token has been revoked","{\"error\": \"Token has been revoked\"}");
                }

            }
        }
        catch (CustomException e)
        {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(e.getMessage());
            return;
        }




        filterChain.doFilter(request, response);

    }
}
