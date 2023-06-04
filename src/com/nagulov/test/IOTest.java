package com.nagulov.test;

import java.io.File;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

class IOTest {

	private static Manager m;
	private static Receptionist r;
	private static Client c;
	private static Beautician ct;
	
	private static final File testFile = new File("/testfile.csv");
	private static HashMap<String, User> testUserDB = new HashMap<String, User>();
	private static HashMap<String, CosmeticService> testServiceDB = new HashMap<String, CosmeticService>();
	private static HashMap<Integer, Treatment> testTreatmentDB = new HashMap<Integer, Treatment>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		m = new UserBuilder("LazarNagulov", "sifra")
				.setName("Lazar")
				.setSurname("Nagulov")
				.setGender("Musko")
				.setAddress("Lazara Nagulov 3")
				.setPhoneNumber("0612133213").buildManager();
		r = new UserBuilder("FilipTot", "sifra2")
				.setName("Filip")
				.setSurname("Tot")
				.setGender("Musko")
				.setAddress("Filipa Tota 2")
				.setPhoneNumber("0696663332")
				.buildReceptionist();
		c = new UserBuilder("MilosMrdja", "sifra123")
				.setName("Milos")
				.setSurname("Mrdja")
				.setGender("Musko")
				.setAddress("Milosa Mrdje 4")
				.setPhoneNumber("0642342342")
				.buildClient();
		ct = new UserBuilder("JovanaPanic", "sifra123")
				.setName("Jovana")
				.setSurname("Panic")
				.setGender("Zensko")
				.setAddress("Jovane Panic 24")
				.setPhoneNumber("0642342342")
				.buildBeautician();
		
		
//		DataBase.users.put(m.getUsername(), m);
//		DataBase.users.put(r.getUsername(), r);
//		DataBase.users.put(c.getUsername(), c);
//		DataBase.users.put(ct.getUsername(), ct);
		
		testUserDB.put(m.getUsername(), m);
		testUserDB.put(r.getUsername(), r);
		testUserDB.put(c.getUsername(), c);
		testUserDB.put(ct.getUsername(), ct);
		
//		CosmeticService cs = new CosmeticService("Depilation");
//		cs.addTreatment("Legs", 1100.0);
//		cs.addTreatment("Thigs", 900.0);
//		cs.addTreatment("Bikini zone", 700.0);
//		cs.addTreatment("Face", 700.0);
//		cs.addTreatment("Hands", 800.0);
//		CosmeticService cs1 = new CosmeticService("Face");
//		cs1.addTreatment("Classic", 3500.0);
//		cs1.addTreatment("Hydrafacial", 4000.0);
//		cs1.addTreatment("Oxygen", 4100.0);
//		cs1.addTreatment("Hollywood", 4200.0);
//		cs1.addTreatment("Dermapen", 8000.0);
//		CosmeticService cs2 = new CosmeticService("Pedicure");
//		cs2.addTreatment("Pedicure", 1100.0);
//		cs2.addTreatment("Pedicure for pensioners", 700.0);
//		
//		DataBase.services.put(cs.getName(), cs);
//		DataBase.services.put(cs1.getName(), cs1);
//		DataBase.services.put(cs2.getName(), cs2);
//		
//		testServiceDB.put(cs.getName(), cs);
//		testServiceDB.put(cs1.getName(), cs1);
//		testServiceDB.put(cs2.getName(), cs2);
//		
//		ct.addService(cs1);
//		ct.addService(cs2);
//		
//		Treatment t = new TreatmentBuilder()
//				.setId(1)
//				.setClient(c)
//				.setBeautician(ct)
//				.setService(cs1)
//				.setDate(LocalDateTime.now())
//				.setStatus(TreatmentStatus.SCHEDULED)
//				.setTreatment("Classic")
//				.build();
//		Treatment t2 = new TreatmentBuilder()
//				.setId(2)
//				.setClient(c)
//				.setBeautician(ct)
//				.setService(cs2)
//				.setDate(LocalDateTime.now())
//				.setStatus(TreatmentStatus.SCHEDULED)
//				.setTreatment("Pedicure")
//				.build();
//		
//		DataBase.treatments.put(t.getId(), t);
//		DataBase.treatments.put(t2.getId(), t2);
//		
//		testTreatmentDB.put(t.getId(), t);
//		testTreatmentDB.put(t2.getId(), t2);
		
	}	
	
	@AfterEach
	public static void destroy() {
		testFile.delete();
	}

	@Test
	@DisplayName("Saving and loading users")
	public void testSaveAndLoadUsers() {
		DataBase.saveUsers(testFile, false);
//		DataBase.users.clear();
		DataBase.loadUsers(testFile);
//		assertEquals(DataBase.users.toString(), testUserDB.toString());
	}
	
//	@Test
//	@DisplayName("Saving and loading services")
//	public void testSaveAndLoadServices() {
//		DataBase.saveServices(testFile);
//		DataBase.services.clear();
//		DataBase.loadServices(testFile);
//		assertEquals(DataBase.services.toString(), testServiceDB.toString());
//	}
//	
//	@Test
//	@DisplayName("Saving and loading treatments")
//	public void testSaveAndLoadTreatments() {
//		DataBase.loadServices();
//		DataBase.saveTreatments(testFile);
//		DataBase.treatments.clear();
//		DataBase.loadTreatments(testFile);
//		assertEquals(DataBase.treatments.toString(), testTreatmentDB.toString());
//	}

}
