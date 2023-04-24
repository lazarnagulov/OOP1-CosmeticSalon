package com.nagulov.controllers;

import java.util.HashMap;
import java.util.Map;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class ManagerController extends ReceptionistController implements ManagerControl{
	
	public static final double LOYALTY_CARD_NEEDED = 15000.0; 
	
	private static ManagerController instance = null;
	
	private ManagerController() {
		
	}
	
	public static ManagerController getInstance() {
		if(instance == null) {
			instance = new ManagerController();
		}
		return instance;
	}
	
	@Override
	public Client createUser(String name, String surname, String gender, String phoneNumber, String address, String username, String password) {
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
	}
	
	@Override
	public Staff createStaff(String username, String password, Class<?> position) {
		if(position.isInstance(Receptionist.class)) {
			Receptionist r = new UserBuilder(username, password).buildReceptionist(); 
			DataBase.users.put(username, r);
			return r;
		}else if(position.isInstance(Beautician.class)) {
			Beautician c = new UserBuilder(username, password).buildBeautician();
			DataBase.users.put(username, c);
			return c;
		}
		return null;
	}
	
	@Override
	public CosmeticService createService(String name) {
		return new CosmeticService(name);
	}
	
	@Override
	public void updateUser(User u, HashMap<String, String> updateMap){
		for(Map.Entry<String, String> s : updateMap.entrySet()) {
			switch(s.getKey()) {
				case "Name" -> u.setName(s.getValue());
				case "Password" -> u.setPassword(s.getValue());
				case "Address" -> u.setAddress(s.getValue());
				case "PhoneNumber" -> u.setPhoneNumber(s.getValue());
				case "Role" -> {
					//TODO: Enable updating users role
				}
			}
		}
	}	
	
	@Override
	public void updateService(String service, String treatment, double price) {
		if(DataBase.services.containsKey(service)) {
			CosmeticService cs = DataBase.services.get(service);
			cs.addTreatment(treatment, price);
		}else {
			CosmeticService newService = createService(service);
			newService.addTreatment(treatment, price);
		}
	}
	
	
	@Override
	public User[] getUsers(String...username) {
		int len = username.length;
		User[] ret = new User[len];
 		for(int i=0; i<len; ++i) {
 			ret[i] = DataBase.users.get(username[i]);
 		}
 		return ret;
	}
	
	@Override
	public User getUser(String username) {
		return DataBase.users.get(username);
	}
	
	@Override
	public void removeUser(String username){
		DataBase.users.remove(username);
		DataBase.saveUsers();
	}
	
	public void removeUser(User user){
		DataBase.users.remove(user.getUsername());
		DataBase.saveUsers();
	}


	@Override
	public CosmeticService getService(String service) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void removeService(CosmeticService service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeService(String service) {
		// TODO Auto-generated method stub
		
	}

}
