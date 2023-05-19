package com.bci.microservice.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(info =
        @Info(
                title = "API microservicio BCI",
                version = "v1",
                description = "Esta es una API para el microservicio BCI. Proporciona endpoints para crear usuarios, obtener el listado de todos los usuarios y hacer login con un usuario existente.. \n\n" +
                        "El repositorio de GitHub para este proyecto se encuentra en: [GitHub](https://github.com/gussvill/microserviceGlBci.git)",
                contact = @Contact(
                        name = "Gustavo Villegas",
                        email = "gussvill@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/users", "/sign-up", "/login")
                .build();
    }

    @Bean
    public SwaggerUiConfigParameters swaggerUiConfigParameters(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        SwaggerUiConfigParameters swaggerUiConfigParameters = new SwaggerUiConfigParameters(swaggerUiConfigProperties);
        swaggerUiConfigParameters.setDeepLinking(true);
        swaggerUiConfigParameters.setDefaultModelsExpandDepth(-1);
        swaggerUiConfigParameters.setFilter(String.valueOf(true));
        return swaggerUiConfigParameters;
    }
}




