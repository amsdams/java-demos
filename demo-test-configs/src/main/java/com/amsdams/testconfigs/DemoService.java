package com.amsdams.testconfigs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DemoService {
	String getDisplayFreeDiskSpace(String path) {
		List<String> command = new ArrayList<>();
		command.add("bash");
		command.add("-c");
		command.add("df");
		command.add("-h");
		command.add(path);
		return this.run(command);

	}

	String getDisplayDiskUsageStatistics(String path) {

		List<String> command = new ArrayList<>();
		command.add("bash");
		command.add("-c");
		command.add("du");
		command.add("-h");
		command.add(path);
		return this.run(command);

	}

	@SneakyThrows
	String run(List<String> command) {
		StringBuilder output = new StringBuilder();

		ProcessBuilder processBuilder = new ProcessBuilder();

		// Run a shell command
		processBuilder.command(command);
		log.info("command:\r\n {}", processBuilder.command());

		Process process;

		process = processBuilder.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0) {
			log.info("Success!");
			log.info("output:\r\n {}", output);
			//System.exit(0);
		} else {
			// abnormal...
			//System.exit(0);
		}

		return output.toString();

	}

}
