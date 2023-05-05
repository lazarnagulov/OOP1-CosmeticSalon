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

import com.nagulov.controllers.ManagerController;
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
	
	public static final String USER_HEADER = "Role,Username,Password,Name,Surname,Gender,Phone number, Address\n"; 
	public static final String SERVICE_HEADER = "Service,Treatment,Duration,Price\n";
	public static final String TREATMENT_HEADER = "Id,Status,Service,Treatment,Beautician,Date,Client\n";
	
	private static final File USERS_FILE = new File("src" + SEPARATOR + "com"+ SEPARATOR + "nagulov" + SEPARATOR + "data"+ SEPARATOR + "users.csv");
	private static final File SERVICES_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR +"data" + SEPARATOR + "services.csv");
	private static final File TREATMENTS_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR + "data" + SEPARATOR + "treatments.csv");
	
	public static final DateTimeFormatter TREATMENTS_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	
	public static HashMap<CosmeticTreatment, CosmeticService> cosmeticTreatments = new HashMap<CosmeticTreatment, CosmeticService>();
	
	public static HashMap<String, User> users = new HashMap<String,User>();
	public static HashMap<String, CosmeticService> services = new HashMap<String, CosmeticService>();
	public static HashMap<Integer, Treatment> treatments = new HashMap<Integer, Treatment>();

	public static User loggedUser;
	
	public static void saveTreatments() {
		saveTreatments(TREATMENTS_FILE);
	}
	
	public static void saveTreatments(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			out.print(TREATMENT_HEADER);
			for(Map.Entry<Integer, Treatment> treatment : treatments.entrySet()) {
				out.print(treatment.getKey());
				out.print(treatment.getValue());
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
				TreatmentStatus status = TreatmentStatus.valueOf(data[1].toUpperCase());
				CosmeticService service = services.get(data[2]);
				CosmeticTreatment treatment = service.getTreatment(data[3]);
				Beautician beautician = (Beautician)users.get(data[4]);
				LocalDateTime date = LocalDateTime.parse(data[5], TREATMENTS_DATE);
				Client client = (Client)users.get(data[6]);
				treatments.put(id, new TreatmentBuilder()
						.setId(id)
						.setStatus(status)
						.setService(service)
						.setTreatment(treatment)
						.setBeautician(beautician)
						.setDate(date)
						.setClient(client)
						.build());
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
					Double price = Pricelist.getInstance().getPrice(ct); 
					out.append(price.toString());
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
					service = new CosmeticService(data[0]);
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
			for(String username : users.keySet()) {
				out.append(users.get(username).toString());
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
						.setQulification(Integer.parseInt(data[11]))
						.setSalary(Double.parseDouble(data[12]))
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildReceptionist();
						DataBase.users.put(receptionist.getUsername(), receptionist);
						break;
					case MANAGER:
						Manager manager = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildManager();
						DataBase.users.put(manager.getUsername(), manager);
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
						
						if(data.length >= 11) {
							String[] clientTreatments = data[10].split(";");
							for(int i = 0; i < clientTreatments.length; ++i) {
								client.addTreatment(ManagerController.getInstance().getTreatment(Integer.parseInt(clientTreatments[0])));
							}
						}
						DataBase.users.put(client.getUsername(), client);
						break;
					case BEAUTICIAN:
						Beautician beautician = new StaffBuilder(data[1], data[2])
						.setBonuses(Double.parseDouble(data[8]))
						.setIncome(Double.parseDouble(data[9]))
						.setInternship(Integer.parseInt(data[10]))
						.setQulification(Integer.parseInt(data[11]))
						.setSalary(Double.parseDouble(data[12]))
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildBeautician();
						if(data.length >= 13) {
							String[] treatments = data[13].split(";");
							for(int i = 0; i < treatments.length; ++i) {
								beautician.addTreatment(DataBase.services.get(treatments[i]));
							}
						}
						DataBase.users.put(beautician.getUsername(), beautician);
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
