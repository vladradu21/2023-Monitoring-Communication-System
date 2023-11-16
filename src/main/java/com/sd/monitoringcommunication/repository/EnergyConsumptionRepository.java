package com.sd.monitoringcommunication.repository;

import com.sd.monitoringcommunication.model.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Integer> {
    Optional <EnergyConsumption> findByUsernameAndDeviceName(String username, String deviceName);
}
