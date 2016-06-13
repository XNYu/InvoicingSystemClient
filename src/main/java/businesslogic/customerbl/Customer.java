package businesslogic.customerbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dataservice.customerDataService.CustomerDataService;

import PO.CommodityPO;
import PO.CustomerPO;
import VO.CustomerVO;
import VO.UserVO;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;


public class Customer implements CustomerInterfaceForBill,CustomerInterfaceForInitial{
	Datafactory datafactory;
	CustomerDataService cds;
	CustomerPO po;
	UserVO uvo;
	private LogInterface loginterface = new Log();
	public void setUser(UserVO u){
		uvo=u;
	}
	public Customer(){
		try {
			datafactory=new Datafactory();
			cds=datafactory.getCustomerData();
			cds.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void  transformVO(CustomerVO vo){
		po=new CustomerPO(vo.getID(), vo.getType(), vo.getRank(), vo.getName(),
				vo.getPhone(), vo.getAddress(), vo.getPostcode(), vo.getEmail(),vo.getQuota(),vo.getPayment(),vo.getReceiving(),vo.getSalesman());
	}
	
	public CustomerVO  transformPO(CustomerPO po){
		CustomerVO vo=new CustomerVO(po.getID(), po.getType(), po.getRank(), po.getName(),
				po.getPhone(), po.getAddress(), po.getPostcode(), po.getEmail(),po.getQuota(),po.getPayment(),po.getReceiving(),po.getSalesman());
		return vo;
	}
	
	public CustomerPO getCustomerPO(){
		return po;
	}
	
	
	
	public String addCustomer(CustomerVO vo){
		String ID=getID();
		boolean flag=false;
		try {
			System.out.println("yes customer");
			transformVO(vo);
			po.setID(ID);
			flag = cds.add(po);
			flag=true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			System.out.println("yes customer");
			loginterface.buildlog(uvo.getName(), "新建客户："+ID);
			return ID;
		}else{
			return null;
		}
	}
	
	public ResultMessage delCustomer(String ID){
		boolean flag=false;
		try {
			flag = cds.del(ID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "删除客户："+ID);
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}
	
	public ResultMessage modifyCustomer(CustomerVO vo){
		boolean flag=false;
		try {
			transformVO(vo);
			flag = cds.modify(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "修改客户："+po.getID());
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}
	}
	
	public CustomerVO searchCustomer(String ID){
		try {
			po = cds.find(ID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(po==null)
			return null;
		
		return transformPO(po);
	}
	
	public ArrayList<CustomerVO> showCustomer(){
		try {
			ArrayList<CustomerPO> POList= cds.show();
			ArrayList<CustomerVO> VOList=new ArrayList<CustomerVO>();
			for(CustomerPO po:POList){
				VOList.add(transformPO(po));
			}
			return VOList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void endCustomerManage(){
		try {
			cds.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CustomerPO getCustomerPO(String name){
		try {
			ArrayList<CustomerPO> poList=cds.vagueFind(name);
			for(CustomerPO po:poList){
				if(po.getName().equals(name)){
					return po;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public int getRank(String name){
		try {
			ArrayList<CustomerPO> poList=cds.vagueFind(name);
			for(CustomerPO po:poList){
				if(po.getName().equals(name)){
					
					return po.getRank();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getID(){
		 DecimalFormat df=new DecimalFormat("00000");
		try {
			
			String str=df.format(cds.readNum()+1);
			return str;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ResultMessage changeReceive(String name ,double receive){
		
		try {
			ArrayList<CustomerPO> poList=cds.vagueFind(name);
			for(CustomerPO po:poList){
				if(po.getName().equals(name)){
					po.setReceiving(po.getReceiving()+receive);
					cds.modify(po);
					return ResultMessage.Success;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultMessage.Failure;
	}
	public ResultMessage changePay(String name,double pay){
		try {
			ArrayList<CustomerPO> poList=cds.vagueFind(name);
			for(CustomerPO po:poList){
				if(po.getName().equals(name)){
					po.setPayment(po.getPayment()+pay);
					cds.modify(po);
					return ResultMessage.Success;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultMessage.Failure;

	}
	public ArrayList<CustomerVO> vagueFind(String str){
		ArrayList<CustomerVO> voList=new ArrayList<CustomerVO>();
		try {
			ArrayList<CustomerPO> poList=cds.vagueFind(str);
			for(CustomerPO p:poList){
				voList.add(transformPO(p));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return voList;
	}
	
	public void delAll(){
		try {
			cds.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
