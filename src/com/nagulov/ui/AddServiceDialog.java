package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.nagulov.controllers.ServiceController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Salon;
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
		
		for(Map.Entry<String, CosmeticService> entry : ServiceController.getInstance().getServices().entrySet()) {	
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

				ErrorMessage error = Validator.createService(serviceName);
				if(!error.equals(ErrorMessage.SUCCESS)) {
					JOptionPane.showMessageDialog(null, error.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ServiceController.getInstance().getServices().put(serviceName, service);
				setVisible(false);
				dispose();
			}
			
		});
		
		addTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CosmeticService service = ServiceController.getInstance().getServices().get(serviceField.getSelectedItem().toString());
				String treatment = treatmentField.getText();
				double price = 0.0;
				LocalTime duration = null;
				try {
					duration = LocalTime.parse(durationField.getText());
					price = Double.parseDouble(priceField.getText());
				}catch(Exception invalidInput) {
					JOptionPane.showMessageDialog(null, ErrorMessage.INVALID_INPUT, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				CosmeticTreatment ct = ServiceController.getInstance().createCosmeticTreatment(service, treatment, duration);
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
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("img" + DataBase.SEPARATOR + "logo.jpg").getImage());
		initAddServiceDialog();
		this.pack();
		this.setVisible(true);
	}
}
