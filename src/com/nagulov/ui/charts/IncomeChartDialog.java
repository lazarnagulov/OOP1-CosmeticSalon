package com.nagulov.ui.charts;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import com.nagulov.treatments.Salon;

import net.miginfocom.swing.MigLayout;

public class IncomeChartDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void initChartDialog() {
		JPanel incomeChart = new XChartPanel<XYChart>(ReportChart.initIncomeChart());
		this.getContentPane().add(incomeChart);
	}
	
	public IncomeChartDialog() {
		setTitle(Salon.getInstance().getSalonName());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initChartDialog();
		pack();
		setVisible(true);
	}
}
