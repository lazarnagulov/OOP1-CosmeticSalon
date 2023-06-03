package com.nagulov.ui.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.controllers.TreatmentController;
import com.nagulov.data.DataBase;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;

public class TimeTableModel extends AbstractTableModel{
private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"Service", "Treatment", "Date", "Start time", "End time"};
	private static List<Treatment> treatments = new ArrayList<Treatment>(); 
	
	public static void init(Beautician b) {
		treatments.clear();
		for(Map.Entry<Integer, Treatment> entry : TreatmentController.getInstance().getTreatments().entrySet()) {
			Treatment t = entry.getValue();
			if(t.getStatus().equals(TreatmentStatus.SCHEDULED) && t.getBeautician().equals(b)) {
				treatments.add(t);
			}
		}
	}
	
	@Override
	public int getRowCount() {
		return treatments.size();
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Treatment t = treatments.get(rowIndex);
		LocalDateTime endTime = t.getDate().plusHours(t.getTreatment().getDuration().getHour()).plusMinutes(t.getTreatment().getDuration().getMinute());
		switch(columnIndex) {
			case 0: return t.getService().getName();
			case 1: return t.getTreatment().getName();
			case 2: return t.getDate().format(DataBase.DATE_FORMAT);
			case 3: return t.getDate().format(DataBase.TIME_FORMAT);
			case 4: return endTime.format(DataBase.TIME_FORMAT);
		}
		return null;
	}

}
