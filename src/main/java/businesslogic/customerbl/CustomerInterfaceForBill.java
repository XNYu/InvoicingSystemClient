package businesslogic.customerbl;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import PO.CustomerPO;
import VO.CustomerVO;

public interface CustomerInterfaceForBill {
	public CustomerPO getCustomerPO(String name);
	public ResultMessage changeReceive(String name ,double pay);
	public ResultMessage changePay(String name,double pay);
	public ArrayList<CustomerVO> showCustomer();
}
