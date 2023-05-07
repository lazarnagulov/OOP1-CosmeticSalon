package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class EditUserDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = ManagerController.getInstance();
	private User user;
	
	private Class<?> getRole(String string){
		switch(string) {
			case DataBase.CLIENT -> {return Client.class;}
			case DataBase.MANAGER -> {return Manager.class;}
			case DataBase.BEAUTICIAN -> {return Beautician.class;}
			case DataBase.RECEPTIONIST -> {return Receptionist.class;}
		}
		return null;
	}
	
	private void initEditUserDialog() {
		JTextField nameField = new JTextField(20);
		JTextField surnameField = new JTextField(20);
		JTextField phoneNumberField = new JTextField(20);
		JTextField addressField = new JTextField(20);
		JTextField usernameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		
		JTextField internshipField = new JTextField(20);
		
		List<JCheckBox> checkboxes = new ArrayList<JCheckBox>();
		
		JComboBox<Integer> qualificationBox = new JComboBox<Integer>();
		qualificationBox.addItem(4);
		qualificationBox.addItem(5);
		qualificationBox.addItem(6);
		qualificationBox.addItem(7);
		qualificationBox.addItem(8);
		
		JTextField bonusesField = new JTextField(20);
		JTextField incomeField = new JTextField(20);
		JTextField salaryField = new JTextField(20);
		
		JComboBox<String> roleComboBox = new JComboBox<String>();

		JButton cancelButton = new JButton("Cancel");
		JButton confirmButton = new JButton("Confirm");
		
		JRadioButton male = new JRadioButton("Male");
		if(user.getGender().equals("Male")) {
			male.setSelected(true);
		}
		JRadioButton female = new JRadioButton("Female");
		if(user.getGender().equals("Female")) {
			female.setSelected(true);
		}
		ButtonGroup bg = new ButtonGroup();
		bg.add(male);
		bg.add(female);
		JPanel genderRadio = new JPanel();
		genderRadio.add(male);
		genderRadio.add(female);
		
		roleComboBox.addItem(DataBase.CLIENT);
		roleComboBox.addItem(DataBase.MANAGER);
		roleComboBox.addItem(DataBase.BEAUTICIAN);
		roleComboBox.addItem(DataBase.RECEPTIONIST);
		roleComboBox.setSelectedItem(DataBase.users.get(user.getUsername()).getClass().getSimpleName());
		
		JPanel treatmentsPanel = new JPanel();
		treatmentsPanel.setLayout(new MigLayout("wrap 2", "[][]"));

		for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
			JCheckBox btn = new JCheckBox(service.getValue().getName());
			treatmentsPanel.add(btn);
			checkboxes.add(btn);
			if(user instanceof Beautician && ((Beautician)DataBase.users.get(user.getUsername())).containsTreatment(service.getValue())) {
				btn.setSelected(true);
			}
		}
		
		nameField.setText(user.getName());
		surnameField.setText(user.getSurname());
		phoneNumberField.setText(user.getPhoneNumber());
		addressField.setText(user.getAddress());
		passwordField.setText(user.getPassword());
		usernameField.setText(user.getUsername());

		if(user instanceof Manager) {
			try {
				Staff s = (Staff)DataBase.users.get(user.getUsername());
				qualificationBox.setSelectedItem(Integer.valueOf(s.getQualification()));
				bonusesField.setText(Double.valueOf(s.getBonuses()).toString());
				salaryField.setText(Double.valueOf(s.getSalary()).toString());
				internshipField.setText(Integer.valueOf(s.getInternship()).toString());
				incomeField.setText(Double.valueOf(s.getIncome()).toString());
			}catch(ClassCastException e) {
				
			}
		}		
		
		if(user instanceof Manager) {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][][][][][][][][][][]20[]"));
		}else {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][][][]20[]"));
		}
		this.getContentPane().add(new JLabel("Register form"), "span 2");
		this.getContentPane().add(new JLabel("Name"));
		this.getContentPane().add(nameField);
		this.getContentPane().add(new JLabel("Surname"));
		this.getContentPane().add(surnameField);
		this.getContentPane().add(new JLabel("Gender"));
		this.getContentPane().add(genderRadio);
		this.getContentPane().add(new JLabel("Phone number"));
		this.getContentPane().add(phoneNumberField);
		this.getContentPane().add(new JLabel("Address"));
		this.getContentPane().add(addressField);
		this.getContentPane().add(new JLabel("Username"));
		this.getContentPane().add(usernameField);
		this.getContentPane().add(new JLabel("Password"));
		this.getContentPane().add(passwordField);
		if(user instanceof Manager) {
			this.getContentPane().add(new JLabel("Role"));
			this.getContentPane().add(roleComboBox);
			this.getContentPane().add(new JLabel("Staff only"), "span 2");
			this.getContentPane().add(new JLabel("Internship"));
			this.getContentPane().add(internshipField);
			this.getContentPane().add(new JLabel("Qualification"));
			this.getContentPane().add(qualificationBox);
			this.getContentPane().add(new JLabel("Bonuses"));
			this.getContentPane().add(bonusesField);
			this.getContentPane().add(new JLabel("Income"));
			this.getContentPane().add(incomeField);
			this.getContentPane().add(new JLabel("Salary"));
			this.getContentPane().add(salaryField);
			this.getContentPane().add(new JLabel("Treatments (Beautician only):"), "span 2");
			this.getContentPane().add(treatmentsPanel, "span 2");
		}
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(confirmButton);
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String surname = surnameField.getText();
				String phoneNumber =  phoneNumberField.getText();
				String gender = male.isSelected() ? "Male" : "Female";
				String username = usernameField.getText();
				String address =  addressField.getText();
				String password = new String(passwordField.getPassword());
				String role = roleComboBox.getSelectedItem().toString();
				
				if((user instanceof Manager) && (role.equals(DataBase.BEAUTICIAN) || role.equals(DataBase.RECEPTIONIST))) {
					int internship = Integer.parseInt(internshipField.getText());
					int qualification = Integer.parseInt(qualificationBox.getSelectedItem().toString());
					double bonuses = Double.parseDouble(bonusesField.getText());
					double income = Double.parseDouble(incomeField.getText());
					double salary = Double.parseDouble(salaryField.getText());
					
					if(DataBase.users.get(username) instanceof Staff)	
						managerController.updateStaff((Staff)DataBase.users.get(username), name, surname, gender, phoneNumber, address, username, password, bonuses, income, internship, qualification, salary, getRole(role));
					else {
						UserModel.removeUser(DataBase.users.get(username));
						User u = managerController.createStaff(name, surname, gender, phoneNumber, address, username, password, bonuses, income, internship, qualification, salary, getRole(role));
						UserModel.addUser(u);
					}
					
					if (role.equals(DataBase.BEAUTICIAN)) {
						Beautician b = (Beautician)DataBase.users.get(username);
						b.getTreatments().clear();
						for(JCheckBox cb : checkboxes) {
							if(cb.isSelected()) {
								CosmeticService service = DataBase.services.get(cb.getText());
								b.addTreatment(service);
							}
						}
					}
					
				}else {
					managerController.updateUser(user, name, surname, gender, phoneNumber, address, username, password, getRole(role));
				}
				try {
					TableDialog.refreshUser();
				}catch(NullPointerException ex) {
					
				}
				setVisible(false);
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	public EditUserDialog(User user) {
		this.user = user;
		setTitle(DataBase.salonName);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initEditUserDialog();
		pack();
		setVisible(true);
	}
}
