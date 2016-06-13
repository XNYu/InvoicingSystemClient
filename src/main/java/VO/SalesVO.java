package VO;

import java.util.ArrayList;
import java.util.Date;




import PO.CommodityPO;
import PO.CustomerPO;

public class SalesVO {
	ArrayList<PromotionVO> promotionlist;
	String ID;
	String type;
	String customer;
	String stock;
	String operator;
	String salesman;
	ArrayList<CommodityPO> commodityList;
	double sum;
	double discount;
	double afterDiscount;
	double voucher;
	String document;
	String examine;
	public boolean th=true;
	public SalesVO(String iD, String type, String customer, String stock,
			String operator,String salesman,ArrayList<CommodityPO> commodityList, double sum,
			double discount, double afterDiscount,
			double voucher, String document,ArrayList<PromotionVO> promotionlist,String examine) {
		super();
		this.promotionlist=promotionlist;
		ID = iD;
		this.type = type;
		this.customer = customer;
		this.stock = stock;
		this.operator = operator;
		this.commodityList = commodityList;
		this.sum = sum;
		this.discount = discount;
		this.afterDiscount = afterDiscount;
		this.voucher = voucher;
		this.document = document;
		this.salesman=salesman;
		this.examine=examine;
	}
	
	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
	}

	public String getID() {
		return ID;
	}
	
	
	public ArrayList<PromotionVO> getPromotionlist() {
		return promotionlist;
	}

	public void setPromotionlist(ArrayList<PromotionVO> promotionlist) {
		this.promotionlist = promotionlist;
	}

	public boolean isTh() {
		return th;
	}

	public void setTh(boolean th) {
		this.th = th;
	}

	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public ArrayList<CommodityPO> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(ArrayList<CommodityPO> commodityList) {
		this.commodityList = commodityList;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getAfterDiscount() {
		return afterDiscount;
	}
	public void setAfterDiscount(double afterDiscount) {
		this.afterDiscount = afterDiscount;
	}
	public double getVoucher() {
		return voucher;
	}
	public void setVoucher(double voucher) {
		this.voucher = voucher;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	
	
	
}	
