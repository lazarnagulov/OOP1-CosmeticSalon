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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.nagulov.controllers.ClientController;
import com.nagulov.controllers.ServiceController;
import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.data.Validator;
import com.nagulov.treatments.Salon;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.ui.models.ServiceModel;
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
	
	private JTable treatmentTable;
	private JButton confirmTreatmentButton = new JButton("Confirm treatment");
	private JButton confirmButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	private JComboBox<String> beauticianBox = new JComboBox<String>();
	private JTextField timeField = new JTextField(10);
	private UtilDateModel model = new UtilDateModel();
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;

	private JPanel filterPanel = new JPanel();
	private JTextField filterCosmeticTreatment = new JTextField(20);
	private JTextField filterCosmeticService = new JTextField(20);
	private JTextField filterDuration = new JTextField(20);
	private JTextField filterPrice = new JTextField(20);
	private JButton filterButton = new JButton("Filter");

	private TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>();
	
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
		ServiceModel.init();
		sorter.setModel(new ServiceModel());
		for(int i=0; i<4; i++) {
			sorter.setSortable(i, false);
		}
		treatmentTable = new JTable(new ServiceModel());
		treatmentTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treatmentTable.getTableHeader().setReorderingAllowed(false);
		treatmentTable.setRowSorter(sorter);
		JScrollPane sc = new JScrollPane(treatmentTable);
		
		filterPanel.setLayout(new MigLayout("wrap 2", "[][]", "[][][][][]"));
		filterPanel.add(new JLabel("Service:"));
		filterPanel.add(filterCosmeticService);
		filterPanel.add(new JLabel("Treatment"));
		filterPanel.add(filterCosmeticTreatment);
		filterPanel.add(new JLabel("Price"));
		filterPanel.add(filterPrice);
		filterPanel.add(new JLabel("Duration:"));
		filterPanel.add(filterDuration);
		filterPanel.add(filterButton, "span 2, center");
		
		beauticianBox.setEnabled(false);
		datePicker.setEnabled(false);
		datePanel.setEnabled(false);
		timeField.setEnabled(false);
		confirmButton.setEnabled(false);
		
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][]", "[][]20[120px][][]20[]"));
		this.getContentPane().add(new JLabel("Schedule treatment"), "span 2, center");
		this.getContentPane().add(new JLabel("Pricelist"), "span 2, center");
		this.getContentPane().add(sc, "span 2");
		this.getContentPane().add(filterPanel, "span 2, center");
		this.getContentPane().add(confirmTreatmentButton, "span 2, center");
		this.getContentPane().add(new JLabel("Beautician:"));
		this.getContentPane().add(beauticianBox);
		this.getContentPane().add(new JLabel("Date:"));
		this.getContentPane().add(datePicker);
		this.getContentPane().add(new JLabel("Time (e.g. 13:00):"));
		this.getContentPane().add(timeField);
		this.getContentPane().add(confirmButton);
		this.getContentPane().add(cancelButton);
		
		filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String service = filterCosmeticService.getText();
				String treatment = filterCosmeticTreatment.getText();
				String price = filterPrice.getText();
				String duration = filterDuration.getText();
				
				List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
				if(!service.equals("")) {
					filters.add(RowFilter.regexFilter("(?i).*" + service + "*.", 0));
				}
				if(!treatment.equals("")) {
					filters.add(RowFilter.regexFilter("(?i).*" + treatment + "*.", 1));
				}
				if(!duration.equals("")) {
					filters.add(RowFilter.regexFilter(duration, 2));
				}
				if(!price.equals("")) {
					filters.add(RowFilter.regexFilter(price, 3));
				}
				RowFilter<Object, Object> filter = RowFilter.andFilter(filters);
				sorter.setRowFilter(filter);				
			}
		});
		
		confirmTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = treatmentTable.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String service = treatmentTable.getValueAt(row, 0).toString();
				String treatment = treatmentTable.getValueAt(row, 1).toString();
				
				if(service == null || treatment == null) {
					JOptionPane.showMessageDialog(null, ErrorMessage.NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				beauticianBox.addItem("");
				for(Map.Entry<String, User> entry : UserController.getInstance().getUsers().entrySet()) {
					if(!(entry.getValue() instanceof Beautician)) {
						continue;
					}
					Beautician b = (Beautician) entry.getValue();
					if(b.containsService(ServiceController.getInstance().getServices().get(service))){
						beauticianBox.addItem(b.getUsername());
					}
				}
				beauticianBox.setEnabled(true);
				timeField.setEnabled(true);
				datePanel.setEnabled(true);
				datePicker.setEnabled(true);
				confirmButton.setEnabled(true);
			}
			
		});

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = treatmentTable.getSelectedRow();
				if(row == -1) {
					JOptionPane.showMessageDialog(null, ErrorMessage.ROW_NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String service = treatmentTable.getValueAt(row, 0).toString();
				String treatment = treatmentTable.getValueAt(row, 1).toString();
				LocalDateTime dateTime = null;
				LocalDate date = null;
				LocalTime time = null;
				try {
					String[] hm = timeField.getText().split(":");
					time = LocalTime.of(Integer.parseInt(hm[0]), Integer.parseInt(hm[1]));
					date = model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					dateTime = LocalDateTime.of(date, time);
				}catch(Exception dateException) {
					JOptionPane.showMessageDialog(null, ErrorMessage.INVALID_TIME.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String beautician = beauticianBox.getSelectedItem().toString();
				Beautician b = (Beautician)UserController.getInstance().getUser(beautician);
				if(b == null) {
					b = UserController.getInstance().findAvailableBeautician(dateTime, ServiceController.getInstance().getServices().get(service));
					if(b == null) {
						JOptionPane.showMessageDialog(null, ErrorMessage.BEAUTICIAN_IS_NOT_AVAILABLE.getError(), "Error", JOptionPane.ERROR_MESSAGE);
						timeField.setText("");
						return;
					}
				}
				ErrorMessage error = Validator.createTreatment(ServiceController.getInstance().getServices().get(service), b, dateTime);
				if(!error.equals(ErrorMessage.SUCCESS)) {
					JOptionPane.showMessageDialog(null, error.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String treatmentStr = new StringBuilder("Service: ")
						.append(service).append("\n")
						.append("Treatment: ")
						.append(treatment).append("\n")
						.append("Beautician: ")
						.append(b.getUsername()).append("\n")
						.append("Date: ")
						.append(date.format(DataBase.DATE_FORMAT)).append("\n")
						.append("Time: ")
						.append(time)
						.toString();
				
				int choice = JOptionPane.showConfirmDialog(null, treatmentStr, "Confirm Treatment", JOptionPane.YES_NO_OPTION);
				if(choice == JOptionPane.OK_OPTION) {
					Treatment t = TreatmentController.getInstance().createTreatment(TreatmentStatus.SCHEDULED, ServiceController.getInstance().getServices().get(service), ServiceController.getInstance().getServices().get(service).getTreatment(treatment), b, dateTime, (Client)DataBase.loggedUser);
					ClientController.getInstance().scheduleTreatment(t);
					TreatmentModel.addTreatment(t);
					dispose();
				}
				
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	
	}
	
	
	public ScheduleTreatmentDialog() {
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("img" + DataBase.SEPARATOR + "logo.jpg").getImage());
		this.initScheduleTreatmentDialog();
		this.pack();
		this.setVisible(true);
	}

}
