package com.nagulov.treatments;

import java.util.HashMap;

public class CosmeticService {
	
	private String name;
	private HashMap<String, Double> treatments = new HashMap<String, Double>();
	
	public CosmeticService() {
		
	}
	
	public CosmeticService(String name){
		this.name = name;
	}
	
	public void addTreatment(String treatment, double price) {
		treatments.put(treatment,price);
	}
	
	public void removeTreatment(String treatment) {
		treatments.remove(treatment);
	}
	
	public HashMap<String, Double> getTreatments() {
		return treatments;
	}
	
	public void setTreatments(HashMap<String, Double> treatments) {
		this.treatments = treatments; 
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name + "," + this.treatments.keySet() + "," +  this.treatments.values();
	}
	
}
