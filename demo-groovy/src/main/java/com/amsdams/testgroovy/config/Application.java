package com.amsdams.testgroovy.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Application {
	String name;
	String groupId;
	String artifactId;
	List<Entity> entities = new ArrayList<>();

}
