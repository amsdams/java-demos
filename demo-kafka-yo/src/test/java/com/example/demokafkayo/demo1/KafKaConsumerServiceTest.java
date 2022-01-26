package com.example.demokafkayo.demo1;

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
class KafKaConsumerServiceTest {

	@Autowired
	KafKaProducerService kafKaProducerService;

	@Autowired
	KafKaConsumerService kafKaConsumerService;

	@Test
	void test() {
		kafKaProducerService.sendMessage("message");
		waitAtMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			log.info("message {}", kafKaConsumerService.getMessage());
			Assertions.assertEquals("message", kafKaConsumerService.getMessage());
		});

	}

}
