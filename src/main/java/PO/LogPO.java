package PO;

import java.io.Serializable;

import VO.LogVO;

public class LogPO implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String time;
	String including;

	public LogPO(String username,String time,String including){
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

	public LogVO transform(){
		return new LogVO(this.username,this.time,this.including);
	}

}
