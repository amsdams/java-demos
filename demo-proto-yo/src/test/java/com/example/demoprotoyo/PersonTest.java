package com.example.demoprotoyo;

import org.junit.jupiter.api.Test;

import com.example.protobuf.PersonProtos.Person;

import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.ValidatorIndex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonTest {

	@Test
	void createPerson() throws ValidationException {

		Person pb = Person.newBuilder().setEmail("amsdams@github.fake").setId(1).build();
		log.info(" pb{}", pb);

		// Create a validator index that reflectively loads generated validators
		ValidatorIndex index = new ReflectiveValidatorIndex();
		// Assert that a message is valid
		index.validatorFor(pb.getClass()).assertValid(pb);
		boolean valid = index.validatorFor(pb.getClass()).isValid(index);
		log.info(" valid{}", valid);

	}

	@Test
	void createPerson2() throws ValidationException {

		Person pb = Person.newBuilder().setEmail("amsdams@github.fake").setId(1).build();
		log.info(" pb{}", pb);

		// Create a validator index that reflectively loads generated validators
		ValidatorIndex index = new ReflectiveValidatorIndex();
		boolean valid = index.validatorFor(pb.getClass()).isValid(index);
		log.info(" valid{}", valid);

	}
}
