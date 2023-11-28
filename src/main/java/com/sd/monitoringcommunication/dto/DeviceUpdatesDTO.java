package com.sd.monitoringcommunication.dto;

public record DeviceUpdatesDTO(
        String username,
        String deviceName,
        double maxConsumption
) {
}