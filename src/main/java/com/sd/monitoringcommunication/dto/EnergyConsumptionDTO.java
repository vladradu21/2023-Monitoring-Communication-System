package com.sd.monitoringcommunication.dto;

public record EnergyConsumptionDTO(
        String username,

        String deviceName,

        double averageConsumption,

        double maxConsumption
) {
}
