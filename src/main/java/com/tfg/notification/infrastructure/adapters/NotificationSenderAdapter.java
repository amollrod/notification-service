package com.tfg.notification.infrastructure.adapters;

import com.tfg.notification.domain.ports.NotificationSenderPort;
import org.springframework.stereotype.Component;

/**
 * Adaptador que envía notificaciones a la consola (simulación).
 */
@Component
public class NotificationSenderAdapter implements NotificationSenderPort {
    @Override
    public void sendNotification(String message) {
        System.out.println("Notificación enviada: " + message);
    }
}
