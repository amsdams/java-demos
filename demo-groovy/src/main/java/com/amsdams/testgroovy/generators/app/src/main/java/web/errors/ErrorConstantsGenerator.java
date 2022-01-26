package com.amsdams.testgroovy.generators.app.src.main.java.web.errors;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class ErrorConstantsGenerator implements Generator {

	private static final String ERROR_CONSTANTS_JAVA_GROOVY = "app/src/main/java/package/web/errors/error-constants-java.groovy";
	private static final String CLASS_NAME = "ErrorConstants";
	private static final String VAR_NAME = "errorConstants";

	WriteService writeService;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();
		Set<String> imports = new HashSet<>();
		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("errorConstantsClassName", this.getClassName());
		binding.put("packageName", this.getPackagePathParent());
		binding.put("imports", imports);
		log.info("binding {}", binding);

		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	private String filePathParent;
	private String packagePathParent;

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;

	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getVarName() {
		return VAR_NAME;
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(ERROR_CONSTANTS_JAVA_GROOVY);
	}
}
