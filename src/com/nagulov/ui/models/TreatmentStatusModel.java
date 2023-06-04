package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.reports.Report;
import com.nagulov.treatments.TreatmentStatus;

public class TreatmentStatusModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Status", "Count"};
	private static List<TreatmentStatus> statuses = new ArrayList<TreatmentStatus>();
	private static HashMap<TreatmentStatus, Integer> reportData = new HashMap<TreatmentStatus, Integer>();
	
	public static void init() {
		reportData = Report.treatmentReport;
		for(Map.Entry<TreatmentStatus, Integer> entry : reportData.entrySet()) {
			statuses.add(entry.getKey());
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
		TreatmentStatus status = statuses.get(rowIndex);
		switch(columnIndex) {
			case 0: return status.getStatus().replace("_", " ");
			case 1: return reportData.get(status);
		}
		return null;
	}
}
