package VO;

import java.util.ArrayList;

import PO.CustomerPO;
import PO.TransferPO;

public class PaymentBillVO extends BillVO{
	public PaymentBillVO(String numberID, String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList,
			boolean isExamined,  boolean isRead,boolean isModified) {
		super(numberID, username, sum,isExamined,isRead,isModified);
		this.cus = cus;
		this.transferList = transferList;
	
	}
	
	
	public PaymentBillVO(String numberID, String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList) {
		// TODO 自动生成的构造函数存根
		super(numberID, username, sum);
		this.cus = cus;
		this.transferList = transferList;
		
	}


	CustomerPO cus = null;
	ArrayList<TransferPO> transferList = new ArrayList<TransferPO>();

	
	
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
