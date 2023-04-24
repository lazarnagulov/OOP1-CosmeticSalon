package com.nagulov.controllers;

import java.util.HashMap;

import com.nagulov.treatments.CosmeticService;
import com.nagulov.users.Client;
import com.nagulov.users.Staff;
import com.nagulov.users.User;

public interface ManagerControl {
	
	Client createUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password);
	void updateUser(User user, HashMap<String, String> updateMap);
	Staff createStaff(String username, String password, Class<?> position);
	User[] getUsers(String...username);
	User getUser(String username); // TODO: change to id
	void removeUser(String username);
	void removeUser(User user);
	
	
	CosmeticService createService(String name);
	CosmeticService getService(String service);
	void updateService(String service, String treatment, double price);
	void removeService(CosmeticService service);
	void removeService(String service);
	
}
