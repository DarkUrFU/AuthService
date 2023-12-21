package com.darkurfu.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@OpenAPIDefinition(
        info = @Info(
                title = "AuthService",
                description = "Сервис регистрации и авторизации", version = "1.0.0",
                contact = @Contact(
                        name = "Serov Alexey"
                )
        )
)
@Configuration
public class SwaggerUIConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/web/v1/auth", "/swagger-ui.html");
        registry.addRedirectViewController("/api/internal/v1/auth", "/swagger-ui.html");

    }
}
