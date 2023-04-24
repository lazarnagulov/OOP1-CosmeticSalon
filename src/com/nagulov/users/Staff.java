package com.nagulov.users;

public abstract class Staff extends User{
	
	private int qulification;
	private double income;
	private int internship;
	private int bonuses;
	private int salary;
	
	public Staff() {
		
	}
	
	public Staff(UserBuilder builder) {
		super(builder);
	}

	public int getQulifications() {
		return qulification;
	}
	public void setQulifications(int qulification) {
		this.qulification = qulification;
	}
	public int getInternship() {
		return internship;
	}
	public void setInternship(int internship) {
		this.internship = internship;
	}
	public int getBonuses() {
		return bonuses;
	}
	public void setBonuses(int bonuses) {
		this.bonuses = bonuses;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
}
