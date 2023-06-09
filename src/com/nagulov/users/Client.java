package com.nagulov.users;

import java.util.ArrayList;
import java.util.List;

import com.nagulov.controllers.UserController;
import com.nagulov.treatments.Treatment;

public class Client extends User{

	private List<Treatment> treatments = new ArrayList<Treatment>();
	private double spent = 0;
	private boolean hasLoyalityCard = false;
	
	public Client() {
		
	}
	
	public Client(UserBuilder builder) {
		super(builder);
	}
	
	public void addTreatment(Treatment t) {
		if(!treatments.contains(t)) {
			spent += t.getPrice();
			treatments.add(t);
		}
	}
	
	public void removeTreatment(Treatment t) {
		spent -= t.getIncome();
		treatments.remove(t);
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}
	
	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public boolean hasLoyalityCard() {
		getSpent();
		if(this.spent >= UserController.getInstance().loyaltyCardNeeded) {
			this.hasLoyalityCard = true;
		}else {
			this.hasLoyalityCard = false;
		}
		return hasLoyalityCard;
	}

	public void setHasLoyalityCard(boolean hasLoyalityCard) {
		this.hasLoyalityCard = hasLoyalityCard;
	}
	

	public double getSpent() {
		double s = 0;
		for(Treatment t : treatments) {
			switch(t.getStatus()) {
				case CANCELED_BY_THE_CLIENT:
					s += t.getPrice() * 0.1;
					break;
				case DID_NOT_SHOW_UP:
				case PERFORMED:
				case SCHEDULED:
					s += t.getPrice();
					break;
				case CANCELED_BY_THE_SALON:
					break;
			
			}
		}
		spent = s;
		return spent;
	}

	public void setSpent(double spent) {
		this.spent = spent;
		if(!this.hasLoyalityCard && this.spent >= UserController.getInstance().loyaltyCardNeeded) {
			this.hasLoyalityCard = true;
		}
	}
	
	public void addSpent(double spent) {
		this.spent += spent;
		if(!this.hasLoyalityCard && this.spent >= UserController.getInstance().loyaltyCardNeeded) {
			this.hasLoyalityCard = true;
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
			    .append(getSpent()).append(",")
			    .append(this.hasLoyalityCard).append(",");
		
		return data.toString();
	}

}
