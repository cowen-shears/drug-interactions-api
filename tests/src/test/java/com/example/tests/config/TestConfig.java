package com.example.tests.config;

import com.example.domain.ports.InteractionRepository;
import com.example.domain.ports.OpenFdaClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public WebClient openFdaWebClient(WebClient.Builder builder) {
        return builder
            .baseUrl("${openfda.baseUrl}")
            .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
