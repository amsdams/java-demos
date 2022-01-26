package com.example.demokafkayo.demo4;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "/kafka4")
public class Kafka4ProducerController
{
	private final KafKa4ProducerService kafKa4ProducerService;

	@Autowired
	public Kafka4ProducerController(KafKa4ProducerService producerService)
	{
		this.kafKa4ProducerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic( @RequestBody MessageDTO payload )
	{
		var topic ="topic4";
		var producerRecord = new ProducerRecord<String, MessageDTO>(topic, payload);
		this.kafKa4ProducerService.send(producerRecord);
	}
}
