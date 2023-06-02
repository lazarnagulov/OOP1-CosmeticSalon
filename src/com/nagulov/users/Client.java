package com.nagulov.users;

import java.util.ArrayList;
import java.util.List;

import com.nagulov.controllers.UserController;
import com.nagulov.treatments.Treatment;

public class Client extends User{

	private List<Treatment> treatments = new ArrayList<Treatment>();
	private double spent = 0;
	private double balance = 0;
	private boolean hasLoyalityCard = false;
	
	public Client() {
		
	}
	
	public Client(UserBuilder builder) {
		super(builder);
	}
	
	public void addTreatment(Treatment t) {
		if(!treatments.contains(t)) {
			treatments.add(t);
		}
	}

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}

	public boolean hasLoyalityCard() {
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
		return spent;
	}

	public void setSpent(double spent) {
		if(!this.hasLoyalityCard && this.spent >= UserController.getInstance().loyaltyCardNeeded) {
			this.hasLoyalityCard = true;
		}
		this.spent = spent;
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
			    .append(this.spent).append(",")
			    .append(this.hasLoyalityCard).append(",")
				.append(this.balance).append(",");
		
		return data.toString();
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
