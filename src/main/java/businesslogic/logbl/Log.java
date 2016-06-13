package businesslogic.logbl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import PO.LogPO;
import VO.LogVO;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;

import dataservice.logDataService.LogDataService;

public class Log implements LogInterface{
	LogDataService data ;
	
	public Log(){
		try {
			
			data=Datafactory.getLogData();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	//建立一条日志
	@Override
	public ResultMessage buildlog(String username, String note) {
		// TODO 自动生成的方法存根
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss");
		String time = df.format(new Date());
		boolean flag = false;
		try {
			flag = data.add(new LogVO(username,time,note).transform());
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

	//按日期搜寻日志
	public ArrayList<LogVO> searchlogs(String time1,String time2) {
		// TODO 自动生成的方法存根
		ArrayList<LogVO> volist = new ArrayList<LogVO>();
		//检查日期输入是否正确
		if(time1.compareTo(time2)>0)
			return new ArrayList<LogVO>();
		try {
			for(LogPO po: data.find(time1, time2)){
				volist.add(po.transform());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return volist;
	}


	public void endLog() {
		// TODO 自动生成的方法存根
		try {
			data.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void delAll() {
		// TODO 自动生成的方法存根
		try {
			data.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
