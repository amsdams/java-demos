package com.example.demovalidateyo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTest {

	@Autowired
	AddressService addressService;
	
	@Test
	void doSomething() {
		Address address = new Address("", "", "", "");
		addressService.doSomething(address);
	}
}
