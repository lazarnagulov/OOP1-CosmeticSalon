package com.nagulov.app;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
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
		Beautician b = new UserBuilder("JovanaPanic", "sifra123")
				.setName("Jovana")
				.setSurname("Panic")
				.setGender("Female")
				.setAddress("Jovane Panic 24")
				.setPhoneNumber("0642342342")
				.buildBeautician();
		
		
		b.addTreatment(DataBase.services.get("Face"));
		
		DataBase.users.put(m.getUsername(), m);
		DataBase.users.put(r.getUsername(), r);
		DataBase.users.put(c.getUsername(), c);
		DataBase.users.put(b.getUsername(), b);
		
		DataBase.saveUsers();

	}
	
	static void testSaveServices() {
		
		CosmeticTreatment legs = new CosmeticTreatment("Legs", LocalTime.of(2, 0)); 
		CosmeticTreatment thigs = new CosmeticTreatment("Thigs", LocalTime.of(1, 0));
		CosmeticTreatment bikiniZone = new CosmeticTreatment("Bikini zone", LocalTime.of(1, 0));
		CosmeticTreatment face = new CosmeticTreatment("Face", LocalTime.of(1, 0));
		CosmeticTreatment hands = new CosmeticTreatment("Hands",LocalTime.of(1, 0));
		CosmeticTreatment classic = new CosmeticTreatment("Classic", LocalTime.of(2, 0));
		CosmeticTreatment hydrafacial = new CosmeticTreatment("Hydrafacial", LocalTime.of(3, 0));
		CosmeticTreatment oxygen = new CosmeticTreatment("Oxygen", LocalTime.of(2, 0));
		CosmeticTreatment hollywood = new CosmeticTreatment("Hollywood", LocalTime.of(2, 0));
		CosmeticTreatment dermapen = new CosmeticTreatment("Dermapen", LocalTime.of(3, 0));
		CosmeticTreatment pedicure = new CosmeticTreatment("Pedicure", LocalTime.of(1, 0));
		CosmeticTreatment pedicureForPensioners = new CosmeticTreatment("Pedicure for pensioners", LocalTime.of(1, 0));
				
		CosmeticService cs = new CosmeticService("Depilation");
		cs.addTreatment(legs); 
		cs.addTreatment(thigs); 
		cs.addTreatment(bikiniZone);
		cs.addTreatment(face);
		cs.addTreatment(hands);
		CosmeticService cs1 = new CosmeticService("Face");
		cs1.addTreatment(classic);
		cs1.addTreatment(hydrafacial);
		cs1.addTreatment(oxygen);
		cs1.addTreatment(hollywood);
		cs1.addTreatment(dermapen);
		CosmeticService cs2 = new CosmeticService("Pedicure");
		cs2.addTreatment(pedicure);
		cs2.addTreatment(pedicureForPensioners);
		
		Pricelist.getInstance().setPrice(legs, 1100.0);
		Pricelist.getInstance().setPrice(thigs, 900.0);
		Pricelist.getInstance().setPrice(bikiniZone, 700.0);
		Pricelist.getInstance().setPrice(face, 700);
		Pricelist.getInstance().setPrice(hands, 800);
		Pricelist.getInstance().setPrice(classic, 3500);
		Pricelist.getInstance().setPrice(hydrafacial, 4000);
		Pricelist.getInstance().setPrice(oxygen, 4100);
		Pricelist.getInstance().setPrice(hollywood, 4200);
		Pricelist.getInstance().setPrice(dermapen, 8000);
		Pricelist.getInstance().setPrice(pedicure, 1100);
		Pricelist.getInstance().setPrice(pedicureForPensioners, 700);
		
		DataBase.services.put(cs.getName(), cs);
		DataBase.services.put(cs1.getName(), cs1);
		DataBase.services.put(cs2.getName(), cs2);
		
		DataBase.saveServices();
	}
	
	static void testSaveTreatments() {
		
		Beautician b = (Beautician) DataBase.users.get("JovanaPanic");
		Client c = (Client)DataBase.users.get("MilosMrdja");
		
		b.addTreatment(DataBase.services.get("Face"));
		
		Treatment t = new TreatmentBuilder()
				.setId(1)
				.setClient(c)
				.setBeautician(b)
				.setService(DataBase.services.get("Face"))
				.setDate(LocalDateTime.now().withMinute(0))
				.setStatus(TreatmentStatus.SCHEDULED)
				.setTreatment(DataBase.services.get("Face").getTreatment("Classic"))
				.build();
		
		DataBase.treatments.put(t.getId(), t);
		DataBase.saveTreatments();
	}
	
	public static void main(String[] args) {
		
		
//		testSaveServices();
		
		DataBase.loadServices();
//		Debug.listUsers();
//		testSaveUsers();
		
		DataBase.loadUsers();
//		Debug.listUsers();
		
		DataBase.loadTreatments();
//		Debug.listTreatments();
		
//		testSaveTreatments();
//		
//		
		new LoginDialog();
	}
}
