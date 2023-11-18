package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.model.MaxConsumption;
import com.sd.monitoringcommunication.repository.MaxConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaxConsumptionService {
    private final MaxConsumptionRepository maxConsumptionRepository;

    @Autowired
    public MaxConsumptionService(MaxConsumptionRepository maxConsumptionRepository) {
        this.maxConsumptionRepository = maxConsumptionRepository;
    }

    public void updateEnergyConsumption(DeviceUpdateDTO data) {
        maxConsumptionRepository.findByUsernameAndDeviceName(data.username(), data.deviceName())
                .ifPresentOrElse(
                        energyConsumption -> {
                            energyConsumption.setMaxConsumption(data.maxConsumption());
                            maxConsumptionRepository.save(energyConsumption);
                        },
                        () -> {
                            MaxConsumption maxConsumption = new MaxConsumption(
                                    data.username(),
                                    data.deviceName(),
                                    data.maxConsumption()
                            );
                            maxConsumptionRepository.save(maxConsumption);
                        }
                );
    }
}