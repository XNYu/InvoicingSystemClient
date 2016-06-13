package dataservice.commodityDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTree;

import PO.*;


public interface CommodityDataService extends Remote{
	public boolean add(String name,String type,int amount,double imp,double exp,String typeName) throws RemoteException;
	public boolean del(String s) throws RemoteException;
	public boolean modify(String id,String newName,String newType) throws RemoteException;
	public ArrayList<CommodityPO> show() throws RemoteException;
	public ArrayList<CommodityPO> showCommodity(String typeName) throws RemoteException;
	public ArrayList<CommodityPO> find (String s) throws RemoteException;
	public void init() throws RemoteException;
	public void finish() throws RemoteException;
	public ArrayList<CommodityTypePO> showTree() throws RemoteException;
	public boolean changeAmount(String id,String operateType,int amount)throws RemoteException;
	public boolean changeReport(String id,int amount)throws RemoteException;
	public boolean ensureAmount(String id,int amount)throws RemoteException;
	
	//
	public boolean addType(String name,String parentName) throws RemoteException;
	public boolean delType(String name) throws RemoteException;
	public void saveTree(JTree tree) throws RemoteException;
	public boolean modifyType(String oldName,String newName) throws RemoteException;
	public ArrayList<CommodityTypePO> getTypeList() throws RemoteException;
	public String getTypeID(String s) throws RemoteException;
	public String getComID(String name,String  type)throws RemoteException;
	public void clear() throws RemoteException;
}
