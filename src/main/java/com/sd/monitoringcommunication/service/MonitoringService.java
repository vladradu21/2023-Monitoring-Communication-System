package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.HourlyConsumptionDTO;
import com.sd.monitoringcommunication.dto.MessageDTO;
import com.sd.monitoringcommunication.model.HourlyConsumption;
import com.sd.monitoringcommunication.repository.HourlyConsumptionRepository;
import com.sd.monitoringcommunication.repository.MaxConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitoringService {
    private final Map<String, Map<String, List<MessageDTO>>> userDeviceConsumptions = new ConcurrentHashMap<>();
    private final HourlyConsumptionRepository hourlyConsumptionRepository;
    private final MaxConsumptionRepository maxConsumptionRepository;

    @Autowired
    public MonitoringService(HourlyConsumptionRepository hourlyConsumptionRepository, MaxConsumptionRepository maxConsumptionRepository) {
        this.hourlyConsumptionRepository = hourlyConsumptionRepository;
        this.maxConsumptionRepository = maxConsumptionRepository;
    }

    public void handleMessage(MessageDTO data) {
        userDeviceConsumptions
                .computeIfAbsent(data.username(), k -> new ConcurrentHashMap<>())
                .computeIfAbsent(data.device(), k -> new ArrayList<>())
                .add(data);
    }

    @Scheduled(fixedDelay = 60000)
    public void calculateHourlyConsumption() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneHourAgo = currentDateTime.minusHours(1).minusMinutes(1);

        userDeviceConsumptions.forEach((username, devices) ->
                devices.forEach((device, dataList) -> {
                    double hourlyConsumption = dataList.stream()
                            .filter(entry ->
                                    entry.time().isAfter(oneHourAgo) && entry.time().isBefore(currentDateTime)
                            )
                            .mapToDouble(MessageDTO::consumption)
                            .sum();
                    hourlyConsumptionRepository.save(
                            new HourlyConsumption(username, device, hourlyConsumption)
                    );
                    handleConsumption(
                            new HourlyConsumptionDTO(username, device, hourlyConsumption, oneHourAgo, currentDateTime)
                    );
                })
        );
    }

    private void handleConsumption(HourlyConsumptionDTO consumptionDTO) {
        System.out.println(consumptionDTO);

        maxConsumptionRepository.findByUsernameAndDeviceName(consumptionDTO.username(), consumptionDTO.deviceName())
                .ifPresent(maxConsumption -> {
                    if (consumptionDTO.averageConsumption() > maxConsumption.getMaxConsumption()) {
                        System.out.println(
                                "Consumption for " + consumptionDTO.username()
                                        + " " + consumptionDTO.deviceName() + " is higher than max consumption");
                    }
                });
    }
}