package PO;

public class ExaminePO {
	String ID;
	String type;
	boolean confirmed;
	
	public ExaminePO(String iD,String type,boolean confirmed){
		ID=iD;
		this.type=type;
		this.confirmed=confirmed;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getType(){
		return type;
	}
	
	public boolean ifConfirmed(){
		return confirmed;
	}
}