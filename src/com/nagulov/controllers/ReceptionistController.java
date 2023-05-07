package com.nagulov.controllers;

import java.time.LocalDateTime;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class ReceptionistController {
	//TODO be able to change treatment price
	private static ReceptionistController instance = null;
	
	protected ReceptionistController() {
		
	}
	
	public static ReceptionistController getInstance() {
		if(instance == null) {
			instance = new ReceptionistController();
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
		DataBase.treatments.put(DataBase.treatmentId, t);
		beautician.addIncome(t.getPrice());
		return t;
	}
	
	public void updateTreatment(int id, TreatmentStatus status, CosmeticService service, CosmeticTreatment treatment,
			Beautician beautician, LocalDateTime date, Client client) {
		Treatment t = DataBase.treatments.get(id);
		if(t == null) {
			return;
		}
		t.setBeautician(beautician);
		t.setClient(client);
		t.setDate(date);
		t.setService(service);
		t.setStatus(status);
		t.setTreatment(treatment);
	}

	public void removeTreatment(Treatment treatment) {
		DataBase.treatments.remove(treatment.getId());
	}

	public void removeTreatment(int id) {
		DataBase.treatments.remove(id);
		--DataBase.treatmentId;
	}
	
	public Treatment getTreatment(int id) {
		return DataBase.treatments.get(id);
	}

}
