package com.example.demokafkayo.demo4;

import static org.awaitility.Awaitility.waitAtMost;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@SpringBootTest
@Slf4j
class KafKa4ConsumerServiceTest {

	private static MessageDTO DEFAULT_PAYLOAD;

	private static final String DEFAULT_TOPIC = "topic4";

	@Autowired
	KafKa4ProducerService KafKa4ProducerService;

	@Autowired
	KafKa4ConsumerService KafKa4ConsumerService;

	@BeforeAll
	static void init() {
		DEFAULT_PAYLOAD = new MessageDTO("message", "version");
	}

	@Test
	void test() {
		var producerRecord = new ProducerRecord<String, MessageDTO>(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
		KafKa4ProducerService.send(producerRecord);
		waitAtMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
			log.info("payload {}", KafKa4ConsumerService.getPayload());
			Assertions.assertNotNull(KafKa4ConsumerService.getPayload());
			log.info("value {}", KafKa4ConsumerService.getPayload().value());
			var payload = KafKa4ConsumerService.getPayload().value();
			ObjectMapper objectMapper = new ObjectMapper();

			var messageDto = objectMapper.readValue(payload.toString(), MessageDTO.class);
			Assertions.assertEquals(DEFAULT_PAYLOAD, messageDto);
		});

	}

	@Test
	void test2() throws InterruptedException, JsonMappingException, JsonProcessingException {
		var producerRecord = new ProducerRecord<String, MessageDTO>(DEFAULT_TOPIC, DEFAULT_PAYLOAD);
		KafKa4ProducerService.send(producerRecord);
		log.info("payload {}", KafKa4ConsumerService.getLatch().await(5, TimeUnit.SECONDS));
		Assertions.assertEquals(0L, KafKa4ConsumerService.getLatch().getCount());
		var payload = KafKa4ConsumerService.getPayload().value();
		ObjectMapper objectMapper = new ObjectMapper();
		var messageDto = objectMapper.readValue(payload.toString(), MessageDTO.class);

		Assertions.assertEquals(DEFAULT_PAYLOAD, messageDto);

	}

}
