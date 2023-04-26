package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.users.Client;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class ManagerTest {
	
	private static Client c;
	private static ManagerController managerController = ManagerController.getInstance();
	@BeforeAll
	public static void init() {
		c = new UserBuilder("ime", "nesto").buildClient();
		DataBase.users.put(c.getUsername(), c);
		for(int i=0; i<10; i++) {
			DataBase.users.put("ime" + i, new UserBuilder("ime" + i, "sifra").buildClient());
		}
	}

	@AfterAll
	public static void destroy() {
		managerController.removeUser(c.getUsername());
	}
	
	
	@Test
	@DisplayName("Getting user")
	public void testGetUser() {
		User[] ret = managerController.getUsers(c.getUsername());
		assertEquals(c, ret[0]);
	}

	@Test
	@DisplayName("Getting invalid user")
	public void testGetInvalidUser() {
		User[] ret = managerController.getUsers("nepoznato");
		assertNull(ret[0]);
	}
	
	@Test
	@DisplayName("Getting more users")
	public void testGetUsers() {
		User[] res = {DataBase.users.get(c.getUsername()), DataBase.users.get("ime0"), DataBase.users.get("ime1")};
		User[] ret = managerController.getUsers(c.getUsername(), "ime0", "ime1");
		assertEquals(ret.length, 3);
		assertEquals(res.toString().split("@")[0],ret.toString().split("@")[0]);
	}
	
	@Test
	@DisplayName("Getting more users with invalid user")
	public void testGettingUsersWithInvalid() {
		User[] res = {DataBase.users.get(c.getUsername()), DataBase.users.get("ime0")};
		User[] ret = managerController.getUsers(c.getUsername(), "ime0", "nepoznato");
		assertEquals(ret.length, 3);
		assertEquals(res.toString().split("@")[0],ret.toString().split("@")[0]);
	}

	@Test
	@DisplayName("Updating existing user")
	public void testUpdateUser() {
		User res = new UserBuilder("ime0", "sifra").setName("Pera").buildClient();
		assertEquals(res.toString(), DataBase.users.get("ime0").toString());
	}
	
	@Test
	@DisplayName("Removing existing user")
	public void testRemoveUser() {
		managerController.removeUser(c.getUsername());
		assertNull(DataBase.users.get(c.getUsername()));
	}
	
	@Test
	@DisplayName("Creating treatment")
	public void testCreateTreatment(){
		
	}
	
	@Test
	@DisplayName("Removing treatment")
	public void testRemoveTreatment() {
		
	}
	
	@Test
	@DisplayName("Updating treatment")
	public void testUpdatingTreatment() {
		
	}
	
	
	
}
