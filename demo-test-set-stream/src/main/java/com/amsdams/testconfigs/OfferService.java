package com.amsdams.testconfigs;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

	@Autowired
	OfferFilterService offerFilterService;

	public List<Offer> filterOffers(List<Offer> offers) {
		return offers.stream().filter(offer -> !offerFilterService.hasCountries(offer)).collect(Collectors.toList());
	}
}
