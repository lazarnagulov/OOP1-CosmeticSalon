package com.nagulov.treatments;

import java.time.LocalDateTime;

import com.nagulov.data.DataBase;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class Treatment {
	private int id;
	private TreatmentStatus status;
	private CosmeticService service;
	private CosmeticTreatment treatment;
	private Beautician beautician;
	private LocalDateTime date;
	private Client client;
	
	public Treatment(TreatmentBuilder builder) {
		this.id = builder.getId();
		this.setStatus(builder.getStatus());
		this.setStatus(builder.getStatus());
		this.setService(builder.getService());
		this.setTreatment(builder.getTreatment());
		this.setBeautician(builder.getBeautician());
		this.setDate(builder.getDate());
		this.setClient(builder.getClient());
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public TreatmentStatus getStatus() {
		return status;
	}
	
	public void setStatus(TreatmentStatus status) {
		this.status = status;
	}
	
	public Beautician getBeautician() {
		return beautician;
	}
	
	public void setBeautician(Beautician beautician) {
		if(beautician.getTreatments().contains(treatment)) {
			this.beautician = beautician;
		}
		else {
			System.out.printf("Beautician %s does not have %s treatment\n", beautician.getUsername(), treatment);
		}
	}

	public CosmeticService getService() {
		return service;
	}

	public Client getClient() {
		return client;
	}
	
	public CosmeticTreatment getTreatment() {
		return treatment;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setService(CosmeticService service) {
		this.service = service;
	}

	public void setTreatment(CosmeticTreatment treatment) {
		this.treatment = treatment;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.id).append(",")
				.append(this.status.getStatus()).append(",")
				.append(this.service.getName()).append(",")
				.append(this.treatment.getName()).append(",")
				.append(this.beautician.getUsername()).append(",")
				.append(this.date.format(DataBase.TREATMENTS_DATE)).append(",")
				.append(this.client.getUsername())
				.toString();
 	}
}

