package businesslogic.accountbl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.AccountPO;
import VO.AccountVO;
import VO.UserVO;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.CheckChinese;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import dataservice.accountDataService.AccountDataService;

public class Account implements AccountInterfaceForBill,AccountInterfaceOfChangeMoney,AccountInterfaceForInitial{
	private AccountDataService accountDataService;
	private LogInterface loginterface = new Log();
	private String userinfo = null;
	//初始化Account 连接data层
	public Account(UserVO user){
		this.setUserinfo(user.getName());//传入操作员姓名
		try {
			accountDataService=Datafactory.getAccountData();


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//处理当前使用用户的信息
		public String getUserinfo() {
			return userinfo;
		}

		private void setUserinfo(String userinfo) {
			this.userinfo = userinfo;
		}


	//po vo转换
	public AccountPO  transform(AccountVO accvo){
		return new AccountPO(accvo.getName(),accvo.getMoney());
	}

	public AccountVO  transform(AccountPO accpo){
		return new AccountVO(accpo.getName(),accpo.getMoney());
	}

	public ResultMessage addAccount(String name) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		if(!CheckChinese.isAllChinese(name))
			return ResultMessage.Failure;
		try {
			flag = accountDataService.add(transform(new AccountVO(name)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(userinfo, "新建账户："+name);
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}

	}

	public ResultMessage deleteAccount(String name) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		if(!CheckChinese.isAllChinese(name))
			return ResultMessage.Failure;
		try {
			flag = accountDataService.del(transform(new AccountVO(name)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(userinfo, "删除账户："+name);
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}

	public ResultMessage modifyAccount(String name, String name_new) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		if(!CheckChinese.isAllChinese(name)||!CheckChinese.isAllChinese(name_new))
			return ResultMessage.Failure;
		if(name.equals(name_new))
			return ResultMessage.Success;
		try {
			flag = accountDataService.modify(transform(new AccountVO(name)),transform(new AccountVO(name_new)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(userinfo, "修改账户：from"+name+"to"+name_new);
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}

	public ArrayList<AccountVO> searchAccount(String name) {
		// TODO 自动生成的方法存根
		ArrayList<AccountVO> accvolist = new ArrayList<AccountVO>();
		ArrayList<AccountPO> accpolist = new ArrayList<AccountPO>();
		if(!CheckChinese.isAllChinese(name))
			return accvolist;
		try {
			accpolist=accountDataService.search(name);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginterface.buildlog(userinfo, "查找账户："+name);
		for(int i = 0;i<accpolist.size();i++){
			accvolist.add(transform(accpolist.get(i)));
		}
		return accvolist;
	}

	//账单修改账户金额的接口
	public ResultMessage changeMoney(String name, double money) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		if(!CheckChinese.isAllChinese(name))
			return ResultMessage.Failure;
		try {
			flag = accountDataService.changeMoney(transform(new AccountVO(name,money)));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}

	public void endAccountManage() {
		// TODO 自动生成的方法存根

		try {
			accountDataService.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//根据输入名字查找账户
	@Override
	public AccountPO getAccountPO(String name) {
		// TODO 自动生成的方法存根
		AccountPO accpo = null;
		if(!CheckChinese.isAllChinese(name))
			return null;
		try {
			accpo = accountDataService.find(name);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return accpo;
	}

	public ArrayList<AccountVO> show() {
		// TODO 自动生成的方法存根
		ArrayList<AccountVO> list = new ArrayList<AccountVO>();
		ArrayList<AccountPO> polist = new ArrayList<AccountPO>();
		try {
			polist = accountDataService.show();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0;i<polist.size();i++){
			list.add(transform(polist.get(i)));
		}

		return list;
	}

	@Override
	public ArrayList<AccountVO> showAccount() {
		// TODO 自动生成的方法存根
		return show();
	}

}

