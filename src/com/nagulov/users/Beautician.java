package com.nagulov.users;

import java.util.ArrayList;
import java.util.List;

import com.nagulov.treatments.CosmeticService;

public class Beautician extends Staff{

	private List<CosmeticService> services = new ArrayList<CosmeticService>();
	
	public Beautician() {
		
	}
	
	public Beautician(UserBuilder builder) {
		super(builder);
	}
	
	public List<CosmeticService> getService(){
		return services;
	}

	public void addService(CosmeticService service) {
		if (!services.contains(service)) {
			services.add(service);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder data = new StringBuilder(this.getClass().getSimpleName() + "," + this.getUsername() + "," + this.getPassword() + "," + this.getName() + "," + this.getSurname() + "," + this.getGender() + "," + this.getPhoneNumber() + "," + this.getAddress() + ",");
		for(CosmeticService t : this.services) {
			data.append(t + ";");
		}
		return data.toString();
	}

}
