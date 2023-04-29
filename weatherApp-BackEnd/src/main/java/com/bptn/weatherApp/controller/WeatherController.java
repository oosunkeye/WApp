package com.bptn.weatherApp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bptn.weatherApp.jpa.Weather;
import com.bptn.weatherApp.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin
@RestController
@RequestMapping("/weathers")
public class WeatherController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/{city}/{save}")
	public Weather getWeather(@PathVariable String city, @PathVariable Boolean save) throws JsonMappingException, JsonProcessingException {

		logger.debug("getWeather() method called with city: {} and save: {}", city, save);
		
		Weather weather = weatherService.getWeather(city, save);
		
		return weather;
	}
	
	@GetMapping
	public List<Weather> getWeathers() {
		
		logger.debug("Weather list is being retreived ");
		
		List <Weather> weather = weatherService.getWeathers();
		
		return weather;
		
		}
	}