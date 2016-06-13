package businesslogic.commoditybl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.CommodityPO;
import businesslogic.utilitybl.ResultMessage;

public interface CommodityInterfaceForInitial {
	public ResultMessage addCom(String name,String type,int amount,double imp,double exp,String typeName);
	public ArrayList<CommodityPO> showCom() throws RemoteException;
}
