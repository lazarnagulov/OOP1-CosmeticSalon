package com.nagulov.app;

import java.time.LocalDateTime;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.LoginDialog;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.UserBuilder;

public class App {

	static void testSaveUsers() {
		Manager m = new UserBuilder("LazarNagulov", "sifra")
				.setName("Lazar")
				.setSurname("Nagulov")
				.setGender("Male")
				.setAddress("Lazara Nagulov 3")
				.setPhoneNumber("0612133213").buildManager();
		Receptionist r = new UserBuilder("FilipTot", "sifra2")
				.setName("Filip")
				.setSurname("Tot")
				.setGender("Male")
				.setAddress("Filipa Tota 2")
				.setPhoneNumber("0696663332")
				.buildReceptionist();
		Client c = new UserBuilder("MilosMrdja", "sifra123")
				.setName("Milos")
				.setSurname("Mrdja")
				.setGender("Male")
				.setAddress("Milosa Mrdje 4")
				.setPhoneNumber("0642342342")
				.buildClient();
		Beautician ct = new UserBuilder("JovanaPanic", "sifra123")
				.setName("Jovana")
				.setSurname("Panic")
				.setGender("Female")
				.setAddress("Jovane Panic 24")
				.setPhoneNumber("0642342342")
				.buildBeautician();
		
		DataBase.users.put(m.getUsername(), m);
		DataBase.users.put(r.getUsername(), r);
		DataBase.users.put(c.getUsername(), c);
		DataBase.users.put(ct.getUsername(), ct);
		
		DataBase.saveUsers();

	}
	
	static void testSaveServices() {
		CosmeticService cs = new CosmeticService("Depilation");
		cs.addTreatment("Legs", 1100.0);
		cs.addTreatment("Thigs", 900.0);
		cs.addTreatment("Bikini zone", 700.0);
		cs.addTreatment("Face", 700.0);
		cs.addTreatment("Hands", 800.0);
		CosmeticService cs1 = new CosmeticService("Face");
		cs1.addTreatment("Classic", 3500.0);
		cs1.addTreatment("Hydrafacial", 4000.0);
		cs1.addTreatment("Oxygen", 4100.0);
		cs1.addTreatment("Hollywood", 4200.0);
		cs1.addTreatment("Dermapen", 8000.0);
		CosmeticService cs2 = new CosmeticService("Pedicure");
		cs2.addTreatment("Pedicure", 1100.0);
		cs2.addTreatment("Pedicure for pensioners", 700.0);
		
		DataBase.services.put(cs.getName(), cs);
		DataBase.services.put(cs1.getName(), cs1);
		DataBase.services.put(cs2.getName(), cs2);
		
		DataBase.saveServices();
	}
	
	static void testSaveTreatments() {
		
		Beautician ct = (Beautician) DataBase.users.get("JovanaPanic");
		CosmeticService cs1 = DataBase.services.get("Face");
		CosmeticService cs2 = DataBase.services.get("Pedicure");
		Client c = (Client)DataBase.users.get("MilosMrdja");
		
		ct.addService(cs1);
		ct.addService(cs2);
		
		Treatment t = new TreatmentBuilder()
				.setId(1)
				.setClient(c)
				.setBeautician(ct)
				.setService(cs1)
				.setDate(LocalDateTime.now())
				.setStatus(TreatmentStatus.SCHEDULED)
				.setTreatment("Classic")
				.build();
		Treatment t2 = new TreatmentBuilder()
				.setId(2)
				.setClient(c)
				.setBeautician(ct)
				.setService(cs2)
				.setDate(LocalDateTime.now())
				.setStatus(TreatmentStatus.SCHEDULED)
				.setTreatment("Pedicure")
				.build();
		
		DataBase.treatments.put(t.getId(), t);
		DataBase.treatments.put(t2.getId(), t2);
		
		DataBase.saveTreatments();
	}
	
	public static void main(String[] args) {
		
		testSaveUsers();
//		testSaveServices();
		
//		DataBase.loadUsers();
		DataBase.loadServices();
		DataBase.loadTreatments();
//			
//		testSaveTreatments();
//		
//		
		new LoginDialog();
	}
}
