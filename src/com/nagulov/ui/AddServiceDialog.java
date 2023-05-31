package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.ui.models.ServiceModel;

import net.miginfocom.swing.MigLayout;

public class AddServiceDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> serviceField;
	private JTextField treatmentField = new JTextField(20);
	private JTextField durationField = new JTextField(20);
	private JTextField priceField = new JTextField(20);
	private JTextField newServiceField = new JTextField(20);
	
	private JButton addTreatmentButton = new JButton("Add Treatment");
	private JButton addServiceButton = new JButton("Add Service");
	private JButton cancelButton = new JButton("Cancel");


	private void initAddServiceDialog() {
		
		serviceField = new JComboBox<String>();
		
		for(Map.Entry<String, CosmeticService> entry : DataBase.services.entrySet()) {	
			serviceField.addItem(entry.getKey());
		}
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][]20[]20[][][][][][]20[]"));
		
		this.getContentPane().add(new JLabel("Add service"), "span 2");
		this.getContentPane().add(new JLabel("Service name"));
		this.getContentPane().add(newServiceField);
		this.getContentPane().add(addServiceButton, "span 2");		
		this.getContentPane().add(new JLabel("Add treatment"), "span 2");
		this.getContentPane().add(new JLabel("Service"));
		this.getContentPane().add(serviceField);
		this.getContentPane().add(new JLabel("Treatment"));
		this.getContentPane().add(treatmentField);
		this.getContentPane().add(new JLabel("Duration"));
		this.getContentPane().add(durationField);
		this.getContentPane().add(new JLabel("Price"));
		this.getContentPane().add(priceField);
		this.getContentPane().add(addTreatmentButton, "span 2");
		this.getContentPane().add(cancelButton);
		
		addServiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String serviceName = newServiceField.getText();
				
				CosmeticService service = new CosmeticService(serviceName);
				
				if(!DataBase.services.containsKey(serviceName)) {
					DataBase.services.put(serviceName, service);
				}
				
				setVisible(false);
				dispose();
			}
			
		});
		
		addTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CosmeticService service = DataBase.services.get(serviceField.getSelectedItem().toString());
				String treatment = treatmentField.getText();
				LocalTime duration = LocalTime.parse(durationField.getText());
				double price = Double.parseDouble(priceField.getText());
				
				CosmeticTreatment ct = UserController.getInstance().createCosmeticTreatment(service, treatment, duration);
				Pricelist.getInstance().setPrice(ct, price);
				
				ServiceModel.addTreatment(ct);
				
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
	
	public AddServiceDialog() {
		setTitle("Cosmetic Salon Nagulov");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initAddServiceDialog();
		pack();
		setVisible(true);
	}
}
