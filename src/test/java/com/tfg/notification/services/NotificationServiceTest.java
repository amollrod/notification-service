package com.tfg.notification.services;

import com.tfg.notification.clients.blockchain.BlockchainClient;
import com.tfg.notification.clients.blockchain.dto.CreatePackageRequest;
import com.tfg.notification.clients.blockchain.dto.UpdatePackageStatusRequest;
import com.tfg.notification.event.PackageEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private BlockchainClient blockchainClient;
    private NotificationService service;

    @BeforeEach
    void setUp() {
        blockchainClient = mock(BlockchainClient.class);
        service = new NotificationService(blockchainClient);
    }

    @Test
    void shouldHandlePackageCreated() {
        PackageEvent event = PackageEvent.builder()
                .id("pkg-1")
                .origin("Madrid")
                .destination("Valencia")
                .status("CREATED")
                .lastLocation("Madrid")
                .build();

        service.handlePackageCreated(event);

        ArgumentCaptor<CreatePackageRequest> captor = ArgumentCaptor.forClass(CreatePackageRequest.class);
        verify(blockchainClient).createPackage(captor.capture());

        CreatePackageRequest request = captor.getValue();
        assertThat(request.getId()).isEqualTo("pkg-1");
        assertThat(request.getOrigin()).isEqualTo("Madrid");
        assertThat(request.getDestination()).isEqualTo("Valencia");
    }

    @Test
    void shouldHandlePackageUpdated() {
        PackageEvent event = PackageEvent.builder()
                .id("pkg-2")
                .origin("Sevilla")
                .destination("Bilbao")
                .status("IN_TRANSIT")
                .lastLocation("Zaragoza")
                .build();

        service.handlePackageUpdated(event);

        ArgumentCaptor<UpdatePackageStatusRequest> captor = ArgumentCaptor.forClass(UpdatePackageStatusRequest.class);
        verify(blockchainClient).updatePackageStatus(eq("pkg-2"), captor.capture());

        UpdatePackageStatusRequest request = captor.getValue();
        assertThat(request.getStatus()).isEqualTo("IN_TRANSIT");
        assertThat(request.getLocation()).isEqualTo("Zaragoza");
    }
}
