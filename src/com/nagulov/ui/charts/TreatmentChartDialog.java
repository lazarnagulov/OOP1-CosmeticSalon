package com.nagulov.ui.charts;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

import com.nagulov.data.DataBase;
import com.nagulov.reports.Report;
import com.nagulov.treatments.Salon;
import com.nagulov.treatments.TreatmentStatus;

import net.miginfocom.swing.MigLayout;

public class TreatmentChartDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void initChartDialog() {
		JPanel treatmentChart = new XChartPanel<PieChart>(ReportChart.initTreatmentChart());
		this.getContentPane().setLayout(new MigLayout("wrap 2", "[][grow]", "[]20[][][][][]20[grow, center]"));
		this.getContentPane().add(new JLabel("Statistics: " + LocalDate.now().minusDays(30).format(DataBase.DATE_FORMAT) + " - " + LocalDate.now().format(DataBase.DATE_FORMAT)), "span 2");
		
		HashMap<TreatmentStatus, Integer> report = Report.treatmentReport;
		
		for(Map.Entry<TreatmentStatus, Integer> entry : report.entrySet()) {
			this.getContentPane().add(new JLabel(entry.getKey().toString().replace("_", " ") + ": " + entry.getValue().toString()), "span 2");
		}
		
		this.getContentPane().add(treatmentChart, "span 2");
	}
	

	public TreatmentChartDialog() {
		setTitle(Salon.getInstance().getSalonName());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initChartDialog();
		pack();
		setVisible(true);
	}
}


