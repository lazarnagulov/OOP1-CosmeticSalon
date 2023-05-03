package com.nagulov.treatments;

import java.time.LocalTime;

public class CosmeticTreatment {
	
	private LocalTime duration;
	private String name;
	
	public CosmeticTreatment() {
		
	}
	
	public CosmeticTreatment(String name, LocalTime duration) {
		setName(name);
		setDuration(duration);
	}

	public LocalTime getDuration() {
		return duration;
	}

	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
