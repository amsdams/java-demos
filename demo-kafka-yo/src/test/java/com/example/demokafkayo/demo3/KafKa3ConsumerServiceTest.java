package com.example.demokafkayo.demo3;

import static org.awaitility.Awaitility.waitAtMost;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import lombok.extern.slf4j.Slf4j;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@SpringBootTest
@Slf4j
class KafKa3ConsumerServiceTest {

	private static final String DEFAULT_PAYLOAD = "payload3";

	private static final String DEFAULT_TOPIC = "topic3";

	@Autowired
	KafKa3ProducerService KafKa3ProducerService;

	@Autowired
	KafKa3ConsumerService KafKa3ConsumerService;

	@Test
	void test() {
		var producerRecord = new ProducerRecord<String, String>(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
		KafKa3ProducerService.send(producerRecord);
		waitAtMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			log.info("payload {}", KafKa3ConsumerService.getPayload());
			Assertions.assertNotNull(KafKa3ConsumerService.getPayload());
			Assertions.assertEquals(DEFAULT_PAYLOAD, KafKa3ConsumerService.getPayload().value());
		});

	}

	@Test
	void test2() throws InterruptedException {
		var producerRecord = new ProducerRecord<String, String>(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
		KafKa3ProducerService.send(producerRecord);
		log.info("payload {}", KafKa3ConsumerService.getLatch().await(5, TimeUnit.SECONDS));
		Assertions.assertEquals(0L, KafKa3ConsumerService.getLatch().getCount());
		var payload = KafKa3ConsumerService.getPayload();
		Assertions.assertEquals(DEFAULT_PAYLOAD, payload.value());

	}

}
