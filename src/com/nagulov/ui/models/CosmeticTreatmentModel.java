package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.nagulov.reports.Report;

public class CosmeticTreatmentModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"Count", "Total income"};
	private static List<Double> reportData = new ArrayList<Double>();
	
	public static void init() {
		reportData = Report.cosmeticTreatmentReport;
	}
		
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch(columnIndex) {
			case 0: return reportData.get(0).intValue();
			case 1: return reportData.get(1);
		}
		return null;
	}

}
