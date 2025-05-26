package com.tfg.notification.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageEvent {
    private String id;
    private String origin;
    private String destination;
    private String status;
    private String lastLocation;
}
