package VO;


public class UserVO {
	
	public UserVO(String iD, String usertype,String name,String password, String rank) {
		super();
		this.name = name;
		this.usertype=usertype;
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
		return usertype;
	}
	public void setType(String usertype) {
		this.usertype = usertype;
	}
	
	String name;
	String usertype;
	String ID;
	String password;
	String rank;
}
