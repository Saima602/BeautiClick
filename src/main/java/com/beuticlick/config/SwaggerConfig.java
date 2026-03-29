package com.beuticlick.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Beuticlick API",
        version = "1.0.0",
        description = "Backend API for Beuticlick — salon management & booking platform",
        contact = @Contact(name = "Beuticlick Team")
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Paste your JWT token from POST /auth/login here"
)
public class SwaggerConfig {
    // springdoc auto-scans all @RestController classes — no manual registration needed
}
