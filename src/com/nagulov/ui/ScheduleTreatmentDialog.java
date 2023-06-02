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

import com.nagulov.controllers.ClientController;
import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.TreatmentModel;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.User;

import net.miginfocom.swing.MigLayout;

public class ScheduleTreatmentDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel stPanel = new JPanel();
	private ButtonGroup serviceTreatmentGroup;
	private JButton confirmTreatmentButton = new JButton("Confirm treatment");
	private JButton skipButton = new JButton("Skip");
	private JButton confirmBeauticianButton = new JButton("Confirm beautician");
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	private JComboBox<String> beauticianBox = new JComboBox<String>();
	private JTextField timeField = new JTextField(10);
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	
	private void initScheduleTreatmentDialog() {
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
		serviceTreatmentGroup = new ButtonGroup();
		stPanel.setLayout(new MigLayout("wrap 2", "[][]"));
		List<JRadioButton> checkboxes = new ArrayList<JRadioButton>();
		for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
			CosmeticService cs = service.getValue();
			for(int i = 0; i < cs.getTreatments().size(); ++i) {
				JRadioButton btn = new JRadioButton(cs.getName() + "-" + cs.getTreatments().get(i));
				serviceTreatmentGroup.add(btn);
				stPanel.add(btn);
				checkboxes.add(btn);
			}
		}
		
		beauticianBox.setEnabled(false);
		skipButton.setEnabled(false);
		confirmBeauticianButton.setEnabled(false);
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[]20[][]20[][][][]20[]"));
		this.getContentPane().add(new JLabel("Schedule treatment"), "span 2, center");
		this.getContentPane().add(new JLabel("Treatment"));
		this.getContentPane().add(stPanel);
		this.getContentPane().add(confirmTreatmentButton, "span 2, center");
		this.getContentPane().add(new JLabel("Beautician"));
		this.getContentPane().add(beauticianBox);
		this.getContentPane().add(skipButton);
		this.getContentPane().add(confirmBeauticianButton);		
		this.getContentPane().add(new JLabel("Date"));
		this.getContentPane().add(datePicker);
		this.getContentPane().add(new JLabel("Time"));
		this.getContentPane().add(timeField);
		this.getContentPane().add(confirmButton);
		this.getContentPane().add(cancelButton);
		
		confirmTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = null;
				String treatment = null;
				beauticianBox.removeAllItems();
				for(JRadioButton btn : checkboxes) {
					if(btn.isSelected()){
						service = btn.getText().split("-")[0];
						treatment = btn.getText().split("-")[1];
						break;
					}
				}
				if(service == null || treatment == null) {
					JOptionPane.showMessageDialog(null, ErrorMessage.NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(Map.Entry<String, User> entry : UserController.getInstance().getUsers().entrySet()) {
					if(!(entry.getValue() instanceof Beautician)) {
						continue;
					}
					Beautician b = (Beautician) entry.getValue();
					if(b.containsTreatment(DataBase.services.get(service))){
						beauticianBox.addItem(b.getUsername());
					}
				}
				beauticianBox.setEnabled(true);
				skipButton.setEnabled(true);
				confirmBeauticianButton.setEnabled(true);
			}
			
		});
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String beautician = beauticianBox.getSelectedItem().toString();
				Beautician b = (Beautician)UserController.getInstance().getUser(beautician);
				if(b == null) {
					return;
				}
				String service = null;
				String treatment = null;
				for(JRadioButton btn : checkboxes) {
					if(btn.isSelected()){
						service = btn.getText().split("-")[0];
						treatment = btn.getText().split("-")[1];
						break;
					}
				}
				if(service == null || treatment == null) {
					JOptionPane.showMessageDialog(null, ErrorMessage.NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String[] hm = timeField.getText().split(":");
				LocalTime time = LocalTime.of(Integer.parseInt(hm[0]), Integer.parseInt(hm[1]));
				LocalDate date = model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDateTime dateTime = LocalDateTime.of(date, time);
				
				String treatmentStr = new StringBuilder("Service: ")
						.append(service).append("\n")
						.append("Treatment: ")
						.append(treatment).append("\n")
						.append("Beautician: ")
						.append(beautician).append("\n")
						.append("Date: ")
						.append(date.format(DataBase.DATE_FORMAT)).append("\n")
						.append("Time: ")
						.append(time)
						.toString();
				
				int choice = JOptionPane.showConfirmDialog(null, treatmentStr, "Confirm Treatment", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, DataBase.services.get(service), DataBase.services.get(service).getTreatment(treatment), b, dateTime, (Client)DataBase.loggedUser);
					ClientController.getInstance().scheduleTreatment(t);
					TreatmentModel.addTreatment(t);
				}
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
	
	
	public ScheduleTreatmentDialog() {
		this.setTitle(DataBase.salonName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.initScheduleTreatmentDialog();
		this.pack();
		this.setVisible(true);
	}

}
