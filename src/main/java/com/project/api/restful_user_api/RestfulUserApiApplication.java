package com.project.api.restful_user_api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * RESTful API using Spring Boot 3.3.2 with JWT authentication to Manage User Entity
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@SpringBootApplication
public class RestfulUserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulUserApiApplication.class, args);
    }

    /**
     * Set Swagger UI documentation info and define JWT security scheme
     *
     * @return OpenAPI
     * @since 1.0.0
     */
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("amirmasoudrahimiofficial@gmail.com");
        contact.setName("Amirmasoud Rahimi");
        contact.setUrl("https://amirmasoudrahimi.ir");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8585");
        localServer.setDescription("Server URL in Local environment");

        Server productionServer = new Server();
        productionServer.setUrl("https://my-vps-server.com");
        productionServer.setDescription("Server URL in Production environment");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("RESTful User API")
                .contact(contact)
                .version("1.0.0")
                .description("This API exposes endpoints to manage user.")
                .license(mitLicense);

        // Define JWT Security Scheme
        SecurityScheme jwtSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("jwtAuth") // Name of the security scheme
                .in(SecurityScheme.In.HEADER); // Where the token will be sent

        // Add the security scheme to components
        Components components = new Components().addSecuritySchemes("jwtAuth", jwtSecurityScheme);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer))
                .addSecurityItem(new SecurityRequirement().addList("jwtAuth")).components(components);
    }
}