package com.example.demokafkayo.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka2")
public class Kafka2ProducerController
{
	private final KafKa2ProducerService kafKa2ProducerService;

	@Autowired
	public Kafka2ProducerController(KafKa2ProducerService producerService)
	{
		this.kafKa2ProducerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("topic") String topic, @RequestParam("payload") String payload)
	{
		this.kafKa2ProducerService.send(topic, payload);
	}
}
