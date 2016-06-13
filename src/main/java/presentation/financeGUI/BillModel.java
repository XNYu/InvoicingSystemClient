package presentation.financeGUI;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import VO.TransferVO;
import businesslogic.accountbl.AccountController;
import businesslogic.accountbl.AccountInterfaceForBill;
import businesslogic.billbl.TransferList;
import businesslogic.utilitybl.CheckDouble;
import businesslogicservice.billBLService.BillBLService;

public class BillModel extends AbstractTableModel{
	Vector columnNames,rows;
	JLabel sumlabel;
	TransferList transferlist1;
	BillBLService bill = null;
	boolean isModified  = false;
	public BillModel(JLabel sumtext, TransferList transferlist, BillBLService bill, boolean isModified){
		this.sumlabel = sumtext;
		this.transferlist1 =transferlist;

		this.bill = bill;
		this.isModified = isModified;
		columnNames=new Vector();
		columnNames.add("账户");
		columnNames.add("金额");
		columnNames.add("备注");

		rows=new Vector();

		for(TransferVO trans:transferlist1.getlist()){
			Vector v=new Vector();
			v.add(trans.acc.getName());
			v.add(Double.toString(trans.getMoney()));
			v.add(trans.getNotes());
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

				 transferlist1.removeTransfer(r);
				 sumlabel .setText(Double.toString(transferlist1.total()));
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
				 if(bill.getAccount((String) getValueAt(getRowCount()-1, 0))==null){
					 new InfoWindows("请输入有效账户，否则无效");
				 }
				 else if(!CheckDouble.isAllDouble((String) getValueAt(getRowCount()-1, 1))||((String) getValueAt(getRowCount()-1, 1)).contains("-")){

					 new InfoWindows("请输入正确数字，否则无效");
				 }

				 else{
					 transferlist1.addTransfer(new TransferVO(bill.getAccount((String)getValueAt(getRowCount()-1, 0)),
			        			Double.parseDouble((String)getValueAt(getRowCount()-1, 1)),(String)getValueAt(getRowCount()-1, 2)));
					 sumlabel .setText(Double.toString(transferlist1.total()));

					 Vector<String> v=new Vector<String>();
			    		v.add("");
			    		v.add("");
			    		v.add("");
			    		addRow(v);
				 }

		       }
		 }

	 }
	 public TransferList gettransferlist(){

		 return transferlist1;

	 }

	 public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.

		 return true;

	 }

}