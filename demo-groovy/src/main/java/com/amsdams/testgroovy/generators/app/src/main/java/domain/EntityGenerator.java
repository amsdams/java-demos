package com.amsdams.testgroovy.generators.app.src.main.java.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.text.CaseUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.config.Entity;
import com.amsdams.testgroovy.config.fields.Field;
import com.amsdams.testgroovy.config.relationships.Relation;
import com.amsdams.testgroovy.util.Generator;
import com.amsdams.testgroovy.util.RootComponent;

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
public class EntityGenerator implements Generator {

	private static final String ENTITY_JAVA_GROOVY = "app/src/main/java/package/domain/entity-java.groovy";

	WriteService writeService;
	RootComponent rootComponent;
	String packagePathParent;
	String filePathParent;
	Entity entity;

	
	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		binding.put("imports", this.getImports(entity));
		binding.put("entityClassName", this.getClassName());
		binding.put("fields", entity.getFields());
		binding.put("relations", entity.getRelations());
		log.info("using {}", getResourceTemplate().getFile().getPath());
		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}
	
	private Set<String> getImports(Entity entity) {
		Set<String> imports = new HashSet<>();
		for (Field field : entity.getFields()) {
			switch (field.getType()) {
			case INSTANT:
				imports.add("import java.time.Instant");
				break;
			case DURATION:
				imports.add("import java.time.Duration");
				break;
			case LOCAL_DATE:
				imports.add("import java.time.LocalDate");
				break;
			case ZONED_DATE_TIME:
				imports.add("import java.time.ZonedDateTime");
				break;
			case UUID:
				imports.add("import java.util.UUID");
				break;
			default:
				break;
			}

		}

		for (Relation relation : entity.getRelations()) {
			switch (relation.getMapping()) {

			case ONE_TO_MANY:
				imports.add("import java.util.List");
				break;
			case MANY_TO_ONE:
				imports.add("import java.util.List");
				break;
			case MANY_TO_MANY:
				imports.add("import java.util.List");
				break;

			default:
				break;
			}

		}
		return imports;

	}


	@Override
	public Resource getResourceTemplate() {
		return new ClassPathResource(ENTITY_JAVA_GROOVY);
	}

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;
	}

	@Override
	public String getClassName() {
		return CaseUtils.toCamelCase(entity.getName(), true, '_');
	}

	@Override
	public String getVarName() {
		return CaseUtils.toCamelCase(entity.getName(), false);
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();

	}

}
