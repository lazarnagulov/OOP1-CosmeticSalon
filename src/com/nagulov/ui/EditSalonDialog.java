package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nagulov.controllers.SalonController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.Salon;

import net.miginfocom.swing.MigLayout;

public class EditSalonDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField salonNameField = new JTextField(20);
	private JTextField openingTimeField = new JTextField(20);
	private JTextField closingTimeField = new JTextField(20);
	private JTextField loyalityCardRequirementField = new JTextField(20);
	
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	private void initEditSalonDialog() {
		salonNameField.setText(Salon.getInstance().getSalonName());
		openingTimeField.setText(Salon.getInstance().getOpening().toString());
		closingTimeField.setText(Salon.getInstance().getClosing().toString());
		loyalityCardRequirementField.setText(Double.valueOf(UserController.getInstance().loyaltyCardNeeded).toString());
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[][][][][][][][]20[]"));
		
		this.getContentPane().add(new JLabel("Salon name"), "span 2");
		this.getContentPane().add(salonNameField, "span 2");
		this.getContentPane().add(new JLabel("Income and expenditure " + LocalDate.now().withDayOfMonth(1).format(DataBase.DATE_FORMAT) + " - " + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DataBase.DATE_FORMAT)), "span 2");
		this.getContentPane().add(new JLabel("Income: "));
		this.getContentPane().add(new JLabel(Double.valueOf(SalonController.getInstance().calculateIncome(LocalDate.now().withDayOfMonth(1), LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()))).toString()));
		this.getContentPane().add(new JLabel("Expenditure: "));
		this.getContentPane().add(new JLabel(Double.valueOf(SalonController.getInstance().calculateExpenditure(LocalDate.now().withDayOfMonth(1), LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()))).toString()));
		this.getContentPane().add(new JLabel("Working time"), "span 2");
		this.getContentPane().add(openingTimeField);
		this.getContentPane().add(closingTimeField);
		this.getContentPane().add(new JLabel("Loyality Card requirement"));
		this.getContentPane().add(loyalityCardRequirementField);
		this.add(cancelButton, "right");
		this.add(confirmButton);	
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String salonName = salonNameField.getText();
				LocalTime opening = LocalTime.parse(openingTimeField.getText());
				LocalTime closing = LocalTime.parse(closingTimeField.getText());
				double loyalityCard = Double.parseDouble(loyalityCardRequirementField.getText());
				Salon.getInstance().setSalonName(salonName);
				Salon.getInstance().setOpening(opening);
				Salon.getInstance().setClosing(closing);
				UserController.getInstance().loyaltyCardNeeded = loyalityCard;
				
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
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.initEditSalonDialog();
		this.pack();
		this.setVisible(true);
	}

}
