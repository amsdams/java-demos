package com.amsdams.testgroovy.generators.app.src.main.java.repository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.generators.app.src.main.java.domain.EntityGenerator;
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
public class RepositoryGenerator implements Generator {

	private static final String REPOSITORY_JAVA_GROOVY = "app/src/main/java/package/repository/repository-java.groovy";

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(REPOSITORY_JAVA_GROOVY);
	}

	WriteService writeService;
	//DtoGenerator dtoGenerator;
	EntityGenerator entityGenerator;
	String filePathParent;
	String packagePathParent;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();
		var imports = new HashSet<>();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		imports.add("import " + this.getPackagePath());

		binding.put("repositoryClassName", this.getClassName());
		imports.add("import " + this.getPackagePath());

		binding.put("entityClassName", entityGenerator.getClassName());
		imports.add("import " + entityGenerator.getPackagePath());

		//binding.put("dtoClassName", dtoGenerator.getClassName());
		//binding.put("dtoVarName", dtoGenerator.getVarName());
		//imports.add("import " + dtoGenerator.getPackagePath());

		binding.put("imports", imports);

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
		return entityGenerator.getClassName() + "Repository";

	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "Repository";

	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}
}
