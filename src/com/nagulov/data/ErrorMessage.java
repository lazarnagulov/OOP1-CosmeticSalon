package com.nagulov.data;

public enum ErrorMessage {
	INVALID_USERNAME("Provided username is not valid!"),
	INVALID_PASSWORD("Provided password is not valid!"),
	EMPTY_USERNAME("You must provide username"),
	EMPTY_PASSWORD("You must provide password"),
	USERNAME_ALREADY_EXISTS("Username already exists!"),
	SUCCESS("Success"),
	BEAUTICIAN_WITHOUT_SERVICE("Beautician does not have service: "),
	BEAUTICIAN_IS_NOT_AVAILABLE("Beautician is not available!"),
	NOT_SELECTED("Please fill all required data!"),
	ROW_NOT_SELECTED("You did not select row!"),
	CANNOT_PERFORM("Treatment is performed or canceled!"),
	CANNOT_CANCEL("Treatment is canceled or performed!"),
	INVALID_DATE_INTERVAL("Invalid date interval!"),
	INVALID_DATE("Invalid date!"),
	INVALID_TIME("Invalid time!"),
	INVALID_PHONE_NUMBER("Invalid phone number!"),
	SERVICE_ALREADY_EXISTS("Service already exists!"),
	INVALID_INPUT("Invalid input!");

	private final String error;
	
	ErrorMessage(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
