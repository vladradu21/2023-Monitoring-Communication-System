package com.sd.monitoringcommunication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "max_consumption")
public class MaxConsumption {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String deviceName;

    private double maxConsumption;

    public MaxConsumption(String username, String deviceName, double maxConsumption) {
        this.username = username;
        this.deviceName = deviceName;
        this.maxConsumption = maxConsumption;
    }
}