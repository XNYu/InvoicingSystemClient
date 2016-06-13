package dataservice.customerDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.CustomerPO;
import PO.PresentPO;

public interface CustomerDataService extends Remote{
	public CustomerPO find(String ID) throws RemoteException;
	public boolean add(CustomerPO po) throws RemoteException;
	public boolean del(String ID) throws RemoteException;
	public boolean modify(CustomerPO po) throws RemoteException;
	public ArrayList<CustomerPO> show() throws RemoteException;
	public boolean init() throws RemoteException;
	public boolean finish() throws RemoteException;
	public int readNum() throws RemoteException;
	public int readPresentNum() throws RemoteException;
	public ArrayList<CustomerPO> vagueFind(String str) throws RemoteException;
	public boolean givePresent(PresentPO present) throws RemoteException;
	public ArrayList<PresentPO> showPresent() throws RemoteException;
	public boolean modifyPresent(PresentPO po) throws RemoteException;
	public void delAll() throws RemoteException;
	
}
