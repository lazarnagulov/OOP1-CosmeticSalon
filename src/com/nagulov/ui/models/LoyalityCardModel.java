package com.nagulov.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.nagulov.reports.Report;
import com.nagulov.users.Client;

public class LoyalityCardModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"Client", "Total Spent"};
	private static List<Client> clients; 
	
	public static void init() {
		clients = Report.calculateLoyalityReport();
	}
	
	@Override
	public int getRowCount() {
		return clients.size();
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
		Client c = clients.get(rowIndex);
		switch(columnIndex) {
			case 0: return c.getUsername();
			case 1: return c.getSpent();
		}
		return null;
	}

}
