package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.ServiceController;
import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.reports.Report;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.UserBuilder;

public class ReportTest {

	private static Beautician b,b2;
	private static Client c,c2;
	
	private static CosmeticService service, service2;
	private static CosmeticTreatment treatment, treatment2, treatment3;
	
	private static Treatment t, t2, t3;
	
	@BeforeAll
	public static void init() {
		TreatmentController.getInstance().getTreatments().clear();
		UserController.getInstance().getUsers().clear();
		ServiceController.getInstance().getServices().clear();
		
		b = new StaffBuilder("Beautician", "123").buildBeautician();
		b2 = new StaffBuilder("Beautician1", "123").buildBeautician();
		c = new UserBuilder("Client", "123").buildClient();
		c2 = new UserBuilder("Client2", "123").buildClient();
		
		service = new CosmeticService("Service");
		service2 = new CosmeticService("Service2");
		treatment = new CosmeticTreatment("Treatment", LocalTime.of(1, 0));
		treatment2 = new CosmeticTreatment("Treatment2", LocalTime.of(1, 0));
		treatment3 = new CosmeticTreatment("Treatment3", LocalTime.of(1, 0));

		service.addTreatment(treatment);
		service.addTreatment(treatment2);
		service2.addTreatment(treatment3);
		
		b.addService(service);
		b2.addService(service);
		
		Pricelist.getInstance().setPrice(treatment3, 1000.0);
		Pricelist.getInstance().setPrice(treatment2, 2000.0);
		Pricelist.getInstance().setPrice(treatment, 3000.0);

		t = TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, service, treatment, b, LocalDateTime.now(), c);
		t2 = TreatmentController.getInstance().createTreatment(TreatmentStatus.PERFORMED, service, treatment, b2, LocalDateTime.now().minusDays(1), c);		
		t3 = TreatmentController.getInstance().createTreatment(TreatmentStatus.PERFORMED, service, treatment, b, LocalDateTime.now().minusDays(2), c2);
		
		UserController.getInstance().addUser(c);
		UserController.getInstance().addUser(c2);

		TreatmentController.getInstance().addTreatment(t);
		TreatmentController.getInstance().addTreatment(t2);
		TreatmentController.getInstance().addTreatment(t3);
		
		UserController.getInstance().loyaltyCardNeeded = 3000.0;
		
	}	
	@Test
	@DisplayName("Testing treatment report")
	public void testTreatmentReport() {
		HashMap<TreatmentStatus, Integer> data =  Report.calculateTreatmentsReport();
		assertEquals(data.get(TreatmentStatus.SCHEDULED), 1);
		assertEquals(data.get(TreatmentStatus.PERFORMED), 2);
	}
	
	@Test
	@DisplayName("Testing service report")
	public void testServiceReport() {
		ArrayList<Double> data =  (ArrayList<Double>)Report.calculateComseticTreatmentReport(treatment, LocalDate.now().minusDays(2), LocalDate.now());
		assertEquals(data.get(0), 3.0);
		assertEquals(data.get(1), 9000.0);
	}
	
	@Test
	@DisplayName("Testing beautician report")
	public void testBeauticianReport() {
		HashMap<Beautician, ArrayList<Double>> data =  Report.calculateBeauticianReport(LocalDate.now().minusDays(2), LocalDate.now());
		assertEquals(data.get(b).get(0), 3000.0);
		assertEquals(data.get(b).get(1), 1.0);
		assertEquals(data.get(b2).get(0), 3000.0);
		assertEquals(data.get(b2).get(1), 1.0);
	}

	@Test
	@DisplayName("Testing loyality user report")
	public void testLoyalityReport() {
		assertEquals(Report.calculateLoyalityReport().size(), 2);
	}
}