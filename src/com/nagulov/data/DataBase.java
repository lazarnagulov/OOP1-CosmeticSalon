package com.nagulov.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
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

public class DataBase {
	
	public static final String SEPARATOR = System.getProperty("file.separator");
	
	public static int treatmentId = -1;
	
	public static String salonName = "Comsetic Salon Nagulov";
	public static LocalTime opening = LocalTime.of(8, 0);
	public static LocalTime closing = LocalTime.of(20, 0);
	
	public static final String CLIENT = "Client";
	public static final String BEAUTICIAN = "Beautician";
	public static final String MANAGER = "Manager";
	public static final String RECEPTIONIST = "Receptionist";
	
	public static final String USER_HEADER = "Role,Username,Password,Name,Surname,Gender,Phone number,Address\n"; 
	public static final String SERVICE_HEADER = "Service,Treatment,Duration,Price\n";
	public static final String TREATMENT_HEADER = "Status,Service,Treatment,Beautician,Date,Client,Price\n";
	
	private static final File USERS_FILE = new File("src" + SEPARATOR + "com"+ SEPARATOR + "nagulov" + SEPARATOR + "data"+ SEPARATOR + "users.csv");
	private static final File SERVICES_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR +"data" + SEPARATOR + "services.csv");
	private static final File TREATMENTS_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR + "data" + SEPARATOR + "treatments.csv");
	
	public static final DateTimeFormatter TREATMENTS_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	
	public static HashMap<CosmeticTreatment, CosmeticService> cosmeticTreatments = new HashMap<CosmeticTreatment, CosmeticService>();
	
	
	public static HashMap<String, CosmeticService> services = new HashMap<String, CosmeticService>();
	

	public static User loggedUser;
	
	public static void saveTreatments() {
		saveTreatments(TREATMENTS_FILE);
	}
	
	public static void saveTreatments(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			out.print(TREATMENT_HEADER);
			for(Map.Entry<Integer, Treatment> treatment : TreatmentController.getInstance().getTreatments().entrySet()) {
				try {
					out.print(treatment.getKey());
					out.print(treatment.getValue());
					out.println();
				}catch(NullPointerException e) {
					System.err.printf("Treatment with id: %d (%s) was modified and cannot be saved!", treatment.getKey(), treatment.getValue());
					continue;
				}
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	public static void loadTreatments() {
		loadTreatments(TREATMENTS_FILE);
	}
	
	public static void loadTreatments(File file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				int id = Integer.parseInt(data[0]);
				treatmentId = treatmentId < id ? id : treatmentId;   
				TreatmentStatus status = TreatmentStatus.valueOf(data[1].toUpperCase().replace(" ", "_"));
				CosmeticService service = services.get(data[2]);
				if(service == null) {
					continue;
				}
				CosmeticTreatment treatment = service.getTreatment(data[3]);
				if(treatment == null) {
					continue;
				}
				Beautician beautician = (Beautician)UserController.getInstance().getUser(data[4]);
				if(beautician == null) {
					continue;
				}
				LocalDateTime date = LocalDateTime.parse(data[5], TREATMENTS_DATE_FORMAT);
				Client client = (Client)UserController.getInstance().getUser(data[6]);
				if(client == null) {
					continue;
				}
				double price = Double.parseDouble(data[7]);
				TreatmentController.getInstance().getTreatments().put(id, new TreatmentBuilder()
					.setId(id)
					.setStatus(status)
					.setService(service)
					.setTreatment(treatment)
					.setBeautician(beautician)
					.setDate(date)
					.setClient(client)
					.setPrice(price)
					.build());
				client.addTreatment(TreatmentController.getInstance().getTreatments().get(id));
			}
			in.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void saveServices() {
		saveServices(SERVICES_FILE);
	}
	
	public static void saveServices(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			out.append(SERVICE_HEADER);
			for(Map.Entry<String, CosmeticService> service : services.entrySet()) {
				for(CosmeticTreatment ct : service.getValue().getTreatments()) {
					out.append(service.getKey() + ",");
					out.append(ct.getName() + ",");
					out.append(ct.getDuration() + ",");
					out.append(Double.valueOf(Pricelist.getInstance().getPrice(ct)).toString());
					out.println();
				}
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	public static void loadServices() {
		loadServices(SERVICES_FILE);
	}
	
	public static void loadServices(File file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String input;
			CosmeticService service = null;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				if(service == null) {
					service = new CosmeticService(data[0]);
					CosmeticTreatment treatment = new CosmeticTreatment(data[1], LocalTime.parse(data[2])); 
					service.addTreatment(treatment);
					Pricelist.getInstance().setPrice(treatment, Double.parseDouble(data[3]));
				}
				else if(service.getName().equals(data[0])) {
					CosmeticTreatment treatment = new CosmeticTreatment(data[1], LocalTime.parse(data[2])); 
					service.addTreatment(treatment);
					Pricelist.getInstance().setPrice(treatment, Double.parseDouble(data[3]));
				}
				else {
					services.put(service.getName(), service);
					service = services.get(data[0]);
					if(service == null) { 
						service = new CosmeticService(data[0]);
					}
					CosmeticTreatment treatment = new CosmeticTreatment(data[1], LocalTime.parse(data[2])); 
					service.addTreatment(treatment);
					Pricelist.getInstance().setPrice(treatment, Double.parseDouble(data[3]));
				}
			}
			if(service != null)
				services.put(service.getName(), service);
			in.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void saveUsers() {
		saveUsers(USERS_FILE, false);
	}
	
	public static void saveUsers(File file) {
		saveUsers(file, false);
	}
	
	public static void saveUsers(File file, boolean append) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8")));
			out.append(USER_HEADER);
			for(String username : UserController.getInstance().getUsers().keySet()) {
				out.append(UserController.getInstance().getUser(username).toString());
				out.println(); 
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	public static void loadUsers() {
		loadUsers(USERS_FILE);
	}
	
	public static void loadUsers(File file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				switch(data[0]) {
					case RECEPTIONIST:
						Receptionist receptionist = new StaffBuilder(data[1], data[2])
						.setBonuses(Double.parseDouble(data[8]))
						.setIncome(Double.parseDouble(data[9]))
						.setInternship(Integer.parseInt(data[10]))
						.setQualification(Integer.parseInt(data[11]))
						.setSalary(Double.parseDouble(data[12]))
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildReceptionist();
						UserController.getInstance().addUser(receptionist);
						break;
					case MANAGER:
						Manager manager = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildManager();
						UserController.getInstance().addUser(manager);
						break;
					case CLIENT:
						Client client = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildClient();
						client.setSpent(Double.parseDouble(data[8]));
						client.setHasLoyalityCard(Boolean.parseBoolean(data[9]));
						client.setBalance(Double.parseDouble(data[10]));
						UserController.getInstance().addUser(client);
						break;
					case BEAUTICIAN:
						Beautician beautician = new StaffBuilder(data[1], data[2])
						.setBonuses(Double.parseDouble(data[8]))
						.setIncome(Double.parseDouble(data[9]))
						.setInternship(Integer.parseInt(data[10]))
						.setQualification(Integer.parseInt(data[11]))
						.setSalary(Double.parseDouble(data[12]))
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildBeautician();
						if(data.length >= 14) {
							String[] treatments = data[13].split(";");
							for(int i = 0; i < treatments.length; ++i) {
								CosmeticService cs = DataBase.services.get(treatments[i]);
								if(cs != null)
									beautician.addTreatment(cs);
							}
						}
						UserController.getInstance().addUser(beautician);
						break;
				}
			}
			in.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
