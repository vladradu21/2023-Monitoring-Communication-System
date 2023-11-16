package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class KafkaListeners {
    private final MaxConsumptionService maxConsumptionService;
    private final MonitoringService monitoringService;

    @Autowired
    public KafkaListeners(MaxConsumptionService maxConsumptionService, MonitoringService monitoringService) {
        this.maxConsumptionService = maxConsumptionService;
        this.monitoringService = monitoringService;
    }

    @KafkaListener(topics = "monitoring", groupId = "group_id", containerFactory = "monitoringListenerContainerFactory")
    void monitoringListener(MessageDTO data) {
        System.out.println("monitoring: " + data);

        monitoringService.handleMessage(data);
        System.out.println(monitoringService.printUserDeviceConsumption(data.username(), data.device()));
    }

    @KafkaListener(topics = "device", groupId = "group_id", containerFactory = "deviceListenerContainerFactory")
    void deviceListener(DeviceUpdateDTO data) {
        System.out.println("device updates: " + data);

        maxConsumptionService.updateEnergyConsumption(data);
    }
}