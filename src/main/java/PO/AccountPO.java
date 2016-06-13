package PO;

import java.io.Serializable;

import VO.AccountVO;

public class AccountPO implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String name = null;
	double money = 0.0;
	public AccountPO(String name){
		this.name = name;
	}
	public AccountPO(String name2, double money2) {
		// TODO 自动生成的构造函数存根
		this.name = name2;
		this.money = money2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}

	public AccountVO transform(AccountPO po){
		return new AccountVO(this.name,this.money);
	}

}
