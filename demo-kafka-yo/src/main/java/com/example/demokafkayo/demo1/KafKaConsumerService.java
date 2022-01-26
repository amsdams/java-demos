package com.example.demokafkayo.demo1;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKaConsumerService {

	@Getter
	private String message;
	
	@KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
	public void consume(String message) {
		log.info(String.format("Message recieved -> %s", message));
		this.message = message;
	}
}
