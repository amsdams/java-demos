package com.amsdams.testgroovy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WriteService {
	public void write(String content, Path path) throws IOException {

		log.info("path {}", path);
		Path parentDir = path.getParent();
		if (!Files.exists(parentDir))
			Files.createDirectories(parentDir);

		// string -> bytes
		Files.write(path, content.getBytes(StandardCharsets.UTF_8));
	}
}
