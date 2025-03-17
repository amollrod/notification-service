package com.tfg.notification.clients.blockchain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BlockchainClientConfig {

    @Value("${blockchain.service.url}")
    private String blockchainServiceUrl;

    @Bean
    public WebClient blockchainWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(blockchainServiceUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}