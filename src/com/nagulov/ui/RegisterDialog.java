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

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Salon;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
import com.nagulov.users.Staff;

import net.miginfocom.swing.MigLayout;

public class RegisterDialog extends JDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserController managerController = UserController.getInstance();
	
	private static Class<?> getRole(String string){
		if(string == null) {
			return Client.class;
		}
		switch(string) {
			case DataBase.CLIENT:
				return Client.class;
			case DataBase.MANAGER:
				return Manager.class;
			case DataBase.BEAUTICIAN:
				return Beautician.class;
			case DataBase.RECEPTIONIST:
				return Receptionist.class;
			default:
				return Client.class; 
		}
	}
	
	private void initRegisterDialog(boolean isManager) {
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
		JTextField salaryField = new JTextField(20);
		
		JComboBox<String> roleComboBox = new JComboBox<String>();

		JButton cancelButton = new JButton("Cancel");
		JButton registerButton = new JButton("Register");
		
		JRadioButton male = new JRadioButton("Male");
		JRadioButton female = new JRadioButton("Female");
		ButtonGroup bg = new ButtonGroup();
		bg.add(male);
		bg.add(female);
		JPanel genderRadio = new JPanel();
		genderRadio.add(male);
		genderRadio.add(female);
		
		if(isManager) {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][][][][][][][][][]20[]"));
		}
		else this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][]20[]"));
		this.getRootPane().setDefaultButton(registerButton);
		
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
		if(isManager) {
			this.getContentPane().add(new JLabel("Role"));
			
			roleComboBox.addItem(DataBase.CLIENT);
			roleComboBox.addItem(DataBase.MANAGER);
			roleComboBox.addItem(DataBase.RECEPTIONIST);
			roleComboBox.addItem(DataBase.BEAUTICIAN);
			
			this.getContentPane().add(roleComboBox);
			
			this.getContentPane().add(new JLabel("Staff only"), "span 2");
			
			this.getContentPane().add(new JLabel("Internship"));
			this.getContentPane().add(internshipField);
			this.getContentPane().add(new JLabel("Qualification"));
			this.getContentPane().add(qualificationBox);
			this.getContentPane().add(new JLabel("Bonuses"));
			this.getContentPane().add(bonusesField);
			this.getContentPane().add(new JLabel("Salary"));
			this.getContentPane().add(salaryField);
			
			this.getContentPane().add(new JLabel("Treatments (Beautician only):"), "span 2");
			JPanel treatmentsPanel = new JPanel();
			treatmentsPanel.setLayout(new MigLayout("wrap 2", "[][]"));
			
			
			
			for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
				JCheckBox btn = new JCheckBox(service.getValue().getName());
				treatmentsPanel.add(btn);
				checkboxes.add(btn);
			}
			
			this.getContentPane().add(treatmentsPanel, "span 2");
			
		}
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(registerButton);
		
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String surname = surnameField.getText();
				String gender = male.isSelected() ? "Male" : "Female";
				String phoneNumber = phoneNumberField.getText();
				String address = addressField.getText();
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				String role = null;
				
				Staff staff = null;
				ErrorMessage message = null;
				
				if(isManager) {
					role = roleComboBox.getSelectedItem().toString();
					if(role.equals(DataBase.BEAUTICIAN) || role.equals(DataBase.RECEPTIONIST)) {
						int internship = Integer.parseInt(internshipField.getText());
						int qualification = Integer.parseInt(qualificationBox.getSelectedItem().toString());
						double bonuses = Double.parseDouble(bonusesField.getText());
						double salary = Double.parseDouble(salaryField.getText());
						
						staff = managerController.createStaff(name, surname, gender, phoneNumber, address, username, password, bonuses, bonuses, internship, qualification, salary, getRole(role));

						if (role == DataBase.BEAUTICIAN) {
							for(JCheckBox cb : checkboxes) {
								if(cb.isSelected()) {
									CosmeticService service = DataBase.services.get(cb.getText());
									Beautician b = (Beautician)UserController.getInstance().getUser(staff.getUsername());
									b.addTreatment(service);
								}
							}
						}
					}
				}
				
				if(staff == null) {
					managerController.createUser(name, surname, gender, phoneNumber, address, username, password, getRole(role));
				}
//				if(!isManager) {
//					message = Validator.registerUser(name, surname, gender, phoneNumber, address, username, password);
//				}
//				if(message != ErrorMessage.SUCCESS) {
//					JOptionPane.showMessageDialog(null, message.getError(), "Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
				
				if(isManager) {
					UserModel.addUser(UserController.getInstance().getUser(username));
					TableDialog.refreshUser();
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

	public RegisterDialog(boolean isManager) {
		this.setTitle(Salon.getInstance().getSalonName());
		this.setSize(724,540);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initRegisterDialog(isManager);
		this.pack();
		this.setVisible(true);
	}
}


