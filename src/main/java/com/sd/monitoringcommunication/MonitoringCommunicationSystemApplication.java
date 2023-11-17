package com.sd.monitoringcommunication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringCommunicationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringCommunicationSystemApplication.class, args);
	}
}