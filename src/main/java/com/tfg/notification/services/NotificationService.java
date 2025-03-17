package com.tfg.notification.services;

import com.tfg.notification.dto.PackageKafkaEvent;
import com.tfg.notification.kafka.KafkaSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final KafkaSender sender;

    public NotificationService(KafkaSender sender) {
        this.sender = sender;
    }

    public void processPackageCreated(PackageKafkaEvent event) {
        String message = String.format(
                "Paquete Creado: %s (Origen: %s, Destino: %s) - Estado: %s",
                event.getId(), event.getOrigin(), event.getDestination(), event.getStatus()
        );
        sender.sendNotification(message);
    }

    public void processPackageUpdated(PackageKafkaEvent event) {
        String message = String.format(
                "Paquete %s actualizado - Estado: %s - Ultima ubicacion: %s",
                event.getId(), event.getStatus(), event.getLastLocation()
        );
        sender.sendNotification(message);
    }
}
