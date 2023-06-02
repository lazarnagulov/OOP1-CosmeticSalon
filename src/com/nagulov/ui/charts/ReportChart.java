package com.nagulov.ui.charts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import com.nagulov.reports.Report;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;

public class ReportChart{

//	private enum Month{
//		JANUARY("January"),o
//		FEBRUARY("February"),
//		MARCH("March"),
//		APRIL("April"),
//		MAY("May"),
//		JUNE("June"),
//		JULY("July"),
//		AUGUST("August"),
//		SEPTEMBER("September"),
//		OCTOBER("October"),
//		NOVEMBER("November"),
//		DECEMBER("December");
//
//		private final String month;
//		
//		private Month(String month) {
//			this.month = month;
//		}
//
//		public String getMonth() {
//			return month;
//		}
//	}
	
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
	
	public static PieChart initTreatmentChart() {
		PieChart treatmentChart = new PieChartBuilder()
				.title("Treatments in past 30 days")
				.width(400)
				.height(300)
				.build();

		HashMap<TreatmentStatus, Integer> data = Report.calculateTreatmentsReport(LocalDate.now().minusDays(30), LocalDate.now());
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
