package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.ui.models.ServiceModel;

import net.miginfocom.swing.MigLayout;

public class RemoveServiceDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox<String> serviceBox = new JComboBox<String>();
	
	JButton cancelButton = new JButton("Cancel");
	JButton confirmButton = new JButton("Confirm");
	
	private void initRemoveServiceDialog() {
		
		for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
			serviceBox.addItem(service.getKey());
		}
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[]20[]"));
		this.getContentPane().add(new JLabel("Remove Service"),"span 2");
		this.getContentPane().add(new JLabel("Service"));
		this.getContentPane().add(serviceBox);
		this.getContentPane().add(confirmButton);
		this.getContentPane().add(cancelButton);
		
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = serviceBox.getSelectedItem().toString();
				
				int choice = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + service + "?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					ServiceModel.removeService(DataBase.services.get(service));
					ManagerController.getInstance().removeService(DataBase.services.get(service));
				}
				TableDialog.refreshService();
				setVisible(false);
				dispose();
			}
		});
		
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
	
	
	
	public RemoveServiceDialog() {
		setTitle(DataBase.salonName);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initRemoveServiceDialog();
		pack();
		setVisible(true);
	}

}
