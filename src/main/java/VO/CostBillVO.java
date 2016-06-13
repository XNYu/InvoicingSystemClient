package VO;

import java.util.ArrayList;

import PO.AccountPO;
import PO.EntryPO;

public class CostBillVO extends BillVO {
	public CostBillVO(String numberID, String username, double sum,
			AccountPO acc, ArrayList<EntryPO> entryList, boolean isExamined, boolean isRead, boolean isModified) {
		super(numberID, username, sum,isExamined,isRead,isModified);
		this.acc = acc;
		this.entryList = entryList;
		
	}
	
	
	public CostBillVO(String numberID, String username, double sum,
			AccountPO acc, ArrayList<EntryPO> entryList) {
		// TODO 自动生成的构造函数存根
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
