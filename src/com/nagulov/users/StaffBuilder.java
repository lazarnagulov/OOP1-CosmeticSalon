package com.nagulov.users;

public class StaffBuilder extends UserBuilder{

	private double income;
	private int internship;
	private int bonuses;
	private int salary;
	
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

	public StaffBuilder setBonuses(int bonuses) {
		this.bonuses = bonuses;
		return this;
	}

	public StaffBuilder setSalary(int salary) {
		this.salary = salary;
		return this;
	}
	
	public double getIncome() {
		return income;
	}

	public int getInternship() {
		return internship;
	}

	public int getBonuses() {
		return bonuses;
	}

	public int getSalary() {
		return salary;
	}

}
