package VO;

import PO.CustomerPO;

public class CustomerVO {
	public CustomerVO(String iD, String type, int rank, String name,
			String phone, String address, String postcode, String email,double quota,double payment,double receiving,String salesman) {
		this.ID = iD;
		this.type = type;
		this.rank = rank;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.postcode = postcode;
		this.email = email;
		this.quota=quota;
		this.payment=payment;
		this.receiving=receiving;
		this.salesman=salesman;
	}
	public String getID() {
		return ID;
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
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getQuota() {
		return quota;
	}
	public void setQuota(double quota) {
		this.quota = quota;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getReceiving() {
		return receiving;
	}
	public void setReceiving(double receiving) {
		this.receiving = receiving;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public CustomerPO transform(){
		CustomerPO po=new CustomerPO(getID(), getType(), getRank(), getName(),
				getPhone(), getAddress(), getPostcode(), getEmail(),getQuota(),getPayment(),getReceiving(),getSalesman());
		return po;
	}
	String ID;
	String type;
	int rank;
	String name;
	String phone;
	String address;
	String postcode;
	String email;
	double quota;//应收额度
	double payment;
	double receiving;
	String salesman;
	
}
