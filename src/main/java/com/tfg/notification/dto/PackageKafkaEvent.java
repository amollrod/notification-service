package com.tfg.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageKafkaEvent {
    private String id;
    private String origin;
    private String destination;
    private String status;
    private String lastLocation;
    private long lastTimestamp;
    private List<PackageKafkaHistoryEvent> history;
}
