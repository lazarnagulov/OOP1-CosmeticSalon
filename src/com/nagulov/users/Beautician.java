package com.nagulov.users;

import java.util.ArrayList;
import java.util.List;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;

public class Beautician extends Staff {

	private List<CosmeticService> services = new ArrayList<CosmeticService>();
	
	public Beautician() {
		
	}
	
	public Beautician(StaffBuilder builder) {
		super(builder);
	}
	
	public Beautician(UserBuilder builder) {
		super(builder);
	}
	
	public List<CosmeticService> getTreatments(){
		return services;
	}

	public void addTreatment(CosmeticService service) {
		if (!services.contains(service)) {
			services.add(service);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder data = new StringBuilder(this.getClass().getSimpleName()).append(",")								
				.append(this.getUsername()).append(",")
				.append(this.getPassword()).append(",")
				.append(this.getName()).append(",")
				.append(this.getSurname()).append(",")
				.append(this.getGender()).append(",")
				.append(this.getPhoneNumber()).append(",")
				.append(this.getAddress()).append(",")
				.append(this.getBonuses()).append(",")
				.append(this.getIncome()).append(",")
				.append(this.getInternship()).append(",")
				.append(this.getQulification()).append(",")
				.append(this.getSalary()).append(",");
		
		for(CosmeticService t : services) {
			data.append(t.getName()).append(";");
		}
		
		return data.toString();
	}

}
