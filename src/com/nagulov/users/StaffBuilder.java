package com.nagulov.users;

public class StaffBuilder extends UserBuilder{

	private int qualification;
	private double income;
	private int internship;
	private double bonuses;
	private double salary;
	
	public StaffBuilder(String username, String password) {
		super(username, password);
	}
	
	public StaffBuilder setIncome(double income) {
		this.income = income;
		return this;
	}

	public StaffBuilder setInternship(int internship) {
		this.internship = internship;
		return this;
	}

	public StaffBuilder setBonuses(double bonuses) {
		this.bonuses = bonuses;
		return this;
	}

	public StaffBuilder setQulification(int qualification) {
		this.qualification = qualification;
		return this;
	}
	
	public StaffBuilder setSalary(double salary) {
		this.salary = salary;
		return this;
	}
	
	public double getIncome() {
		return income;
	}

	public int getInternship() {
		return internship;
	}

	public double getBonuses() {
		return bonuses;
	}

	public double getSalary() {
		return salary;
	}
	public int getQualification() {
		return qualification;
	}



}
