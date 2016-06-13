package dataservice.documentDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.*;

public interface DocumentDataService extends Remote{
	public boolean createOverflow(String name,String type,String id,int oldAmount,int newAmount)throws RemoteException;
	public boolean createDamage(String name,String type,String id,int oldAmount,int newAmount)throws RemoteException;
	public boolean createReport(String name,String type,String id,int reportAmount)throws RemoteException;
	public ArrayList<DocumentPO> showAll()throws RemoteException;
	public ArrayList<DocumentPO> showDoc(String type)throws RemoteException;
	public ArrayList<PresentPO> showPresent()throws RemoteException;
	public boolean examineDocument(String id,boolean examineState) throws RemoteException;
	public double overflowIncome()throws RemoteException;
	public double damageOutcome() throws RemoteException;
	public void clear() throws RemoteException;
	
}
