package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.CosmeticTreatment;
import com.nagulov.treatments.Pricelist;

public class ServiceModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;
	protected String[] columnNames = DataBase.SERVICE_HEADER.split(",");
	private static List<String> services = new ArrayList<String>();
	
	public static void init() {
		StringBuilder sb = new StringBuilder();
		if(services.isEmpty()) {
			for(Map.Entry<String, CosmeticService> service : DataBase.services.entrySet()) {
				sb.append(service.getKey()).append(",");
				for(int i = 0; i < service.getValue().getTreatments().size(); ++i) {
					sb.append(service.getValue().getTreatments().get(i).getName()).append(",")
						.append(service.getValue().getTreatments().get(i).getDuration()).append(",")
						.append(Pricelist.getInstance().getPrice(service.getValue().getTreatments().get(i)));
					services.add(sb.toString());
					sb.delete(0, sb.length());
					sb.append(service.getKey()).append(",");
				}
				sb.delete(0, sb.length());
			}
		}
	}
	
	public static void addService(CosmeticService service) {
//		StringBuilder sb = new StringBuilder();
//		if(services.isEmpty()) {
//			sb.append(service).append(",");
//			for(int i = 0; i < service.getTreatments().size(); ++i) {
//				sb.append(service.getTreatments().get(i).getName()).append(",")
//					.append(service.getTreatments().get(i).getDuration()).append(",")
//					.append(Pricelist.getInstance().getPrice(service.getTreatments().get(i)));
//				services.add(sb.toString());
//				sb.delete(0, sb.length());
//				sb.append(service).append(",");
//			}
//		}
//		services.add(sb.toString());
	}
	
	public static void addTreatment(CosmeticTreatment treatment) {
		CosmeticService cs = DataBase.cosmeticTreatments.get(treatment);
		services.add(new StringBuilder(cs.getName()).append(",")
						.append(treatment.getName()).append(",")
						.append(treatment.getDuration()).append(",")
						.append(Pricelist.getInstance().getPrice(treatment))
						.toString()
					);
	}
	
	public static void removeService(int row) {
		services.remove(row);
	}
	
	public static void updateService(int row, String service, String treatment, String duration, String price) {
		services.set(row, service + "," + treatment + "," + duration + "," + price);
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
