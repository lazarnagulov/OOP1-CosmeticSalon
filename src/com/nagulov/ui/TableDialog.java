package com.nagulov.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.ui.models.ServiceModel;
import com.nagulov.ui.models.UserModel;
import com.nagulov.users.User;

public class TableDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = ManagerController.getInstance();
	
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
				// TODO Auto-generated method stub
				
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
				new EditServiceDialog(service, table.getValueAt(row, 1).toString(), row);
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
				String serviceName = table.getValueAt(row, 0).toString();
				String treatmentName = table.getValueAt(row, 1).toString();
				CosmeticService service = DataBase.services.get(serviceName);
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete", "Confirm", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
//					service.removeTreatment(treatmentName);
					ServiceModel.removeService(row);
					refreshService();
				}
			}
		});
	}
	
	private void init() {
		this.setSize(500,300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(addButton);
		this.getContentPane().add(editButton);
		this.getContentPane().add(removeButton);
		
		addButton.setIcon(addIcon);
		editButton.setIcon(editIcon);
		removeButton.setIcon(removeIcon);
		
		toolbar = new JToolBar();
		toolbar.add(addButton);
		toolbar.add(editButton);
		toolbar.add(removeButton);
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
				//TODO
				break;
		}
		
	}
	
	
}
