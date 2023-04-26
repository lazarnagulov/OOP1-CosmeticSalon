package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Manager;
import com.nagulov.users.Receptionist;
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
			case "Client" -> {return Client.class;}
			case "Manager" -> {return Manager.class;}
			case "Beautician" -> {return Beautician.class;}
			case "Receptionist" -> {return Receptionist.class;}
		}
		return null;
	}
	
	private void initEditUserDialog() {
		JTextField nameField = new JTextField(20);
		JTextField surnameField = new JTextField(20);
		JTextField phoneNumberField = new JTextField(20);
		JTextField addressField = new JTextField(20);
		
		JButton confirmButton = new JButton("Confirm");
		JButton cancelButton = new JButton("Cancel");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		
		comboBox.addItem("Client");
		comboBox.addItem("Manager");
		comboBox.addItem("Receptionist");
		comboBox.addItem("Beautician");
		comboBox.setSelectedItem(DataBase.users.get(user.getUsername()).getClass().getSimpleName());
		
		nameField.setText(user.getName());
		surnameField.setText(user.getSurname());
		phoneNumberField.setText(user.getPhoneNumber());
		addressField.setText(user.getAddress());
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][]20[]"));
		this.add(new JLabel("Edit account"), "span 2");
		this.add(new JLabel("Name"));
		this.add(nameField);
		this.add(new JLabel("Surname"));
		this.add(surnameField);
		this.add(new JLabel("Phone number"));
		this.add(phoneNumberField);
		this.add(new JLabel("Address"));
		this.add(addressField);
		this.getContentPane().add(new JLabel("Role"));
		this.getContentPane().add(comboBox);
		this.add(cancelButton);
		this.add(confirmButton);
		
		EditUserDialog frame = this;
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String surname = surnameField.getText();
				String phoneNumber =  phoneNumberField.getText();
				String address =  addressField.getText();
				String role = comboBox.getSelectedItem().toString();
				
				UserModel.removeUser(user);
				managerController.updateUser(user, name, surname, user.getGender(), phoneNumber, address, user.getUsername(), user.getPassword(), getRole(role));
				TableDialog.refreshUser();
				setVisible(false);
				frame.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
	}

	public EditUserDialog(User user) {
		this.user = user;
		setTitle("Cosmetic Salon Nagulov");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initEditUserDialog();
		pack();
		setVisible(true);
	}
}
