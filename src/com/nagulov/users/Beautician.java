package com.nagulov.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;

public class Beautician extends Staff {

	private List<CosmeticService> services = new ArrayList<CosmeticService>();
	private List<Treatment> treatments = new ArrayList<Treatment>();
	
	public Beautician() {
		
	}
	
	public Beautician(StaffBuilder builder) {
		super(builder);
	}
	
	public Beautician(UserBuilder builder) {
		super(builder);
	}
	
	public boolean hasService(CosmeticService service) {
		return services.contains(service);
	}
	
	public List<CosmeticService> getServices(){
		return services;
	}
	
	public List<Treatment> getTreatments(){
		return treatments;
	}
	
	public void addTreatment(Treatment t) {
		if(!treatments.contains(t)) {
			treatments.add(t);
		}
	}
	
	public void removeTreatment(Treatment t) {
		treatments.remove(t);
	}
	
	public double calculateIncome() {
		double income = 0;
		for(Treatment t : treatments) {
			switch(t.getStatus()) {
				case PERFORMED:
				case SCHEDULED:
				case DID_NOT_SHOW_UP:
					income += t.getPrice();
					break;
				case CANCELED_BY_THE_CLIENT:
					income += t.getPrice() * 0.1;
					break;
				case CANCELED_BY_THE_SALON:
				default:
					break;
			}
		}
		this.setIncome(income);
		return income;
	}
	
	public boolean canOperate(LocalDateTime time) {
		for(Treatment t : treatments) {
			LocalDateTime treatmentEnd = t.getDate().plusHours(t.getTreatment().getDuration().getHour()).plusMinutes(t.getTreatment().getDuration().getMinute());
			if(!(time.isAfter(treatmentEnd) || time.isBefore(t.getDate()))){
				return false;
			}
		}
		return true;
	}
	
	public boolean containsTreatment(CosmeticService service) {
		return services.contains(service);
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
				.append(this.getQualification()).append(",")
				.append(this.getSalary()).append(",");
		
		for(CosmeticService t : services) {
			if(t == null) {
				services.remove(t);
				continue;
			}
			data.append(t.getName()).append(";");
		}
		
		return data.toString();
	}

}
