package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nagulov.controllers.ServiceController;
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
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class EditUserDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserController managerController = UserController.getInstance();
	private User user;
	
	private Class<?> getRole(String string){
		switch(string) {
			case DataBase.CLIENT:
				return Client.class;
			case DataBase.MANAGER:
				return Manager.class;
			case DataBase.BEAUTICIAN:
				return Beautician.class;
			case DataBase.RECEPTIONIST:
				return Receptionist.class;
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
		roleComboBox.setSelectedItem(UserController.getInstance().getUser(user.getUsername()).getClass().getSimpleName());
		
		JPanel treatmentsPanel = new JPanel();
		treatmentsPanel.setLayout(new MigLayout("wrap 2", "[][]"));

		for(Map.Entry<String, CosmeticService> service : ServiceController.getInstance().getServices().entrySet()) {
			JCheckBox btn = new JCheckBox(service.getValue().getName());
			treatmentsPanel.add(btn);
			checkboxes.add(btn);
			if(user instanceof Beautician && ((Beautician)UserController.getInstance().getUser(user.getUsername())).containsService(service.getValue())) {
				btn.setSelected(true);
			}
		}
		
		nameField.setText(user.getName());
		surnameField.setText(user.getSurname());
		phoneNumberField.setText(user.getPhoneNumber());
		addressField.setText(user.getAddress());
		passwordField.setText(user.getPassword());
		usernameField.setText(user.getUsername());
		
		if(roleComboBox.getSelectedItem() != DataBase.RECEPTIONIST && roleComboBox.getSelectedItem() != DataBase.BEAUTICIAN) {
			internshipField.setEnabled(false);
			qualificationBox.setEnabled(false);
			bonusesField.setEnabled(false);
			incomeField.setEnabled(false);
			salaryField.setEnabled(false);
			
		}
		if(roleComboBox.getSelectedItem() != DataBase.BEAUTICIAN) {
			for(JCheckBox cb : checkboxes) {
				cb.setEnabled(false);
			}
		}
		
		if(user instanceof Staff) {
			try {
				Staff s = (Staff)UserController.getInstance().getUser(user.getUsername());
				qualificationBox.setSelectedItem(Integer.valueOf(s.getQualification()));
				bonusesField.setText(Double.valueOf(s.getBonuses()).toString());
				salaryField.setText(Double.valueOf(s.getSalary()).toString());
				internshipField.setText(Integer.valueOf(s.getInternship()).toString());
				if(s instanceof Beautician) {
					incomeField.setText(Double.valueOf(((Beautician)s).calculateIncome()).toString());
				}else {
					incomeField.setText(Double.valueOf(s.getIncome()).toString());
				}
			}catch(ClassCastException e) {
				
			}
		}		
		
		if(DataBase.loggedUser instanceof Manager) {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][][][][][][][][][][]20[]"));
		}else {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][]20[]"));
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
		if(DataBase.loggedUser instanceof Manager) {
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
			this.getContentPane().add(new JLabel("Treatments:"), "span 2");
			this.getContentPane().add(treatmentsPanel, "span 2");
		}
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(confirmButton);

		roleComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String role = roleComboBox.getSelectedItem().toString();
				if(role == DataBase.BEAUTICIAN) {
					for(JCheckBox cb : checkboxes) {
						if(cb.isEnabled()) {
							break;
						}
						cb.setEnabled(true);
					}
				}else {
					for(JCheckBox cb : checkboxes) {
						if(!cb.isEnabled()) {
							break;
						}
						cb.setEnabled(false);
					}
				}
				if(role == DataBase.BEAUTICIAN || role == DataBase.RECEPTIONIST) {
					if(internshipField.isEnabled()) {
						return;
					}
					internshipField.setEnabled(true);
					qualificationBox.setEnabled(true);
					bonusesField.setEnabled(true);
					incomeField.setEnabled(true);
					salaryField.setEnabled(true);
				}else {
					if(!internshipField.isEnabled()) {
						return;
					}
					internshipField.setEnabled(false);
					qualificationBox.setEnabled(false);
					bonusesField.setEnabled(false);
					incomeField.setEnabled(false);
					salaryField.setEnabled(false);

				}
			}
		});
		
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
				
				if((DataBase.loggedUser instanceof Manager) && (role.equals(DataBase.BEAUTICIAN) || role.equals(DataBase.RECEPTIONIST))) {
					int internship = 0;
					int qualification = 0;
					double bonuses = 0.0;
					double income = 0.0;
					double salary = 0.0;
					try {
						internship = Integer.parseInt(internshipField.getText());
						qualification = Integer.parseInt(qualificationBox.getSelectedItem().toString());
						bonuses = Double.parseDouble(bonusesField.getText());
						income = Double.parseDouble(incomeField.getText());
						salary = Double.parseDouble(salaryField.getText());
					}catch(Exception inputError) {
						JOptionPane.showMessageDialog(null, ErrorMessage.INVALID_INPUT, "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(UserController.getInstance().getUser(username) instanceof Staff)	
						managerController.updateStaff((Staff)UserController.getInstance().getUser(username), name, surname, gender, phoneNumber, address, username, password, bonuses, income, internship, qualification, salary, getRole(role));
					else {
						UserModel.removeUser(UserController.getInstance().getUser(username));
						User u = managerController.createStaff(name, surname, gender, phoneNumber, address, username, password, bonuses, income, internship, qualification, salary, getRole(role));
						UserModel.addUser(u);
					}
					
					if (role.equals(DataBase.BEAUTICIAN)) {
						Beautician b = (Beautician)UserController.getInstance().getUser(username);
						b.getServices().clear();
						for(JCheckBox cb : checkboxes) {
							if(cb.isSelected()) {
								CosmeticService service = ServiceController.getInstance().getServices().get(cb.getText());
								b.addService(service);
							}
						}
					}
					
				}else {
					UserController.getInstance().updateUser(user, name, surname, gender, phoneNumber, address, username, password, getRole(role));
				}
				try {
					TableDialog.refreshUser();
				}catch(Exception ex) {
					
				}
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	public EditUserDialog(User user) {
		this.user = user;
		setTitle(Salon.getInstance().getSalonName());
		this.setIconImage(new ImageIcon("img" + DataBase.SEPARATOR + "logo.jpg").getImage());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initEditUserDialog();
		pack();
		setVisible(true);
	}
}
