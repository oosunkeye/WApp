package com.bptn.weatherApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bptn.weatherApp.jpa.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	Optional<Country> findByCountryCode(String countryCode);
	
}
