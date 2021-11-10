package com.amsdams.testconfigs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amsdams.testconfigs.Offer;
import com.amsdams.testconfigs.OfferFilterService;
import com.amsdams.testconfigs.OfferService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class OfferServiceTest {

	@Autowired
	OfferService offerService;
	
	@Autowired
	OfferFilterService offerFilterService;
	
	@Test
	void filterOffersTest() {
		
		offerFilterService.setCountries(new HashSet<>(Arrays.asList("NL", "BE")));
		
		List<Offer> offers = new ArrayList<>();
		
		Offer offerNL = new Offer();
		offerNL.setName("offerNL");
		Set<String> countriesNL = new HashSet<>();
		countriesNL.add("NL");
		offerNL.setCountries(countriesNL);
		offers.add(offerNL);
		
		Offer offerBE = new Offer();
		offerBE.setName("offerBE");
		Set<String> countriesBE = new HashSet<>();
		countriesBE.add("BE");
		offerBE.setCountries(countriesBE);
		offers.add(offerBE);
		
		Offer offerBENL = new Offer();
		offerBENL.setName("offerBENL");
		Set<String> countriesBENL = new HashSet<>();
		countriesBENL.add("BE");
		countriesBENL.add("NL");
		offerBENL.setCountries(countriesBENL);
		offers.add(offerBENL);
		
		Offer offerNLBE = new Offer();
		offerNLBE.setName("offerNLBE");
		Set<String> countriesNLBE = new HashSet<>();
		countriesNLBE.add("NL");
		countriesNLBE.add("BE");
		offerNLBE.setCountries(countriesBENL);
		offers.add(offerNLBE);
		
		Offer offerNULL = new Offer();
		offerNULL.setName("offerNULL");
		offerNULL.setCountries(null);
		offers.add(offerNULL);
		
		Offer offerEmpty = new Offer();
		offerEmpty.setName("offerEmpty");
		offerEmpty.setCountries(new HashSet<>());
		offers.add(offerEmpty);
		
		var filtered =  offerService.filterOffers(offers);
		filtered.forEach(offer->log.info("offer {}", offer.toString()));
		Assertions.assertEquals(4, filtered.size());
		
	}
	
	@Test
	void filterOffersTest_CoutriesFilterNull() {
		offerFilterService.setCountries(null);
		
		List<Offer> offers = new ArrayList<>();
		
		Offer offerNL = new Offer();
		offerNL.setName("offerNL");
		Set<String> countriesNL = new HashSet<>();
		countriesNL.add("NL");
		offerNL.setCountries(countriesNL);
		offers.add(offerNL);
		
		Offer offerBE = new Offer();
		offerBE.setName("offerBE");
		Set<String> countriesBE = new HashSet<>();
		countriesBE.add("BE");
		offerBE.setCountries(countriesBE);
		offers.add(offerBE);
		
		Offer offerBENL = new Offer();
		offerBENL.setName("offerBENL");
		Set<String> countriesBENL = new HashSet<>();
		countriesBENL.add("BE");
		countriesBENL.add("NL");
		offerBENL.setCountries(countriesBENL);
		offers.add(offerBENL);
		
		Offer offerNLBE = new Offer();
		offerNLBE.setName("offerNLBE");
		Set<String> countriesNLBE = new HashSet<>();
		countriesNLBE.add("NL");
		countriesNLBE.add("BE");
		offerNLBE.setCountries(countriesBENL);
		offers.add(offerNLBE);
		
		Offer offerNULL = new Offer();
		offerNULL.setName("offerNULL");
		offerNULL.setCountries(null);
		offers.add(offerNULL);
		
		Offer offerEmpty = new Offer();
		offerEmpty.setName("offerEmpty");
		offerEmpty.setCountries(new HashSet<>());
		offers.add(offerEmpty);
		
		var filtered =  offerService.filterOffers(offers);
		filtered.forEach(offer->log.info("offer {}", offer.toString()));
		Assertions.assertEquals(6, filtered.size());
		
	}
	
	@Test
	void filterOffersTest_CoutriesFilterEmpty() {
		offerFilterService.setCountries(new HashSet<>());
		
		List<Offer> offers = new ArrayList<>();
		
		Offer offerNL = new Offer();
		offerNL.setName("offerNL");
		Set<String> countriesNL = new HashSet<>();
		countriesNL.add("NL");
		offerNL.setCountries(countriesNL);
		offers.add(offerNL);
		
		Offer offerBE = new Offer();
		offerBE.setName("offerBE");
		Set<String> countriesBE = new HashSet<>();
		countriesBE.add("BE");
		offerBE.setCountries(countriesBE);
		offers.add(offerBE);
		
		Offer offerBENL = new Offer();
		offerBENL.setName("offerBENL");
		Set<String> countriesBENL = new HashSet<>();
		countriesBENL.add("BE");
		countriesBENL.add("NL");
		offerBENL.setCountries(countriesBENL);
		offers.add(offerBENL);
		
		Offer offerNLBE = new Offer();
		offerNLBE.setName("offerNLBE");
		Set<String> countriesNLBE = new HashSet<>();
		countriesNLBE.add("NL");
		countriesNLBE.add("BE");
		offerNLBE.setCountries(countriesBENL);
		offers.add(offerNLBE);
		
		Offer offerNULL = new Offer();
		offerNULL.setName("offerNULL");
		offerNULL.setCountries(null);
		offers.add(offerNULL);
		
		Offer offerEmpty = new Offer();
		offerEmpty.setName("offerEmpty");
		offerEmpty.setCountries(new HashSet<>());
		offers.add(offerEmpty);
		
		var filtered =  offerService.filterOffers(offers);
		filtered.forEach(offer->log.info("offer {}", offer.toString()));
		Assertions.assertEquals(6, filtered.size());
		
	}
}
