package com.bptn.weatherApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bptn.weatherApp.jpa.User;
import com.bptn.weatherApp.jpa.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
	List<Weather> findFirst10ByUserOrderByWeatherIdDesc(User user);
}
