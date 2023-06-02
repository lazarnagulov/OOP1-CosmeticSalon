package com.nagulov.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

public class Report {
//	private static HashMap<LocalDate, HashMap<Beautician, HashMap<ReportOption, Double>>> beauticianReport = new HashMap<LocalDate, HashMap<Beautician, HashMap<ReportOption, Double>>>();
	private static HashMap<CosmeticService, HashMap<String, Integer>> servicesReport = new HashMap<CosmeticService, HashMap<String, Integer>>();
	
	public static HashMap<TreatmentStatus, Integer> treatmentReport = new HashMap<TreatmentStatus, Integer>();
	public static HashMap<Beautician, ArrayList<Double>> beauticianReport = new HashMap<Beautician, ArrayList<Double>>();
	private static boolean isInInterval(LocalDate startDate, LocalDate endDate, LocalDate checkDate) {
		return !(checkDate.isAfter(endDate) || checkDate.isBefore(startDate));
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
	
	public static HashMap<Beautician, ArrayList<Double>> calculateBeauticianReport(LocalDate startDate, LocalDate endDate){
		beauticianReport.clear();
		
		for(Map.Entry<Integer, Treatment> treatment : TreatmentController.getInstance().getTreatments().entrySet()) {
			LocalDate treatmentDate = treatment.getValue().getDate().toLocalDate();
			if(isInInterval(startDate, endDate, treatmentDate) && treatment.getValue().getStatus().equals(TreatmentStatus.PERFORMED)) {
				if(!beauticianReport.containsKey(treatment.getValue().getBeautician())) {
					ArrayList<Double> data = new ArrayList<Double>();
					data.add(treatment.getValue().getPrice());
					treatment.getValue().getBeautician().setIncome(treatment.getValue().getPrice());
					data.add(1.0);
					beauticianReport.put(treatment.getValue().getBeautician(), data);
				}else {
					double count = beauticianReport.get(treatment.getValue().getBeautician()).get(1) + 1.0;
					double price = beauticianReport.get(treatment.getValue().getBeautician()).get(0) + treatment.getValue().getPrice();
					beauticianReport.get(treatment.getValue().getBeautician()).set(0, price);
					treatment.getValue().getBeautician().setIncome(price);
					beauticianReport.get(treatment.getValue().getBeautician()).set(1, count);
				}
			}
		}
		
		
		return beauticianReport;
	}
	
	public static HashMap<CosmeticService, HashMap<String, Integer>> getServicesReport() {
		return servicesReport;
	}
}
