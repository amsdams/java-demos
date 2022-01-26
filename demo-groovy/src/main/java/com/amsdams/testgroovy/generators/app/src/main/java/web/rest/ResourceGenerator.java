package com.amsdams.testgroovy.generators.app.src.main.java.web.rest;

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
import com.amsdams.testgroovy.generators.app.src.main.java.repository.RepositoryGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.ServiceGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.dto.DtoGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.errors.BadRequestAlertExceptionGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.util.HeaderUtilGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.util.ResponseUtilGenerator;
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
public class ResourceGenerator implements Generator {

	private static final String RESOURCE_JAVA_GROOVY = "app/src/main/java/package/web/rest/resource-java.groovy";

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(RESOURCE_JAVA_GROOVY);
	}

	WriteService writeService;

	HeaderUtilGenerator headerUtilGenerator;

	ServiceGenerator serviceGenerator;

	RepositoryGenerator repositoryGenerator;

	DtoGenerator dtoGenerator;

	BadRequestAlertExceptionGenerator badRequestAlertExceptionGenerator;

	EntityGenerator entityGenerator;

	ResponseUtilGenerator responseUtilGenerator;
	String filePathParent;
	String packagePathParent;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		var imports = new HashSet<>();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		imports.add("import " + this.getPackagePath());

		binding.put("resourceClassName", this.getClassName());

		binding.put("entityClassName", entityGenerator.getClassName());
		// TODO maybe resourcePath REST
		binding.put("entityVarName", entityGenerator.getVarName());
		// imports.add("import " + entityHelper.getEntityPackagePath());

		binding.put("serviceClassName", serviceGenerator.getClassName());
		binding.put("serviceVarName", serviceGenerator.getVarName());
		imports.add("import " + serviceGenerator.getPackagePath());

		binding.put("repositoryClassName", repositoryGenerator.getClassName());
		binding.put("repositoryVarName", repositoryGenerator.getVarName());
		imports.add("import " + repositoryGenerator.getPackagePath());

		binding.put("dtoClassName", dtoGenerator.getClassName());
		binding.put("dtoVarName", dtoGenerator.getVarName());
		imports.add("import " + dtoGenerator.getPackagePath());

		binding.put("responseUtilClassName", responseUtilGenerator.getClassName());
		binding.put("responseUtilVarName", responseUtilGenerator.getVarName());
		imports.add("import " + responseUtilGenerator.getPackagePath());

		binding.put("headerUtilClassName", headerUtilGenerator.getClassName());
		imports.add("import " + headerUtilGenerator.getPackagePath());

		binding.put("badRequestAlertExceptionClassName", badRequestAlertExceptionGenerator.getClassName());
		imports.add("import " + badRequestAlertExceptionGenerator.getPackagePath());

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
		return entityGenerator.getClassName() + "Resource";
	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "Resource";

	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();
	}

}
