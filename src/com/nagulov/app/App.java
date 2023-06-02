package com.nagulov.app;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.ui.LoginDialog;
import com.nagulov.users.Client;

public class App {
	
	public static void main(String[] args) {
		DataBase.loadServices();
		DataBase.loadUsers();
		DataBase.loadTreatments();

		Client c = (Client)UserController.getInstance().getUser("mikamikic");
		c.setSpent(100000.0);
		
		new LoginDialog();
	}
}
