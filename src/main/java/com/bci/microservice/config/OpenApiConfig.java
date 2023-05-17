package com.bci.microservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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




