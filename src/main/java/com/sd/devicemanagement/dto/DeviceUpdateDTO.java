package com.sd.devicemanagement.dto;

public record DeviceUpdateDTO(
        String username,
        String deviceName,
        double maxConsumption
) {
}