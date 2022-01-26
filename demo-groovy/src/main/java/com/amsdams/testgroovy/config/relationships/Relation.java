package com.amsdams.testgroovy.config.relationships;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
	String name;
	String entity;
	Mapping mapping;
}
