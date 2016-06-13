package dataservice.userDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.UserPO;

public interface UserDataService extends Remote{
	public UserPO find(String ID) throws RemoteException;
	public boolean add(UserPO po) throws RemoteException;
	public boolean del(String ID) throws RemoteException;
	public boolean modify(UserPO po) throws RemoteException;
	public ArrayList<UserPO> show() throws RemoteException;
	public boolean init() throws RemoteException;
	public boolean finish() throws RemoteException;
	public void delAll() throws RemoteException;

}
