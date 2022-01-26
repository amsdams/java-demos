package com.example.demokafkayo.demo3;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKa3ProducerService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(ProducerRecord<String,String> producerRecord) {
		log.info("sending producerRecord='{}'", producerRecord);
		kafkaTemplate.send(producerRecord);
	}
}
