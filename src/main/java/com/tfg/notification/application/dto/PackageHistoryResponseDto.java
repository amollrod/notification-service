package com.tfg.notification.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PackageHistoryResponseDto {
    private String status;
    private String location;
    private long timestamp;
}
