package com.bptn.weatherApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bptn.weatherApp.jpa.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	Optional<City> findByWeatherCityId(Integer weatherCityId);
}
