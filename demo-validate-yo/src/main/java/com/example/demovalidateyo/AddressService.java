package com.example.demovalidateyo;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
public class AddressService {

	public void doSomething(@Valid Address address) {
		/*ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		var validator = factory.getValidator();
		Set<ConstraintViolation<Address>> violations = validator.validate(address);
		if (!violations.isEmpty()) {
		  throw new ConstraintViolationException(violations);
		}
		*/
		log.info("address {}", address);
	}
}
