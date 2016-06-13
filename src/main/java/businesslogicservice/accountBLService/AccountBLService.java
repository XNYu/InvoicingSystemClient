package businesslogicservice.accountBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.AccountVO;


public interface AccountBLService{
	public ResultMessage addAccount(String name);
	public ResultMessage deleteAccount(String name);
	public ResultMessage modifyAccount(String name,String name_new);
	public ArrayList<AccountVO> searchAccount(String name); 
	public ArrayList<AccountVO> show();
	public void endAccountManage();

}
