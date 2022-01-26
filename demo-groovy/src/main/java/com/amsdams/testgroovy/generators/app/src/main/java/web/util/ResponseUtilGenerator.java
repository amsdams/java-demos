package com.amsdams.testgroovy.generators.app.src.main.java.web.util;

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
import com.amsdams.testgroovy.generators.app.src.main.java.ApplicationGenerator;
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
public class ResponseUtilGenerator implements Generator {

	private static final String RESPONSE_UTIL_JAVA_GROOVY = "app/src/main/java/package/web/util/response-util-java.groovy";

	WriteService writeService;
	ApplicationGenerator applicationGenerator;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();
		Set<String> imports = new HashSet<>();
		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("responseUtilClassName", this.getClassName());
		binding.put("packageName", this.getPackagePathParent());
		imports.add("import " + applicationGenerator.getPackagePath());
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
		return "ResponseUtil";
	}

	@Override
	public String getVarName() {
		return "responseUtil";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}

	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(RESPONSE_UTIL_JAVA_GROOVY);
	}
}
