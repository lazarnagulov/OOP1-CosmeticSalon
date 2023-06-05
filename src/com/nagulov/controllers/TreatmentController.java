package com.nagulov.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class TreatmentController {

	private static TreatmentController instance = null;
	private HashMap<Integer, Treatment> treatments = new HashMap<Integer, Treatment>();
	
	private TreatmentController() {
		
	}
	
	public static TreatmentController getInstance() {
		if(instance == null) {
			instance = new TreatmentController();
		}
		return instance;
	}
	
	
	public Treatment createTreatment(TreatmentStatus status, CosmeticService service, CosmeticTreatment treatment,
			Beautician beautician, LocalDateTime date, Client client) {
		++DataBase.treatmentId;
		Treatment t = new TreatmentBuilder()
				.setId(DataBase.treatmentId)
				.setBeautician(beautician)
				.setClient(client)
				.setDate(date)
				.setService(service)
				.setStatus(status)
				.setTreatment(treatment)
				.setPrice(Pricelist.getInstance().getPrice(treatment))
				.build();
		if(t.getClient().hasLoyalityCard()) {
			t.setPrice(t.getPrice() * 0.9);
		}
		t.getBeautician().addTreatment(t);
		t.getClient().addTreatment(t);
		treatments.put(DataBase.treatmentId, t);
		return t;
	}
	
	public void updateTreatment(int id, TreatmentStatus status, CosmeticService service, CosmeticTreatment treatment,
			Beautician beautician, LocalDateTime date, Client client) {
		Treatment t = treatments.get(id);
		if(t == null) {
			return;
		}
		t.getClient().removeTreatment(t);
		t.getBeautician().removeTreatment(t);
		
		t.setBeautician(beautician);
		t.setClient(client);
		t.setDate(date);
		t.setService(service);
		t.setStatus(status);
		t.setTreatment(treatment);
		
		t.getClient().addTreatment(t);
		t.getBeautician().addTreatment(t);
		
		if(t.getStatus().equals(TreatmentStatus.PERFORMED)) {
			for(Treatment ct : t.getClient().getTreatments()) {
				if(!(t.getId() == ct.getId()) && t.getDate().equals(ct.getDate())) {
					ct.setStatus(TreatmentStatus.CANCELED_BY_THE_CLIENT);
				}
			}
		}
	}
	

	public void removeTreatment(Treatment treatment) {
		treatment.getBeautician().removeTreatment(treatment);
		treatment.getClient().removeTreatment(treatment);
		treatments.remove(treatment.getId());
	}

	public void removeTreatment(int id) {
		Treatment t = treatments.get(id);
		t.getBeautician().removeTreatment(t);
		t.getClient().removeTreatment(t);
		treatments.remove(id);
	}
	
	public Treatment getTreatment(int id) {
		return treatments.get(id);
	}
	
	public HashMap<Integer, Treatment> getTreatments(){
		return treatments;
	}
	
}
