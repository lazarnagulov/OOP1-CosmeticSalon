package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;

public class ServiceModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	protected String[] columnNames = DataBase.SERVICE_HEADER.split(",");
	private static List<String> services = new ArrayList<String>();
	
	public static void init() {
		if(services.isEmpty()) {
			for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
				for(Map.Entry<String, Double> treatment : service.getValue().getTreatments().entrySet()) {
					services.add(service.getKey() + "," + treatment.getKey() + "," +  treatment.getValue());
				}
				
			}
		}
	}
	
	public static void addService(String service) {
		services.add(service);
	}
	
	public static void removeService(int row) {
		services.remove(row);
	}
	
	public static void updateService(int row, String service, String treatment, String price) {
		services.set(row, service + "," + treatment + "," + price);
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return services.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String[] service = services.get(rowIndex).split(",");
		return service[columnIndex];
	}

}
