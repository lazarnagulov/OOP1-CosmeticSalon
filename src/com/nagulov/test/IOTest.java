package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.ServiceController;
import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class IOTest {

	private static HashMap<String, User> testUserMap = new HashMap<String, User>();
	private static HashMap<String, CosmeticService> testServiceMap = new HashMap<String, CosmeticService>();
	private static HashMap<Integer, Treatment> testTreatmentMap = new HashMap<Integer, Treatment>();
	private static final File USER_TEST = new File("src" + DataBase.SEPARATOR + "com"+ DataBase.SEPARATOR + "nagulov" + DataBase.SEPARATOR + "data"+ DataBase.SEPARATOR + "testUsers.csv");
	private static final File SERVICE_TEST = new File("src" + DataBase.SEPARATOR + "com"+ DataBase.SEPARATOR + "nagulov" + DataBase.SEPARATOR + "data"+ DataBase.SEPARATOR + "testServices.csv");
	private static final File TREATMENT_TEST = new File("src" + DataBase.SEPARATOR + "com"+ DataBase.SEPARATOR + "nagulov" + DataBase.SEPARATOR + "data"+ DataBase.SEPARATOR + "testTreatment.csv");
	
	private static Beautician b = null;
	private static Client c = null;
	private static Manager m = null;
	private static Receptionist r = null;
	
	private static Treatment t = null;
	
	private static CosmeticService service, service2;
	private static CosmeticTreatment treatment, treatment2, treatment3;
	
	@BeforeAll
	public static void init() {
		b = new StaffBuilder("Beautician", "123").buildBeautician();
		c = new UserBuilder("Client", "123").buildClient();
		m = new UserBuilder("Manager", "123'").buildManager();
		r = new StaffBuilder("Receptionist", "123").buildReceptionist();
		
		service = new CosmeticService("Service");
		service2 = new CosmeticService("Service2");
		treatment = new CosmeticTreatment("Treatment", LocalTime.of(1, 0));
		treatment2 = new CosmeticTreatment("Treatment2", LocalTime.of(1, 0));
		treatment3 = new CosmeticTreatment("Treatment3", LocalTime.of(1, 0));

		service.addTreatment(treatment);
		service.addTreatment(treatment2);
		service2.addTreatment(treatment3);
		
		b.addService(service);
		
		Pricelist.getInstance().setPrice(treatment3, 1000.0);
		Pricelist.getInstance().setPrice(treatment2, 2000.0);
		Pricelist.getInstance().setPrice(treatment, 3000.0);

		t = new TreatmentBuilder()
				.setBeautician(b)
				.setClient(c)
				.setService(service)
				.setTreatment(treatment)
				.setDate(LocalDateTime.now())
				.setPrice(Pricelist.getInstance().getPrice(treatment))
				.setStatus(TreatmentStatus.SCHEDULED)
				.build();
		
		testServiceMap.put(service.getName(), service);
		testServiceMap.put(service2.getName(), service2);
		
		testUserMap.put(b.getUsername(), b);
		testUserMap.put(c.getUsername(), c);
		testUserMap.put(m.getUsername(), m);
		testUserMap.put(r.getUsername(), r);
		
		testTreatmentMap.put(t.getId(), t);
		
		TreatmentController.getInstance().getTreatments().clear();
		ServiceController.getInstance().getServices().clear();
		UserController.getInstance().getUsers().clear();
		
		TreatmentController.getInstance().addTreatment(t);
		
		ServiceController.getInstance().getServices().put(service.getName(), service);
		ServiceController.getInstance().getServices().put(service2.getName(), service2);
		
		UserController.getInstance().addUser(b);
		UserController.getInstance().addUser(c);
		UserController.getInstance().addUser(r);
		UserController.getInstance().addUser(m);
	}
	
	@AfterAll
	public static void destroyTestFiles() {
		USER_TEST.delete();
		SERVICE_TEST.delete();
		TREATMENT_TEST.delete();
	}
	
	@Test
	@DisplayName("Testing user input/output")
	public void testUserIO() {
		DataBase.saveUsers(USER_TEST);
		UserController.getInstance().getUsers().clear();
		DataBase.loadUsers(USER_TEST);
		assertEquals(testUserMap.keySet(), UserController.getInstance().getUsers().keySet());
	}
	
	@Test
	@DisplayName("Testing service input/output")
	public void testServiceIO() {
		DataBase.saveServices(SERVICE_TEST);
		ServiceController.getInstance().getServices().clear();
		DataBase.loadServices(SERVICE_TEST);
		assertEquals(testServiceMap.keySet(), ServiceController.getInstance().getServices().keySet());
		assertNotNull(ServiceController.getInstance().getServices().get(service.getName()).getTreatment("Treatment"));
		assertNotNull(ServiceController.getInstance().getServices().get(service.getName()).getTreatment("Treatment2"));
		assertNotNull(ServiceController.getInstance().getServices().get(service2.getName()).getTreatment("Treatment3"));
	}

	@Test
	@DisplayName("Testing treatments input/output")
	public void testTreatmentIO() {
		DataBase.saveTreatments(TREATMENT_TEST);
		TreatmentController.getInstance().getTreatments().clear();
		DataBase.loadTreatments(TREATMENT_TEST);
		assertEquals(testTreatmentMap.keySet(), TreatmentController.getInstance().getTreatments().keySet());
	}
	
}
