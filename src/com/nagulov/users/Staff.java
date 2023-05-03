package com.nagulov.users;

public abstract class Staff extends User {
	
	private int qualification;
	private double income;
	private int internship;
	private double bonuses;
	private double salary;
	
	public Staff() {
		
	}
	
	public Staff(StaffBuilder builder) {
		super(builder);
	}
	
	public Staff(UserBuilder builder) {
		super(builder);
	}

	public int getQulification() {
		return qualification;
	}
	public void setQulification(int qulification) {
		this.qualification = qulification;
	}
	public int getInternship() {
		return internship;
	}
	public void setInternship(int internship) {
		this.internship = internship;
	}
	public double getBonuses() {
		return bonuses;
	}
	public void setBonuses(int bonuses) {
		this.bonuses = bonuses;
	}
	public double getSalary() {
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
	
	@Override
	public String toString() {
		StringBuilder data = new StringBuilder(this.getClass().getSimpleName()).append(",")								
				.append(this.getUsername()).append(",")
				.append(this.getPassword()).append(",")
				.append(this.getName()).append(",")
				.append(this.getSurname()).append(",")
				.append(this.getGender()).append(",")
				.append(this.getPhoneNumber()).append(",")
				.append(this.getAddress()).append(",")
				.append(this.getBonuses()).append(",")
				.append(this.getIncome()).append(",")
				.append(this.getInternship()).append(",")
				.append(this.getQulification()).append(",")
				.append(this.getSalary()).append(",");
		return data.toString();
	}
	
}
