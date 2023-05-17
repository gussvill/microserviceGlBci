package com.bci.microservice.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * La clase `WebSecurityConfig` es una clase de configuración que define la cadena de filtros de seguridad y la configuración de autenticación y autorización en una aplicación basada en Spring Security. La anotación `@Configuration` indica que esta clase proporciona configuración a la aplicación.
 * La anotación `@AllArgsConstructor` indica que se debe generar automáticamente un constructor que acepte todos los campos de la clase como parámetros, para que Spring pueda inyectarlos automáticamente.
 *
 */
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Método de configuración que configura la cadena de filtros de seguridad para la aplicación
     *
     * @param http        the http
     * @param authManager the auth manager
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-up").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Método de configuración que devuelve un objeto `AuthenticationManager`
     *
     * @param http            the http
     * @param passwordEncoder the password encoder
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    /**
     * Método que devuelve un objeto `PasswordEncoder` que se utiliza para cifrar y verificar las contraseñas de usuario
     *
     * @return the password encoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
