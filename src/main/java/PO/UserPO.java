package PO;

import java.io.Serializable;


public class UserPO implements Serializable{
	
	public UserPO(String iD, String type,String name, String password, String rank) {
		super();
		this.name = name;
		this.userType=type;
		ID = iD;
		this.password = password;
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getType() {
		return userType;
	}
	public void setType(String type) {
		this.userType = type;
	}
	
	String name;
	String userType;
	String ID;
	String password;
	String rank;
}
