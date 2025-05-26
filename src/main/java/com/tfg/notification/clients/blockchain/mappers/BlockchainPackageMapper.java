package com.tfg.notification.clients.blockchain.mappers;

import com.tfg.notification.clients.blockchain.dto.CreatePackageRequest;
import com.tfg.notification.clients.blockchain.dto.UpdatePackageStatusRequest;
import com.tfg.notification.event.PackageEvent;

public class BlockchainPackageMapper {
    public static CreatePackageRequest toCreatePackageRequest(PackageEvent event) {
        return CreatePackageRequest.builder()
                .id(event.getId())
                .origin(event.getOrigin())
                .destination(event.getDestination())
                .build();
    }

    public static UpdatePackageStatusRequest toUpdatePackageStatusRequest(PackageEvent event) {
        return UpdatePackageStatusRequest.builder()
                .status(event.getStatus())
                .location(event.getLastLocation())
                .build();
    }
}
