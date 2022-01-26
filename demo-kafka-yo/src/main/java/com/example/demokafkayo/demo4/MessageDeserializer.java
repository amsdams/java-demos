package com.example.demokafkayo.demo4;

import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDeserializer implements Deserializer<MessageDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

  

	@Override
	public MessageDTO deserialize(String topic, byte[] data) {
		try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            log.info("Deserializing...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MessageDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
	}

	
}