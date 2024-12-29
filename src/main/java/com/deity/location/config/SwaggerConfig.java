package com.deity.location.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiEndPointinfo())
                .addServersItem(new Server().url("https://daity-location-service-production.up.railway.app").description("Generated server url"))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Location Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    private Info apiEndPointinfo() {
        return new Info().title("Location - By Santiago Betancur Villegas")
                .description("Spring location application")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url("https://springdoc.org"));
    }
}