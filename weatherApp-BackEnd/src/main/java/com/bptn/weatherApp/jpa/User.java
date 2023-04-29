package com.bptn.weatherApp.jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="\"User\"")
public class User implements Serializable { 
	private static final long serialVersionUID = 1L;
	//1. sets the field below as the primary key
	@Id
	//2. specifies that the value of the key is auto-generated 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//3. specifies which column in our database to map values into
	@Column(name="\"userId\"")
	//4. when converting to JSON, the userId field 
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer userId;
	@Column(name="\"firstName\"")
	private String firstName;
	
	//see 3
	@Column(name="\"lastName\"")
	private String lastName;

	private String username;
	// see 4
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
		
	private String phone;
	
	//see 3
	@Column(name="\"emailId\"")
	private String emailId;
	//see 3
	@Column(name="\"emailVerified\"")
	private Boolean emailVerified;
	//see 3
	@Column(name="\"createdOn\"")
	private Timestamp createdOn;

	//5. indicates that a property should be ignored
	@JsonIgnore
	//6. "eagerly" loaded = loaded immediately and completely
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Weather> weathers;
	
	public User() {
	    
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + ", phone=" + phone + ", emailId=" + emailId + ", emailVerified="
				+ emailVerified + ", createdOn=" + createdOn + ", weathers=" + weathers + "]";
	}

	public List<Weather> getWeathers() {
		return weathers;
	}

	public void setWeathers(List<Weather> weathers) {
		this.weathers = weathers;
	}
}