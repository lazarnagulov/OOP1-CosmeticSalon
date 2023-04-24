package com.nagulov.data;

public enum ErrorMessage {
	INVALID_USERNAME("Provided username is not valid!"),
	INVALID_PASSWORD("Provided password is not valid!"),
	EMPTY_USERNAME("You must provide username"),
	EMPTY_PASSWORD("You must provide password"),
	USERNAME_ALREADY_EXISTS("Username already exists!"),
	SUCCESS("Success"),
	ROW_NOT_SELECTED("You did not select row!");

	private final String error;
	
	ErrorMessage(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
