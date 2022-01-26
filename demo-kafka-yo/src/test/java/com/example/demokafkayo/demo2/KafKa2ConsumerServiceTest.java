package com.example.demokafkayo.demo2;

import static org.awaitility.Awaitility.waitAtMost;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import lombok.extern.slf4j.Slf4j;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@SpringBootTest
@Slf4j
class KafKa2ConsumerServiceTest {

	private static final String DEFAULT_PAYLOAD = "payload2";

	private static final String DEFAULT_TOPIC = "topic2";
	@Autowired
	KafKa2ProducerService kafKa2ProducerService;

	@Autowired
	KafKa2ConsumerService kafKa2ConsumerService;

	@Test
	void test() {
		kafKa2ProducerService.send(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
		waitAtMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			log.info("payload {}", kafKa2ConsumerService.getPayload());
			Assertions.assertNotNull(kafKa2ConsumerService.getPayload());
			Assertions.assertEquals(DEFAULT_PAYLOAD, kafKa2ConsumerService.getPayload().value());
		});

	}
	
	@Test
	void test2() throws InterruptedException {
		kafKa2ProducerService.send(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
			log.info("payload {}", kafKa2ConsumerService.getLatch().await(5, TimeUnit.SECONDS));
			Assertions.assertEquals(0L, kafKa2ConsumerService.getLatch().getCount());
			var payload = kafKa2ConsumerService.getPayload();
			Assertions.assertEquals(DEFAULT_PAYLOAD, payload.value());
		

	}
	
}
