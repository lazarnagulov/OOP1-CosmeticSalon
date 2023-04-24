package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.ui.models.UserModel;

import net.miginfocom.swing.MigLayout;

public class RegisterDialog extends JDialog{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = ManagerController.getInstance();
	
	private void initRegisterDialog(boolean isManager) {
		JTextField nameField = new JTextField(20);
		JTextField surnameField = new JTextField(20);
		JTextField phoneNumberField = new JTextField(20);
		JTextField addressField = new JTextField(20);
		JTextField usernameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		
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
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][][]20[][]"));
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
			
			comboBox.addItem("Client");
			comboBox.addItem("Manager");
			comboBox.addItem("Receptionist");
			comboBox.addItem("Beautician");
			
			this.getContentPane().add(comboBox);
		}
		this.getContentPane().add(cancelButton);
		this.getContentPane().add(registerButton, "split 2");
		
		JDialog dialog = this;
		
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
				
				ErrorMessage message = null;
				
				if(isManager) {
					String role = comboBox.getSelectedItem().toString();
					message = Validator.registerUser(role, name, surname, gender, phoneNumber, address, username, password);
				}
				else {
					message = Validator.registerUser(name, surname, gender, phoneNumber, address, username, password);
				}
				
				if(message != ErrorMessage.SUCCESS) {
					JOptionPane.showMessageDialog(null, message.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!UserModel.isEmpty()) {
					UserModel.addUser(managerController.getUser(username));
				}
				
				if(isManager) {
					TableDialog.refreshUser();
				}
				
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
		
	}

	public RegisterDialog(boolean isManager) {
		this.setTitle("Cosmetic Salon Nagulov");
		this.setSize(724,540);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initRegisterDialog(isManager);
		this.pack();
		this.setVisible(true);
	}
}


