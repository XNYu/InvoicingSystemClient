package businesslogic.accountbl;

import java.util.ArrayList;

import VO.AccountVO;
import VO.UserVO;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.accountBLService.AccountBLService;

public class AccountController implements AccountBLService {
	private Account acc = null;
	private UserVO user = null;

	public AccountController(UserVO user1){
		this.user = user1;
		acc = new Account(user);
	}

	public ResultMessage addAccount(String name) {
		// TODO 自动生成的方法存根
		return acc.addAccount(name);
	}

	public ResultMessage deleteAccount(String name) {
		// TODO 自动生成的方法存根
		return acc.deleteAccount(name);
	}

	public ResultMessage modifyAccount(String name, String name_new) {
		// TODO 自动生成的方法存根
		return acc.modifyAccount(name, name_new);
	}

	public ArrayList<AccountVO> searchAccount(String name) {
		// TODO 自动生成的方法存根
		return acc.searchAccount(name);
	}


	public void endAccountManage() {
		// TODO 自动生成的方法存根
		acc.endAccountManage();
	}

	@Override
	public ArrayList<AccountVO> show() {
		// TODO 自动生成的方法存根
		return acc.show();
	}



}
