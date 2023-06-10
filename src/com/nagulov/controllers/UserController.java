package com.nagulov.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class UserController{
	
	public double loyaltyCardNeeded = 15000.0; 
	
	private static UserController instance = null;
	private HashMap<String, User> users = new HashMap<String,User>();
	
	
	private UserController() {
		
	}
	
	
	public static UserController getInstance() {
		if(instance == null) {
			instance = new UserController();
		}
		return instance;
	}

	public Beautician findAvailableBeautician(LocalDateTime dateTime, CosmeticService service) {
		for(Map.Entry<String, User> entry : users.entrySet()) {
			if(entry.getValue() instanceof Beautician && ((Beautician)entry.getValue()).canOperate(dateTime) && ((Beautician)entry.getValue()).hasService(service)) {
				return (Beautician)entry.getValue();
			}
		}
		return null;
	}
	
	public User createUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password, Class<?> position) {
		if(position == Receptionist.class) {
			Receptionist r = new UserBuilder(username, password)
					.setName(name)
					.setSurname(surname)
					.setGender(gender)
					.setPhoneNumber(phoneNumber)
					.setAddress(address)
					.buildReceptionist(); 
			users.put(username, r);
			DataBase.saveUsers();
			return r;
		}else if(position == Beautician.class) {
			Beautician c = new UserBuilder(username, password)
					.setName(name)
					.setSurname(surname)
					.setGender(gender)
					.setPhoneNumber(phoneNumber)
					.setAddress(address)
					.buildBeautician();
			users.put(username, c);
			DataBase.saveUsers();
			return c;
		}else if(position == Client.class) {
			Client c = new UserBuilder(username, password)
					.setAddress(address)
					.setName(name)
					.setPhoneNumber(phoneNumber)
					.setGender(gender)
					.setSurname(surname)
					.buildClient();
			users.put(username, c);
			DataBase.saveUsers();
			return c;
		}else if(position == Manager.class){
			Manager c = new UserBuilder(username, password)
					.setAddress(address)
					.setName(name)
					.setPhoneNumber(phoneNumber)
					.setGender(gender)
					.setSurname(surname)
					.buildManager();
			users.put(username, c);
			DataBase.saveUsers();
			return c;
		}
		return null;
	}

	public HashMap<String, User> getUsers(){
		return users;
	}
	
	public Staff createStaff(String name, String surname, String gender, 
			String phoneNumber, String address, String username, String password, 
			double bonuses, double income, int internship, int qualification, double salary, Class<?> position) {
		if(position == Receptionist.class) {
			Receptionist r = new StaffBuilder(username, password)
					.setBonuses(bonuses)
					.setIncome(income)
					.setInternship(internship)
					.setQualification(qualification)
					.setSalary(salary)
					.setName(name)
					.setSurname(surname)
					.setGender(gender)
					.setPhoneNumber(phoneNumber)
					.setAddress(address)
					.buildReceptionist();
			users.put(username, r);
			DataBase.saveUsers();
			return r;
		}else if(position == Beautician.class) {
			Beautician c = new StaffBuilder(username, password)
					.setBonuses(bonuses)
					.setIncome(income)
					.setInternship(internship)
					.setQualification(qualification)
					.setSalary(salary)
					.setName(name)
					.setSurname(surname)
					.setGender(gender)
					.setPhoneNumber(phoneNumber)
					.setAddress(address)
					.buildBeautician();
			
			users.put(username, c);
			DataBase.saveUsers();
			return c;
		}
		return null;
	}
	
	
	
	public void addUser(User u) {
		users.put(u.getUsername(), u);
	}

	public void updateUser(User u, String name, String surname, String gender, String phoneNumber, String address, String username, String password, Class<?> position){
		if(u.getClass() != position) {
			removeUser(u);
			UserModel.removeUser(u);
			User nu = createUser(name, surname, gender, phoneNumber, address, username, password, position);
			UserModel.addUser(nu);
			return;
		}
		u.setName(name);
		u.setAddress(address);
		u.setGender(gender);
		u.setPassword(password);
		u.setPhoneNumber(phoneNumber);
		u.setSurname(surname);
		if(!username.equals(u.getUsername())) {
			users.remove(u.getUsername());
			u.setUsername(username);
			users.put(username, u);
		}
	}
	
	public User[] getUsers(String...username) {
		int len = username.length;
		User[] ret = new User[len];
 		for(int i = 0; i < len; ++i) {
 			ret[i] = users.get(username[i]);
 		}
 		return ret;
	}
	
	public User getUser(String username) {
		return users.get(username);
	}
	
	public void removeUser(String username){
		users.remove(username);
		DataBase.saveUsers();
	}
	
	public void removeUser(User user){
		users.remove(user.getUsername());
		DataBase.saveUsers();
	}
	
	public void updateStaff(Staff s, String name, String surname, String gender, String phoneNumber, String address,
			String username, String password, double bonuses, double income, int internship, int qualification,
			double salary, Class<?> role) {
		if(s.getClass() != role) {
			removeUser(s);
			UserModel.removeUser(s);
			Staff ns = createStaff(name, surname, gender, phoneNumber, address, username, password, bonuses, income, internship, qualification, salary, role);
			UserModel.addUser(ns);
			return;
		}
	
		s.setName(name);
		s.setAddress(address);
		s.setGender(gender);
		s.setPassword(password);
		s.setPhoneNumber(phoneNumber);
		s.setSurname(surname);
		s.setBonuses(bonuses);
		s.setInternship(internship);
		s.setQualification(qualification);
		s.setIncome(income);
		if(!username.equals(s.getUsername())) {
			users.remove(s.getUsername());
			s.setUsername(username);
			users.put(username, s);
		}
	}



}
