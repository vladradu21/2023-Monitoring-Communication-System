package com.sd.monitoringcommunication.repository;

import com.sd.monitoringcommunication.model.HourlyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourlyConsumptionRepository extends JpaRepository<HourlyConsumption, Integer> {
}
