package com.sd.monitoringcommunication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@EnableScheduling
public class MonitoringCommunicationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringCommunicationSystemApplication.class, args);
	}

	@Value("${usermicroservice.port}")
	private int user_port;
	@Value("${usermicroservice.host}")
	private String user_host;
	@Bean
	public WebClient webClient() {
		String baseUrl = UriComponentsBuilder.fromUriString("http://" + user_host + ":")
				.port(user_port)
				.path("/api")
				.build()
				.toUriString();

		return WebClient.builder().baseUrl(baseUrl).build();
	}
}