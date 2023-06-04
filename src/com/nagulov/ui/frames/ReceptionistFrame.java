package com.nagulov.ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.Salon;
import com.nagulov.ui.EditUserDialog;
import com.nagulov.ui.LoginDialog;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;

import net.miginfocom.swing.MigLayout;

public class ReceptionistFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton treatmentButton = new JButton("Schedule treatment");
	private JButton editInfoButton = new JButton("Edit user info");
	private JButton logoutButton = new JButton("Logout");
	
	private JPanel userInfo = new JPanel();
	
	private void initReceptionistFrame() {
	
		userInfo.setLayout(new MigLayout("wrap", "[]", "[][][][][][][]"));
		userInfo.add(new JLabel("-- User info --"));
		userInfo.add(new JLabel("Name: " + DataBase.loggedUser.getName()));
		userInfo.add(new JLabel("Surname: " + DataBase.loggedUser.getSurname()));
		userInfo.add(new JLabel("Gender: " + DataBase.loggedUser.getGender()));
		userInfo.add(new JLabel("Phone number: " + DataBase.loggedUser.getPhoneNumber()));
		userInfo.add(new JLabel("Address: " + DataBase.loggedUser.getAddress()));
		
		this.getContentPane().setLayout(new MigLayout("wrap 2, fillx", "[][]", "[]20[]20[]"));
		
		this.getContentPane().add(new JLabel("Welcome, " + DataBase.loggedUser.getUsername() + "!"));	
		this.getContentPane().add(logoutButton, "right");
		this.getContentPane().add(userInfo, "span 2");
		this.getContentPane().add(editInfoButton);
		this.getContentPane().add(treatmentButton);
		
		treatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.TREATMENT);
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
				DataBase.saveServices();
				DataBase.saveTreatments();
				DataBase.saveUsers();
				setVisible(false);
				dispose();
				new LoginDialog();
			}
		});
		
	}

	public ReceptionistFrame() {
		this.setTitle(Salon.getInstance().getSalonName());
//		this.setSize(500,500);
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
		initReceptionistFrame();
		this.pack();
		this.setVisible(true);
	}


}
