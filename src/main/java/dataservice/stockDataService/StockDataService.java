package dataservice.stockDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import PO.StockPO;

public interface StockDataService extends Remote{
	public ArrayList<StockPO> init()throws RemoteException;
	public ArrayList<StockPO> showStock(String start,String end)throws RemoteException;
	public boolean createStock(StockPO stoPO)throws RemoteException;
	public String getBatchID()throws RemoteException;
	public void clear() throws RemoteException;
}
