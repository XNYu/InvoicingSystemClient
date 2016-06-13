package businesslogic.commoditybl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import PO.CommodityPO;
import PO.CommodityTypePO;
import VO.CommodityVO;
import dataservice.commodityDataService.CommodityDataService;



public class Commodity implements Serializable ,CommodityInterfaceForInitial{

	private static final long serialVersionUID = 1L;
	CommodityDataService cds;
	ArrayList<CommodityTypePO> typeList;
	public Commodity(){
		try {
			cds=Datafactory.getCommodityData();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//clear the data for JUnit test
	public void clear(){
		try {
			cds.clear();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//ensure the commodity's amount for sale
	public boolean ensureAmount(String id, int amount){
		try {
			return cds.ensureAmount(id,amount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	//change the commodity's report amount
	public ResultMessage changeReport(String id,int amount){
		boolean flag = false;
		try {
			flag =  cds.changeReport(id,amount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//change the commodity's amount in variety operation
	public ResultMessage changeAmount(String id,String operateType,int amount){
		boolean flag = false;
		try {
			flag =  cds.changeAmount(id, operateType,amount);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//show commodity list in a category
	public ArrayList<CommodityPO> showCommodity(String typeName){
		try {
			ArrayList<CommodityPO> POList= cds.showCommodity(typeName);
			if(POList==null){
				return null;
			}
			return POList;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	//get the id of a commodity
	public String getComID(String name,String  type){
		try {
			return cds.getComID(name,type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//get id of category
	public String getTypeID(String s){
		try {
			return cds.getTypeID(s);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//get commodity category list
	public ArrayList<CommodityTypePO> getTypeList() throws RemoteException{
		return cds.getTypeList();
	}
	//modify a commodity category
	public ResultMessage modifyComType(String oldName,String newName){
		boolean flag = false;
		try {
			flag = cds.modifyType(oldName, newName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	

	//add a commodity category
	public ResultMessage addType(String name ,String parentName){
		boolean flag = false;
		try {
			flag = cds.addType(name,parentName);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//delete a commodity category
	public ResultMessage delType(String name){
		boolean flag = false;
		try {
			flag = cds.delType(name);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//add a commodity
	public ResultMessage addCom(String name,String type,int amount,double imp,double exp,String typeName){
		boolean flag = false;
		try {
			flag = cds.add(name,type,amount,imp, exp, typeName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//delete a commodity
	public ResultMessage delCom(String id){
		boolean flag = false;
		try {
			flag = cds.del(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//modify a commodity
	public ResultMessage modifyCom(String id,String newName,String newType){
		boolean flag = false;
		try {
			flag = cds.modify(id,newName,newType);
		}catch (RemoteException e) {
			e.printStackTrace();
		}
		if(flag)
			return ResultMessage.Success;
		else
			return ResultMessage.Failure;
	}
	//find a commodity
	public ArrayList<CommodityPO> findCom(String input) throws RemoteException{
		ArrayList<CommodityPO> comlist = cds.find(input);
		return comlist;
	}
	//show all commodity
    public ArrayList<CommodityPO> showCom() {
    	try {
			ArrayList<CommodityPO> POList= cds.show();
			if(POList==null){
				return null;
			}
			return POList;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
    //create a JTree depends on category
    public JTree showTree() {
    	try {
    	    typeList = new ArrayList<CommodityTypePO>();
			typeList = cds.showTree();
			JTree tree ;
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("全部商品分类");
			createChild(root,"全部商品分类");
			tree = new JTree(root);
			return tree;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
    }
    public void createChild(DefaultMutableTreeNode node,String name){
		for(CommodityTypePO p : typeList){
			if(p.getType().equals(name)){
				
				ArrayList<String> al = p.child;
				for(int i = 0;i<al.size();++i){
					DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(al.get(i));
					createChild(tmp,al.get(i));
					node.add(tmp);
				}
				break;
			}
		}
	}

}
