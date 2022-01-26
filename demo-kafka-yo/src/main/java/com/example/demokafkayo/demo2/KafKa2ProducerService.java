package com.example.demokafkayo.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKa2ProducerService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, String payload) {
		log.info("sending payload='{}' to topic='{}'", payload, topic);
		kafkaTemplate.send(topic, payload);
	}
}
