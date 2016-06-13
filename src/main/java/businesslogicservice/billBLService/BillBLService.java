package businesslogicservice.billBLService;

import java.util.ArrayList;

import PO.AccountPO;
import PO.CustomerPO;
import VO.AccountVO;
import VO.BillVO;
import VO.CustomerVO;
import businesslogic.utilitybl.ResultMessage;


public interface BillBLService {
	public ResultMessage createbill(BillVO bill);
	public ArrayList<BillVO> getBillExamined();
	public ArrayList<BillVO> show();
	public void endBillBuilding();
	public ArrayList<CustomerVO> showCustomer();
	public ArrayList<AccountVO> showAccount();
	public CustomerPO getCustomer(String name);
	public AccountPO getAccount(String name);
	public ResultMessage modifyBill(BillVO bill);
	public String getBillID(String type);
	public ResultMessage changeMoney (String name,double money);
	public ResultMessage changePay (String name,double pay);
	public ResultMessage changeReceive (String name,double receive);
	public void delAll();

}
