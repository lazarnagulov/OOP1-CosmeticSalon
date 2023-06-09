package com.nagulov.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Pricelist;
import com.nagulov.treatments.Salon;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.TreatmentModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class EditTreatmentDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Treatment treatment;
	private JComboBox<String> statusBox;
	private JPanel stPanel = new JPanel();
	private JTextField timeField = new JTextField(10);
	private ButtonGroup serviceTreatmentGroup;
	private JComboBox<String> beauticianBox;
	private JComboBox<String> clientBox;
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;

	private void initEditServiceDialog() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new AbstractFormatter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		    private String datePattern = "dd.MM.yyyy.";
		    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		    @Override
		    public Object stringToValue(String text) throws ParseException {
		        return dateFormatter.parseObject(text);
		    }

		    @Override
		    public String valueToString(Object value) throws ParseException {
		        if (value != null) {
		            Calendar cal = (Calendar) value;
		            return dateFormatter.format(cal.getTime());
		        }

		        return "";
		    }
		});
		
		if(treatment != null) {
			statusBox = new JComboBox<String>();
			statusBox.addItem(TreatmentStatus.SCHEDULED.getStatus());
			statusBox.addItem(TreatmentStatus.PERFORMED.getStatus());
			statusBox.addItem(TreatmentStatus.DID_NOT_SHOW_UP.getStatus());
			statusBox.addItem(TreatmentStatus.CANCELED_BY_THE_SALON.getStatus());
			statusBox.addItem(TreatmentStatus.CANCELED_BY_THE_CLIENT.getStatus());
			statusBox.setSelectedItem(treatment.getStatus().getStatus());
			timeField.setText(treatment.getDate().toLocalTime().toString());
			LocalDateTime date = treatment.getDate();
			model.setDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
			model.setSelected(true);
		}
		
		serviceTreatmentGroup = new ButtonGroup();
		stPanel.setLayout(new MigLayout("wrap 2", "[][]"));
		List<JRadioButton> checkboxes = new ArrayList<JRadioButton>();
		for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
			CosmeticService cs = service.getValue();
			for(int i = 0; i < cs.getTreatments().size(); ++i) {
				JRadioButton btn = new JRadioButton(cs.getName() + "-" + cs.getTreatments().get(i) + "-" + Pricelist.getInstance().getPrice(cs.getTreatments().get(i)));
				serviceTreatmentGroup.add(btn);
				stPanel.add(btn);
				checkboxes.add(btn);
				if(treatment != null && cs.getName().equals(treatment.getService().getName()) && cs.getTreatments().get(i).getName().equals(treatment.getTreatment().getName())) {
					serviceTreatmentGroup.setSelected(btn.getModel(), true);
				}
			}
		}
		
		beauticianBox = new JComboBox<String>();
		clientBox = new JComboBox<String>();
		for(Map.Entry<String, User> user : UserController.getInstance().getUsers().entrySet()) {
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

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(confirmButton);
		buttonPanel.add(cancelButton);
			
		if(statusBox != null) {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[fill][fill][fill, grow][fill][fill][fill]20[]"));
			this.getContentPane().add(new JLabel("Edit treatment"), "center, span 2");
			this.getContentPane().add(new JLabel("Status"));
			this.getContentPane().add(statusBox);
		}else {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[fill][fill, grow][fill][fill][fill]20[]"));
			this.getContentPane().add(new JLabel("Edit treatment"), "center, span 2");
		}
		
		this.getContentPane().add(new JLabel("Date"));
		this.getContentPane().add(datePicker);
		this.getContentPane().add(new JLabel("Time"));
		this.getContentPane().add(timeField);
		this.getContentPane().add(new JLabel("Service and treatment:"));
		this.getContentPane().add(stPanel, "center");
		this.getContentPane().add(new JLabel("Beautician"));
		this.getContentPane().add(beauticianBox);
		this.getContentPane().add(new JLabel("Client"));
		this.getContentPane().add(clientBox);
		this.getContentPane().add(buttonPanel, "span 2, center");
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String status = statusBox == null ? TreatmentStatus.SCHEDULED.getStatus() : statusBox.getSelectedItem().toString();
				String beautician = beauticianBox.getSelectedItem().toString();
				Beautician b = (Beautician)UserController.getInstance().getUser(beautician);
				if(b == null) {
					return;
				}
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
				String[] hm = timeField.getText().split(":");
				LocalTime time = LocalTime.of(Integer.parseInt(hm[0]), Integer.parseInt(hm[1]));
				LocalDate date = model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDateTime dateTime = LocalDateTime.of(date, time);
		
				if(!b.containsService(DataBase.services.get(service))) {
					JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_WITHOUT_SERVICE.getError() + service, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(treatment != null && !treatment.getDate().equals(dateTime) && !b.canOperate(dateTime)) {
					JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(treatment != null && !treatment.getDate().equals(dateTime) && !b.canOperate(dateTime)) {
					JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String client = clientBox.getSelectedItem().toString();
				if(treatment != null) {
					TreatmentController.getInstance().updateTreatment(treatment.getId(), TreatmentStatus.valueOf(status.toUpperCase().replace(" ", "_")), DataBase.services.get(service), DataBase.services.get(service).getTreatment(ctreatment), b, dateTime, (Client)UserController.getInstance().getUser(client));
				}
				else {
					if(!b.canOperate(dateTime)) {
						JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE.getError(), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.valueOf(status.toUpperCase().replace(" ", "_")), DataBase.services.get(service), DataBase.services.get(service).getTreatment(ctreatment), b, dateTime, (Client)UserController.getInstance().getUser(client));
					TreatmentModel.addTreatment(t);
				}
				TableDialog.refreshTreatment();

				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public EditTreatmentDialog(Treatment treatment) {
		this.treatment = treatment;
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initEditServiceDialog();
		this.pack();
		this.setVisible(true);
	}
	
	public EditTreatmentDialog() {
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initEditServiceDialog();
		this.pack();
		this.setVisible(true);
	}

}
