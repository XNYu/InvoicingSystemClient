package businesslogic.initialbl;

import java.rmi.RemoteException;

import PO.InitialPO;
import VO.InitialVO;
import VO.UserVO;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import dataservice.initialDataService.InitialDataService;

public class Initial {
	private InitialDataService inidataservice;
	private String userinfo = null;
	private LogInterface loginterface = new Log();

	public Initial(UserVO user){
		this.setUserinfo(user.getName());//传入操作员姓名

		try {
			inidataservice=Datafactory.getInitialData();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

	public ResultMessage createInitial(InitialVO inivo){
		boolean flag=false;
		try {
			flag = inidataservice.add(inivo.transform(inivo));
			loginterface.buildlog(userinfo, "期初建账："+inivo.getYear());
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

	public InitialVO search(String year){
		InitialPO po = null;
		try {
			po = inidataservice.find(year);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(po == null)
			return null;
		return po.transform(po);
	}

	public void finish(){
		try {
			inidataservice.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delAll() {
		// TODO 自动生成的方法存根
		try {
			inidataservice.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
