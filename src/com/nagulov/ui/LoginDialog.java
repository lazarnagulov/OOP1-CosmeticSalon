package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class LoginDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = ManagerController.getInstance();

	void initLoginDialog(JDialog d) {
		
		JTextField usernameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");
		JButton registerButton = new JButton("Create new account");

		
		d.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][]20[]"));
		d.getRootPane().setDefaultButton(loginButton);
		
		d.getContentPane().add(new JLabel("Login form"), "span 2");
		d.getContentPane().add(new JLabel("Username"));
		d.getContentPane().add(usernameField);
		d.getContentPane().add(new JLabel("Password"));
		d.getContentPane().add(passwordField);
		d.getContentPane().add(loginButton);
		d.getContentPane().add(cancelButton, "split 2");
		d.getContentPane().add(registerButton);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				passwordField.setText("");
				ErrorMessage message = Validator.loginUser(username,password);
				if(message != ErrorMessage.SUCCESS) {
					JOptionPane.showMessageDialog(null, message.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				User u = managerController.getUser(username);
				
				switch(u.getClass().getSimpleName()) {
					case "Manager":
						new ManagerFrame();
						break;
				}
				d.setVisible(false);
				d.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterDialog(false);
			}
		});

		
	}

	private void loginDialog() {
		this.setTitle("Cosmetic Salon Nagulov");
		this.setSize(724,540);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		initLoginDialog(this);
		this.pack();
		this.setVisible(true);
	}
	
	
	
	public LoginDialog() {
		loginDialog();
	}

}
