package com.bptn.weatherApp.exception.domain;

public class UsernameExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameExistException(String message) {
		super(message);
	}
}