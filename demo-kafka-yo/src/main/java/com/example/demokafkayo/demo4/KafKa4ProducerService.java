package com.example.demokafkayo.demo4;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKa4ProducerService {

	
	public KafKa4ProducerService(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		Map<String, Object> config = new HashMap<>();
		
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");

		kafkaTemplate.getProducerFactory().updateConfigs(config);
		//log.info("kafkaTemplate {}", ;
	}


	@Autowired
	private KafkaTemplate<String, MessageDTO> kafkaTemplate;

	
	
	public void send(ProducerRecord<String,MessageDTO> producerRecord) {
		log.info("sending producerRecord='{}'", producerRecord);
		kafkaTemplate.send(producerRecord);
	}
}
