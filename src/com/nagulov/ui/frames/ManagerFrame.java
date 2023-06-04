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
import com.nagulov.reports.ReportsDateDialog;
import com.nagulov.treatments.Salon;
import com.nagulov.ui.EditSalonDialog;
import com.nagulov.ui.EditUserDialog;
import com.nagulov.ui.LoginDialog;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;
import com.nagulov.ui.charts.BeauticianChartDialog;
import com.nagulov.ui.charts.TreatmentChartDialog;

import net.miginfocom.swing.MigLayout;

public class ManagerFrame extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton editInfoButton = new JButton("Edit user info");
	private JButton logoutButton = new JButton("Logout");
	private JButton editSalonButton = new JButton("Salon options");
	
	private JMenuBar menu = new JMenuBar();
	
	private JMenu dataMenu = new JMenu("Data");
	private JMenuItem userItem = new JMenuItem("Users");
	private JMenuItem serviceItem = new JMenuItem("Services");
	private JMenuItem treatmentItem = new JMenuItem("Treatments");

	private JMenu analyticsMenu = new JMenu("Analytics");
	private JMenuItem analyticsTreatment = new JMenuItem("Treatments in past 30 days");
	private JMenuItem analyticsBeautician = new JMenuItem("Beauticians in past 30 days");
	
	private JMenu reportsMenu = new JMenu("Reports");
	private JMenuItem loyalityCardReport = new JMenuItem("Loyality Card");
	private JMenuItem treatmentsReport = new JMenuItem("Treaments status");
	private JMenuItem beauticianIncomeReport = new JMenuItem("Beautician income");
	private JMenuItem cosmeticTreatmentReport = new JMenuItem("Cosmetic Treatment");
	
	private JPanel userInfo = new JPanel();

	private void initManagerFrame() {
		
		dataMenu.add(userItem);
		dataMenu.add(serviceItem);
		dataMenu.add(treatmentItem);
		
		analyticsMenu.add(analyticsTreatment);
		analyticsMenu.add(analyticsBeautician);
		
		reportsMenu.add(loyalityCardReport);
		reportsMenu.add(treatmentsReport);
		reportsMenu.add(beauticianIncomeReport);
		reportsMenu.add(cosmeticTreatmentReport);
		
		menu.add(analyticsMenu);
		menu.add(dataMenu);
		menu.add(reportsMenu);
		
		userInfo.setLayout(new MigLayout("wrap", "[]", "[][][][][][]"));
		userInfo.add(new JLabel("-- User info --"));
		userInfo.add(new JLabel("Name: " + DataBase.loggedUser.getName()));
		userInfo.add(new JLabel("Surname: " + DataBase.loggedUser.getSurname()));
		userInfo.add(new JLabel("Gender: " + DataBase.loggedUser.getGender()));
		userInfo.add(new JLabel("Phone number: " + DataBase.loggedUser.getPhoneNumber()));
		userInfo.add(new JLabel("Address: " + DataBase.loggedUser.getAddress()));
		
//		JPanel beauticianChart = new XChartPanel<PieChart>(ReportChart.initBeauticianChart());
		
//		JPanel serviceIncomeChart = new XChartPanel<XYChart>(ReportChart.initServiceIncomeChart());
		
		this.setJMenuBar(menu);
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][grow]", "[fill]20[grow, center]20[center]"));
		
		this.getContentPane().add(new JLabel("Welcome, " + DataBase.loggedUser.getUsername() + "!"));	
		this.getContentPane().add(logoutButton, "right");
		this.getContentPane().add(userInfo, "wrap, span 2");
		this.getContentPane().add(editInfoButton, "left");
		this.getContentPane().add(editSalonButton, "right");
//		this.getContentPane().add(beauticianChart);
//		this.getContentPane().add(treatmentChart);
//		this.getContentPane().add(serviceIncomeChart, "span 2");
	
		treatmentsReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReportsDateDialog(Table.TREATMENTS_STATUS);
			}
		});
		
		cosmeticTreatmentReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReportsDateDialog(Table.COSMETIC_TREATMENT_STATUS);
			}
		});
		
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
				DataBase.saveSalon();
				DataBase.saveServices();
				DataBase.saveTreatments();
				DataBase.saveUsers();
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
		
		analyticsTreatment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TreatmentChartDialog();
			}
		});
		
		analyticsBeautician.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new BeauticianChartDialog();
			}
		});
		
		beauticianIncomeReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReportsDateDialog(Table.BEAUTICIAN_INCOME);
			}
			
		});
		
		loyalityCardReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TableDialog(Table.LOYALITY_CARD);
			}
		});
	
	}
	
	void managerFrame() {
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DataBase.saveSalon();
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
