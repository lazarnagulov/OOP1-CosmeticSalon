package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;

import net.miginfocom.swing.MigLayout;

public class EditSalonDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField salonNameField = new JTextField(20);
	private JTextField openingTimeField = new JTextField(20);
	private JTextField closingTimeField = new JTextField(20);
	
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	private void initEditSalonDialog() {
		salonNameField.setText(DataBase.salonName);
		openingTimeField.setText(DataBase.opening.toString());
		closingTimeField.setText(DataBase.closing.toString());
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[][][][]20[]"));
		
		this.getContentPane().add(new JLabel("Salon name"), "span 2");
		this.getContentPane().add(salonNameField, "span 2");
		this.getContentPane().add(new JLabel("Working time"), "span 2");
		this.getContentPane().add(openingTimeField);
		this.getContentPane().add(closingTimeField);
		this.add(cancelButton, "right");
		this.add(confirmButton);	
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String salonName = salonNameField.getText();
				LocalTime opening = LocalTime.parse(openingTimeField.getText());
				LocalTime closing = LocalTime.parse(closingTimeField.getText());
				
				UserController.getInstance().updateSalonName(salonName);
				UserController.getInstance().updateWorkingTime(opening, closing);
				
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
	
	public EditSalonDialog() {
		this.setTitle(DataBase.salonName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.initEditSalonDialog();
		this.pack();
		this.setVisible(true);
	}

}
