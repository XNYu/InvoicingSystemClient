package PO;

import java.io.Serializable;

public class TransferPO implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public TransferPO(AccountPO acc, double money, String notes) {
		super();
		this.acc = acc;
		this.money = money;
		this.notes = notes;
	}
	public AccountPO acc = null;
	public AccountPO getAcc() {
		return acc;
	}
	public double getMoney() {
		return money;
	}
	public String getNotes() {
		return notes;
	}
	double money = 0.0;
	String notes = null;
}
