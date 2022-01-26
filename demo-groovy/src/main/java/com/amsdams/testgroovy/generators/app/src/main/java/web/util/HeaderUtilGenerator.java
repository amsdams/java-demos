package com.amsdams.testgroovy.generators.app.src.main.java.web.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.text.CaseUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.config.Entity;
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Data
public class HeaderUtilGenerator implements Generator {

	private static final String CLASS_NAME = "HeaderUtil";
	private static final String VAR_NAME = "headerUtil";
	private static final String HEADER_UTIL_JAVA_GROOVY = "app/src/main/java/package/web/util/header-util-java.groovy";

	@Autowired
	WriteService writeService;

	Set<String> getImports(Entity entity, String package1) {
		String name = CaseUtils.toCamelCase(entity.getName(), true, '_');
		Set<String> imports = new HashSet<>();
		imports.add("import " + package1 + "." + name);
		return imports;

	}

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("headerUtilClassName", this.getClassName());
		binding.put("packageName", this.getPackagePathParent());

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
		return new ClassPathResource(HEADER_UTIL_JAVA_GROOVY);
	}
}
