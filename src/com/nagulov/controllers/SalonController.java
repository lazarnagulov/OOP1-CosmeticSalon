package com.nagulov.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import com.nagulov.treatments.Treatment;
import com.nagulov.users.Staff;
import com.nagulov.users.User;

public class SalonController {

	public static SalonController instance = null;
	
	private SalonController() {
		
	}
	
	public static SalonController getInstance() {
		if(instance == null) {
			instance = new SalonController();
		}
		return instance;
	}
	
	public double calculateIncome(LocalDate startDate, LocalDate endDate) {
		double income = 0;
		for(Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			Treatment t = entry.getValue();
			if(t.getDate().toLocalDate().isAfter(endDate) || t.getDate().toLocalDate().isBefore(startDate)) {
				continue;
			}
			
			switch(t.getStatus()) {
				case PERFORMED:
				case SCHEDULED:
				case DID_NOT_SHOW_UP:
					income += t.getPrice();
					break;
				case CANCELED_BY_THE_CLIENT:
					income += t.getPrice() * 0.1;
					break;
				case CANCELED_BY_THE_SALON:
				default:
					break;
			}
		}
		return income;
	}
	
	public double calculateExpenditure(LocalDate startDate, LocalDate endDate) {
		double expenditure = 0;
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			Treatment t = entry.getValue();
			if(t.getDate().toLocalDate().isAfter(endDate) || t.getDate().toLocalDate().isBefore(startDate)) {
				continue;
			}
			switch(t.getStatus()) {
				case PERFORMED:
				case SCHEDULED:
				case DID_NOT_SHOW_UP:
					break;
				case CANCELED_BY_THE_CLIENT:
					expenditure += t.getPrice() * 0.9;
					break;
				case CANCELED_BY_THE_SALON:
					expenditure += t.getPrice();
					break;
				default:
					break;
			}
		}
		
		int months = endDate.getMonthValue() - startDate.getMonthValue();
		
		if(startDate.getDayOfMonth() == 1) {
			months ++;
		}
		
		if(months > 0) {
			for(Map.Entry<String, User> entry : UserController.getInstance().getUsers().entrySet()) {
				User user = entry.getValue();
				if(!(user instanceof Staff)) {
					continue;
				}
				expenditure += months * ((Staff) user).getSalary() + ((Staff) user).getBonuses(); 
			}
		}
		return expenditure;
	}
}
