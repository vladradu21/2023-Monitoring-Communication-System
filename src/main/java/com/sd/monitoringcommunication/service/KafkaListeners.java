package com.sd.monitoringcommunication.service;

import com.sd.monitoringcommunication.dto.MessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "device", groupId = "group_id", containerFactory = "listenerContainerFactory")
    void listener(MessageDTO data) {
        System.out.println("recived: " + data);
    }
}
