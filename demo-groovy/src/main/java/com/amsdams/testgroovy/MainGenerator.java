package com.amsdams.testgroovy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.amsdams.testgroovy.config.Application;
import com.amsdams.testgroovy.config.Domain;
import com.amsdams.testgroovy.config.Entity;
import com.amsdams.testgroovy.generators.app.PomGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.ApplicationGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.domain.EntityGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.repository.RepositoryGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.ServiceGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.dto.DtoGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.impl.ServiceImplGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.mapper.EntityMapperGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.service.mapper.impl.MapperImplGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.errors.BadRequestAlertExceptionGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.errors.ErrorConstantsGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.rest.ResourceGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.util.HeaderUtilGenerator;
import com.amsdams.testgroovy.generators.app.src.main.java.web.util.ResponseUtilGenerator;
import com.amsdams.testgroovy.generators.app.src.resources.ApplicationPropertiesGenerator;
import com.amsdams.testgroovy.util.RootComponent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MainGenerator {

	@Autowired
	RootComponent rootComponent;

	@Autowired
	Domain domain;

	@Autowired
	WriteService writeService;

	boolean generate() throws CompilationFailedException, ClassNotFoundException, IOException {
		log.info("domain {}", domain);
		Application application = domain.getApplication();

		String rootPath = "../" + application.getName();
		String srcPackage = application.getGroupId() + "." + application.getArtifactId();
		String srcPath = "../" + application.getName() + "/src/main/java/" + application.getGroupId().replace(".", "/")
				+ "/" + application.getArtifactId();
		String resourcesPath = "../" + application.getName() + "/src/main/resources/";

		rootComponent.setRootFilePath(rootPath);
		rootComponent.setSrcFilePath(srcPath);
		rootComponent.setSrcPackagePath(srcPackage);
		rootComponent.setResourcesFilePath(resourcesPath);

		ApplicationGenerator applicationGenerator = new ApplicationGenerator();
		applicationGenerator.setWriteService(writeService);
		applicationGenerator.setApplication(application);
		applicationGenerator.setFilePathParent(rootComponent.getApplicationFilePathParent());
		applicationGenerator.setPackagePathParent(rootComponent.getApplicationPackagePathParent());

		PomGenerator pomGenerator = new PomGenerator();
		pomGenerator.setWriteService(writeService);
		pomGenerator.setApplicationGenerator(applicationGenerator);
		pomGenerator.setFilePathParent(rootComponent.getPomFilePathParent());

		ApplicationPropertiesGenerator applicationPropertiesGenerator = new ApplicationPropertiesGenerator();
		applicationPropertiesGenerator.setWriteService(writeService);
		applicationPropertiesGenerator.setApplcaApplicationGenerator(applicationGenerator);
		applicationPropertiesGenerator.setFilePathParent(rootComponent.getApplicationPropertiesFilePathParent());

		EntityMapperGenerator entityMapperGenerator = new EntityMapperGenerator();
		entityMapperGenerator.setWriteService(writeService);
		entityMapperGenerator.setFilePathParent(rootComponent.getEntityMapperFilePathParent());
		entityMapperGenerator.setPackagePathParent(rootComponent.getEntityMapperPackagePathParent());

		ResponseUtilGenerator responseUtilGenerator = new ResponseUtilGenerator();
		responseUtilGenerator.setWriteService(writeService);
		responseUtilGenerator.setFilePathParent(rootComponent.getResponseUtilFilePathParent());
		responseUtilGenerator.setPackagePathParent(rootComponent.getResponseUtilPackagePathParent());
		responseUtilGenerator.setApplicationGenerator(applicationGenerator);

		HeaderUtilGenerator headerUtilGenerator = new HeaderUtilGenerator();
		headerUtilGenerator.setWriteService(writeService);
		headerUtilGenerator.setFilePathParent(rootComponent.getHeaderUtilFilePathParent());
		headerUtilGenerator.setPackagePathParent(rootComponent.getHeaderUtilPackagePathParent());

		ErrorConstantsGenerator errorConstantsGenerator = new ErrorConstantsGenerator();
		errorConstantsGenerator.setWriteService(writeService);
		errorConstantsGenerator.setFilePathParent(rootComponent.getErrorConstantsFilePathParent());
		errorConstantsGenerator.setPackagePathParent(rootComponent.getErrorConstantsPackagePathParent());

		BadRequestAlertExceptionGenerator badRequestAlertExceptionGenerator = new BadRequestAlertExceptionGenerator();
		badRequestAlertExceptionGenerator.setWriteService(writeService);
		badRequestAlertExceptionGenerator.setFilePathParent(rootComponent.getBadRequestAlertExceptionFilePathParent());
		badRequestAlertExceptionGenerator
				.setPackagePathParent(rootComponent.getBadRequestAlertExceptionPackagePathParent());
		badRequestAlertExceptionGenerator.setErrorConstantsGenerator(errorConstantsGenerator);

		// delete files
		this.deleteAll(Paths.get(applicationGenerator.getFilePath()));
		this.deleteAll(Paths.get(pomGenerator.getFilePath()));
		this.deleteAll(Paths.get(applicationPropertiesGenerator.getFilePath()));
		this.deleteAll(Paths.get(entityMapperGenerator.getFilePath()));
		this.deleteAll(Paths.get(responseUtilGenerator.getFilePath()));
		this.deleteAll(Paths.get(headerUtilGenerator.getFilePath()));
		this.deleteAll(Paths.get(badRequestAlertExceptionGenerator.getFilePath()));

		// delete directories
		this.deleteAll(Paths.get(rootComponent.getEntityFilePathParent()));
		this.deleteAll(Paths.get(rootComponent.getDtoFilePathParent()));
		this.deleteAll(Paths.get(rootComponent.getRepositoryFilePathParent()));
		this.deleteAll(Paths.get(rootComponent.getResourceFilePathParent()));

		this.deleteAll(Paths.get(rootComponent.getMapperImplFilePathParent()));
		this.deleteAll(Paths.get(rootComponent.getServiceFilePathParent()));
		this.deleteAll(Paths.get(rootComponent.getServiceImplFilePathParent()));

		applicationGenerator.write();
		pomGenerator.write();
		applicationPropertiesGenerator.write();

		entityMapperGenerator.write();
		responseUtilGenerator.write();
		headerUtilGenerator.write();
		errorConstantsGenerator.write();
		badRequestAlertExceptionGenerator.write();

		List<EntityGenerator> entityGenerators = new ArrayList<>();
		List<DtoGenerator> dtoGenerators = new ArrayList<>();
		List<RepositoryGenerator> repositoryGenerators = new ArrayList<>();
		List<ResourceGenerator> resourceGenerators = new ArrayList<>();
		List<ServiceImplGenerator> serviceImplGenerators = new ArrayList<>();
		List<ServiceGenerator> serviceGenerators = new ArrayList<>();
		List<MapperImplGenerator> mapperImplGenerators = new ArrayList<>();

		for (Entity entity : application.getEntities()) {
			log.info("entity", entity);

			EntityGenerator entityGenerator = new EntityGenerator(writeService, rootComponent,
					rootComponent.getEntityPackagePathParent(), rootComponent.getEntityFilePathParent(), entity);

			DtoGenerator dtoGenerator = new DtoGenerator(writeService, entityGenerator,
					rootComponent.getDtoFilePathParent(), rootComponent.getDtoPackagePathParent());

			RepositoryGenerator repositoryGenerator = new RepositoryGenerator(writeService, entityGenerator,
					rootComponent.getRepositoryFilePathParent(), rootComponent.getRepositoryPackagePathParent());

			MapperImplGenerator mapperImplGenerator = new MapperImplGenerator(writeService, entityMapperGenerator,
					dtoGenerator, rootComponent.getMapperImplFilePathParent(),
					rootComponent.getMapperImplPackagePathParent(), entityGenerator);

			ServiceGenerator serviceGenerator = new ServiceGenerator(rootComponent.getServiceFilePathParent(),
					rootComponent.getServicePackagePathParent(), writeService, dtoGenerator, entityGenerator);

			ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator(writeService, repositoryGenerator,
					serviceGenerator, mapperImplGenerator, dtoGenerator, entityGenerator,
					rootComponent.getServiceImplFilePathParent(), rootComponent.getServiceImplPackagePathParent());

			ResourceGenerator resourceGenerator = new ResourceGenerator(writeService, headerUtilGenerator,
					serviceGenerator, repositoryGenerator, dtoGenerator, badRequestAlertExceptionGenerator,
					entityGenerator, responseUtilGenerator, rootComponent.getResourceFilePathParent(),
					rootComponent.getResourcePackagePathParent());

			entityGenerators.add(entityGenerator);
			dtoGenerators.add(dtoGenerator);
			repositoryGenerators.add(repositoryGenerator);
			mapperImplGenerators.add(mapperImplGenerator);
			serviceGenerators.add(serviceGenerator);
			serviceImplGenerators.add(serviceImplGenerator);
			resourceGenerators.add(resourceGenerator);

		}
		for (EntityGenerator entityGenerator : entityGenerators) {
			entityGenerator.write();

		}
		for (DtoGenerator dtoGenerator : dtoGenerators) {
			dtoGenerator.write();
		}
		for (ResourceGenerator resourceGenerator : resourceGenerators) {
			resourceGenerator.write();
		}

		for (RepositoryGenerator repositoryGenerator : repositoryGenerators) {
			repositoryGenerator.write();
		}

		for (MapperImplGenerator mapperImplGenerator : mapperImplGenerators) {
			mapperImplGenerator.write();

		}
		for (ServiceGenerator serviceGenerator : serviceGenerators) {
			serviceGenerator.write();
		}
		for (ServiceImplGenerator serviceImplGenerator : serviceImplGenerators) {
			serviceImplGenerator.write();
		}
		return true;

	}

	
	private void deleteAll(Path parent) throws IOException {
		
		FileSystemUtils.deleteRecursively(parent);

	}

	

}
