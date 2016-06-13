package dataservice.promotionDataService;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.PromotionPO;

public interface PromotionDataService extends Remote{
	public ArrayList<PromotionPO> show() throws RemoteException;
	public boolean modify(PromotionPO po) throws RemoteException;
	public boolean add(PromotionPO po) throws RemoteException;
	public boolean delete(PromotionPO po) throws RemoteException;
	public PromotionPO search(String id) throws RemoteException;
	public String getID() throws RemoteException;
	public void finish() throws RemoteException;;
	public boolean delAll() throws RemoteException;
	}
