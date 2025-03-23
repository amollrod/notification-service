package com.tfg.notification.clients.blockchain;

import com.tfg.notification.clients.blockchain.dto.CreatePackageRequest;
import com.tfg.notification.clients.blockchain.dto.UpdatePackageStatusRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BlockchainClient {
    private final WebClient webClient;

    public BlockchainClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void createPackage(CreatePackageRequest dto) {
        webClient.post()
                .uri("/package")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    public void updatePackageStatus(long packageId, UpdatePackageStatusRequest dto) {
        webClient.put()
                .uri("/package/{id}/status", packageId)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
