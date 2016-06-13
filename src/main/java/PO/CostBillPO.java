package PO;

import java.io.Serializable;
import java.util.ArrayList;

public class CostBillPO extends BillPO implements Serializable{
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CostBillPO(String numberID, String username, double sum,
			AccountPO acc, ArrayList<EntryPO> entryList, boolean isExamined, boolean isRead, boolean isModified) {
		super(numberID, username, sum,isExamined,isRead,isModified);
		this.acc = acc;
		this.entryList = entryList;
		
	}
	
	public CostBillPO(String numberID, String username, double sum,
			AccountPO acc, ArrayList<EntryPO> entryList) {
		super(numberID, username, sum);
		this.acc = acc;
		this.entryList = entryList;
		
	}
	
	AccountPO acc = null;
	ArrayList<EntryPO> entryList = new ArrayList<EntryPO>();
	
	
	public AccountPO getAcc() {
		return acc;
	}
	public void setAcc(AccountPO acc) {
		this.acc = acc;
	}
	public ArrayList<EntryPO> getEntryList() {
		return entryList;
	}
	public void setEntryList(ArrayList<EntryPO> entryList) {
		this.entryList = entryList;
	}
	
}
