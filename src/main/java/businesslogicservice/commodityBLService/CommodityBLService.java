package businesslogicservice.commodityBLService;


import java.util.ArrayList;

import javax.swing.JTree;

import PO.CommodityPO;
import PO.CommodityTypePO;
import businesslogic.utilitybl.ResultMessage;


public interface CommodityBLService {
	public ResultMessage addCommodity(String name,String type,int amount,double imp,double exp,String typeName);
	public ResultMessage delCommodity(String id);
	public ResultMessage modifyCommodity(String id,String newName,String newType);
	public ArrayList<CommodityPO> findCommodity(String s);
	public ArrayList<CommodityPO> showCommodity();
	public ArrayList<CommodityPO> showCommodity(String typeName);
	public JTree showTree();
	public ResultMessage addCommodityType(String name,String parentName);
	public ResultMessage delCommodityType(String info);
	public ResultMessage modifyCommodityType(String info,String info_new);
	public ResultMessage changeAmount(String id,String operateType,int amount);
	public ResultMessage changeReport(String id,int amount);
	public void endCommodity();
	public void endCommodityType();
	public ArrayList<CommodityTypePO> getTypeList();
	public String getTypeID(String s);
	public String getComID(String name,String  type);
	public void clear();
}
