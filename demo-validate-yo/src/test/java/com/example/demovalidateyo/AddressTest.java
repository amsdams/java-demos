package com.example.demovalidateyo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {

	@Test
	void createAddress() {
		Address address = new Address("", "", "", "");
		Assertions.assertNotNull(address);
	}
}
