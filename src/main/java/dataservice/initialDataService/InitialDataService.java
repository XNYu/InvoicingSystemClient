package dataservice.initialDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import PO.InitialPO;


public interface InitialDataService extends Remote{
	public boolean add(InitialPO ini) throws RemoteException;
	public InitialPO find(String year) throws RemoteException;
	public void finish() throws RemoteException;
	public void delAll() throws RemoteException;

}
