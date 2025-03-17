package com.tfg.notification.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    public void sendNotification(String message) {
        System.out.println("Notificacion enviada: " + message);
    }
}
