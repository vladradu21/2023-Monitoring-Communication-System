package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.dto.MonitoringMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
    void monitoringListener(MonitoringMessageDTO data) {
        System.out.println("monitoring: " + data);
        monitoringService.handleMessage(data);
    }

    @KafkaListener(topics = "device", groupId = "group_id", containerFactory = "deviceListenerContainerFactory")
    void deviceListener(DeviceUpdateDTO data) {
        System.out.println("device updates: " + data);
        maxConsumptionService.updateEnergyConsumption(data);
    }
}