package com.nagulov.data;

import com.nagulov.controllers.ServiceController;
import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;

public class Debug {
	public static void listServices() {
		System.out.println(ServiceController.getInstance().getServices());
	}
	
	public static void listUsers() {
		System.out.println(UserController.getInstance().getUsers());
	}
	
	public static void listTreatments() {
		System.out.println(TreatmentController.getInstance().getTreatments());
	}
}
