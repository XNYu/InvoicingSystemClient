package PO;

import java.io.Serializable;
import java.util.Calendar;

import businesslogic.utilitybl.DateHelper;
public class StockPO implements Serializable {
	private static final long serialVersionUID = 1L;
	//format 20141031  =2014 10 31
	String operateType = "DEFAULT";//STOCKIN,STOCKOUT,EXP,IMP
	String name = "" , type = "", id = "" ;
	String batch,batchID,leaveDate;
	boolean isExamined = false;
	Infomation Info = new Infomation() ;
	
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	class Infomation implements Serializable{
		private static final long serialVersionUID = 1L;
		private int amount = 0;
		private double price = 0.0;
		
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		
	}
	public String getInfo(){
		return " ";
	}
	public StockPO(String name,String type,String id,String operateType,
			String leaveDate,String batchID,int amount,double price){
		DateHelper dh = new DateHelper();
		this.name = name;
		this.type = type;
		this.id = id;
		this.operateType = operateType;
		this.leaveDate = leaveDate;
		this.batch = dh.getDate();
		this.batchID = batchID;
		this.Info.setAmount(amount);
		this.Info.setPrice(price);
			
	}
	public boolean getIsExamined(){
		return isExamined;
	}
	public boolean isExamined() {
		return isExamined;
	}

	public void setExamined(boolean isExamined) {
		this.isExamined = isExamined;
	}

	public double getPrice(){
		return Info.getPrice();
	}
	public int getAmount(){
		return Info.getAmount();
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
}
