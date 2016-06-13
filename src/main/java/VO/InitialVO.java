package VO;

import java.util.ArrayList;

import PO.AccountPO;
import PO.CustomerPO;
import PO.InitialPO;
import PO.CommodityPO;

public class InitialVO {
	String year;
	ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();
	ArrayList<CustomerVO> customerList = new ArrayList<CustomerVO>();
	ArrayList<AccountVO> accountList = new ArrayList<AccountVO>();

	public InitialVO(String year) {
		super();
		this.year = year;
	}
	public InitialVO(String i, ArrayList<CommodityPO> commodityList,
			ArrayList<CustomerVO> customerList, ArrayList<AccountVO> accountList) {
		super();
		this.year = i;
		this.commodityList = commodityList;
		this.customerList = customerList;
		this.accountList = accountList;
	}


	public String getYear() {
		return year;
	}
	public ArrayList<CommodityPO> getcommodityList() {
		return commodityList;
	}
	public ArrayList<CustomerVO> getCustomerList() {
		return customerList;
	}
	public ArrayList<AccountVO> getAccountList() {
		return accountList;
	}

	public InitialPO transform(InitialVO vo){
		if(vo == null)
			return null;

		ArrayList<CommodityPO> CommodityPOList = new ArrayList<CommodityPO>();
		ArrayList<CustomerPO> customerpoList = new ArrayList<CustomerPO>();
		ArrayList<AccountPO> accountpoList = new ArrayList<AccountPO>();

		for(AccountVO accvo:this.accountList){
			accountpoList.add(accvo.transform(accvo));
		}

		for(CustomerVO cusvo:this.customerList){
			customerpoList.add(cusvo.transform());
		}

		CommodityPOList = this.commodityList;

		return new InitialPO(this.year, CommodityPOList, customerpoList, accountpoList);
	}
}
