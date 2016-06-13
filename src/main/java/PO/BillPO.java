package PO;

import java.io.Serializable;

public class BillPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public BillPO(String numberID, String username, double sum, boolean isExamined, boolean isRead, boolean isModified) {
		super();
		this.numberID = numberID;
		this.username = username;
		this.sum = sum;
		this.isExamined = isExamined;
		this.isRead = isRead;
		this.isModified = isModified;
	}
	
	
	public BillPO(String numberID, String username, double sum) {
		super();
		this.numberID = numberID;
		this.username = username;
		this.sum = sum;
		this.isExamined = false;
		this. isRead = false;
		this.isModified = false;
	}

	
	String numberID = null;
	String username  = null;
	double sum = 0.0;
	boolean isExamined = false;
	boolean isRead = false;
	boolean isModified = false;

	public boolean isExamined() {
		return isExamined;
	}


	public void setExamined(boolean isExamined) {
		this.isExamined = isExamined;
	}


	public boolean isRead() {
		return isRead;
	}


	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}


	public boolean isModified() {
		return isModified;
	}


	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}


	
	
	
	public String getNumberID() {
		return numberID;
	}
	public void setNumberID(String numberID) {
		this.numberID = numberID;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
}
