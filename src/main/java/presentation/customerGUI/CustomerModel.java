package presentation.customerGUI;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class CustomerModel extends AbstractTableModel{
	Vector columnNames,rows;
	String rank;
	public CustomerModel(String rank){
		this.rank=rank;
		columnNames=new Vector();
		columnNames.add("ID");	
		columnNames.add("类型");
		columnNames.add("等级");
		columnNames.add("姓名");
		columnNames.add("电话");
		columnNames.add("地址");
		columnNames.add("邮编");
		columnNames.add("e-mail");
		columnNames.add("应收额度");
		columnNames.add("应付");
		columnNames.add("应收");
		columnNames.add("默认业务员");
		rows=new Vector();
	}
	
	public void addRow(Vector v){
		 rows.add(v); 
	 }
	
	public void removeRow(int r){
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
	     if(col==0||col==9||col==10){
	    	 return false;
	     }
	     if(rank.equals("普通权限")&&col==8){
	    	 return false;
	     }
		 return true;  
	        
	 }  

}


