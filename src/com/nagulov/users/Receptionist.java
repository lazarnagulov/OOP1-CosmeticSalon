package com.nagulov.users;

public class Receptionist extends Staff{
	
	public Receptionist() {
		
	}

	public Receptionist(StaffBuilder builder) {
		super(builder);
	}
	
	public Receptionist(UserBuilder builder) {
		super(builder);
	}
	
}
