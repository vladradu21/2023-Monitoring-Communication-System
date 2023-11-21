package com.sd.monitoringcommunication.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record MonitoringMessageDTO(
        String username,
        String device,
        LocalDateTime time,
        double consumption
) {
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = time.format(formatter);

        return "{ username= " + username +
                ", device= " + device +
                ", time= " + formatDateTime +
                ", consumption= " + consumption +
                " }";
    }
}