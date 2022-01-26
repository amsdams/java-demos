package com.amsdams.testgroovy.config;

import java.util.ArrayList;
import java.util.List;

import com.amsdams.testgroovy.config.fields.Field;
import com.amsdams.testgroovy.config.relationships.Relation;

import lombok.Data;

@Data
public class Entity {

	String name;
	List<Field> fields = new ArrayList<>();
	List<Relation> relations = new ArrayList<>();

}