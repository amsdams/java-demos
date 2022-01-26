package com.amsdams.testgroovy;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class MainGeneratorTest {
	@Autowired
	MainGenerator mainGenerator;

	@Test
	void testGenerate() throws CompilationFailedException, ClassNotFoundException, IOException {

		mainGenerator.generate();

	}

}
