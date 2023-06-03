package com.nagulov.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.BeauticianIncomeModel;
import com.nagulov.ui.models.LoyalityCardModel;
import com.nagulov.ui.models.ServiceModel;
import com.nagulov.ui.models.TimeTableModel;
import com.nagulov.ui.models.TreatmentModel;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Receptionist;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class TableDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UserController managerController = UserController.getInstance();
	
	protected JToolBar toolbar;
	protected static JTable table;
	
	protected JButton addButton = new JButton();
	protected JButton editButton = new JButton();
	protected JButton removeButton = new JButton();
	
	protected ImageIcon addIcon = new ImageIcon("img/add.gif");
	protected ImageIcon editIcon = new ImageIcon("img/edit.gif");
	protected ImageIcon removeIcon = new ImageIcon("img/remove.gif");
	
	public static void refreshUser() {
		UserModel model = (UserModel)table.getModel();
		model.fireTableDataChanged();
	}
	
	public static void refreshService() {
		ServiceModel model = (ServiceModel)table.getModel();
		model.fireTableDataChanged();
	}
	
	public static void refreshTreatment() {
		TreatmentModel model = (TreatmentModel)table.getModel();
		model.fireTableDataChanged();
	}
	
	private void initUserModel() {
		this.setTitle("Users");
		table = new JTable(new UserModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		init();
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterDialog(true);
			}
		});
		
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String username = table.getValueAt(row, 1).toString();
				User u = managerController.getUser(username);
				new EditUserDialog(u);
				refreshUser();
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String username = table.getValueAt(row, 1).toString();
				User u = managerController.getUser(username);
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete", 
						u.getName() + " "+u.getSurname() +" - Confirm", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					managerController.removeUser(u);
					UserModel.removeUser(row);
					refreshUser();
				}
			}
			
		});
		
	}
		
	private void initServiceModel() {
		this.setTitle("Services");	
		table = new JTable(new ServiceModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		init();
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddServiceDialog();
			}
		});
		
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				CosmeticService service = DataBase.services.get(table.getValueAt(row, 0).toString());
				new EditServiceDialog(service, service.getTreatment(table.getValueAt(row, 1).toString()), row);
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					new RemoveServiceDialog();
					return;
				}
				String serviceName = table.getValueAt(row, 0).toString();
				String treatmentName = table.getValueAt(row, 1).toString();
				CosmeticService service = DataBase.services.get(serviceName);
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + treatmentName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					service.removeTreatment(treatmentName);
					ServiceModel.removeService(row);
					refreshService();
				}
			}
		});
	}
	
	private void initTreatmentModel(){
		this.setTitle("Treatments");	
		table = new JTable(new TreatmentModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		init();
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EditTreatmentDialog();
			}
		});
		
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				new EditTreatmentDialog(TreatmentModel.getTreatment(row));
			}
		});	
		
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int treatmentId = TreatmentModel.getTreatment(row).getId();
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					TreatmentController.getInstance().removeTreatment(treatmentId);
					TreatmentModel.removeTreatment(row);
					refreshTreatment();
				}
			}
		});
	}
	
	private void initBeauticianIncome() {
		this.setTitle("Beautician income");	
		table = new JTable(new BeauticianIncomeModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		this.setSize(500,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	private void initLoyalityCard() {
		this.setTitle("Loyality card");	
		table = new JTable(new LoyalityCardModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	private void initBeauticianTreatment() {
		this.setTitle("Treatments");	
		table = new JTable(new TreatmentModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton performTreatment = new JButton("Perform treatment");
		
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new MigLayout("wrap", "[grow,fill]", "[center]20[grow,fill][]"));
		JScrollPane sc = new JScrollPane(table);
		this.getContentPane().add(new JLabel("Treatments list:"), "center");
		this.getContentPane().add(sc, "center");
		this.getContentPane().add(performTreatment, "center");
		
		this.setVisible(true);
	
		performTreatment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int treatmentId = TreatmentModel.getTreatment(row).getId();
				TreatmentStatus status = TreatmentController.getInstance().getTreatment(treatmentId).getStatus();
				if(status.equals(TreatmentStatus.SCHEDULED)) {
					TreatmentController.getInstance().getTreatment(treatmentId).setStatus(TreatmentStatus.PERFORMED);
					refreshTreatment();
				}else {
					JOptionPane.showMessageDialog(null, ErrorMessage.CANNOT_PERFORM.getError(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void initClientTreatment() {
		this.setTitle("Treatments");	
		table = new JTable(new TreatmentModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton cancelTreatment = new JButton("Cancel treatment");
		
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new MigLayout("wrap", "[grow,fill]", "[]20[grow,fill][]"));
		JScrollPane sc = new JScrollPane(table);
		this.getContentPane().add(new JLabel("Treatments list:"), "center");
		this.getContentPane().add(sc, "center");
		this.getContentPane().add(cancelTreatment, "center");
		
		this.setVisible(true);
	
		cancelTreatment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int treatmentId = TreatmentModel.getTreatment(row).getId(); 
				TreatmentStatus status = TreatmentController.getInstance().getTreatment(treatmentId).getStatus();
				if(status.equals(TreatmentStatus.SCHEDULED)) {
					int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel treatment?", "Confirm", JOptionPane.YES_NO_OPTION);
					if(choice == JOptionPane.OK_OPTION) {	
						TreatmentController.getInstance().getTreatment(treatmentId).setStatus(TreatmentStatus.CANCELED_BY_THE_CLIENT);
						refreshTreatment();
					}
				}else {
					JOptionPane.showMessageDialog(null, ErrorMessage.CANNOT_CANCEL.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
	}
	
	private void initTimeTable() {
		this.setTitle("Timetable");	
		table = new JTable(new TimeTableModel());
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		this.setLocationRelativeTo(null);
		JScrollPane sc = new JScrollPane(table);
		this.getContentPane().add(sc, BorderLayout.CENTER);
		
		this.setVisible(true);
	
	}
	
	private void init() {
		this.setSize(800,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(addButton);
		this.getContentPane().add(editButton);
		this.getContentPane().add(removeButton);
		
		addButton.setIcon(addIcon);
		editButton.setIcon(editIcon);
		toolbar = new JToolBar();
		toolbar.add(addButton);
		toolbar.add(editButton);
		
		if(!(DataBase.loggedUser instanceof Receptionist)) {
			removeButton.setIcon(removeIcon);
			toolbar.add(removeButton);
		}
		
		toolbar.setFloatable(false);		

		this.getContentPane().add(toolbar, BorderLayout.NORTH);
		JScrollPane sc = new JScrollPane(table);
		add(sc, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public TableDialog(Table table) {
		switch(table) {
			case USER:
				UserModel.init();
				initUserModel();
				break;
			case SERVICE:
				ServiceModel.init();
				initServiceModel();
				break;
			case TREATMENT:
				TreatmentModel.init();
				initTreatmentModel();
				break;
			case BEAUTICIAN_INCOME:
				BeauticianIncomeModel.init();
				initBeauticianIncome();
				break;
			case LOYALITY_CARD:
				LoyalityCardModel.init();
				initLoyalityCard();
				break;
			case TIMETABLE:
			default:
				break;
		}
		
	}
	
	public TableDialog(Table table, User user) {
		if(table.equals(Table.TREATMENT)) {
			if(user instanceof Client) {
				TreatmentModel.init((Client)user);
				initClientTreatment();
				return;
			}else if(user instanceof Beautician) {
				TreatmentModel.init((Beautician)user);
				initBeauticianTreatment();
				return;
			}
		}
		
		if(table.equals(Table.TIMETABLE)) {
			if(user instanceof Beautician) {
				TimeTableModel.init((Beautician)user);
				initTimeTable();
				return;
			}
		}
	}
	
	
}
