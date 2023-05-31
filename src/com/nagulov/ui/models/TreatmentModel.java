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
			if(!treatments.contains(entry.getValue())) {
				treatments.add(entry.getValue());
			}
		}
	}
	
	public static void addTreatment(Treatment treatment) {
		if(treatments.contains(treatment)) {
			treatments.remove(treatment);
		}
		treatments.add(treatment);
	}
	
	public static void removeTreatment(Treatment treatment) {
		treatments.remove(treatment);
	}
	
	public static void removeTreatment(int row) {
		treatments.remove(row);
	}
	
	public static Treatment getTreatment(int row) {
		return treatments.get(row);
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
		try {
			switch(columnIndex) {
				case 0 -> {return t.getStatus();}
				case 1 -> {return t.getService();} 
				case 2 -> {return t.getTreatment().getName();}
				case 3 -> {return t.getBeautician().getUsername();}
				case 4 -> {return t.getDate().format(DataBase.TREATMENTS_DATE_FORMAT);}
				case 5 -> {return t.getClient().getUsername();}
				case 6 -> {return t.getPrice();}
				default -> {return null; }
			}
		}catch(NullPointerException e) {
			return "Deleted";
		}
	}
}
