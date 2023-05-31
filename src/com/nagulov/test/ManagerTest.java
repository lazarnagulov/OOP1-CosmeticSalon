package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class ManagerTest {
	
	private  Client c;
	private  Beautician b;
	private  CosmeticService s;
	private  LocalDateTime dt;
	private  UserController managerController = UserController.getInstance();
	
	@BeforeAll
	public void init() {
		c = new UserBuilder("ime", "nesto").buildClient();
		b = new UserBuilder("neko", "nesto").buildBeautician();
		s = new CosmeticService("TestService");
		dt = LocalDateTime.now();
		
//		b.addService(s);
		
//		DataBase.users.put(c.getUsername(), c);
		for(int i=0; i<10; i++) {
//			DataBase.users.put("ime" + i, new UserBuilder("ime" + i, "sifra").buildClient());
		}
	}

	@AfterAll
	public void destroy() {
		managerController.removeUser(c.getUsername());
		managerController.removeUser(b.getUsername());
		managerController.removeService(s);
		for(int i=0; i<10; i++) {
			managerController.removeUser("ime" + i);
		}
	}
	
	@Test
	@DisplayName("Getting user")
	public void testGetUser() {
		User ret = managerController.getUser(c.getUsername());
		assertEquals(c, ret);
	}

	@Test
	@DisplayName("Getting invalid user")
	public void testGetInvalidUser() {
		User ret = managerController.getUser("nepoznato");
		assertNull(ret);
	}
	
	@Test
	@DisplayName("Getting more users")
	public void testGetUsers() {
//		User[] res = {DataBase.users.get(c.getUsername()), DataBase.users.get("ime0"), DataBase.users.get("ime1")};
//		User[] ret = managerController.getUsers(c.getUsername(), "ime0", "ime1");
//		assertEquals(3, ret.length);
//		assertEquals(ret.toString().split("@")[0], res.toString().split("@")[0]);
	}
	
	@Test
	@DisplayName("Getting more users with invalid user")
	public void testGettingUsersWithInvalid() {
//		User[] res = {DataBase.users.get(c.getUsername()), DataBase.users.get("ime0")};
//		User[] ret = managerController.getUsers(c.getUsername(), "ime0", "nepoznato");
//		assertEquals(ret.length, 3);
//		assertEquals(ret.toString().split("@")[0], res.toString().split("@")[0]);
	}

	@Test
	@DisplayName("Updating existing user")
	public void testUpdateUser() {
		fail("Not implemented");
	}
	
	@Test
	@DisplayName("Removing existing user")
	public void testRemoveUser() {
//		managerController.removeUser(c.getUsername());
//		assertNull(DataBase.users.get(c.getUsername()));
	}
	
	@Test
	@DisplayName("Creating service")
	public void testCreateService() {
		managerController.createService("service");
		CosmeticService s = DataBase.services.get("service");
		assertNotNull(s);
		assertEquals("service", s.getName());
	}
	
	@Test
	@DisplayName("Creating cosmetic treatment")
	public void testCreateCosmeticTreatment() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Removing cosmetic treatment")
	public void testRemoveCosmeticTreatment() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Updating cosmetic treatment")
	public void testUpdateCosmeticTreatment() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Getting cosmetic treatment")
	public void testGetCosmeticTreatment() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Removing service")
	public void testRemoveService() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Update service")
	public void testUpdateService() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Getting service")
	public void testGetService() {
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Creating treatment")
	public void testCreateTreatment(){
//		managerController.createTreatment(TreatmentStatus.SCHEDULED, s,"Treatment", b, dt, c);
//		Treatment t = DataBase.treatments.get(DataBase.treatmentId);
//		System.out.println(t);
//		assertNotNull(t);
//		assertEquals(b, t.getBeautician());
//		assertEquals(dt, t.getDate());
//		assertEquals(c, t.getClient());
//		assertEquals("Treatment", t.getTreatment());
//		assertEquals(TreatmentStatus.SCHEDULED, t.getStatus());
		fail("Not implemented!");
	}
	
	@Test
	@DisplayName("Removing treatment")
	public void testRemoveTreatment() {
		managerController.removeTreatment(DataBase.treatmentId);
		assertNull(DataBase.treatments.get(DataBase.treatmentId));
	}
	
	@Test
	@DisplayName("Updating treatment")
	public void testUpdatingTreatment() {
		fail("Not implemented!");
	}
}
