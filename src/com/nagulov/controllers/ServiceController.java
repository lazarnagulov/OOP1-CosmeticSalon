package com.nagulov.controllers;

import java.time.LocalTime;
import java.util.HashMap;

import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;

public class ServiceController {
	
	private HashMap<CosmeticTreatment, CosmeticService> cosmeticTreatments = new HashMap<CosmeticTreatment, CosmeticService>();
	private HashMap<String, CosmeticService> services = new HashMap<String, CosmeticService>();
	
	public static ServiceController instance = null;
	
	private ServiceController() {
		
	}
	
	public static ServiceController getInstance() {
		if(instance == null) {
			instance = new ServiceController();
		}
		return instance;
	}
	
	public HashMap<String, CosmeticService> getServices(){
		return services;
	}
	
	
	public HashMap<CosmeticTreatment, CosmeticService> getCosmeticTreatments(){
		return cosmeticTreatments;
	}
	
	public void updateCosmeticTreatment(CosmeticTreatment treatment, String name, LocalTime duration, double price) {
		treatment.setName(name);
		treatment.setDuration(duration);
		Pricelist.getInstance().setPrice(treatment, price);
	}
	
	public CosmeticService createService(String name) {
		CosmeticService service = new CosmeticService(name);
		services.put(name, service);
		return service;
	}
	
	public void updateService(CosmeticService service, String name) {
		service.setName(name);
	}
	
	public CosmeticService getService(String service) {
		return services.get(service);
	}

	public void removeService(CosmeticService service) {
		services.remove(service.getName());
	}

	public void removeService(String service) {
		services.remove(service);
	}
	
	public CosmeticTreatment createCosmeticTreatment(CosmeticService service, String name, LocalTime duration) {
		CosmeticTreatment ct = new CosmeticTreatment(name, duration);
		service.addTreatment(ct);
		return ct;
	}
	
	public CosmeticTreatment getCosmeticTreatment(CosmeticService service, String name) {
		return service.getTreatment(name);
	}

}
