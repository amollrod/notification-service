package com.tfg.notification.domain.ports;

/**
 * Puerto que define el envío de notificaciones.
 */
public interface NotificationSenderPort {
    void sendNotification(String message);
}
