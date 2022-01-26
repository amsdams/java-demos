package com.example.demokafkayo.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaProducerService
{


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message)
    {
        log.info(String.format("Message sent -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
    }
}
