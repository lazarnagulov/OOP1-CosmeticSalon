package com.nagulov.users;

public abstract class User {
	private String name;
	private String surname;
	private String gender;
	private String phoneNumber;
	private String address;
	private String username;
	private String password;
	
	public User() {
		
	}
	
	public User(UserBuilder builder) {
		this.name = builder.getName();
		this.surname = builder.getSurname();
		this.gender = builder.getGender();
		this.phoneNumber = builder.getPhoneNumber();
		this.address = builder.getAddress();
		this.username = builder.getUsername();
		this.password = builder.getPassword();
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName())
				.append(",")
			    .append(this.getUsername())
			    .append(",")
			    .append(this.getPassword())
			    .append(",")
			    .append(this.getName())
			    .append(",")
			    .append(this.getSurname())
			    .append(",")
			    .append(this.getGender())
			    .append(",")
			    .append(this.getPhoneNumber())
			    .append(",")
			    .append(this.getAddress())
			    .toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) {
			return true;
		}
		if(!(other instanceof User)) {
			return false;
		}
		
		User u = (User)other;
		if(this.getUsername().equals(u.getUsername())) {
			return true;
		}
		return false;
	}

}
