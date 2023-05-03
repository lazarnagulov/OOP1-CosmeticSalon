package com.nagulov.reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

public class Report {
	private static HashMap<LocalDate, HashMap<Beautician, HashMap<ReportOption, Double>>> beauticianReport = new HashMap<LocalDate, HashMap<Beautician, HashMap<ReportOption, Double>>>();
	private static HashMap<LocalDate, HashMap<TreatmentStatus, Integer>> treatmentsReport = new HashMap<LocalDate, HashMap<TreatmentStatus, Integer>>();
	private static HashMap<CosmeticService, HashMap<String, Integer>> servicesReport = new HashMap<CosmeticService, HashMap<String, Integer>>();
	private static List<Client> loyalityReport = new ArrayList<Client>();

	public static void updateCosmetologistReport(LocalDate date, Beautician c, double income) {
		HashMap<Beautician, HashMap<ReportOption, Double>> report = new HashMap<Beautician, HashMap<ReportOption, Double>>();
		if(beauticianReport.get(date) == null) {
			HashMap<ReportOption, Double> data = new HashMap<ReportOption, Double>();
			data.put(ReportOption.INCOME, income);
			data.put(ReportOption.TREATMENT, 1.0);
			report.put(c, data);
			beauticianReport.put(date, report);	
			return;
		}
		
		if(beauticianReport.get(date).get(c) == null) {
			HashMap<ReportOption, Double> data = new HashMap<ReportOption, Double>();
			data.put(ReportOption.INCOME, income);
			data.put(ReportOption.TREATMENT, 1.0);
			beauticianReport.get(date).put(c, data);	
			return;
		}
		
		double newIncome = beauticianReport.get(date).get(c).get(ReportOption.INCOME) + income;
		beauticianReport.get(date).get(c).put(ReportOption.INCOME, newIncome);
		double newTreatmentsCount = beauticianReport.get(date).get(c).get(ReportOption.TREATMENT) + 1;
		beauticianReport.get(date).get(c).put(ReportOption.TREATMENT, newTreatmentsCount);
	}
	
	public static void updateServicesReport() {
		
	}
	
	public static void updateLoyalityReport(Client client) {
		if(!loyalityReport.contains(client)) {
			loyalityReport.add(client);
		}
	}
		
	public static void updateTreatmentsReport(TreatmentStatus status, LocalDate date) {
		if(treatmentsReport.containsKey(date)) {
			if(treatmentsReport.get(date).containsKey(status)) {
				treatmentsReport.get(date).put(status, treatmentsReport.get(date).get(status) + 1);
			}
			else {
				treatmentsReport.get(date).put(status, 1);
			}
			return;
		}
		HashMap<TreatmentStatus, Integer> treatmentStatus = new HashMap<TreatmentStatus, Integer>();
		treatmentStatus.put(status, 1);
		treatmentsReport.put(date, treatmentStatus);
	}
	
	public static List<Client> getLoyalityReport() {
		if(loyalityReport.isEmpty()) {
			for(Map.Entry<String, User> entry : DataBase.users.entrySet()) {
				User u = entry.getValue(); 
				if(u instanceof Client && ((Client) u).isHasLoyalityCard()) {
					loyalityReport.add((Client)u);
				}
			}
		}
		return loyalityReport;
	}

	public static HashMap<LocalDate, HashMap<Beautician, HashMap<ReportOption, Double>>> getBeauticianReport() {
		return beauticianReport;
	}

	public static HashMap<LocalDate, HashMap<TreatmentStatus, Integer>> getTreatmentsReport() {
		return treatmentsReport;
	}
	
	public static HashMap<CosmeticService, HashMap<String, Integer>> getServicesReport() {
		return servicesReport;
	}
}
