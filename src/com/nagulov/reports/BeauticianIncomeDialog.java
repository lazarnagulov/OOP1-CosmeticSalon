package com.nagulov.reports;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.nagulov.data.DataBase;
import com.nagulov.ui.Table;
import com.nagulov.ui.TableDialog;

import net.miginfocom.swing.MigLayout;

public class BeauticianIncomeDialog extends JDialog{

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
	
	void initBeauticianDialog() {
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
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][grow]", "[fill]20[grow, center][grow, center][grow, center]20[center]"));
		this.getContentPane().add(new JLabel("Beautician Report"), "span 2, center");
		this.getContentPane().add(new JLabel("Start date:"));
		this.getContentPane().add(startDatePicker);
		this.getContentPane().add(new JLabel("End date:"));
		this.getContentPane().add(endDatePicker);
		this.getContentPane().add(confirmButton, "span 2, center");
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate startDate = model.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate endDate = endModel.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Report.calculateBeauticianReport(startDate, endDate);
				new TableDialog(Table.BEAUTICIAN_INCOME);
			}
		});
	}
	
	public BeauticianIncomeDialog() {
		setTitle(DataBase.salonName);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initBeauticianDialog();
		pack();
		setVisible(true);
	}

}
