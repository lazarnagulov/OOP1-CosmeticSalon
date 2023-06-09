package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.UserController;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.UserBuilder;

public class UserTest {

	
	private static Client c;
	private static Beautician b;
	
	@BeforeAll
	public static void init() {
		c = new UserBuilder("Client", "123").buildClient();
		b = new StaffBuilder("Beautician", "123").buildBeautician();
		UserController.getInstance().addUser(c);
	}
	
	@Test
	@DisplayName("Logging with invalid user")
	public void testInvalidLogin() {
		assertEquals(Validator.loginUser("Nonvalid", "123"), ErrorMessage.INVALID_USERNAME);
	}
	
	@Test
	@DisplayName("Logging with valid user")
	public void testValidLogin() {
		assertEquals(Validator.loginUser("Client", "123"), ErrorMessage.SUCCESS);
	}
	
	@Test
	@DisplayName("Register user with existing username")
	public void testInvalidRegister() {
		assertEquals(Validator.registerUser("Name", "Surname", "Male", "123456789", "Address 12", "Client", "123"), ErrorMessage.USERNAME_ALREADY_EXISTS);
	}
}
