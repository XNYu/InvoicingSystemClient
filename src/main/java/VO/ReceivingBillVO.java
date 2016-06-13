package VO;

import java.util.ArrayList;

import PO.CustomerPO;
import PO.TransferPO;

public class ReceivingBillVO extends BillVO{
	public ReceivingBillVO(String numberID,String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList,
			boolean isExamined,boolean isRead ,boolean isModified) {
		super(numberID, username, sum,isExamined,isRead,isModified);
		this.cus = cus;
		this.transferList = transferList;
		
	}
	public ReceivingBillVO(String numberID,String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList) {
		super(numberID, username, sum);
		this.cus = cus;
		this.transferList = transferList;
	}
	
	
	CustomerPO cus = null;
	ArrayList<TransferPO> transferList = new ArrayList<TransferPO>();
	boolean isExamined = false;
	boolean isRead = false;
	boolean isModified = false;
	
	
	
	public CustomerPO getCus() {
		return cus;
	}
	public void setCus(CustomerPO cus) {
		this.cus = cus;
	}
	public ArrayList<TransferPO> getTransferList() {
		return transferList;
	}
	public void setTransferList(ArrayList<TransferPO> transferList) {
		this.transferList = transferList;
	}
	
}
