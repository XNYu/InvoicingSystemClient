package VO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import PO.CommodityPO;
import PO.PromotionPO;

public class PromotionVO implements Serializable{
	String ID;
	Date startTime;
	Date endTime;
	int rank;
	int totalPrice;
	public enum types{g,d,v,p};//g:赠品 d:折扣 v:代金券 p:特价包
	types type;
	ArrayList<CommodityPO> giftList;
	double discount;
	double voucher;
	double pdiscount;
	ArrayList<CommodityPO> packList;
	
	public PromotionVO(String i,int r,int p,types t,ArrayList<CommodityPO> g,double d,double v,ArrayList<CommodityPO> pa,double pd,Date st,Date et){
		ID=i;
		rank=r;
		totalPrice=p;
		type=t;
		giftList=g;
		discount=d;
		voucher=v;
		packList=pa;
		pdiscount=pd;
		startTime=st;
		endTime=et;
		
	}
	
	public void transform(PromotionPO po){
		setID(po.getID());
		setStartTime(po.getStartTime());
		setEndTime(po.getEndTime());
		setRank(po.getRank());
		setTotalPrice(po.getTotalPrice());
		setType(po.getType());
		setGiftList(po.getGiftList());
		setDiscount(po.getDiscount());
		setPackDiscount(po.getPackDiscount());
		setVoucher(po.getVoucher());
		setPackList(po.getpackList());
	}
	

	public double getPackDiscount(){
		return pdiscount;
	}
	
	public String getID() {
		return ID;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public types getType(){
		return type;
	}
	
	public ArrayList<CommodityPO> getGiftList(){
		return giftList;
	}
	
	public double getDiscount(){
		return discount;
	}
	
	public double getVoucher(){
		return voucher;
	}
	
	public ArrayList<CommodityPO> getpackList(){
		return packList;
	}
	
	public void setPackDiscount(double d){
		this.pdiscount=d;
	}
	
	public void setID(String ID){
		this.ID=ID;
	}
	
	public void setStartTime(Date date){
		this.startTime=date;
	}
	
	public void setEndTime(Date date){
		this.endTime=date;
	}
	
	public void setType(types t){
		this.type=t;
	}
	
	public void setRank(int rank){
		this.rank=rank;
	}
	
	public void setTotalPrice(int t){
		this.totalPrice=t;
	}
	public void setGiftList(ArrayList<CommodityPO> g){
		this.giftList=g;
	}
	public void setDiscount(double d){
		this.discount=d;
	}
	public void setVoucher(double v){
		this.voucher=v;
	}
	public void setPackList(ArrayList<CommodityPO> p){
		this.packList=p;
	}
	
	

}
