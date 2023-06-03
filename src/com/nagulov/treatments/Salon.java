package com.nagulov.treatments;

import java.time.LocalTime;

import com.nagulov.data.DataBase;

public class Salon {

	public static Salon instance = null;
	
	private String salonName = "Comsetic Salon Nagulov";
	private LocalTime opening = LocalTime.of(8, 0);
	private LocalTime closing = LocalTime.of(20, 0);
	
	private Salon() {
		
	}
	
	public static Salon getInstance() {
		if(instance == null) {
			instance = new Salon();
		}
		return instance;
	}
	
	public LocalTime getClosing() {
		return closing;
	}

	public void setClosing(LocalTime closing) {
		this.closing = closing;
	}

	public LocalTime getOpening() {
		return opening;
	}

	public void setOpening(LocalTime opening) {
		this.opening = opening;
	}

	public String getSalonName() {
		return salonName;
	}

	public void setSalonName(String salonName) {
		this.salonName = salonName;
	}
}
