package com.amsdams.testgroovy.generators.app.src.main.java;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.config.Application;
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationGenerator implements Generator {

	private static final String APPLICATION_JAVA_GROOVY = "app/src/main/java/application-java.groovy";

	WriteService writeService;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("applicationClassName", this.getClassName());
		binding.put("packageName", this.getPackagePathParent());

		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	private Application application;
	private String filePathParent;
	private String packagePathParent;

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;

	}

	@Override
	public String getClassName() {
		return "Application";
	}

	@Override
	public String getVarName() {
		return "application";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "/" + getClassName();

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(APPLICATION_JAVA_GROOVY);
	}

}
