package com.nagulov.ui.charts;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.Salon;

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
		this.setIconImage(new ImageIcon("img" + DataBase.SEPARATOR + "logo.jpg").getImage());
		this.setTitle(Salon.getInstance().getSalonName());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initChartDialog();
		this.pack();
		this.setVisible(true);
	}
}
