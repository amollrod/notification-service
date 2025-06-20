package com.tfg.notification.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageHistoryEvent {
    private String status;
    private String location;
    private long timestamp;
}

