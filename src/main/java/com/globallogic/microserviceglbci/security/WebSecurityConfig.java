package com.globallogic.microserviceglbci.security;

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

//  define la configuración de seguridad de Spring para una aplicación web
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    // este método configura la cadena de filtros de seguridad para la aplicación
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        /*
             El método toma dos parámetros: una instancia de HttpSecurity y un objeto AuthenticationManager.
             En este método se deshabilita la protección CSRF,
             se autoriza el acceso sin autenticación a la URL /sign-up,
             y se configura la autenticación básica HTTP.
             Además, se establece la política de creación de sesión en STATELESS (es decir, no se crea sesión),
             y se agrega un filtro de autorización JWT personalizado (jwtAuthorizationFilter)
             antes del filtro de autenticación de nombre de usuario y contraseña.
         */
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

    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
    La clase `WebSecurityConfig` es una clase de configuración de seguridad en una aplicación basada en Spring Security. La anotación `@Configuration` indica que esta clase proporciona configuración a la aplicación.

    La anotación `@AllArgsConstructor` indica que se debe generar automáticamente un constructor que acepte todos los campos de la clase como parámetros, para que Spring pueda inyectarlos automáticamente.

    Esta clase configura la seguridad de la aplicación mediante la definición de una cadena de filtros de seguridad que se aplican a las solicitudes entrantes a la aplicación.

    El método `filterChain()` es un método de configuración que configura la cadena de filtros de seguridad para la aplicación.
    En este método, se deshabilita la protección CSRF, se autoriza el acceso sin autenticación a la URL /sign-up, se configura la autenticación básica HTTP,
    se establece la política de creación de sesión en STATELESS (es decir, no se crea sesión), y se agrega un filtro de autorización JWT personalizado (`jwtAuthorizationFilter`)
    antes del filtro de autenticación de nombre de usuario y contraseña.

    El método `authManager()` es un método de configuración que devuelve un objeto `AuthenticationManager`. Este método configura el `AuthenticationManagerBuilder` con el `UserDetailsService` proporcionado,
    establece el codificador de contraseñas utilizado para cifrar y verificar las contraseñas, y construye y devuelve un objeto `AuthenticationManager`.

    El método `passwordEncoder()` es un método que devuelve un objeto `PasswordEncoder` que se utiliza para cifrar y verificar las contraseñas de usuario.

    En resumen, la clase `WebSecurityConfig` es una clase de configuración que define la cadena de filtros de seguridad y la configuración de autenticación y autorización en una aplicación basada en Spring Security.
    Esta clase utiliza varios métodos de configuración para configurar el `AuthenticationManager`, el `PasswordEncoder` y los filtros de seguridad que se aplican a las solicitudes entrantes en la aplicación.
 */