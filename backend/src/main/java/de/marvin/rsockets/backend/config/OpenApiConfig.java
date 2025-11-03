package de.marvin.rsockets.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RSocket Backend API")
                        .version("0.0.1-SNAPSHOT")
                        .description("Spring Boot backend application with RSocket support")
                        .contact(new Contact()
                                .name("Marvin")
                                .email("contact@example.com"))
                        .license(new License()
                                .name("License")
                                .url("https://example.com/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server")
                ));
    }
}
