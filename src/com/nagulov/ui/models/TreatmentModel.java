package com.nagulov.ui.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;
import com.nagulov.users.Receptionist;

public class TreatmentModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private String[] columnNames = DataBase.TREATMENT_HEADER.split(",");
	private static List<Treatment> treatments = new ArrayList<Treatment>();
	
	public static void init() {
		treatments.clear();
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			if(!UserController.getInstance().getUsers().containsKey(entry.getValue().getClient().getUsername())) {
				TreatmentController.getInstance().removeTreatment(entry.getKey());
				continue;
			}
			if(!UserController.getInstance().getUsers().containsKey(entry.getValue().getBeautician().getUsername())) {
				TreatmentController.getInstance().removeTreatment(entry.getKey());
				continue;
			}
			if(!treatments.contains(entry.getValue())) {
				if(DataBase.loggedUser instanceof Receptionist && !entry.getValue().getStatus().equals(TreatmentStatus.SCHEDULED)) {
					continue;
				}
				treatments.add(entry.getValue());
			}
		}
	}
	
	public static void init(Beautician b) {
		treatments.clear();
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			if(!entry.getValue().getBeautician().equals(b)) {
				continue;
			}
			if(!treatments.contains(entry.getValue())) {
				treatments.add(entry.getValue());
			}
		}
	}
	
	public static void init(Client c) {
		treatments.clear();
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			if(!entry.getValue().getClient().equals(c)) {
				continue;
			}
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
				case 0: return t.getStatus();
				case 1: return t.getService();
				case 2: return t.getTreatment().getName();
				case 3: return t.getBeautician().getUsername();
				case 4: return t.getDate().format(DataBase.TREATMENTS_DATE_FORMAT);
				case 5: return t.getClient().getUsername();
				case 6: return t.getIncome();
				default: return null; 
			}
		}catch(NullPointerException e) {
			return null;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex){
		if(columnIndex == 6) {
			return Double.class;
		}else if(columnIndex == 4) {
			return LocalDateTime.class;
		}
		else {
			return String.class;
		}
	}
}
