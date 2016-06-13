package VO;

import PO.AccountPO;

public class AccountVO {
	private String name = null;
	private double money = 0.0;
	public String getName() {
		return name;
	}
	public double getMoney() {
		return money;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AccountVO(String name){
		this.name =  name;
	}
	public AccountVO(String name2, double money2) {
		// TODO 自动生成的构造函数存根
		this.name = name2;
		this.money = money2;
	}
	public AccountPO transform(AccountVO Vo){
		return new AccountPO(this.name,this.money);
	}
}
