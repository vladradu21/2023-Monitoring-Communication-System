package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.DeviceUpdateDTO;
import com.sd.monitoringcommunication.repository.MaxConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteDeviceService {
    private final MaxConsumptionRepository maxConsumptionRepository;

    @Autowired
    public DeleteDeviceService(MaxConsumptionRepository maxConsumptionRepository) {
        this.maxConsumptionRepository = maxConsumptionRepository;
    }

    public void deleteDevice(DeviceUpdateDTO device) {
        maxConsumptionRepository.findByUsernameAndDeviceName(
                device.username(),
                device.deviceName()).ifPresent(maxConsumptionRepository::delete);
    }
}