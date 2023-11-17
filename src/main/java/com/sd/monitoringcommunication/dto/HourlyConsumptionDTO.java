package com.sd.monitoringcommunication.dto;

import java.time.LocalDateTime;

public record HourlyConsumptionDTO(
        String username,

        String deviceName,

        double averageConsumption,

        LocalDateTime startTime,

        LocalDateTime currentTime
) {
}