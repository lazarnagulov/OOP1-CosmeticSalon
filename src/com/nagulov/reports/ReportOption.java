package com.nagulov.reports;

public enum ReportOption {
	INCOME("income"),
	TREATMENT("treatment");

	private final String option;
	
	private ReportOption(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}
}
