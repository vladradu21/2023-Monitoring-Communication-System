package com.sd.monitoringcommunication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hourly_consumption")
public class HourlyConsumption {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String deviceName;

    private double averageConsumption;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;

    public HourlyConsumption(String username, String deviceName, double averageConsumption, LocalDateTime startTime, LocalDateTime endTime) {
        this.username = username;
        this.deviceName = deviceName;
        this.averageConsumption = averageConsumption;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}