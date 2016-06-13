package businesslogicservice.customerBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.CustomerVO;
import VO.UserVO;

public interface CustomerBLService {
	public String addCustomer(CustomerVO vo);
	public ResultMessage delCustomer(String ID);
	public ResultMessage modifyCustomer(CustomerVO vo);
	public CustomerVO searchCustomer(String ID);
	public ArrayList<CustomerVO> showCustomer();
	public void endCustomerManage();
	public ArrayList<CustomerVO> vagueFind(String str);
	public void setUser(UserVO u);
	
}
