package com.nagulov.app;

import com.nagulov.data.DataBase;
import com.nagulov.ui.LoginDialog;

public class App {
	
	public static void main(String[] args) {
		DataBase.loadSalon();
		DataBase.loadServices();
		DataBase.loadUsers();
		DataBase.loadTreatments();

		new LoginDialog();
	}
}
