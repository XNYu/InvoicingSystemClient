package presentation.financeGUI;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import VO.EntryVO;
import VO.TransferVO;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.EntryList;
import businesslogic.utilitybl.CheckDouble;
import businesslogicservice.billBLService.BillBLService;

public class CostModel extends AbstractTableModel{
	Vector columnNames,rows;
	JLabel sumlabel;
	EntryList entrylist = null;
	BillBLService bill = null;
	boolean isModified = false;
	public CostModel(JLabel sumtext, EntryList entrylist, BillBLService bill, boolean isModified){
		this.sumlabel = sumtext;
		this.entrylist =entrylist;
		this.bill = bill;
		this.isModified = isModified;
		columnNames=new Vector();
		columnNames.add("条目");
		columnNames.add("金额");
		columnNames.add("备注");

		rows=new Vector();

		for(EntryVO en:entrylist.getlist()){
			Vector v=new Vector();
			v.add(en.getEntry());
			v.add(Double.toString(en.getMoney()));
			v.add(en.getNotes());
			addRow(v);
		}

		if(isModified){
		Vector v=new Vector();
		v.add("");
		v.add("");
		v.add("");
		addRow(v);
		}
	}

	void addRow(Vector v){
		 rows.add(v);

	 }

	void removeRow(int r){
		if(isModified){
			if(!((String)(((Vector)rows.get(r)).get(0))).equals("")&&!((String)(((Vector)rows.get(r)).get(1))).equals("")&&!((String)(((Vector)rows.get(r)).get(2))).equals("")){

				 entrylist.removeEntry(r);
				 sumlabel .setText(Double.toString(entrylist.total()));
				 rows.remove(r);
			}
		}

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
		 if(isModified){
			 ((Vector)this.rows.get(row)).set(column, value);
			 fireTableCellUpdated(row, column);

			 if(!getValueAt(getRowCount()-1, 0).equals("")&&!getValueAt(getRowCount()-1, 1).equals("")&&!getValueAt(getRowCount()-1, 2).equals("")){
				 if(!CheckDouble.isAllDouble((String) getValueAt(getRowCount()-1, 1))||((String) getValueAt(getRowCount()-1, 1)).contains("-")){
					 new InfoWindows("请输入正确数字，否则无效");
				 }

				 else{
					 entrylist.addEntry(new EntryVO(((String)getValueAt(getRowCount()-1, 0)),
			        			Double.parseDouble((String)getValueAt(getRowCount()-1, 1)),(String)getValueAt(getRowCount()-1, 2)));
					 sumlabel .setText(Double.toString(entrylist.total()));
					 Vector<String> v=new Vector<String>();
			    		v.add("");
			    		v.add("");
			    		v.add("");
			    		addRow(v);
				 }
		       }
		 }
	 }

	 public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.

		 return true;

	 }

}