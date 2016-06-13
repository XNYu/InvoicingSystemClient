package businesslogic.customerbl;

import java.util.ArrayList;

import VO.CustomerVO;
import businesslogic.utilitybl.ResultMessage;

public interface CustomerInterfaceForInitial {
	public String addCustomer(CustomerVO vo);
	public ResultMessage delCustomer(String ID);
	public ResultMessage modifyCustomer(CustomerVO vo);
	public ArrayList<CustomerVO> vagueFind(String str);
	public ArrayList<CustomerVO> showCustomer();
}
