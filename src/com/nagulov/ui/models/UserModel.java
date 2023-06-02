package com.nagulov.ui.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.nagulov.controllers.UserController;
import com.nagulov.data.DataBase;
import com.nagulov.users.User;

public class UserModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnNames = DataBase.USER_HEADER.split(",");
	private static List<User> users = new ArrayList<User>();
	
	public static void init() {
		if(users.isEmpty()) {
			for(Map.Entry<String, User> entry : UserController.getInstance().getUsers().entrySet()) {
				users.add(entry.getValue());
			}
		}
	}
	
	public static void removeUser(int row) {
		users.remove(row);
	}
	
	public static void removeUser(User user) {
		users.remove(user);
	}
	
	public static void addUser(User user) {
		if(users.contains(user)) {
			users.remove(user);
		}
		users.add(user);
	}
	
	public static boolean isEmpty() {
		return users.isEmpty();
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public int getRowCount() {
		return UserController.getInstance().getUsers().size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User u = users.get(rowIndex);
		switch (columnIndex) {
			case 0: return u.getClass().getSimpleName();
			case 1: return u.getUsername();
			case 2: return u.getPassword();
			case 3: return u.getName();
			case 4: return u.getSurname();
			case 5: return u.getGender();
			case 6: return u.getPhoneNumber();
			case 7: return u.getAddress();
		}
		return null;
	}

}
