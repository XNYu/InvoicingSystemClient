package businesslogic.customerbl;

import java.util.ArrayList;

import PO.PresentPO;
import VO.CustomerVO;
import VO.UserVO;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.customerBLService.CustomerBLService;

public class CustomerController implements CustomerBLService{
	Customer customer=new Customer();
	Present present=new Present();
	public void setUser(UserVO u){
		customer.setUser(u);
		present.setUser(u);
	}
	public String addCustomer(CustomerVO vo){
		return customer.addCustomer(vo);
	}
	
	public ResultMessage delCustomer(String ID){
		return customer.delCustomer(ID);
	}
	
	public ResultMessage modifyCustomer(CustomerVO vo){
		return customer.modifyCustomer(vo);
	}
	
	public ArrayList<CustomerVO> showCustomer(){
		return customer.showCustomer();
	}

	@Override
	public CustomerVO searchCustomer(String ID) {
		// TODO Auto-generated method stub
		return customer.searchCustomer(ID);
	}

	@Override
	public void endCustomerManage() {
		// TODO Auto-generated method stub
		customer.endCustomerManage();
	}
	
	public ArrayList<CustomerVO> vagueFind(String str){
		return customer.vagueFind(str);
	}
	
	public ResultMessage givePresent(PresentPO p){
		return this.present.givePresent(p);
	}
}
