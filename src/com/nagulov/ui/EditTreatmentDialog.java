package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.nagulov.controllers.ManagerController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.TreatmentModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class EditTreatmentDialog extends JDialog{

	private Treatment treatment;
	/*	private TreatmentStatus status;
		private CosmeticService service;
		private CosmeticTreatment treatment;
		private Beautician beautician;
		private LocalDateTime date;
		private Client client;
	 * 
	 * 
	 */
	//TODO: Datetime picker
	private JComboBox<String> statusBox;
	private JPanel stPanel = new JPanel();
	private ButtonGroup serviceTreatmentGroup;
	private JComboBox<String> beauticianBox;
	private JComboBox<String> clientBox;
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	private void initEditServiceDialog() {
		
		if(treatment != null) {
			statusBox = new JComboBox<String>();
			statusBox.addItem(TreatmentStatus.SCHEDULED.getStatus());
			statusBox.addItem(TreatmentStatus.PERFORMED.getStatus());
			statusBox.addItem(TreatmentStatus.DID_NOT_SHOW_UP.getStatus());
			statusBox.addItem(TreatmentStatus.CANCELED_BY_THE_SALON.getStatus());
			statusBox.addItem(TreatmentStatus.CANCELED_BY_THE_CLIENT.getStatus());
			statusBox.setSelectedItem(treatment.getStatus().getStatus());
		}
		
		serviceTreatmentGroup = new ButtonGroup();
		List<JRadioButton> checkboxes = new ArrayList<JRadioButton>();
		for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
			CosmeticService cs = service.getValue();
			for(int i = 0; i < cs.getTreatments().size(); ++i) {
				JRadioButton btn = new JRadioButton(cs.getName() + "-" + cs.getTreatments().get(i));
				serviceTreatmentGroup.add(btn);
				stPanel.add(btn);
				checkboxes.add(btn);
				if(treatment != null && cs.equals(treatment.getService()) && cs.getTreatments().get(i).equals(treatment.getTreatment())) {
					serviceTreatmentGroup.setSelected(btn.getModel(), true);
				}
			}
		}
		
		beauticianBox = new JComboBox<String>();
		clientBox = new JComboBox<String>();
		for(Map.Entry<String, User> user : DataBase.users.entrySet()) {
			if(user.getValue() instanceof Beautician) {
				beauticianBox.addItem(user.getValue().getUsername());
				if(treatment != null && user.getValue().getUsername().equals(treatment.getBeautician().getUsername())) {
					beauticianBox.setSelectedItem(user.getValue().getUsername());
				}
			}
			if(user.getValue() instanceof Client) {
				clientBox.addItem(user.getValue().getUsername());
				if(treatment != null && user.getValue().getUsername().equals(treatment.getClient().getUsername())) {
					clientBox.setSelectedItem(user.getValue().getUsername());
				}
			}
		}
		
		stPanel.setLayout(new MigLayout("wrap 2", "[][]"));
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][][][][][]20[]"));
		
		this.getContentPane().add(new JLabel("Edit treatment"), "span 2");
		if(statusBox != null) {
			this.getContentPane().add(new JLabel("Status"));
			this.getContentPane().add(statusBox);
		}
		this.getContentPane().add(new JLabel("Service and treatment:"));
		this.getContentPane().add(stPanel);
		this.getContentPane().add(new JLabel("Beautician"));
		this.getContentPane().add(beauticianBox);
		this.getContentPane().add(new JLabel("Client"));
		this.getContentPane().add(clientBox);
		this.getContentPane().add(confirmButton);
		this.getContentPane().add(cancelButton);
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String status = statusBox == null ? TreatmentStatus.SCHEDULED.getStatus() : statusBox.getSelectedItem().toString();
				String beautician = beauticianBox.getSelectedItem().toString();
				Beautician b = (Beautician)DataBase.users.get(beautician);
				String service = null;
				String ctreatment = null;
				for(JRadioButton btn : checkboxes) {
					if(btn.isSelected()){
						service = btn.getText().split("-")[0];
						ctreatment = btn.getText().split("-")[1];
						break;
					}
				}
				if(service == null || ctreatment == null) {
					JOptionPane.showMessageDialog(null, ErrorMessage.NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!b.containsTreatment(DataBase.services.get(service))) {
					JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_WITHOUT_SERVICE.getError() + service, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String client = clientBox.getSelectedItem().toString();
				Treatment t = null;
				if(treatment != null) {
					TreatmentModel.removeTreatment(treatment);
					t = ManagerController.getInstance().updateTreatment(treatment.getId(), TreatmentStatus.valueOf(status.toUpperCase().replace(" ", "_")), DataBase.services.get(service), DataBase.services.get(service).getTreatment(ctreatment), b, LocalDateTime.now().plusDays(1).withMinute(0).withHour(12), (Client)DataBase.users.get(client));
					TreatmentModel.addTreatment(t);
				}
				else {
					t = ManagerController.getInstance().createTreatment(TreatmentStatus.valueOf(status.toUpperCase().replace(" ", "_")), DataBase.services.get(service), DataBase.services.get(service).getTreatment(ctreatment), b, LocalDateTime.now().plusDays(1).withMinute(0).withHour(12), (Client)DataBase.users.get(client));
					TreatmentModel.addTreatment(t);
				}
				TableDialog.refreshTreatment();
				
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
	
	public EditTreatmentDialog() {
		this.setTitle(DataBase.salonName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initEditServiceDialog();
		this.pack();
		this.setVisible(true);
	}
	
	
	public EditTreatmentDialog(Treatment treatment) {
		this.treatment = treatment;
		this.setTitle(DataBase.salonName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initEditServiceDialog();
		this.pack();
		this.setVisible(true);
	}
}
