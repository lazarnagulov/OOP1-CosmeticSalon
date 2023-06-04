package com.nagulov.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

public class Report {

	public static List<Double> cosmeticTreatmentReport = new ArrayList<Double>();
	public static HashMap<TreatmentStatus, Integer> treatmentReport = new HashMap<TreatmentStatus, Integer>();
	public static HashMap<Beautician, ArrayList<Double>> beauticianReport = new HashMap<Beautician, ArrayList<Double>>();
	
	private static boolean isInInterval(LocalDate startDate, LocalDate endDate, LocalDate checkDate) {
		return !(checkDate.isAfter(endDate) || checkDate.isBefore(startDate));
	}
	
	public static List<Double> calculateComseticTreatmentReport(CosmeticTreatment cosmeticTreatment, LocalDate startDate, LocalDate endDate) {
		cosmeticTreatmentReport.clear();
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			if(isInInterval(startDate, endDate, entry.getValue().getDate().toLocalDate()) && entry.getValue().getTreatment().equals(cosmeticTreatment)) {
				if(cosmeticTreatmentReport.size() == 0) {
					cosmeticTreatmentReport.add(1.0);
					cosmeticTreatmentReport.add(entry.getValue().getIncome());
				}else {
					cosmeticTreatmentReport.set(0, cosmeticTreatmentReport.get(0) + 1.0);
					cosmeticTreatmentReport.set(1, cosmeticTreatmentReport.get(1) + entry.getValue().getIncome());
				}
			}
		}
		return cosmeticTreatmentReport;
	}
	
	public static List<Client> calculateLoyalityReport() {
		List<Client> loyalityReport = new ArrayList<Client>();
		for(Map.Entry<String, User> entry : UserController.getInstance().getUsers().entrySet()) {
			User u = entry.getValue(); 
			if(u instanceof Client && ((Client) u).hasLoyalityCard()) {
				loyalityReport.add((Client)u);
			}
		}
		return loyalityReport;
	}

	
	public static HashMap<TreatmentStatus, Integer> calculateTreatmentsReport(LocalDate startDate, LocalDate endDate) {
		treatmentReport.clear();
		treatmentReport.put(TreatmentStatus.CANCELED_BY_THE_CLIENT, 0);
		treatmentReport.put(TreatmentStatus.CANCELED_BY_THE_SALON, 0);
		treatmentReport.put(TreatmentStatus.DID_NOT_SHOW_UP, 0);
		treatmentReport.put(TreatmentStatus.PERFORMED, 0);
		treatmentReport.put(TreatmentStatus.SCHEDULED, 0);
		
		for(Map.Entry<Integer, Treatment> treatment : TreatmentController.getInstance().getTreatments().entrySet()) {
			LocalDate treatmentDate = treatment.getValue().getDate().toLocalDate();
			if(isInInterval(startDate, endDate, treatmentDate)) {
				if(treatment.getValue().getStatus().equals(TreatmentStatus.CANCELED_BY_THE_CLIENT)) {
					treatmentReport.put(TreatmentStatus.CANCELED_BY_THE_CLIENT, treatmentReport.get(TreatmentStatus.CANCELED_BY_THE_CLIENT) + 1);
				}else if(treatment.getValue().getStatus().equals(TreatmentStatus.CANCELED_BY_THE_SALON)) {	
					treatmentReport.put(TreatmentStatus.CANCELED_BY_THE_SALON, treatmentReport.get(TreatmentStatus.CANCELED_BY_THE_SALON) + 1);
				}else if(treatment.getValue().getStatus().equals(TreatmentStatus.DID_NOT_SHOW_UP)) {
					treatmentReport.put(TreatmentStatus.DID_NOT_SHOW_UP, treatmentReport.get(TreatmentStatus.DID_NOT_SHOW_UP) + 1);
				}else if(treatment.getValue().getStatus().equals(TreatmentStatus.PERFORMED)) {
					treatmentReport.put(TreatmentStatus.PERFORMED, treatmentReport.get(TreatmentStatus.PERFORMED) + 1);
				}else {
					treatmentReport.put(TreatmentStatus.SCHEDULED, treatmentReport.get(TreatmentStatus.SCHEDULED) + 1);
				}
			}
		}
		
		return treatmentReport;
	}
	
	private static double getPrice(Treatment treatment) {
		double price = 0;
		switch(treatment.getStatus()) {
			case PERFORMED:
			case SCHEDULED:
			case DID_NOT_SHOW_UP:
				price += treatment.getPrice();
				break;
			case CANCELED_BY_THE_CLIENT:
				price += treatment.getPrice() * 0.1;
				break;
			case CANCELED_BY_THE_SALON:
			default:
				break;
		}
		return price;
	}
	
	public static HashMap<Beautician, ArrayList<Double>> calculateBeauticianReport(LocalDate startDate, LocalDate endDate){
		beauticianReport.clear();
		
		for(Map.Entry<Integer, Treatment> treatment : TreatmentController.getInstance().getTreatments().entrySet()) {
			LocalDate treatmentDate = treatment.getValue().getDate().toLocalDate();
			if(isInInterval(startDate, endDate, treatmentDate) && treatment.getValue().getStatus().equals(TreatmentStatus.PERFORMED)) {
				if(!beauticianReport.containsKey(treatment.getValue().getBeautician())) {
					ArrayList<Double> data = new ArrayList<Double>();
					data.add(getPrice(treatment.getValue()));
					data.add(1.0);
					beauticianReport.put(treatment.getValue().getBeautician(), data);
				}else {
					double count = beauticianReport.get(treatment.getValue().getBeautician()).get(1) + 1.0;
					double price = beauticianReport.get(treatment.getValue().getBeautician()).get(0) + getPrice(treatment.getValue());
					beauticianReport.get(treatment.getValue().getBeautician()).set(0, price);
					beauticianReport.get(treatment.getValue().getBeautician()).set(1, count);
				}
			}
		}
		
		
		return beauticianReport;
	}
	

	
}
