package com.example.demokafkayo.demo3;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafKa3ConsumerService {

	@Getter
	private CountDownLatch latch = new CountDownLatch(1);
	@Getter
	@Setter
	private ConsumerRecord<?, ?> payload = null;

	@KafkaListener(topics = "topic3")
	public void receive(ConsumerRecord<?, ?> consumerRecord) {
		log.info("received payload='{}'", consumerRecord);
		setPayload(consumerRecord);
		latch.countDown();
	}

}
