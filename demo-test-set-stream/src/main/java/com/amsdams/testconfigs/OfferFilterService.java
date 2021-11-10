package com.amsdams.testconfigs;


import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Setter;

@Component

public class OfferFilterService {
	@Setter
	private Set<String> countries ;

	boolean hasCountries(Offer offer) {
		if (countries==null) {
			return false;
		}
		
		if (countries.isEmpty()) {
			return false;
		}
		
		if (offer.getCountries()==null) {
			return false;
		}
		return offer.getCountries().containsAll(countries);
	}
}
