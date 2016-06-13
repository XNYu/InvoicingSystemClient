package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.AccountVO;
import VO.CustomerVO;
import VO.InitialVO;

public class InitialPO implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String year;
	ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();
	ArrayList<CustomerPO> customerList = new ArrayList<CustomerPO>();
	ArrayList<AccountPO> accountList = new ArrayList<AccountPO>();

	public InitialPO(String year) {
		super();
		this.year = year;
	}
	public InitialPO(String year, ArrayList<CommodityPO> commodityList,
			ArrayList<CustomerPO> customerList, ArrayList<AccountPO> accountList) {
		super();
		this.year = year;
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
	public ArrayList<CustomerPO> getCustomerList() {
		return customerList;
	}
	public ArrayList<AccountPO> getAccountList() {
		return accountList;
	}

	public InitialVO transform(InitialPO po){
		if(po == null)
			return null;

		ArrayList<CustomerVO> customervoList = new ArrayList<CustomerVO>();
		ArrayList<AccountVO> accountvoList = new ArrayList<AccountVO>();

		for(AccountPO accpo:this.accountList){
			accountvoList.add(accpo.transform(accpo));
		}
		for(CustomerPO cuspo:this.customerList){
			customervoList.add(cuspo.transform());
		}

		return new InitialVO(this.year, commodityList, customervoList, accountvoList);
	}

}
