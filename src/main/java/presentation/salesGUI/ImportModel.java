package presentation.salesGUI;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import VO.PromotionVO;


public class ImportModel extends AbstractTableModel{
Vector columnNames,rows;
	
	public ImportModel(){
		columnNames=new Vector();
		columnNames.add("ID");	
		columnNames.add("客户");
		columnNames.add("仓库");
		columnNames.add("操作员");
		columnNames.add("入库商品");
		columnNames.add("总额");
		columnNames.add("备注");
		columnNames.add("审批状态");
		rows=new Vector();
		
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
	     if(col==4)
	    	 return true;
	   
	     return false;
	        
	 }  

}
