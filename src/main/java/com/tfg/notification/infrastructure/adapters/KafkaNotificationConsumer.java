package com.tfg.notification.infrastructure.adapters;

import com.tfg.notification.application.dto.PackageResponseDto;
import com.tfg.notification.domain.services.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Adaptador que consume eventos desde Kafka y los envía a NotificationService.
 */
@Component
public class KafkaNotificationConsumer {
    private final NotificationService notificationService;

    public KafkaNotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "package-created", groupId = "notification-group")
    public void consumePackageCreated(PackageResponseDto event) {
        notificationService.processPackageCreated(event);
    }

    @KafkaListener(topics = "package-updated", groupId = "notification-group")
    public void consumePackageUpdated(PackageResponseDto event) {
        notificationService.processPackageUpdated(event);
    }
}
