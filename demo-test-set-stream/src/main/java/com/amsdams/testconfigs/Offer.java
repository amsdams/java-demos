package com.amsdams.testconfigs;


import java.util.Set;

import lombok.Data;

@Data
public class Offer {
	private Set<String> countries;
	private String name;
}
