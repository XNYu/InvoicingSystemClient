package presentation.salesGUI;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CommodityModel2 extends AbstractTableModel {
	Vector columnNames,rows;
	public CommodityModel2(){
		columnNames= new Vector();
		columnNames.add("编号");
		columnNames.add("名称");
		columnNames.add("型号");
		columnNames.add("数量");
		columnNames.add("单价");
		columnNames.add("出厂日期");
		rows = new Vector();
	}

	void addRow(Vector v){
		 rows.add(v); 
	 }
	
	void removeRow(int r){
		 rows.remove(r); 
	 }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rows.size();
	}
	

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}

	public Class getColumnClass(int c) {  
		return getValueAt(0, c).getClass();  
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rows.get(row)).get(column);
	}
	
	 public void setValueAt(Object value, int row, int column) {  
		 ((Vector)this.rows.get(row)).set(column, value);
		 fireTableCellUpdated(row, column);  
		 
	 }  
	 
	 public boolean isCellEditable(int row, int col) {  
	        //Note that the data/cell address is constant,  
	        //no matter where the cell appears onscreen.  
	     if((col==2)||(col==1)||(col==0)) {
	    	 return false;
	     }
		 return true;  
	        
	 }  
}

