package businesslogic.userbl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import PO.UserPO;
import VO.CustomerVO;
import VO.UserVO;

import dataservice.userDataService.UserDataService;



public class User {
	UserDataService uds;
	UserPO po;
	
	UserVO uvo;
	private LogInterface loginterface = new Log();
	public void setUser(UserVO u){
		uvo=u;
	}
	public User(){
		try {
		
			uds=Datafactory.getUserData();
			if(uds==null)
				return ;
			uds.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void  transformVO(UserVO vo){
		po=new UserPO(vo.getID(),vo.getType(),vo.getName(),vo.getPassword(),vo.getRank());
	}
	
	public UserVO  transformPO(UserPO po){
		UserVO vo=new UserVO(po.getID(),po.getType(),po.getName(),po.getPassword(),po.getRank());
		return vo;
	}
	
	public UserPO getUserPO(){
		return po;
	}
	
	public UserVO login(String ID,String password){
		 	searchUser(ID);
		 	if(po==null){
		 		return null;
		 	}
			if(po.getPassword().equals(password)){
				return transformPO(po);
			}else{
				return null;
			}
	}
	
	public ResultMessage register(UserVO vo){
		ResultMessage rm=addUser(vo);
		return rm;
	}
	
	public ResultMessage addUser(UserVO vo){
		boolean flag=false;
		try {
			transformVO(vo);
			flag = uds.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "新建用户："+po.getID());
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}
	
	public ResultMessage delUser(String ID){
		boolean flag=false;
		try {
			flag = uds.del(ID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "删除用户："+ID);
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}
	
	public ResultMessage modifyUser(UserVO vo){
		boolean flag=false;
		try {
			transformVO(vo);
			flag = uds.modify(po);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "修改用户："+po.getID());
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}
	
	public UserVO searchUser(String ID){
		try {
			po = uds.find(ID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(po==null)
			return null;
		return transformPO(po);
	}
	
	public ArrayList<UserVO> showUser(){
		try {
			ArrayList<UserPO> POList= uds.show();
			if(POList==null){
				return null;
			}
			ArrayList<UserVO> VOList=new ArrayList<UserVO>();
			for(UserPO po:POList){
				VOList.add(transformPO(po));
			}
			return VOList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void endUserManage(){
		try {
			uds.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delAll(){
		try {
			uds.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
