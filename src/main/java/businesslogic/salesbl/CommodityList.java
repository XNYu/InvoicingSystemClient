package businesslogic.salesbl;

import java.util.ArrayList;

import PO.CommodityPO;

import businesslogic.utilitybl.ResultMessage;

public class CommodityList {
	ArrayList<CommodityLineItem> commodityList=new ArrayList<CommodityLineItem>();
	
	public CommodityList(ArrayList<CommodityPO> list){
		for(CommodityPO commodity:list){
			commodityList.add(new CommodityLineItem(commodity));
		}
	}
	
	public ResultMessage addCommodity(CommodityLineItem c){
		commodityList.add(c);
		return ResultMessage.Success;
	}
	public double total(){
		double sum=0;
		for(CommodityLineItem c:commodityList){
			sum+=c.total();
		}
		return sum;
	}
}
