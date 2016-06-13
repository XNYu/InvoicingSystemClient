package VO;

public class EntryVO {
	public EntryVO(String entry, double money, String notes) {
		super();
		this.entry = entry;
		this.money = money;
		this.notes = notes;
	}
	String entry = null;
	public double money = 0.0;
	String notes = null;
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
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
}
