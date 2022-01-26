package com.example.demokafkayo.demo4;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSerializer implements Serializer<MessageDTO> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, MessageDTO data) {
		try {
			if (data == null) {
				log.info("Null received at serializing");
				return new byte[0];
			}
			log.info("Serializing...");
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error when serializing MessageDto to byte[]");
		}
	}

}