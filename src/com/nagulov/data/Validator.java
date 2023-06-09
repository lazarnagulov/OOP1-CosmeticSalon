package com.nagulov.data;

import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import com.nagulov.controllers.UserController;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class Validator {
	
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
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage createTreatment(CosmeticService service, Beautician beautician, LocalDateTime date) {
		if(!beautician.containsService(service)) {
			return ErrorMessage.BEAUTICIAN_WITHOUT_SERVICE;
		}
//		if(date.isBefore(LocalDateTime.now())) {
//			return ErrorMessage.INVALID_DATE;
//		}
		if(!beautician.canOperate(date)) {
			return ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE;
		}
		return ErrorMessage.SUCCESS;
	}
}
