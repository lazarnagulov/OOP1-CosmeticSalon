package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nagulov.controllers.ManagerController;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.ui.models.ServiceModel;

import net.miginfocom.swing.MigLayout;

public class EditServiceDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = ManagerController.getInstance();
	
	private CosmeticService service;
	private String treatment;
	private int row;
	protected JTextField serviceField = new JTextField(20);
	protected JTextField treatmentField = new JTextField(20);
	protected JTextField priceField = new JTextField(20);
	
	private JButton confirmButton = new JButton("Confirm");
	protected JButton cancelButton = new JButton("Cancel");

	
	protected void initEditServiceDialog(String name) {
		
		serviceField.setText(service.getName());
		treatmentField.setText(treatment);
		priceField.setText(service.getTreatments().get(treatment).toString());
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][]20[]"));
		
		this.getContentPane().add(new JLabel(name), "span 2");
		this.getContentPane().add(new JLabel("Service"));
		this.getContentPane().add(serviceField);
		this.getContentPane().add(new JLabel("Treatments"));
		this.getContentPane().add(treatmentField);
		this.getContentPane().add(new JLabel("Price"));
		this.getContentPane().add(priceField);
		this.add(cancelButton);
		this.add(confirmButton);
		
		JDialog frame = this;
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = serviceField.getText();
				String treatment = treatmentField.getText();
				double price = Double.parseDouble(priceField.getText());
				
				ServiceModel.updateService(row, service, treatment, priceField.getText());
				managerController.updateService(service, treatment, price);
				TableDialog.refreshService();
				
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
	
	}
	
	public EditServiceDialog(CosmeticService service, String treatment, int row) {
		this.service = service;
		this.treatment = treatment;
		this.row = row;
		setTitle("Cosmetic Salon Nagulov");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initEditServiceDialog("Edit service");
		pack();
		setVisible(true);
	}

}
