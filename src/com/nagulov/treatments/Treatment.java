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
	private double price;
	
	public Treatment(TreatmentBuilder builder) {
		this.id = builder.getId();
		this.setStatus(builder.getStatus());
		this.setStatus(builder.getStatus());
		this.setService(builder.getService());
		this.setTreatment(builder.getTreatment());
		this.setBeautician(builder.getBeautician());
		this.setDate(builder.getDate());
		this.setClient(builder.getClient());
		this.setPrice(builder.getPrice());
	}
	
	public double getIncome() {
		switch(this.getStatus()) {
			case CANCELED_BY_THE_CLIENT:
				return this.getPrice() * 0.1;
			case DID_NOT_SHOW_UP:
			case PERFORMED:
			case SCHEDULED:
				return this.getPrice();
			case CANCELED_BY_THE_SALON:
			default:
				return 0;
		}
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
		if(beautician.getServices().contains(service)) {
			this.beautician = beautician;
		}
		else {
			System.err.printf("Beautician %s does not have %s service\n", beautician.getUsername(), service);
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
				.append(this.date.format(DataBase.TREATMENTS_DATE_FORMAT)).append(",")
				.append(this.client.getUsername()).append(",")
				.append(this.getPrice())
				.toString();
 	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}

