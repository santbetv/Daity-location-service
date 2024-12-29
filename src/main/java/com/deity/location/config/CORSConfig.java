package com.deity.location.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CORSConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas
                .allowedOrigins("*") // Permite cualquier origen
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permite todos los encabezados
                .exposedHeaders("Authorization") // Encabezados visibles
                .allowCredentials(true) // Permite enviar credenciales
                .maxAge(3600); // Cache por 1 hora
    }
}
