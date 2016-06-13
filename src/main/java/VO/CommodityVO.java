package VO;

import java.io.Serializable;



public class CommodityVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String ID = "000000000000",
	        name = null,
	        type = null,
	        leaveDate = "",
	        commodityType = null;
	private int amount = 0;
	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	private double impPrice =0,
	               expPrice =0,
	               recentImpPrice =-1,
	               recentExpPrice =-1;
	
	
	public CommodityVO(String name,String type, int amount, double impPrice,double expPrice,
			String comType){
		this.type = type;
		this.name = name;
		this.amount = amount;
		this.impPrice = impPrice;
		this.expPrice = expPrice;
		this.recentExpPrice = expPrice;
		this.recentImpPrice = impPrice;
		this.commodityType = comType;
		
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public void setImpPrice(double impPrice) {
		this.impPrice = impPrice;
	}

	public double getImpPrice() {
		return impPrice;
	}
	public double getExpPrice() {
		return expPrice;
	}
	public void setExpPrice(double expPrice) {
		this.expPrice = expPrice;
	}
	public double getRecentImpPrice() {
		return recentImpPrice;
	}
	public void setRecentImpPrice(double recentImpPrice) {
		this.recentImpPrice = recentImpPrice;
	}
	public double getRecentExpPrice() {
		return recentExpPrice;
	}
	public void setRecentExpPrice(double recentExpPrice) {
		this.recentExpPrice = recentExpPrice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
