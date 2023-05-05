package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.Treatment;

public class TreatmentModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	protected String[] columnNames = DataBase.TREATMENT_HEADER.split(",");
	private static List<Treatment> treatments = new ArrayList<Treatment>();
	
	public static void init() {
		for(Map.Entry<Integer, Treatment> entry : DataBase.treatments.entrySet()) {
			treatments.add(entry.getValue());
		}
	}
	
	public static void addTreatment(Treatment treatment) {
		treatments.add(treatment);
	}
	
	
	public static void removeTreatment(int row) {
		treatments.remove(row);
	}
	
	public static void updateTreatment(int row, Treatment treatment) {
		treatments.set(row, treatment);
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return treatments.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Treatment t = treatments.get(rowIndex);
		// "Id,Status,Service,Treatment,Beautician,Date,Client\n";
		switch(columnIndex) {
			case 0 -> {return t.getId(); }
			case 1 -> {return t.getStatus();}
			case 2 -> {return t.getService();} 
			case 3 -> {return t.getTreatment().getName();}
			case 4 -> {return t.getBeautician().getUsername();}
			case 5 -> {return t.getDate();}
			case 6 -> {return t.getClient().getUsername();}
			default -> {return null; }
		}
	}
}
