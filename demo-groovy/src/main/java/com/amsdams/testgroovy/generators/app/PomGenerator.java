package com.amsdams.testgroovy.generators.app;

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
import com.amsdams.testgroovy.generators.app.src.main.java.ApplicationGenerator;
import com.amsdams.testgroovy.util.Generator;

import groovy.text.SimpleTemplateEngine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PomGenerator implements Generator{

	private static final String POM_XML_GROOVY = "app/pom-xml.groovy";
	private WriteService writeService;
	private ApplicationGenerator applicationGenerator;
	@Override
	public void write() throws CompilationFailedException, ClassNotFoundException, IOException {
		var simpleTemplateEngine = new SimpleTemplateEngine();
		var application = applicationGenerator.getApplication();
		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("artifactId", application.getArtifactId());
		binding.put("groupId", application.getGroupId());

		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	private String filePathParent;
	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + "pom" + ApplicationConstants.XML_EXT;
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getVarName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPackagePath() {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(POM_XML_GROOVY);
	}

}
