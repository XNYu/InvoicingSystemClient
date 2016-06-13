package dataservice.accountDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.AccountPO;

public interface AccountDataService extends Remote{
	public boolean add(AccountPO acc) throws RemoteException;
	public boolean del(AccountPO acc) throws RemoteException;
	public boolean modify(AccountPO oldacc,AccountPO newacc) throws RemoteException;

	//find根据名字找PO 可以确认是否含有改名字的对象； search可进行模糊查询 返回list
	public AccountPO find(String s) throws RemoteException;
	public ArrayList<AccountPO> search(String s) throws RemoteException ;

	ArrayList<AccountPO> show() throws RemoteException;
	public boolean changeMoney(AccountPO acc) throws RemoteException;
	public void finish() throws RemoteException;


}
