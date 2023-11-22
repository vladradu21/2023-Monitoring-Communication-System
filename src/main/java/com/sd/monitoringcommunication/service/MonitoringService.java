package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.config.MyWebSocketHandler;
import com.sd.monitoringcommunication.dto.HourlyConsumptionDTO;
import com.sd.monitoringcommunication.dto.MonitoringMessageDTO;
import com.sd.monitoringcommunication.dto.NotificationDTO;
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
    private final Map<String, Map<String, List<MonitoringMessageDTO>>> userDeviceConsumptions = new ConcurrentHashMap<>();
    private final HourlyConsumptionRepository hourlyConsumptionRepository;
    private final MaxConsumptionRepository maxConsumptionRepository;
    private final MyWebSocketHandler myWebSocketHandler;

    @Autowired
    public MonitoringService(HourlyConsumptionRepository hourlyConsumptionRepository, MaxConsumptionRepository maxConsumptionRepository, MyWebSocketHandler myWebSocketHandler) {
        this.hourlyConsumptionRepository = hourlyConsumptionRepository;
        this.maxConsumptionRepository = maxConsumptionRepository;
        this.myWebSocketHandler = myWebSocketHandler;
    }

    public void handleMessage(MonitoringMessageDTO data) {
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
                                    entry.time().isAfter(oneHourAgo) && entry.time().isBefore(currentDateTime))
                            .mapToDouble(MonitoringMessageDTO::consumption)
                            .sum();
                    hourlyConsumptionRepository.save(
                            new HourlyConsumption(username, device, hourlyConsumption, oneHourAgo, currentDateTime)
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
                        sendNotification(consumptionDTO);
                    }
                });
    }

    private void sendNotification(HourlyConsumptionDTO consumptionDTO) {
        NotificationDTO notificationDTO = new NotificationDTO(
                "Consumption allert for user: " + consumptionDTO.username()
                        + " and device: " + consumptionDTO.deviceName(),
                "Max consumption exceeded: " + consumptionDTO.averageConsumption()
        );

        System.out.println(notificationDTO);
        try {
            myWebSocketHandler.sendNotificationToSession(notificationDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}