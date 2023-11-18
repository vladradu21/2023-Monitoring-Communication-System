package com.sd.monitoringcommunication.repository;

import com.sd.monitoringcommunication.model.MaxConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaxConsumptionRepository extends JpaRepository<MaxConsumption, Integer> {
    Optional<MaxConsumption> findByUsernameAndDeviceName(String username, String deviceName);
}
