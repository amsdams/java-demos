package com.example.demokafkayo.demo3;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka3")
public class Kafka3ProducerController
{
	private final KafKa3ProducerService kafKa3ProducerService;

	@Autowired
	public Kafka3ProducerController(KafKa3ProducerService producerService)
	{
		this.kafKa3ProducerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("topic") String topic, @RequestParam("payload") String payload)
	{
		var producerRecord = new ProducerRecord<String, String>(topic, payload);
		this.kafKa3ProducerService.send(producerRecord);
	}
}
