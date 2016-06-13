package presentation.financeGUI;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class InfoModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191228325896780187L;
	Vector<String> columnNames;
	Vector<Vector<String>> rows;
	
	public InfoModel(){
		
		columnNames=new Vector<String>();
		columnNames.add("单据ID");
		columnNames.add("类型");
		columnNames.add("操作员");
		columnNames.add("审查结果");
		
		rows=new Vector<Vector<String>>();
		
	}
	
	void addRow(Vector<String> v){
		 rows.add(v); 
		 
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {  
		return getValueAt(0, c).getClass();  
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rows.get(row)).get(column);
	}
	
	

}