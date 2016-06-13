package PO;

import java.io.Serializable;
import java.util.ArrayList;

public class PaymentBillPO extends BillPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public PaymentBillPO(String numberID, String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList,
			boolean isExamined,  boolean isRead,boolean isModified) {
		super(numberID, username, sum,isExamined,isRead,isModified);
		this.cus = cus;
		this.transferList = transferList;
		
	}
	
	public PaymentBillPO(String numberID, String username, double sum,
			CustomerPO cus, ArrayList<TransferPO> transferList) {
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
