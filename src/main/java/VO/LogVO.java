package VO;

import PO.LogPO;

public class LogVO {
	String username;
	String time;
	String including;

	public LogVO(String username,String time,String including){
		this.username=username;
		this.time=time;
		this.including=including;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIncluding() {
		return including;
	}

	public void setIncluding(String including) {
		this.including = including;
	}

	public LogPO transform(){
		return new LogPO(this.username,this.time,this.including);
	}

}
