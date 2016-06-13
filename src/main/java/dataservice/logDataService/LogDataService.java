package dataservice.logDataService;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.LogPO;

public interface LogDataService extends Remote{
	public boolean add(LogPO po) throws RemoteException;
	public ArrayList<LogPO> find(String time1,String time2) throws RemoteException;
	public boolean finish() throws RemoteException;
	public void delAll() throws RemoteException;
}
