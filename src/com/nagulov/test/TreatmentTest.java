package com.nagulov.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.StaffBuilder;
import com.nagulov.users.UserBuilder;

public class TreatmentTest {
	
	private static CosmeticService service;
	private static CosmeticTreatment treatment;
	private static LocalDateTime datetime;
	private static Beautician b;
	private static Client c;
	
	@BeforeAll
	public static void init() {
		b = new StaffBuilder("Beautician", "123").buildBeautician();
		c = new UserBuilder("Client", "123").buildClient();
		datetime = LocalDateTime.of(2023, 6, 8, 13, 0);
		service = new CosmeticService("Service");
		treatment = new CosmeticTreatment("Treatment", LocalTime.of(1, 0));
		
		Pricelist.getInstance().setPrice(treatment, 1000);
		
		service.addTreatment(treatment);
		b.addService(service);
		
		UserController.getInstance().addUser(b);
	}	
	
	@Test
	@DisplayName("Creating treatment in past")
	public void testCreatingTreatmentInPast() {
		assertEquals(Validator.createTreatment(service, b, datetime.minusMonths(1)), ErrorMessage.INVALID_DATE);
	}
	
	
	@Test
	@DisplayName("Cheking if beautician can schedule two treatments at the same time")
	public void testUserCantOperate() {
		TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, service, treatment, b, datetime, c);
		assertEquals(Validator.createTreatment(service, b, datetime), ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE);
	}
	
	@Test
	@DisplayName("Creating treatment for user with loyality card")
	public void testTreatmentLoyalityCard() {
		UserController.getInstance().loyaltyCardNeeded = 0;
		Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, service, treatment, b, datetime, c);
		assertEquals(Pricelist.getInstance().getPrice(treatment) * 0.9, t.getPrice());
		UserController.getInstance().loyaltyCardNeeded = 100000.0;
	}
	
	@Test
	@DisplayName("Checking treatment income after client cancelation")
	public void testTreatmentClientCancelation() {
		Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.CANCELED_BY_THE_CLIENT, service, treatment, b, datetime, c);
		assertEquals(Pricelist.getInstance().getPrice(treatment) * 0.1, t.getIncome());
	}
	
	@Test
	@DisplayName("Checking treatment income after salon cancelation")
	public void testTreatmentSalonCalcelation() {
		Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.CANCELED_BY_THE_SALON, service, treatment, b, datetime, c);
		assertEquals(0, t.getIncome());
	}
	
	@Test
	@DisplayName("Creating treatment with beautician without service")
	public void testInvalidBeauticianTreatment() {
		b.removeService(service);
		assertEquals(Validator.createTreatment(service, b, datetime), ErrorMessage.BEAUTICIAN_WITHOUT_SERVICE);
		b.addService(service);
	}
	
	@Test
	@DisplayName("Checking scheduled treatment price after pricelist change")
	public void testTreatmentPriceChanged() {
		double currentPrice = Pricelist.getInstance().getPrice(treatment);
		Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, service, treatment, b, datetime, c);
		Pricelist.getInstance().setPrice(treatment, 500.0);
		assertEquals(t.getPrice(), currentPrice);
	}
}
