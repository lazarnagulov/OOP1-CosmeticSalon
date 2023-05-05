package com.nagulov.users;

public class StaffBuilder{

	private String name;
	private String surname;
	private String gender;
	private String phoneNumber;
	private String address;
	private final String username;
	private final String password;
	private int qualification;
	private double income;
	private int internship;
	private double bonuses;
	private double salary;
	
	public StaffBuilder(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public StaffBuilder setIncome(double income) {
		this.income = income;
		return this;
	}
	
	public StaffBuilder setAddress(String address) {
		this.address = address;
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

	public StaffBuilder setQualification(int qualification) {
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
	
	public Beautician buildBeautician() {
		return new Beautician(this);
	}
	
	public Receptionist buildReceptionist() {
		return new Receptionist(this);
	}
	
	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getGender() {
		return gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public StaffBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public StaffBuilder setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public StaffBuilder setGender(String gender) {
		this.gender = gender;
		return this;
	}

	public StaffBuilder setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}
}
