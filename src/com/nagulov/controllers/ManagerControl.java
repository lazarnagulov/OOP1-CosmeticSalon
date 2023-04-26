package com.nagulov.controllers;

import java.util.HashMap;

import com.nagulov.treatments.CosmeticService;
import com.nagulov.users.Client;
import com.nagulov.users.Staff;
import com.nagulov.users.User;

public interface ManagerControl {
	
	User createUser(String name, String surname, String gender, String phoneNumber, String address, String username,String password, Class<?> position);
	void removeService(String service);
	void updateUser(User u, String name, String surname, String gender, String phoneNumber, String address,String username, String password, Class<?> position);
	Staff createStaff(String username, String password, Class<?> position);
	User[] getUsers(String...username);
	User getUser(String username); // TODO: change to id
	void removeUser(String username);
	void removeUser(User user);
	
	CosmeticService createService(String name);
	CosmeticService getService(String service);
	void updateService(String service, String treatment, double price);
	void removeService(CosmeticService service);

}
