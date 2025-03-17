package com.tfg.notification.kafka;

import com.tfg.notification.kafka.dto.PackageEvent;
import com.tfg.notification.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PackageListener {
    private final NotificationService service;

    public PackageListener(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "package-created", groupId = "notification-group")
    public void consumePackageCreated(PackageEvent event) {
        service.handlePackageCreated(event);
    }

    @KafkaListener(topics = "package-updated", groupId = "notification-group")
    public void consumePackageUpdated(PackageEvent event) {
        service.handlePackageUpdated(event);
    }
}
