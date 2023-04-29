package com.bptn.weatherApp.jpa;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//marks this class as a persistent entity in the database. objects of this class are saved as rows in the db table
@Entity
//specifies the name of the database table associated w. this entity
@Table(name="\"Country\"")
//tells the compiler that the class Country is serializable

public class Country implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//specifies this field is primary key
	@Id
	//specifies that the value of the primary key is automatically generated by the db
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//specifies the mapping of the annotated field to the countryId column
	@Column(name="\"countryId\"")
	private Integer countryId;
	
	@Column(name="\"countryCode\"")
	private String countryCode;
	
	//prevents infinite recursion by omitting from serialization/deserialization
	@JsonBackReference
	//used on the "one" side of the relationship, and specifies the "many" side via mappedBy
	@OneToMany(mappedBy="country", cascade=CascadeType.ALL)
	private List<City> cities;
	
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryCode=" + countryCode + ", cities=" + cities + "]";
	}

	//default constructor
	public Country() {

	}
	
	//addCity method
public City addCity(City city) {
		
		this.getCities().add(city);
		city.setCountry(this);

		return city;
}

}
