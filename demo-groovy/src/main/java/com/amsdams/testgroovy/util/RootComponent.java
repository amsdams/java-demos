package com.amsdams.testgroovy.util;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class RootComponent {

	private static final String SERVICE_IMPL_PACKAGE = "service.impl";
	private static final String SERVICE_PACKAGE = "service";
	private static final String ENTITY_PACKAGE = "entity";
	private static final String SERVICE_DTO_PACKAGE = "service.dto";
	private static final String SERVICE_MAPPER_PACKAGE = "service.mapper";
	private static final String SERVICE_MAPPER_IMPL_PACKAGE = "service.mapper.impl";
	private static final String REPOSITORY_PACKAGE = "repository";
	private static final String RESOURCE_PACKAGE = "web.rest";
	private static final String RESPONSE_UTIL_PACKAGE = "web.util";
	private static final String HEADER_UTIL_PACKAGE = "web.util";
	private static final String BAD_REQUEST_ALERT_EXCEPTION_PACKAGE = "web.errors";
	private static final String ERROR_CONSTANTS_PACKAGE = "web.errors";
	private String rootFilePath;
	private String srcFilePath;
	private String srcPackagePath;
	private String resourcesFilePath;

	public String getEntityFilePathParent() {
		return getFilePathParentFromPackagePathParent(ENTITY_PACKAGE);
	}

	private String getFilePathParentFromPackagePathParent(String packagePathParent) {
		return srcFilePath + "/" + packagePathParent.replace(".", "/");
	}

	public String getEntityPackagePathParent() {
		return getPackagePathParentFromPackage(ENTITY_PACKAGE);
	}

	private String getPackagePathParentFromPackage(String package1) {
		return srcPackagePath + "." + package1;
	}

	public String getDtoFilePathParent() {
		return getFilePathParentFromPackagePathParent(SERVICE_DTO_PACKAGE);
	}

	public String getDtoPackagePathParent() {
		return getPackagePathParentFromPackage(SERVICE_DTO_PACKAGE);
	}

	public String getServiceFilePathParent() {
		return getFilePathParentFromPackagePathParent(SERVICE_PACKAGE);

	}

	public String getServicePackagePathParent() {
		return getPackagePathParentFromPackage(SERVICE_PACKAGE);

	}

	public String getRepositoryFilePathParent() {
		return getFilePathParentFromPackagePathParent(REPOSITORY_PACKAGE);
	}

	public String getRepositoryPackagePathParent() {
		return getPackagePathParentFromPackage(REPOSITORY_PACKAGE);
	}

	public String getResourceFilePathParent() {
		return getFilePathParentFromPackagePathParent(RESOURCE_PACKAGE);

	}

	public String getResourcePackagePathParent() {
		return getPackagePathParentFromPackage(RESOURCE_PACKAGE);
	}

	public String getMapperImplFilePathParent() {
		return getFilePathParentFromPackagePathParent(SERVICE_MAPPER_IMPL_PACKAGE);
	}

	public String getMapperImplPackagePathParent() {
		return getPackagePathParentFromPackage(SERVICE_MAPPER_IMPL_PACKAGE);
	}

	public String getApplicationFilePathParent() {
		return srcFilePath + "/";
	}

	public String getApplicationPackagePathParent() {
		return srcPackagePath;
	}

	public String getEntityMapperFilePathParent() {
		return getFilePathParentFromPackagePathParent(SERVICE_MAPPER_PACKAGE);
	}

	public String getEntityMapperPackagePathParent() {
		return getPackagePathParentFromPackage(SERVICE_MAPPER_PACKAGE);
	}

	public String getServiceImplFilePathParent() {
		return getFilePathParentFromPackagePathParent(SERVICE_IMPL_PACKAGE);
	}

	public String getServiceImplPackagePathParent() {
		return getPackagePathParentFromPackage(SERVICE_IMPL_PACKAGE);
	}

	public String getPomFilePathParent() {
		return rootFilePath;
	}

	public String getApplicationPropertiesFilePathParent() {
		return resourcesFilePath;
	}

	public String getResponseUtilFilePathParent() {
		return getFilePathParentFromPackagePathParent(RESPONSE_UTIL_PACKAGE);
	}

	public String getResponseUtilPackagePathParent() {
		return getPackagePathParentFromPackage(RESPONSE_UTIL_PACKAGE);
	}

	public String getHeaderUtilFilePathParent() {
		return getFilePathParentFromPackagePathParent(HEADER_UTIL_PACKAGE);
	}

	public String getHeaderUtilPackagePathParent() {
		return getPackagePathParentFromPackage(HEADER_UTIL_PACKAGE);
	}

	public String getBadRequestAlertExceptionFilePathParent() {
		return getFilePathParentFromPackagePathParent(BAD_REQUEST_ALERT_EXCEPTION_PACKAGE);
	}

	public String getBadRequestAlertExceptionPackagePathParent() {
		return getPackagePathParentFromPackage(BAD_REQUEST_ALERT_EXCEPTION_PACKAGE);
	}

	// ErrorConstants
	public String getErrorConstantsFilePathParent() {
		return getFilePathParentFromPackagePathParent(ERROR_CONSTANTS_PACKAGE);
	}

	public String getErrorConstantsPackagePathParent() {
		return getPackagePathParentFromPackage(ERROR_CONSTANTS_PACKAGE);
	}

}
