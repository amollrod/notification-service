package com.tfg.notification.domain.services;

import com.tfg.notification.application.dto.PackageResponseDto;
import com.tfg.notification.domain.ports.NotificationSenderPort;
import org.springframework.stereotype.Service;

/**
 * Servicio de dominio encargado de procesar las notificaciones basadas en eventos de Kafka.
 */
@Service
public class NotificationService {
    private final NotificationSenderPort notificationSender;

    public NotificationService(NotificationSenderPort notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void processPackageCreated(PackageResponseDto event) {
        String message = String.format("Paquete Creado: %s (Origen: %s, Destino: %s) - Estado: %s",
                event.getId(), event.getOrigin(), event.getDestination(), event.getStatus());
        notificationSender.sendNotification(message);
    }

    public void processPackageUpdated(PackageResponseDto event) {
        String message = String.format("Paquete %s actualizado - Estado: %s - Última ubicación: %s",
                event.getId(), event.getStatus(), event.getLastLocation());
        notificationSender.sendNotification(message);
    }
}
