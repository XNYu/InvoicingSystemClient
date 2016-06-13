package dataservice.salesDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;


import java.util.ArrayList;

import PO.SalesPO;

public interface SalesDataService extends Remote{
	public SalesPO find(String ID) throws RemoteException;
	public boolean add(SalesPO po) throws RemoteException;
	public boolean del(SalesPO po) throws RemoteException;
	public boolean modify(SalesPO po) throws RemoteException;
	public boolean init() throws RemoteException;
	public ArrayList<SalesPO> showImport() throws RemoteException;
	public ArrayList<SalesPO> showImportReturn() throws RemoteException;
	public ArrayList<SalesPO> showSales() throws RemoteException;
	public ArrayList<SalesPO> showSalesReturn() throws RemoteException;
	public int getNumOfJHD(String date) throws RemoteException;
	public int getNumOfJHTHD(String date) throws RemoteException;
	public int getNumOfXSD(String date) throws RemoteException;
	public int getNumOfXSTHD(String date) throws RemoteException;
	public boolean finish() throws RemoteException;
	public void saveSales()  throws RemoteException;
	public void modifySales(SalesPO po) throws RemoteException;
	public void delAll() throws RemoteException;
	
}
