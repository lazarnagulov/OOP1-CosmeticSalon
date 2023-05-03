package com.nagulov.users;

import java.util.ArrayList;
import java.util.List;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticTreatment;

public class Beautician extends Staff {

	private List<CosmeticTreatment> treatments = new ArrayList<CosmeticTreatment>();
	
	public Beautician() {
		
	}
	
	public Beautician(StaffBuilder builder) {
		super(builder);
	}
	
	public Beautician(UserBuilder builder) {
		super(builder);
	}
	
	public List<CosmeticTreatment> getTreatments(){
		return treatments;
	}

	public void addTreatment(CosmeticTreatment service) {
		if (!treatments.contains(service)) {
			treatments.add(service);
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
		
		for(CosmeticTreatment t : treatments) {
			data.append(DataBase.cosmeticTreatments.get(t)).append("|")
				.append(t.getName()).append(";");
		}
		
		return data.toString();
	}

}
