package com.amsdams.testgroovy.util;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.Resource;

public interface Generator {

	public String getFilePath();

	public String getClassName() ;

	public String getVarName() ;

	public String getPackagePath() ;

	public void write() throws IOException, CompilationFailedException, ClassNotFoundException;

	Resource getResourceTemplate();
}
