package com.nagulov.treatments;

import java.util.HashMap;

public class Pricelist {
	
	private static Pricelist instance = null;
	private HashMap<CosmeticTreatment, Double> prices = new HashMap<CosmeticTreatment, Double>(); 
	
	private Pricelist() {
		
	}
	
	public static Pricelist getInstance() {
		if(instance == null) {
			instance = new Pricelist();
		}
		return instance;
	}

	public void setPrice(CosmeticTreatment treatment, double price) {
		prices.put(treatment, price);
	}
	
	public HashMap<CosmeticTreatment, Double> getPrices() {
		return prices;
	}
	
	public double getPrice(CosmeticTreatment treatment) {
		return prices.get(treatment);
	}
	
}
