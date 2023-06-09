package com.nagulov.controllers;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.Treatment;
import com.nagulov.users.Client;

public class ClientController {
	
	private static ClientController instance = null;
	
	private ClientController() {
		
	}
	
	public static ClientController getInstance() {
		if(instance == null) {
			instance = new ClientController();
		}
		return instance;
	}
	
	public void scheduleTreatment(Treatment treatment) {
		((Client) DataBase.loggedUser).addTreatment(treatment);
	}

}