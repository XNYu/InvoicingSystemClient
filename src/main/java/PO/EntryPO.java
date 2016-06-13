package PO;

import java.io.Serializable;

public class EntryPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntryPO(String entry, double money, String notes) {
		super();
		this.entry = entry;
		this.money = money;
		this.notes = notes;
	}
	String entry = null;
	double money = 0.0;
	String notes = null;
	public String getEntry() {
		return entry;
	}
	public double getMoney() {
		return money;
	}
	public String getNotes() {
		return notes;
	}
}
