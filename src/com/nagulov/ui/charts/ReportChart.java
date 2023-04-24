package com.nagulov.ui.charts;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import com.nagulov.reports.Report;
import com.nagulov.reports.ReportOption;
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
		
		HashMap<Beautician, Integer> beauticians = new HashMap<Beautician, Integer>();
		LocalDate endDate = LocalDate.now().plusDays(1);
		
		for(LocalDate start = LocalDate.now().minusDays(30); start.isBefore(endDate); start = start.plusDays(1)) {
			if(Report.getBeauticianReport().containsKey(start)){
				for(Map.Entry<Beautician, HashMap<ReportOption, Double>> entry : Report.getBeauticianReport().get(start).entrySet()) {
					if(beauticians.containsKey(entry.getKey())) {
						beauticians.put(entry.getKey(), (int) (beauticians.get(entry.getValue()) + entry.getValue().get(ReportOption.TREATMENT)));
					}
					else {
						beauticians.put(entry.getKey(), (int) (0 + entry.getValue().get(ReportOption.TREATMENT)));
					}
				}
			}
		}
		
		
		for(Map.Entry<Beautician, Integer> entry : beauticians.entrySet()) {
			beauticianChart.addSeries(entry.getKey().getUsername(), entry.getValue());
		}
		
		return beauticianChart;
	}
	
	public static PieChart initTreatmentChart() {
		PieChart treatmentChart = new PieChartBuilder()
				.title("Status of treatments in past 30 days")
				.width(400)
				.height(300)
				.build();
		
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
