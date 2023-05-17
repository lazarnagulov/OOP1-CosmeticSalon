package com.nagulov.ui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.nagulov.data.DataBase;
import com.nagulov.ui.EditSalonDialog;
import com.nagulov.ui.EditUserDialog;
import com.nagulov.ui.LoginDialog;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;

import net.miginfocom.swing.MigLayout;

public class ManagerFrame extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton editInfoButton = new JButton("Edit user info");
	private JButton logoutButton = new JButton("Logout");
	private JButton editSalonButton = new JButton("Edit salon info");
	
	private JMenuBar menu = new JMenuBar();
	
	private JMenu dataMenu = new JMenu("Data");
	private JMenuItem userItem = new JMenuItem("Users");
	private JMenuItem serviceItem = new JMenuItem("Services");
	private JMenuItem treatmentItem = new JMenuItem("Treatments");

	private JMenu analyticsMenu = new JMenu("Analytics");
	private JPanel userInfo = new JPanel();

	private void initManagerFrame() {
		
		dataMenu.add(userItem);
		dataMenu.add(serviceItem);
		dataMenu.add(treatmentItem);
		
		menu.add(analyticsMenu);
		menu.add(dataMenu);
		
		userInfo.setLayout(new MigLayout("wrap", "[]", "[][][][][][]"));
		userInfo.add(new JLabel("-- User info --"));
		userInfo.add(new JLabel("Name: " + DataBase.loggedUser.getName()));
		userInfo.add(new JLabel("Surname: " + DataBase.loggedUser.getSurname()));
		userInfo.add(new JLabel("Gender: " + DataBase.loggedUser.getGender()));
		userInfo.add(new JLabel("Phone number: " + DataBase.loggedUser.getPhoneNumber()));
		userInfo.add(new JLabel("Address: " + DataBase.loggedUser.getAddress()));
		
//		JPanel beauticianChart = new XChartPanel<PieChart>(ReportChart.initBeauticianChart());
//		JPanel treatmentChart = new XChartPanel<PieChart>(ReportChart.initTreatmentChart());
//		JPanel serviceIncomeChart = new XChartPanel<XYChart>(ReportChart.initServiceIncomeChart());
		
		this.setJMenuBar(menu);
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[grow][grow]", "[fill]20[grow, center]20[center]"));
		
		this.getContentPane().add(new JLabel("Welcome, " + DataBase.loggedUser.getUsername() + "!"));	
		this.getContentPane().add(logoutButton, "right");
		this.getContentPane().add(userInfo, "wrap, span 2, center");
		this.getContentPane().add(editInfoButton, "left");
		this.getContentPane().add(editSalonButton, "right");
//		this.getContentPane().add(beauticianChart);
//		this.getContentPane().add(treatmentChart);
//		this.getContentPane().add(serviceIncomeChart, "span 2");
		
		editInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditUserDialog(DataBase.loggedUser);
			}
		});
		
		editSalonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditSalonDialog();
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
		
		treatmentItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.TREATMENT);
			}
		});
	
	}
	
	void managerFrame() {
		this.setTitle(DataBase.salonName);
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
		initManagerFrame();
		this.pack();
		this.setVisible(true);
	}
	
	public ManagerFrame(){
		managerFrame();
	}
}
