package com.tfg.notification.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageResponseDto {
    private String id;
    private String origin;
    private String destination;
    private PackageStatusDto status;
    private String lastLocation;
    private long lastTimestamp;
    private List<PackageHistoryResponseDto> history;
}
