package com.amsdams.testgroovy.generators.app.src.main.java.service;

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
import com.amsdams.testgroovy.generators.app.src.main.java.domain.EntityGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.dto.DtoGenerator;
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceGenerator implements Generator {

	private static final String SERVICE_JAVA_GROOVY = "app/src/main/java/package/service/service-java.groovy";

	String filePathParent;
	String packagePathParent;
	WriteService writeService;
	DtoGenerator dtoGenerator;
	EntityGenerator entityGenerator;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		Set<String> imports = new HashSet<>();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		binding.put("serviceClassName", this.getClassName());
		imports.add("import " + this.getPackagePath());

		binding.put("dtoClassName", dtoGenerator.getClassName());
		binding.put("dtoVarName", dtoGenerator.getVarName());
		imports.add("import " + dtoGenerator.getPackagePath());

		binding.put("imports", imports);

		log.info("bindings: {}", binding.toString());
		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(SERVICE_JAVA_GROOVY);
	}

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;

	}

	@Override
	public String getClassName() {
		return entityGenerator.getClassName() + "Service";
	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "Service";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}
}
