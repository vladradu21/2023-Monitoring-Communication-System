package com.sd.monitoringcommunication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.dto.MonitoringMessageDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private final MaxConsumptionService maxConsumptionService;
    private final MonitoringService monitoringService;
    private final DeleteDeviceService deleteDeviceService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    public KafkaListeners(MaxConsumptionService maxConsumptionService, MonitoringService monitoringService, DeleteDeviceService deleteDeviceService) {
        this.maxConsumptionService = maxConsumptionService;
        this.monitoringService = monitoringService;
        this.deleteDeviceService = deleteDeviceService;
    }

    @KafkaListener(topics = "monitoring", groupId = "m_group")
    void monitoringListener(ConsumerRecord<?, ?> record) {
        System.out.println("monitoring: " + record.value());
        MonitoringMessageDTO monitoringMessageDTO = objectMapper.convertValue(record.value(), MonitoringMessageDTO.class);
        monitoringService.handleMessage(monitoringMessageDTO);
    }

    @KafkaListener(topics = "device", groupId = "d_group")
    void deviceListener(ConsumerRecord<?, ?> record) {
        System.out.println("device updates: " + record.value());
        DeviceUpdateDTO deviceUpdateDTO = objectMapper.convertValue(record.value(), DeviceUpdateDTO.class);
        maxConsumptionService.updateEnergyConsumption(deviceUpdateDTO);
    }

    @KafkaListener(topics = "deleteDevice", groupId = "del_group")
    void deleteDeviceListener(ConsumerRecord<?, ?> record) {
        System.out.println("Delete: " + record.value());
        DeviceUpdateDTO deviceUpdateDTO = objectMapper.convertValue(record.value(), DeviceUpdateDTO.class);
        deleteDeviceService.deleteDevice(deviceUpdateDTO);
    }
}