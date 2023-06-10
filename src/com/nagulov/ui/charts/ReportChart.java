package com.nagulov.ui.charts;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import com.nagulov.reports.Report;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;

public class ReportChart{
	
	public static PieChart initBeauticianChart(){
		PieChart beauticianChart = new PieChartBuilder()
				.title("Beautician workload in past 30 days")
				.width(400)
				.height(300)
				.build();
		
		HashMap<Beautician, ArrayList<Double>> data = Report.calculateBeauticianReport(LocalDate.now().minusDays(30), LocalDate.now());
		
		for(Map.Entry<Beautician, ArrayList<Double>> entry : data.entrySet()) {
			beauticianChart.addSeries(entry.getKey().getUsername(), entry.getValue().get(1));
		}
		
		return beauticianChart;
	}
	
	public static XYChart initIncomeChart() {
		XYChart incomeChart = new XYChartBuilder()
				.title("Income in past 12 months")
				.width(800)
				.height(300)
				.build();
		
		List<Date> xData = new ArrayList<Date>();
		LocalDate firstDate = LocalDate.now().minusMonths(11).withDayOfMonth(1);
		for(int i=1; i<=12; i++) {
			xData.add(Date.from(firstDate.plusMonths(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
		
		HashMap<LocalDate, HashMap<CosmeticService, Double>> data = Report.calculateIncomeReport(12);
		List<Double> totalIncome = new ArrayList<Double>();

		for(Map.Entry<LocalDate, HashMap<CosmeticService, Double>> entry : data.entrySet()) {
			double income = 0;
			for(Map.Entry<CosmeticService, Double> service : entry.getValue().entrySet()) {
				income += service.getValue();
			}
			totalIncome.add(income);
		}
		
		HashMap<CosmeticService, List<Double>> chartData = new HashMap<CosmeticService, List<Double>>();
		for(Map.Entry<LocalDate, HashMap<CosmeticService, Double>> entry : data.entrySet()) {
			for(Map.Entry<CosmeticService, Double> service : entry.getValue().entrySet()) {
				if(!chartData.containsKey(service.getKey())) {
					List<Double> income = new ArrayList<Double>();
					income.add(service.getValue());
					chartData.put(service.getKey(), income);
				}else {
					chartData.get(service.getKey()).add(service.getValue());
				}
			}	
		}
		
		for(Map.Entry<CosmeticService, List<Double>> entry : chartData.entrySet()) {
			incomeChart.addSeries(entry.getKey().getName(), xData, entry.getValue());
		}
		incomeChart.addSeries("Total income", xData, totalIncome);
		
		return incomeChart;
	}
	
	public static PieChart initTreatmentChart() {
		PieChart treatmentChart = new PieChartBuilder()
				.title("Treatments in past 30 days")
				.width(400)
				.height(300)
				.build();

		HashMap<TreatmentStatus, Integer> data = Report.calculateTreatmentsReport();
		for(Map.Entry<TreatmentStatus, Integer> entry : data.entrySet()) {
			treatmentChart.addSeries(entry.getKey().toString().replace("_", " "), entry.getValue());
		}
		
		return treatmentChart;
	}
	
	public static XYChart initServiceIncomeChart() {
		XYChart serviceChart = new XYChartBuilder()
				.title("Income from cosmetic service")
				.width(900)
				.height(300)
				.build();
		serviceChart.setXAxisTitle("Months");
		serviceChart.setYAxisTitle("Income");
	
		return serviceChart;
	}
}
