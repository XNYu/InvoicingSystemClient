
package PO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import PO.CommodityPO;
import VO.PromotionVO;
import VO.PromotionVO.types;

public class PromotionPO implements Serializable{
	String ID;
	Date startTime;
	Date endTime;
	int rank;
	int totalPrice;
	types type;
	ArrayList<CommodityPO> giftList;
	double discount;
	double voucher;
	double pdiscount;//特价包折扣
	ArrayList<CommodityPO> packList;
	
	
	public PromotionPO(String i,int r,int p,types t,ArrayList<CommodityPO> g,double d,double v,ArrayList<CommodityPO> pa,double pd,Date st,Date et){
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
	
	public PromotionPO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getID() {
		return ID;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public Date getEndTime(){
		return endTime;
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getTotalPrice() {
		return totalPrice;
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
	
	public double getPackDiscount(){
		return pdiscount;
	}
	
	public double getVoucher(){
		return voucher;
	}
	
	public ArrayList<CommodityPO> getpackList(){
		return packList;
	}
	
	
	public void transform(PromotionVO vo){
		setID(vo.getID());
		setStartTime(vo.getStartTime());
		setEndTime(vo.getEndTime());
		setRank(vo.getRank());
		setTotalPrice(vo.getTotalPrice());
		setType(vo.getType());
		setGiftList(vo.getGiftList());
		setDiscount(vo.getDiscount());
		setPackDiscount(vo.getPackDiscount());
		setVoucher(vo.getVoucher());
		setPackList(vo.getpackList());
	}
	
	public void setID(String ID){
		this.ID=ID;
	}
	
	public void setPackDiscount(double d){
		this.pdiscount=d;
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
