package com.project.api.restful_user_api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class RestfulUserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulUserApiApplication.class, args);
    }

    //Swagger UI Documentation Info
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
                .description("This API exposes endpoints for users to manage their tasks.")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}