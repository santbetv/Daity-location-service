package com.deity.location.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * Elemento que permite manejar las peticiones externas para otras apis desde el WebClient
     *
     * @return
     */
    @Bean
    //@LoadBalanced//Solo utilizar para Service Discovery (por ejemplo, Eureka o Consul)
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
