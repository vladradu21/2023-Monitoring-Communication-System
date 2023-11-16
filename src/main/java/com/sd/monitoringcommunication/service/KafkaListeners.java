package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.dto.MessageDTO;
import com.sd.monitoringcommunication.model.EnergyConsumption;
import com.sd.monitoringcommunication.repository.EnergyConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final EnergyConsumptionRepository energyConsumptionRepository;

    @Autowired
    public KafkaListeners(EnergyConsumptionRepository energyConsumptionRepository) {
        this.energyConsumptionRepository = energyConsumptionRepository;
    }

    @KafkaListener(topics = "monitoring", groupId = "group_id", containerFactory = "monitoringListenerContainerFactory")
    void monitoringListener(MessageDTO data) {
        System.out.println("monitoring: " + data);
    }

    @KafkaListener(topics = "device", groupId = "group_id", containerFactory = "deviceListenerContainerFactory")
    void deviceListener(DeviceUpdateDTO data) {
        System.out.println("device updates: " + data);

        energyConsumptionRepository.findByUsernameAndDeviceName(data.username(), data.deviceName())
                .ifPresentOrElse(
                        energyConsumption -> {
                            energyConsumption.setMaxConsumption(data.maxConsumption());
                            energyConsumptionRepository.save(energyConsumption);
                        },
                        () -> {
                            EnergyConsumption energyConsumption = new EnergyConsumption(
                                    data.username(),
                                    data.deviceName(),
                                    data.maxConsumption()
                            );
                            energyConsumptionRepository.save(energyConsumption);
                        }
                );
    }
}