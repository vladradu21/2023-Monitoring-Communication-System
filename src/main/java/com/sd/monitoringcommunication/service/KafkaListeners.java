package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private final EnergyConsumptionService energyConsumptionService;

    @Autowired
    public KafkaListeners(EnergyConsumptionService energyConsumptionService) {
        this.energyConsumptionService = energyConsumptionService;
    }

    @KafkaListener(topics = "monitoring", groupId = "group_id", containerFactory = "monitoringListenerContainerFactory")
    void monitoringListener(MessageDTO data) {
        System.out.println("monitoring: " + data);
    }

    @KafkaListener(topics = "device", groupId = "group_id", containerFactory = "deviceListenerContainerFactory")
    void deviceListener(DeviceUpdateDTO data) {
        System.out.println("device updates: " + data);

        energyConsumptionService.updateEnergyConsumption(data);
    }
}