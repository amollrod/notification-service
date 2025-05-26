package com.tfg.notification.services;

import com.tfg.notification.clients.blockchain.BlockchainClient;
import com.tfg.notification.clients.blockchain.mappers.BlockchainPackageMapper;
import com.tfg.notification.event.PackageEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class NotificationService {
    private final BlockchainClient client;

    public NotificationService(BlockchainClient client) {
        this.client = client;
    }

    public void handlePackageCreated(PackageEvent event) {
        log.info("Package with id {} created. Its origin is {} and its destination is {}", event.getId(), event.getOrigin(), event.getDestination());
        client.createPackage(BlockchainPackageMapper.toCreatePackageRequest(event));
    }

    public void handlePackageUpdated(PackageEvent event) {
        log.info("Package with id {} updated. Its status is {} and its last location is {}", event.getId(), event.getStatus(), event.getLastLocation());
        client.updatePackageStatus(event.getId(), BlockchainPackageMapper.toUpdatePackageStatusRequest(event));
    }
}
