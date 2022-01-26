package com.amsdams.testgroovy.config.fields;

import lombok.Data;

@Data
public class Field {
	String  name;
	Type type;
	String validation;
}
