package com.tfg.notification.kafka;

import com.tfg.notification.dto.PackageKafkaEvent;
import com.tfg.notification.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final NotificationService service;

    public KafkaConsumer(NotificationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "package-created", groupId = "notification-group")
    public void consumePackageCreated(PackageKafkaEvent event) {
        service.processPackageCreated(event);
    }

    @KafkaListener(topics = "package-updated", groupId = "notification-group")
    public void consumePackageUpdated(PackageKafkaEvent event) {
        service.processPackageUpdated(event);
    }
}
