package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.reports.Report;
import com.nagulov.users.Beautician;

public class BeauticianIncomeModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Beautician", "Treatments count", "Income"};
	private static List<Beautician> beautician = new ArrayList<Beautician>();
	private static HashMap<Beautician, ArrayList<Double>> reportData = new HashMap<Beautician, ArrayList<Double>>();
	
	public static void init() {
		reportData = Report.beauticianReport;
		for(Map.Entry<Beautician, ArrayList<Double>> entry : reportData.entrySet()) {
			beautician.add(entry.getKey());
		}
	}
		
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return reportData.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Beautician b = beautician.get(rowIndex);
		switch(columnIndex) {
			case 0: return b.getUsername();
			case 1: return reportData.get(b).get(1).intValue();
			case 2: return reportData.get(b).get(0);
		}
		return null;
	}

}
