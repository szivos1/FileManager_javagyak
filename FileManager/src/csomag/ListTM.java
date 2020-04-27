package csomag;

import javax.swing.table.DefaultTableModel;

public class ListTM extends DefaultTableModel {

	public ListTM(Object fieldNames[], int rows) {
		super(fieldNames, rows);
	}
	
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {return true;}
		return false;
	}

	public Class<?> getColumnClass(int index){
		if (index == 0 || index == 2) {return(Boolean.class);}
		return(String.class);
	}
}
