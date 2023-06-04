package com.nagulov.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.nagulov.data.DataBase;
import com.nagulov.data.ErrorMessage;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Salon;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;

import net.miginfocom.swing.MigLayout;

public class ReportsDateDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton confirmButton = new JButton("Confirm");
	private UtilDateModel model = new UtilDateModel();
	private UtilDateModel endModel = new UtilDateModel();
	private JDatePanelImpl startDatePanel;
	private JDatePickerImpl startDatePicker;
	private JDatePanelImpl endDatePanel;
	private JDatePickerImpl endDatePicker;
	
	private JComboBox<String> cosmeticTreatmentBox;
	
	
	private Table table;
	
	void init() {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		startDatePanel = new JDatePanelImpl(model, p);
		startDatePicker = new JDatePickerImpl(startDatePanel, new AbstractFormatter() {
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
		endDatePanel = new JDatePanelImpl(endModel, p);
		endDatePicker = new JDatePickerImpl(endDatePanel, new AbstractFormatter() {
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
		if(table.equals(Table.COSMETIC_TREATMENT_STATUS)) {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][grow]", "[fill]20[grow, center][grow, center]20[grow, center]20[center]"));
		}else {
			this.getContentPane().setLayout(new MigLayout("wrap 2", "[][grow]", "[fill]20[grow, center][grow, center][grow, center][grow, center]20[center]"));
		}

		if(table.equals(Table.TREATMENTS_STATUS)) {
			this.getContentPane().add(new JLabel("Treatments Report"), "span 2, center");
		}
		else if(table.equals(Table.BEAUTICIAN_INCOME)) {
			this.getContentPane().add(new JLabel("Beautician Report"), "span 2, center");
		}else if(table.equals(Table.COSMETIC_TREATMENT_STATUS)) {
			this.getContentPane().add(new JLabel("Service status"), "span 2, center");
		}
		
		this.getContentPane().add(new JLabel("Start date:"));
		this.getContentPane().add(startDatePicker);
		this.getContentPane().add(new JLabel("End date:"));
		this.getContentPane().add(endDatePicker);
		if(table.equals(Table.COSMETIC_TREATMENT_STATUS)) {
			cosmeticTreatmentBox = new JComboBox<String>();
			for(Map.Entry<String, CosmeticService> entry : DataBase.services.entrySet()) {
				for(CosmeticTreatment treatment : entry.getValue().getTreatments()) {
					cosmeticTreatmentBox.addItem(entry.getValue().getName() + "-" + treatment.getName());
				}
			}
			this.getContentPane().add(cosmeticTreatmentBox, "span 2, center");
		}
		this.getContentPane().add(confirmButton, "span 2, center");
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate startDate = null;
				LocalDate endDate = null;
				try {
					startDate = model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					endDate = endModel.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				} catch(Exception excetion){
					JOptionPane.showMessageDialog(null, ErrorMessage.NOT_SELECTED.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(endDate.isBefore(startDate)) {
					JOptionPane.showMessageDialog(null, ErrorMessage.INVALID_DATE_INTERVAL.getError(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(table.equals(Table.BEAUTICIAN_INCOME)) {
					Report.calculateBeauticianReport(startDate, endDate);
				}else if(table.equals(Table.TREATMENTS_STATUS)) {
					Report.calculateTreatmentsReport(startDate, endDate);
				}else if(table.equals(Table.COSMETIC_TREATMENT_STATUS)) {
					String[] ct = cosmeticTreatmentBox.getSelectedItem().toString().split("-");
					CosmeticService service = DataBase.services.get(ct[0]);
					CosmeticTreatment treatment = service.getTreatment(ct[1]);
					Report.calculateComseticTreatmentReport(treatment, startDate, endDate);
					
					new TableDialog(treatment);
					return;
				}
				new TableDialog(table);
			}
		});
	}
	
	public ReportsDateDialog(Table table) {
		this.table = table;
		setTitle(Salon.getInstance().getSalonName());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		init();
		pack();
		setVisible(true);
	}
}
