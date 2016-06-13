package businesslogic.billbl;

import java.util.ArrayList;

import PO.AccountPO;
import PO.CustomerPO;
import VO.AccountVO;
import VO.BillVO;
import VO.CustomerVO;
import VO.UserVO;
import businesslogic.accountbl.Account;
import businesslogic.accountbl.AccountInterfaceForBill;
import businesslogic.accountbl.AccountInterfaceOfChangeMoney;
import businesslogic.customerbl.Customer;
import businesslogic.customerbl.CustomerInterfaceForBill;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.billBLService.BillBLService;

public class BillController implements BillBLService {
	private UserVO user = null;
	private CustomerInterfaceForBill customerInterfaceForBill = null;
	private AccountInterfaceForBill accountInterfaceForBill = null;
	private AccountInterfaceOfChangeMoney accountInterfaceOfChangeMoney = null;
	private	Bill bill = null;
	private LogInterface loginterface = new Log();


	public BillController(UserVO user1){
		this.user = user1;
		customerInterfaceForBill = new Customer();//need user
		accountInterfaceForBill = new Account(user);
		accountInterfaceOfChangeMoney = new Account(user);
		bill = new Bill(user);
	}


	public ResultMessage createbill(BillVO billvo) {
		// TODO 自动生成的方法存根
		return bill.createBill(billvo);
	}

	public void endBillBuilding() {
		// TODO 自动生成的方法存根
		bill.endBillBuilding();
	}

	@Override
	public ArrayList<BillVO> getBillExamined() {
		// TODO 自动生成的方法存根
		return bill.getBillExamined();
	}


	public CustomerPO getCustomer(String name){
		return customerInterfaceForBill.getCustomerPO(name);
	}

	public AccountPO getAccount(String name){
		return accountInterfaceForBill.getAccountPO(name);
	}

	public ArrayList<CustomerVO> showCustomer(){
		return customerInterfaceForBill.showCustomer();
	}

	public ArrayList<AccountVO> showAccount(){
		return accountInterfaceForBill.showAccount();
	}

	public ResultMessage changeMoney (String name,double money){
		loginterface.buildlog(user.getName(), "修改账户金额："+name+" "+money);
		return accountInterfaceOfChangeMoney.changeMoney(name, money);
	}

	public ResultMessage changePay (String name,double pay){
		loginterface.buildlog(user.getName(), "修改客户应付："+name+" "+pay);
		return customerInterfaceForBill.changePay(name, pay);
	}

	public ResultMessage changeReceive (String name,double receive){
		loginterface.buildlog(user.getName(), "修改客户应收："+name+" "+receive);
		return customerInterfaceForBill.changeReceive(name, receive);
	}

	@Override
	public ResultMessage modifyBill(BillVO billvo) {
		// TODO 自动生成的方法存根
		return bill.modifyBill(billvo);
	}

	@Override
	public String getBillID(String type) {
		// TODO 自动生成的方法存根
		return bill.getBillID(type);
	}

	@Override
	public ArrayList<BillVO> show() {
		// TODO 自动生成的方法存根
		return bill.show();
	}

	@Override
	public void delAll() {
		// TODO 自动生成的方法存根
		bill.delAll();
	}





}
