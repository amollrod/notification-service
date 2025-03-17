package com.tfg.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageKafkaHistoryEvent {
    private String status;
    private String location;
    private long timestamp;
}

