package com.nagulov.data;

import com.nagulov.controllers.UserController;

public class Debug {
	public static void listServices() {
		System.out.println(DataBase.services);
	}
	
	public static void listUsers() {
		System.out.println(UserController.getInstance().getUsers());
	}
	
	public static void listTreatments() {
		System.out.println(DataBase.treatments);
	}
}
