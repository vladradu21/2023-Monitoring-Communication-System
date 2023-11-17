package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.HourlyConsumptionDTO;
import com.sd.monitoringcommunication.dto.MessageDTO;
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

                    System.out.println(new HourlyConsumptionDTO(username, device, hourlyConsumption, oneHourAgo, currentDateTime));
                })
        );
    }
}