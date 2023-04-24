package com.nagulov.treatments;

import java.time.LocalDateTime;

import com.nagulov.data.DataBase;
import com.nagulov.users.Client;
import com.nagulov.users.Beautician;

public class Treatment {
	private int id;
	private TreatmentStatus status;
	private CosmeticService service;
	private String treatment;
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
		if(beautician.getService().contains(service)) {
			this.beautician = beautician;
		}
	}

	public CosmeticService getService() {
		return service;
	}

	public Client getClient() {
		return client;
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

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	
	@Override
	public String toString() {
		return this.id + "," + this.status.getStatus() + "," + this.service.getName() + "," + this.treatment + "," + this.beautician.getUsername() + "," + this.date.format(DataBase.TREATMENTS_DATE) + "," + this.client.getUsername();
 	}
}

