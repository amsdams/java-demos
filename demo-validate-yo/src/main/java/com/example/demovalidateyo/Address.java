package com.example.demovalidateyo;

import javax.annotation.ManagedBean;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.executable.ValidateOnExecution;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
@Valid
public class Address {
	@NonNull
	@NotBlank
	@Valid
    private String address;

    @NonNull
    private String postalCode;
    @NonNull
    private String city;
    
    @NonNull
    @Size(max = 2, min=2, message = "s")
    @Length(max = 2, min=2, message = "l")
    @NotBlank
    private String countryCode;
}
