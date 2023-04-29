package com.bptn.weatherApp.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bptn.weatherApp.exception.domain.CityNotFoundException;
import com.bptn.weatherApp.jpa.Weather;
import com.bptn.weatherApp.repository.WeatherRepository;
import com.bptn.weatherApp.security.JwtService;
import com.bptn.weatherApp.service.WeatherService;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)

public class WeatherControllerTest {

	String city;
	String username;
	boolean save;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	JwtService jwtService;

	@Autowired
	WeatherRepository weatherRepository;

	@SpyBean
	WeatherService weatherService;

	@BeforeAll

	public void setupData() {

		this.city = "toronto";
		this.username = "johndoe";
		this.save = true;
	}

	private String getApiJson() {
		return "{\"coord\":{\"lon\":-79.4163,\"lat\":43.7001},\"weather\":[{\"id\":601,"
				+ "\"main\":\"Snow\",\"description\":\"snow\",\"icon\":\"13n\"}],\"base\":"
				+ "\"stations\",\"main\":{\"temp\":-2.2,\"feels_like\":-9.2,\"temp_min\":-2.81,"
				+ "\"temp_max\":-1.34,\"pressure\":1007,\"humidity\":90},\"visibility\":2816,"
				+ "\"wind\":{\"speed\":10.29,\"deg\":340,\"gust\":13.38},\"snow\":{\"1h\":1.62},"
				+ "\"clouds\":{\"all\":100},\"dt\":1673601725,\"sys\":{\"type\":1,\"id\":718,"
				+ "\"country\":\"CA\",\"sunrise\":1673614150,\"sunset\":1673647377},\"timezone\":-18000,"
				+ "\"id\":6167865,\"name\":\"Toronto\",\"cod\":200}";
	}

	@Test
	@Order(1)
	@Sql("/scripts/insert_user.txt")
	public void getWeatherIntegrationTest() throws Exception {

		doReturn(this.getApiJson()).when(this.weatherService).getWeatherFromApi(anyString());

		String jwt = String.format("Bearer %s", this.jwtService.generateJwtToken(this.username, 10_000));

		/* Check the Rest End Point Response */
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/weathers/{city}/{save}", this.city, this.save)
						.header(AUTHORIZATION, jwt))
				.andExpect(status().isOk()).andExpect(jsonPath("$.weatherId", is(1)))
				.andExpect(jsonPath("$.cloudsAll", is(100))).andExpect(jsonPath("$.description", is("snow")))
				.andExpect(jsonPath("$.feelsLike", is(-9.2))).andExpect(jsonPath("$.humidity", is(90)))
				.andExpect(jsonPath("$.icon", is("13n"))).andExpect(jsonPath("$.pressure", is(1007)))
				.andExpect(jsonPath("$.sunrise", is("2023-01-13 07:49:10")))
				.andExpect(jsonPath("$.sunset", is("2023-01-13 17:02:57"))).andExpect(jsonPath("$.temp", is(-2.2)))
				.andExpect(jsonPath("$.tempMax", is(-1.34))).andExpect(jsonPath("$.tempMin", is(-2.81)))
				.andExpect(jsonPath("$.visibility", is(2816))).andExpect(jsonPath("$.weatherStatusId", is(601)))
				.andExpect(jsonPath("$.windDirection", is(10.29))).andExpect(jsonPath("$.windSpeed", is(340)))
				.andExpect(jsonPath("$.city.cityId", is(1))).andExpect(jsonPath("$.city.latitude", is(43.7001)))
				.andExpect(jsonPath("$.city.longitude", is(-79.4163))).andExpect(jsonPath("$.city.name", is("Toronto")))
				.andExpect(jsonPath("$.city.timezone", is("-18000")))
				.andExpect(jsonPath("$.city.weatherCityId", is(6167865)))
				.andExpect(jsonPath("$.city.country.countryId", is(1)))
				.andExpect(jsonPath("$.city.country.countryCode", is("CA")));

		/* Verify the Mocked method was called */
		verify(this.weatherService).getWeatherFromApi(anyString());

		/* Check the DB */
		Optional<Weather> opt = this.weatherRepository.findById(1);

		assertTrue(opt.isPresent(), "Weather Should Exist");

		assertEquals(1, opt.get().getWeatherId());
		assertEquals("100.00", opt.get().getCloudsAll().toString());
		assertEquals("snow", opt.get().getDescription());
		assertEquals("-9.20", opt.get().getFeelsLike().toString());
		assertEquals("90.00", opt.get().getHumidity().toString());
		assertEquals("13n", opt.get().getIcon());
		assertEquals("1007.00", opt.get().getPressure().toString());
		assertEquals("2023-01-13 07:49:10.0", opt.get().getSunrise().toString());
		assertEquals("2023-01-13 17:02:57.0", opt.get().getSunset().toString());
		assertEquals("-2.20", opt.get().getTemp().toString());
		assertEquals("-1.34", opt.get().getTempMax().toString());
		assertEquals("-2.81", opt.get().getTempMin().toString());
		assertEquals("2816.00", opt.get().getVisibility().toString());
		assertEquals(601, opt.get().getWeatherStatusId());
		assertEquals("10.29", opt.get().getWindDirection().toString());
		assertEquals("340.00", opt.get().getWindSpeed().toString());
		assertEquals(1, opt.get().getCity().getCityId());
		assertEquals("43.7001", opt.get().getCity().getLatitude().toString());
		assertEquals("-79.4163", opt.get().getCity().getLongitude().toString());
		assertEquals("Toronto", opt.get().getCity().getName());
		assertEquals("-18000", opt.get().getCity().getTimezone());
		assertEquals(6167865, opt.get().getCity().getWeatherCityId());
		assertEquals(1, opt.get().getCity().getCountry().getCountryId());
		assertEquals("CA", opt.get().getCity().getCountry().getCountryCode());

	}

	@Test
	@Order(2)
	public void getWeatherCityNotFoundIntegrationTest() throws Exception {

		doThrow(new CityNotFoundException("City doesn't exist, abcd")).when(this.weatherService)
				.getWeatherFromApi(anyString());

		String jwt = String.format("Bearer %s", this.jwtService.generateJwtToken(this.username, 10_000));

		/* Check the Rest End Point Response */
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/weathers/{city}/{save}", "abcd", this.save).header(AUTHORIZATION,
						jwt))
				.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.httpStatusCode", is(400)))
				.andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
				.andExpect(jsonPath("$.reason", is("BAD REQUEST")))
				.andExpect(jsonPath("$.message", is("City doesn't exist, abcd")));

		/* Verify the Mocked method was called */
		verify(this.weatherService).getWeatherFromApi(anyString());
	}

	@Test
	@Order(3)
	@Sql("/scripts/insert_weathers.txt")
	public void getWeathersIntegrationTest() throws Exception {

		String jwt = String.format("Bearer %s", this.jwtService.generateJwtToken(this.username, 10_000));

		/* Check the Rest End Point Response */
		this.mockMvc.perform(MockMvcRequestBuilders.get("/weathers").header(AUTHORIZATION, jwt))
			  .andExpect(status().isOk())
	          .andExpect(jsonPath("$", hasSize(10)))
	          .andExpect(jsonPath("$.[0].weatherId", is(11)))
	          .andExpect(jsonPath("$.[0].cloudsAll", is(100.0)))
			  .andExpect(jsonPath("$.[0].description", is("snow")))
	          .andExpect(jsonPath("$.[0].feelsLike", is(-9.2)))
			  .andExpect(jsonPath("$.[0].humidity", is(90.0)))
	          .andExpect(jsonPath("$.[0].icon", is("13n")))
			  .andExpect(jsonPath("$.[0].pressure", is(1007.0)))
			  .andExpect(jsonPath("$.[0].sunrise", is("2023-01-13 07:49:10")))
			  .andExpect(jsonPath("$.[0].sunset", is("2023-01-13 17:02:57")))
			  .andExpect(jsonPath("$.[0].temp", is(-2.2)))
	          .andExpect(jsonPath("$.[0].tempMax", is(-1.34)))
			  .andExpect(jsonPath("$.[0].tempMin", is(-2.81)))
	          .andExpect(jsonPath("$.[0].visibility", is(2816.0)))
			  .andExpect(jsonPath("$.[0].weatherStatusId", is(601)))
			  .andExpect(jsonPath("$.[0].windDirection", is(10.29)))
	          .andExpect(jsonPath("$.[0].windSpeed", is(340.0)))
			  .andExpect(jsonPath("$.[0].city.cityId", is(1)))
	          .andExpect(jsonPath("$.[0].city.latitude", is(43.7001)))
			  .andExpect(jsonPath("$.[0].city.longitude", is(-79.4163)))
			  .andExpect(jsonPath("$.[0].city.name", is("Toronto")))
			  .andExpect(jsonPath("$.[0].city.timezone", is("-18000")))
			  .andExpect(jsonPath("$.[0].city.weatherCityId", is(6167865)))
			  .andExpect(jsonPath("$.[0].city.country.countryId", is(1)))
			  .andExpect(jsonPath("$.[0].city.country.countryCode", is("CA")));

			  /* Check the DB */
			  List<Weather> weathers = this.weatherRepository.findAll();

			  /* 11 Records in the Weather DB table but the API returns max 10. */
			  assertEquals(11, weathers.size());
	}
}
