package presentation.promotionGUI;
import java.util.Vector;
import javax.swing.table.*;

public class discountModel extends AbstractTableModel{
	Vector columnNames,rows;
	
	public discountModel(){
		columnNames=new Vector();
		columnNames.add("ID");
		columnNames.add("折扣值");
		columnNames.add("客户等级");
		columnNames.add("起始时间");
		columnNames.add("终止时间");
		//f
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
	     if(col==0){
	    	 return false;
	     }   
		 return true;  
	        
	 }  

}
