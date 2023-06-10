package com.nagulov.data;

import java.time.LocalDateTime;

import com.nagulov.controllers.ServiceController;
import com.nagulov.controllers.UserController;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Salon;
import com.nagulov.users.Beautician;

public class Validator {

	private static boolean isNumerical(String string) {
		if(string == null) {
			return false;
		}
		try {
			Double.parseDouble(string);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public static ErrorMessage createService(String service) {
		if(ServiceController.getInstance().getServices().containsKey(service)) {
			return ErrorMessage.SERVICE_ALREADY_EXISTS;
		}
		return ErrorMessage.SUCCESS;
	}
	
	
	public static ErrorMessage loginUser(String username, String password) {
		if(username.isEmpty()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isEmpty()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(!UserController.getInstance().getUsers().containsKey(username)) {
			return ErrorMessage.INVALID_USERNAME;
		}
		if(!UserController.getInstance().getUser(username).getPassword().equals(password)) {
			return ErrorMessage.INVALID_PASSWORD;
		}
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage registerUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
		if(username.isEmpty()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isEmpty()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(UserController.getInstance().getUsers().containsKey(username)) {
			return ErrorMessage.USERNAME_ALREADY_EXISTS;
		}
		if(!isNumerical(phoneNumber)) {
			return ErrorMessage.INVALID_PHONE_NUMBER;
		}
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage createTreatment(CosmeticService service, Beautician beautician, LocalDateTime date) {
		if(!beautician.containsService(service)) {
			return ErrorMessage.BEAUTICIAN_WITHOUT_SERVICE;
		}
		if(!beautician.canOperate(date)) {
			return ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE;
		}
		if(date.isBefore(LocalDateTime.now())) {
			return ErrorMessage.INVALID_DATE;
		}
		if(date.toLocalTime().isBefore(Salon.getInstance().getOpening()) || date.toLocalTime().isAfter(Salon.getInstance().getClosing())) {
			return ErrorMessage.INVALID_TIME;
		}
		if(!beautician.canOperate(date)) {
			return ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE;
		}
		return ErrorMessage.SUCCESS;
	}
}
