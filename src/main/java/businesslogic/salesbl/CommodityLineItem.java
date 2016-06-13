package businesslogic.salesbl;

import PO.CommodityPO;


public class CommodityLineItem {
	CommodityPO commodityInfo;
	public CommodityLineItem(CommodityPO commodity){
		commodityInfo=commodity;
	}
	public double total(){
		return commodityInfo.getImpPrice()*commodityInfo.getAmount();
	}
}
