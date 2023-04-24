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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.ServiceModel;
import com.nagulov.users.Client;
import com.nagulov.users.Beautician;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;
import com.nagulov.users.User;
import com.nagulov.users.UserBuilder;

public class DataBase {
	
	public static final String SEPARATOR = System.getProperty("file.separator");
	
	public static final String USER_HEADER = "Role,Username,Password,Name,Surname,Gender,Phone number, Address\n"; 
	public static final String SERVICE_HEADER = "Service,Treatment,Price\n";
	public static final String TREATMENT_HEADER = "Id,Status,Service,Treatment,Beautician,Date,Client\n";
	
	public static final File USERS_FILE = new File("src" + SEPARATOR + "com"+ SEPARATOR + "nagulov" + SEPARATOR + "data"+ SEPARATOR + "users.csv");
	public static final File SERVICES_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR +"data" + SEPARATOR + "services.csv");
	public static final File TREATMENTS_FILE = new File("src" + SEPARATOR + "com" + SEPARATOR + "nagulov" + SEPARATOR + "data" + SEPARATOR + "treatments.csv");
	
	public static final DateTimeFormatter TREATMENTS_DATE = DateTimeFormatter.ofPattern("dd.M.yyyy. HH");

	public static HashMap<String, User> users = new HashMap<String,User>();
	public static HashMap<String, CosmeticService> services = new HashMap<String, CosmeticService>();
	public static HashMap<Integer, Treatment> treatments = new HashMap<Integer, Treatment>();

	public static User loggedUser;
	
	public static void listServices() {
		System.out.println(services);
	}
	
	public static void listUsers() {
		System.out.println(users);
	}
	
	public static void listTreatments() {
		for(Map.Entry<Integer, Treatment> entry : treatments.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static void saveTreatments() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TREATMENTS_FILE), "UTF-8")));
			out.print(TREATMENT_HEADER);
			for(Map.Entry<Integer, Treatment> treatment : treatments.entrySet()) {
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
	
	public static void saveTreatments(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			out.print(TREATMENT_HEADER);
			for(Map.Entry<Integer, Treatment> treatment : treatments.entrySet()) {
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
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(TREATMENTS_FILE), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				int id = Integer.parseInt(data[0]);
				TreatmentStatus status = TreatmentStatus.valueOf(data[1].toUpperCase());
				CosmeticService service = services.get(data[2]);
				String treatment = data[3];
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
	
	public static void loadTreatments(File file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				int id = Integer.parseInt(data[0]);
				TreatmentStatus status = TreatmentStatus.valueOf(data[1]);
				CosmeticService service = services.get(data[2]);
				String treatment = data[3];
				Beautician cosmetologist = (Beautician)users.get(data[4]);
				LocalDateTime date = LocalDateTime.parse(data[5], TREATMENTS_DATE);
				Client client = (Client)users.get(data[6]);
				treatments.put(id, new TreatmentBuilder()
						.setId(id)
						.setStatus(status)
						.setService(service)
						.setTreatment(treatment)
						.setBeautician(cosmetologist)
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
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SERVICES_FILE), "UTF-8")));
			out.append(SERVICE_HEADER);
			for(Map.Entry<String, CosmeticService> service : services.entrySet()) {
				for(Map.Entry<String, Double> treatment : service.getValue().getTreatments().entrySet()) {
					out.append(service.getKey() + "," + treatment.getKey() + "," +  treatment.getValue());
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
	
	public static void saveServices(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
			out.append(SERVICE_HEADER);
			for(Map.Entry<String, CosmeticService> service : services.entrySet()) {
				for(Map.Entry<String, Double> treatment : service.getValue().getTreatments().entrySet()) {
					out.append(service.getKey() + "," + treatment.getKey() + "," +  treatment.getValue());
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
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(SERVICES_FILE), "UTF-8"));
			String input;
			CosmeticService service = null;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				if(service == null) {
					service = new CosmeticService(data[0]);
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
				else if(service.getName().equals(data[0])) {
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
				else {
					services.put(service.getName(), service);
					service = new CosmeticService(data[0]);
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
			}
			services.put(service.getName(), service);
			in.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
				else if(service.getName().equals(data[0])) {
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
				else {
					services.put(service.getName(), service);
					service = new CosmeticService(data[0]);
					service.addTreatment(data[1], Double.valueOf(data[2]));
				}
			}
			services.put(service.getName(), service);
			in.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: Save staff differently!
	
	public static void saveUsers() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(USERS_FILE), "UTF-8")));
			out.print(USER_HEADER);
			for(String username : users.keySet()) {
				out.print(users.get(username).toString());
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
	
	public static void saveUsers(File file) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8")));
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
	
	public static void saveUsers(boolean append) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(USERS_FILE, append), "UTF-8")));
			if(!append) out.append(USER_HEADER);
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
	
	public static void loadUsers(File file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				switch(data[0]) {
					case "Receptionist":
						Receptionist receptionist = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildReceptionist();
						DataBase.users.put(receptionist.getUsername(), receptionist);
						break;
					case "Manager":
						Manager manager = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildManager();
						DataBase.users.put(manager.getUsername(), manager);
						break;
					case "Client":
						Client client = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildClient();
						DataBase.users.put(client.getUsername(), client);
						break;
					case "Beautician":
						Beautician cosmetologist = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildBeautician();
						if(data.length == 9) {
							for(String t : data[8].split(";")) {
//								cosmetologist.addTreatment(null, t);;
							}
						}
						DataBase.users.put(cosmetologist.getUsername(), cosmetologist);
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
	
	public static void loadUsers() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(USERS_FILE), "UTF-8"));
			String input;
			in.readLine();
			while((input = in.readLine()) != null) {
				String[] data = input.split(",");
				switch(data[0]) {
					case "Receptionist":
						Receptionist receptionist = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildReceptionist();
						DataBase.users.put(receptionist.getUsername(), receptionist);
						break;
					case "Manager":
						Manager manager = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildManager();
						DataBase.users.put(manager.getUsername(), manager);
						break;
					case "Client":
						Client client = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildClient();
						DataBase.users.put(client.getUsername(), client);
						break;
					case "Beautician":
						Beautician cosmetologist = new UserBuilder(data[1], data[2])
						.setName(data[3])
						.setSurname(data[4])
						.setGender(data[5])
						.setPhoneNumber(data[6])
						.setAddress(data[7])
						.buildBeautician();
						if(data.length == 9) {
							for(String t : data[8].split(";")) {
//								cosmetologist.treatments.add(t);
							}
						}
						DataBase.users.put(cosmetologist.getUsername(), cosmetologist);
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
