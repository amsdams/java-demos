package com.amsdams.testgroovy.generators.app.src.main.java.service.mapper;

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
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class EntityMapperGenerator implements Generator {

	private static final String MAPPER_JAVA_GROOVY = "app/src/main/java/package/service/mapper/mapper-java.groovy";

	private WriteService writeService;
	private String filePathParent;
	private String packagePathParent;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("entityMapperClassName", this.getClassName());
		binding.put("packageName", this.getPackagePathParent());

		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;
	}

	@Override
	public String getClassName() {
		return "EntityMapper";
	}

	@Override
	public String getVarName() {
		return "entityMapper";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();
	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(MAPPER_JAVA_GROOVY);
	}

}
