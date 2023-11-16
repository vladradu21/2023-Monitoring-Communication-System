package com.sd.monitoringcommunication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import static java.lang.Thread.sleep;

@SpringBootApplication
public class MonitoringCommunicationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringCommunicationSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
		return args -> {
			for (int i = 0; i < 1000; i++) {
				kafkaTemplate.send("device", "Hello, World!" + i);
				sleep(1000);
			}
		};
	}
}
