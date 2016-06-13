package presentation.stockGUI.document;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DocReportTableModel extends AbstractTableModel implements Serializable {
	private static final long serialVersionUID = 1L;
	Vector columnNames,rows;
	public DocReportTableModel(){
		columnNames= new Vector();
		columnNames.add("单据类型");
		columnNames.add("商品名称");
		columnNames.add("型号");
		columnNames.add("ID");
		columnNames.add("设置警戒数量");
		columnNames.add("创建日期");
		columnNames.add("审批状态");
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
//	     if((col==2)||(col==1)) {
//	    	 return true;
//	     }   
		 return false;  
	        
	 }  
}


