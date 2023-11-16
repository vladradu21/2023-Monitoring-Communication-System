package com.sd.monitoringcommunication.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "device", groupId = "group_id")
    void listener(String data) {
        System.out.println("listener1 recived: " + data);
    }
}
