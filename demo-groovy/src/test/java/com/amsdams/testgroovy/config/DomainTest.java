package com.amsdams.testgroovy.config;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class DomainTest {
	@Autowired
	Domain domain;

	@Test
	@Disabled
	void testReadDomain() throws CompilationFailedException, ClassNotFoundException, IOException {
		domain.writeDomain();
		Application package1 = domain.getApplication();
		log.info("entity {}", package1.toString());
	}
}
