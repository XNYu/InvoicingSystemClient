package VO;

import PO.AccountPO;

public class TransferVO {
	public AccountPO acc = null;
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	private double money = 0.0;
	private String notes = null;
	public TransferVO(AccountPO acc, double money, String notes) {
		super();
		this.acc = acc;
		this.money = money;
		this.notes = notes;
	}
	
	
	
}
