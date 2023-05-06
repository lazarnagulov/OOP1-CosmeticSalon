package com.nagulov.controllers;

import java.time.LocalTime;

import javax.swing.JTextField;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.ui.models.ServiceModel;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class ManagerController extends ReceptionistController {
	
	public static double loyaltyCardNeeded = 15000.0; 
	
	private static ManagerController instance = null;
	
	private ManagerController() {
		
	}
	
	public static ManagerController getInstance() {
		if(instance == null) {
			instance = new ManagerController();
		}
		return instance;
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
			DataBase.users.put(username, r);
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
			DataBase.users.put(username, c);
			return c;
		}else if(position == Client.class) {
			Client c = new UserBuilder(username, password)
					.setAddress(address)
					.setName(name)
					.setPhoneNumber(phoneNumber)
					.setGender(gender)
					.setSurname(surname)
					.buildClient();
			DataBase.users.put(username, c);
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
			DataBase.users.put(username, c);
			DataBase.saveUsers();
			return c;
		}
		return null;
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
			DataBase.users.put(username, r);
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
			
			DataBase.users.put(username, c);
			DataBase.saveUsers();
			return c;
		}
		return null;
	}
	
	public CosmeticService createService(String name) {
		CosmeticService service = new CosmeticService(name);
		DataBase.services.put(name, service);
		ServiceModel.addService(service);
		return service;
	}
	
	public void updateUser(User u, String name, String surname, String gender, String phoneNumber, String address, String username, String password, Class<?> position){
		if(!username.equals(u.getUsername()) || u.getClass() != position) {
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
	}
	
	public User[] getUsers(String...username) {
		int len = username.length;
		User[] ret = new User[len];
 		for(int i = 0; i < len; ++i) {
 			ret[i] = DataBase.users.get(username[i]);
 		}
 		return ret;
	}
	
	public User getUser(String username) {
		return DataBase.users.get(username);
	}
	
	public void removeUser(String username){
		DataBase.users.remove(username);
		DataBase.saveUsers();
	}
	
	public void removeUser(User user){
		DataBase.users.remove(user.getUsername());
		DataBase.saveUsers();
	}
	
	public void updateStaff(Staff s, String name, String surname, String gender, String phoneNumber, String address,
			String username, String password, double bonuses, double income, int internship, int qualification,
			double salary, Class<?> role) {
		if(!username.equals(s.getUsername()) || s.getClass() != role) {
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
		s.setSalary(salary);
		s.setInternship(internship);
		s.setQualification(qualification);
		s.setIncome(income);
	}


	public void updateService(String service, String treatment, LocalTime duration, double price) {
		CosmeticTreatment ct = new CosmeticTreatment(treatment, duration); 
		CosmeticService cs = DataBase.services.get(service);
		cs.addTreatment(ct);
		Pricelist.getInstance().setPrice(ct, price);
	}
	
	public CosmeticService getService(String service) {
		return DataBase.services.get(service);
	}

	public void removeService(CosmeticService service) {
		DataBase.services.remove(service.getName());
	}

	public void removeService(String service) {
		DataBase.services.remove(service);
	}
	
	public CosmeticTreatment createCosmeticTreatment(CosmeticService service, String name, LocalTime duration) {
		CosmeticTreatment ct = new CosmeticTreatment(name, duration);
		service.addTreatment(ct);
		return ct;
	}
	
	public CosmeticTreatment getCosmeticTreatment(CosmeticService service, String name) {
		return service.getTreatment(name);
	}
	
	public void updateSalonName(String newName) {
		DataBase.salonName = newName;
	}
	
	public void updateWorkingTime(LocalTime opening, LocalTime closing) {
		DataBase.opening = opening;
		DataBase.closing = closing;
	}


}
