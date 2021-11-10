package com.amsdams.testconfigs;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "config")
@Data
@Validated
public class DemoConfig {
	@NotBlank
	private String path;
}
