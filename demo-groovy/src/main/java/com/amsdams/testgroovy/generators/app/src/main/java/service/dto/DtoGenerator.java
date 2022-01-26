package com.amsdams.testgroovy.generators.app.src.main.java.service.dto;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.amsdams.testgroovy.ApplicationConstants;
import com.amsdams.testgroovy.WriteService;
import com.amsdams.testgroovy.config.Entity;
import com.amsdams.testgroovy.config.fields.Field;
import com.amsdams.testgroovy.config.relationships.Relation;
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
public class DtoGenerator implements Generator {

	private static final String DTO_JAVA_GROOVY = "app/src/main/java/package/service/dto/dto-java.groovy";

	WriteService writeService;
	EntityGenerator entityGenerator;
	String filePathParent;
	String packagePathParent;

	@Override
	public void write() throws IOException, CompilationFailedException, ClassNotFoundException {
		var simpleTemplateEngine = new SimpleTemplateEngine();

		var imports = new HashSet<>();

		Entity entity = entityGenerator.getEntity();

		List<Relation> dtoRelations = entity.getRelations().stream()
				.map(u -> new Relation(u.getName(), u.getEntity() + "Dto", u.getMapping()))
				.collect(Collectors.toList());

		HashMap<Object, Object> binding = new HashMap<>();
		binding.put("packageName", this.getPackagePathParent());
		imports.add("import " + this.getPackagePath());

		binding.put("dtoClassName", this.getClassName());
		binding.put("fields", entity.getFields());
		binding.put("relations", dtoRelations);
		
		imports.addAll(getImports(entity));
		binding.put("imports", imports);

		log.info("bindings {}", binding.toString());

		log.info("using {}", getResourceTemplate().getFile().getPath());

		String content = simpleTemplateEngine.createTemplate(getResourceTemplate().getFile()).make(binding).toString();

		Path path = Paths.get(this.getFilePath());

		writeService.write(content, path);

	}

	Set<String> getImports(Entity entity) {
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
		return new ClassPathResource(DTO_JAVA_GROOVY);
	}

	@Override
	public String getFilePath() {
		return this.getFilePathParent() + "/" + getClassName() + ApplicationConstants.JAVA_EXT;
	}

	@Override
	public String getClassName() {
		return entityGenerator.getClassName() + "Dto";
	}

	@Override
	public String getVarName() {
		return entityGenerator.getVarName() + "Dto";
	}

	@Override
	public String getPackagePath() {
		return this.getPackagePathParent() + "." + getClassName();
	}

}
