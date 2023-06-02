package com.nagulov.controllers;

import java.time.LocalDateTime;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class ReceptionistController {
	//TODO be able to change treatment price
	private static ReceptionistController instance = null;
	
	protected ReceptionistController() {
		
	}
	
	public static ReceptionistController getInstance() {
		if(instance == null) {
			instance = new ReceptionistController();
		}
		return instance;
	}
	
	
}
