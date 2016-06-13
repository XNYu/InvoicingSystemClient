package businesslogic.commoditybl;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTree;

import PO.*;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;

public class CommodityController implements CommodityBLService,Serializable{
	private static final long serialVersionUID = 1L;
	Commodity commodity = new Commodity();
	
	public ArrayList<CommodityPO> showCommodity(String typeName){
		return commodity.showCommodity(typeName);
		
	}
	
	public String getTypeID(String s){
		return commodity.getTypeID(s);
	}
	public String getComID(String name,String  type)
	{
		return commodity.getComID(name,type);
	}
	public ArrayList<CommodityTypePO> getTypeList(){
		try {
			return commodity.getTypeList();
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultMessage addCommodityType(String name, String parentName) {
		// TODO 自动生成的方法存根
		return commodity.addType(name, parentName);
	}
	
	public ResultMessage addCommodity(String name,String type,int amount,double imp,double exp,String typeName) {
		return commodity.addCom(name,type,amount,imp, exp, typeName);
	}

	public ResultMessage modifyCommodity(String id,String newName,String newType) {
		
		return commodity.modifyCom(id,newName,newType);
	}

	@Override
	public ResultMessage delCommodity(String info) {
		return commodity.delCom(info);
	}


	@Override
	public ArrayList<CommodityPO> findCommodity(String s) {
		try {
			return commodity.findCom(s);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public ResultMessage delCommodityType(String info) {
		return commodity.delType(info);
	}

	@Override
	public ResultMessage modifyCommodityType(String info, String info_new) {
		return commodity.modifyComType(info, info_new);
	}

	@Override
	public void endCommodity() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void endCommodityType() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public ArrayList<CommodityPO> showCommodity() {
		// TODO 自动生成的方法存根
		
			return commodity.showCom();
		
		
	}

	@Override
	public JTree showTree() {
		return commodity.showTree();
	}


	@Override
	public ResultMessage changeReport(String id,int amount) {
		return commodity.changeReport(id,amount);
	}

	@Override
	public ResultMessage changeAmount(String id, String operateType, int amount) {
		// TODO Auto-generated method stub
		return commodity.changeAmount(id, operateType, amount);
	}

	@Override
	public void clear() {
		commodity.clear();
		
	}

	


}
