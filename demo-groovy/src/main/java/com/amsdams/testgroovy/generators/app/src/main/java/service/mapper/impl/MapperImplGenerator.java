package com.amsdams.testgroovy.generators.app.src.main.java.service.mapper.impl;

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
import com.amsdams.testgroovy.generators.app.src.main.java.service.mapper.EntityMapperGenerator;
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
public class MapperImplGenerator implements Generator {

	private static final String MAPPER_IMPL_JAVA_GROOVY = "app/src/main/java/package/service/mapper/impl/mapper-impl-java.groovy";

	WriteService writeService;
	EntityMapperGenerator entityMapperGenerator;
	DtoGenerator dtoGenerator;
	String filePathParent;
	String packagePathParent;
	EntityGenerator entityGenerator;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		log.info("starting bindings..");
		Set<String> imports = new HashSet<>();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());

		binding.put("mapperImplClassName", this.getClassName());

		binding.put("dtoClassName", dtoGenerator.getClassName());
		imports.add("import " + dtoGenerator.getPackagePath());

		binding.put("entityClassName", entityGenerator.getClassName());
		binding.put("entityVarName", entityGenerator.getVarName());
		imports.add("import " + entityGenerator.getPackagePath());

		binding.put("entityMapperClassName", entityMapperGenerator.getClassName());
		imports.add("import " + entityMapperGenerator.getPackagePath());

		binding.put("imports", imports);

		log.info("finished bindings..");

		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(MAPPER_IMPL_JAVA_GROOVY);
	}

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;
	}

	@Override
	public String getClassName() {
		return entityGenerator.getClassName() + "MapperImpl";
	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "MapperImpl";

	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();
	}

}
