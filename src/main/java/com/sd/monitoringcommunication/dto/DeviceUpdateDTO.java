package com.sd.monitoringcommunication.dto;

public record DeviceUpdateDTO(
        String username,
        String deviceName,
        double maxConsumption
) {
}