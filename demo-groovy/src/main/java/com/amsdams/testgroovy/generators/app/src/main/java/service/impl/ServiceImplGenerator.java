package com.amsdams.testgroovy.generators.app.src.main.java.service.impl;

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
import com.amsdams.testgroovy.generators.app.src.main.java.repository.RepositoryGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.ServiceGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.dto.DtoGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.mapper.impl.MapperImplGenerator;
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

public class ServiceImplGenerator implements Generator {

	private static final String SERVICE_IMPL_JAVA_GROOVY = "app/src/main/java/package/service/impl/service-impl-java.groovy";

	WriteService writeService;
	RepositoryGenerator repositoryGenerator;
	ServiceGenerator serviceGenerator;
	MapperImplGenerator mapperImplGenerator;
	DtoGenerator dtoGenerator;
	EntityGenerator entityGenerator;
	String filePathParent;
	String packagePathParent;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		Set<String> imports = new HashSet<>();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		binding.put("serviceImplClassName", this.getClassName());

		binding.put("serviceClassName", serviceGenerator.getClassName());
		imports.add("import " + serviceGenerator.getPackagePath());

		binding.put("dtoClassName", dtoGenerator.getClassName());
		binding.put("dtoVarName", dtoGenerator.getVarName());
		imports.add("import " + dtoGenerator.getPackagePath());

		binding.put("mapperImplClassName", mapperImplGenerator.getClassName());
		binding.put("mapperImplVarName", mapperImplGenerator.getVarName());
		imports.add("import " + mapperImplGenerator.getPackagePath());

		binding.put("repositoryClassName", repositoryGenerator.getClassName());
		binding.put("repositoryVarName", repositoryGenerator.getVarName());
		imports.add("import " + repositoryGenerator.getPackagePath());

		binding.put("entityClassName", entityGenerator.getClassName());
		binding.put("entityVarName", entityGenerator.getVarName());
		imports.add("import " + entityGenerator.getPackagePath());

		binding.put("imports", imports);

		log.info("bindings: {}", binding.toString());

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
		return entityGenerator.getClassName() + "ServiceImpl";

	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "ServiceImpl";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(SERVICE_IMPL_JAVA_GROOVY);

	}
}
