package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;

import net.miginfocom.swing.MigLayout;

public class ManagerFrame extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private void initChangePasswordDialog(JDialog d) {
//		JPasswordField oldPasswordField = new JPasswordField(20);
//		JPasswordField newPasswordField = new JPasswordField(20);
//	
//		JButton confirmButton = new JButton("Comfirm");
//		JButton cancelButton = new JButton("Cancel");
//		
//		d.setLayout(new MigLayout("wrap 2", "[][]", "[][]20[]"));
//		d.add(new JLabel("Old Password"));
//		d.add(oldPasswordField);
//		d.add(new JLabel("New Password"));
//		d.add(newPasswordField);
//		d.add(cancelButton);
//		d.add(confirmButton);
//		
//		confirmButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String oldPassword = new String(oldPasswordField.getPassword());
//				String newPassword = new String(newPasswordField.getPassword());
//				
//				ErrorMessage message = Validator.changePassword(oldPassword, newPassword);
//				if(message != ErrorMessage.SUCCESS) {
//					JOptionPane.showMessageDialog(null, message.getError(), "Error", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				d.setVisible(false);
//				d.dispose();
//			}
//			
//		});
//		
//		cancelButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				d.setVisible(false);
//				d.dispose();
//			}
//		});
//	}
	
//	private void changePasswordDialog(){
//		JDialog d = new JDialog();
//		d.setTitle("Cosmetic Salon Nagulov");
//		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		d.setSize(300,300);
//		initChangePasswordDialog(d);
//		d.setVisible(true);
//	}
//	
	
	private void initManagerFrame() {
		JButton editInfoButton = new JButton("Edit info");
		JButton logoutButton = new JButton("Logout");
		JButton changePasswordButton = new JButton("Change password");
		
		JMenuBar menu = new JMenuBar();
		
		JMenu dataMenu = new JMenu("Data");
		JMenuItem userItem = new JMenuItem("Users");
		JMenuItem serviceItem = new JMenuItem("Services");
		JMenuItem treatmentItem = new JMenuItem("Treatments");
		
		dataMenu.add(userItem);
		dataMenu.add(serviceItem);
		dataMenu.add(treatmentItem);
		
		JMenu analyticsMenu = new JMenu("Analytics");

		menu.add(analyticsMenu);
		menu.add(dataMenu);
		
		JPanel userInfo = new JPanel();
		userInfo.setLayout(new MigLayout("wrap", "[]", "[][][][][][]20[][]"));
		userInfo.add(new JLabel("User info"));
		userInfo.add(new JLabel("Name: " + DataBase.loggedUser.getName()));
		userInfo.add(new JLabel("Surname: " + DataBase.loggedUser.getSurname()));
		userInfo.add(new JLabel("Gender: " + DataBase.loggedUser.getGender()));
		userInfo.add(new JLabel("Phone number: " + DataBase.loggedUser.getPhoneNumber()));
		userInfo.add(new JLabel("Address: " + DataBase.loggedUser.getAddress()));
		userInfo.add(editInfoButton);
		userInfo.add(changePasswordButton);
		
//		JPanel beauticianChart = new XChartPanel<PieChart>(ReportChart.initBeauticianChart());
//		JPanel treatmentChart = new XChartPanel<PieChart>(ReportChart.initTreatmentChart());
//		JPanel serviceIncomeChart = new XChartPanel<XYChart>(ReportChart.initServiceIncomeChart());
		
		this.setJMenuBar(menu);
		this.getContentPane().setLayout(new MigLayout("wrap 2, fillx", "[][]", "[]20[][][]"));
		
		this.getContentPane().add(new JLabel("Welcome, " + DataBase.loggedUser.getUsername() + "!"));	
		this.getContentPane().add(logoutButton, "right");
		this.getContentPane().add(userInfo, "wrap");
//		this.getContentPane().add(beauticianChart);
//		this.getContentPane().add(treatmentChart);
//		this.getContentPane().add(serviceIncomeChart, "span 2");
		
		editInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditUserDialog(DataBase.loggedUser);
			}
			
		});
		
		JFrame frame = this;
		
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				new LoginDialog();
			}
		});
		
		
		userItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.USER);
			}
			
		});
		
		serviceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.SERVICE);
			}
			
		});
	
	}
	
	void managerFrame() {
		this.setTitle("Comestic Salon Nagulov");
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DataBase.saveServices();
				DataBase.saveTreatments();
				DataBase.saveUsers();
			}	
		});
		initManagerFrame();
		this.setVisible(true);
	}
	
	public ManagerFrame(){
		managerFrame();
	}
}
