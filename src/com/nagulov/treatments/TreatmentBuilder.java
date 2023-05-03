package com.nagulov.treatments;

import java.time.LocalDateTime;

import com.nagulov.users.Client;
import com.nagulov.users.Beautician;

public class TreatmentBuilder {
	private int id;
	private TreatmentStatus status;
	private CosmeticService service;
	private CosmeticTreatment treatment;
	private Beautician beautician;
	private LocalDateTime date;
	private Client client;
	
	public TreatmentBuilder() {
		
	}
	
	public TreatmentBuilder setId(int id) {
		this.id = id;
		return this;
	}
	public TreatmentBuilder setStatus(TreatmentStatus status) {
		this.status = status;
		return this;
	}
	public TreatmentBuilder setService(CosmeticService service) {
		this.service = service;
		return this;
	}
	public TreatmentBuilder setTreatment(CosmeticTreatment treatment) {
		this.treatment = treatment;
		return this;
	}
	public TreatmentBuilder setBeautician(Beautician beautician) {
		this.beautician = beautician;
		return this;
	}
	public TreatmentBuilder setDate(LocalDateTime date) {
		this.date = date;
		return this;
	}
	public TreatmentBuilder setClient(Client client) {
		this.client = client;
		return this;
	}
	
	public Treatment build() {
		return new Treatment(this);
	}

	public int getId() {
		return id;
	}

	public TreatmentStatus getStatus() {
		return status;
	}

	public CosmeticService getService() {
		return service;
	}

	public CosmeticTreatment getTreatment() {
		return treatment;
	}

	public Beautician getBeautician() {
		return beautician;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Client getClient() {
		return client;
	}
	
	
}
