package com.nagulov.data;

import com.nagulov.controllers.ManagerController;
import com.nagulov.treatments.Treatment;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;

public class Validator {
	
	private static ManagerController managerController = ManagerController.getInstance();
	
	private static Class<?> getRole(String string){
		switch(string) {
			case DataBase.CLIENT -> {return Client.class;}
			case DataBase.MANAGER -> {return Manager.class;}
			case DataBase.BEAUTICIAN -> {return Beautician.class;}
			case DataBase.RECEPTIONIST -> {return Receptionist.class;}
		}
		return null;
	}
	
	public static void scheduleTreatment(Treatment t) {
		return;
	}
		
	public static ErrorMessage loginUser(String username, String password) {
		if(username.isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(!DataBase.users.containsKey(username)) {
			return ErrorMessage.INVALID_USERNAME;
		}
		if(!DataBase.users.get(username).getPassword().equals(password)) {
			return ErrorMessage.INVALID_PASSWORD;
		}

		DataBase.loggedUser = managerController.getUser(username);
		return ErrorMessage.SUCCESS;
	}
	
	public static ErrorMessage registerUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
		return registerUser(Client.class.getSimpleName(), name, surname, gender, phoneNumber, address, username, password);
	}
	
	public static ErrorMessage registerUser(String role, String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
		if(username.isBlank()) {
			return ErrorMessage.EMPTY_USERNAME;
		}
		if(password.isBlank()) {
			return ErrorMessage.EMPTY_PASSWORD;
		}
		if(DataBase.users.containsKey(username)) {
			return ErrorMessage.USERNAME_ALREADY_EXISTS;
		}
		
		managerController.createUser(name, surname, gender, phoneNumber, address, username, password, getRole(role));
		DataBase.saveUsers();
		
		return ErrorMessage.SUCCESS;
	}
	

}
