package com.sd.monitoringcommunication.controller;

import com.sd.monitoringcommunication.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    @Autowired
    public MessageController(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MessageDTO messageDTO) {
        kafkaTemplate.send("monitoring", messageDTO);
    }
}