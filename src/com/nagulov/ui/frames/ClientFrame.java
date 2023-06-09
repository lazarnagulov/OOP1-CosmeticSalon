package com.nagulov.ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.Salon;
import com.nagulov.ui.EditUserDialog;
import com.nagulov.ui.LoginDialog;
import com.nagulov.ui.ScheduleTreatmentDialog;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;
import com.nagulov.users.Client;

import net.miginfocom.swing.MigLayout;

public class ClientFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton editInfoButton = new JButton("Edit user info");
	private JButton treatmentButton = new JButton("Schedule treatment");
	private JButton cancelTreatmentButton = new JButton("Treatments");
	private JButton logoutButton = new JButton("Logout");
	private JButton loyalityStatusButton = new JButton("Status");
	
	
	private JPanel userInfo = new JPanel();
	
	private void initClientFrame() {
	
		Client c = (Client) DataBase.loggedUser;
		
		userInfo.setLayout(new MigLayout("wrap", "[]", "[][][][][]"));
		userInfo.add(new JLabel("-- User info --"));
		userInfo.add(new JLabel("Name: " + c.getName()));
		userInfo.add(new JLabel("Surname: " + c.getSurname()));
		userInfo.add(new JLabel("Gender: " + c.getGender()));
		userInfo.add(new JLabel("Phone number: " + c.getPhoneNumber()));
		userInfo.add(new JLabel("Address: " + c.getAddress()));
		
		this.getContentPane().setLayout(new MigLayout("wrap 2, fillx", "[][]", "[]20[][]20[][]"));

		this.getContentPane().add(new JLabel("Welcome, " + DataBase.loggedUser.getUsername() + "!"));	
		this.getContentPane().add(logoutButton, "right");
		this.getContentPane().add(userInfo, "span 2");
		this.getContentPane().add(new JLabel("Loyality card:"), "center");
		this.getContentPane().add(loyalityStatusButton, "center");
		this.getContentPane().add(editInfoButton, "center, span 2");
		this.getContentPane().add(treatmentButton);
		this.getContentPane().add(cancelTreatmentButton);
		
		loyalityStatusButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Spent money: " + ((Client)DataBase.loggedUser).getSpent() + " / " + UserController.getInstance().loyaltyCardNeeded, "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		cancelTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.TREATMENT, (Client)DataBase.loggedUser);
			}
			
		});
		
		treatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ScheduleTreatmentDialog();
			}
		});
		
		editInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditUserDialog(DataBase.loggedUser);
			}
		});
		
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataBase.saveSalon();
				DataBase.saveServices();
				DataBase.saveTreatments();
				DataBase.saveUsers();
				setVisible(false);
				dispose();
				new LoginDialog();
			}
		});
		
	}

	public ClientFrame() {
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DataBase.saveServices();
				DataBase.saveTreatments();
				DataBase.saveUsers();
			}	
		});
		initClientFrame();
		this.pack();
		this.setVisible(true);
	}
	
}
