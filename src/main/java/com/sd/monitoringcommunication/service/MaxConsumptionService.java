package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.model.EnergyConsumption;
import com.sd.monitoringcommunication.repository.EnergyConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaxConsumptionService {
    private final EnergyConsumptionRepository energyConsumptionRepository;

    @Autowired
    public MaxConsumptionService(EnergyConsumptionRepository energyConsumptionRepository) {
        this.energyConsumptionRepository = energyConsumptionRepository;
    }

    public void updateEnergyConsumption(DeviceUpdateDTO data) {
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