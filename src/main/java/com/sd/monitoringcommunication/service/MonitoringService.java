package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitoringService {

    private final Map<String, Map<String, List<Double>>> userDeviceConsumptions  = new ConcurrentHashMap<>();

    void handleMessage(MessageDTO data) {
        userDeviceConsumptions
                .computeIfAbsent(data.username(), k -> new ConcurrentHashMap<>())
                .computeIfAbsent(data.device(), k -> new ArrayList<>())
                .add(data.consumption());
    }

    private Double calculateHourlyConsumptionForUserAndDevice(String username, String device) {
        List<Double> consumptions = userDeviceConsumptions
                .getOrDefault(username, new ConcurrentHashMap<>())
                .getOrDefault(device, new ArrayList<>());

        return consumptions.stream().mapToDouble(Double::doubleValue).sum();
    }

    public String printUserDeviceConsumption(String username, String device) {
        return "User: " + username +
                " Device: " + device +
                " Consumption: " + calculateHourlyConsumptionForUserAndDevice(username, device);
    }
}
