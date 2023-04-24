package com.nagulov.users;


public class UserBuilder {
	
	private String name;
	private String surname;
	private String gender;
	private String phoneNumber;
	private String address;
	private final String username;
	private final String password;
	
	public UserBuilder(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public UserBuilder setGender(String gender) {
		this.gender = gender;
		return this;
	}

	public UserBuilder setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public UserBuilder setAddress(String address) {
		this.address = address;
		return this;
	}
	
	public Manager buildManager() {
		return new Manager(this);
	}
	
	public Beautician buildBeautician() {
		return new Beautician(this);
	}
	
	public Receptionist buildReceptionist() {
		return new Receptionist(this);
	}

	public Client buildClient() {
		return new Client(this);
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
	
}
