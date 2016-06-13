package dataservice.billDataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import PO.*;

public interface BillDataService extends Remote{
	public boolean add(ReceivingBillPO bill) throws RemoteException;
	public boolean add(PaymentBillPO bill) throws RemoteException;
	public boolean add(CostBillPO bill) throws RemoteException;
	public boolean modify(ReceivingBillPO bill) throws RemoteException;
	public boolean modify(PaymentBillPO bill) throws RemoteException;
	public boolean modify(CostBillPO bill) throws RemoteException;

	public BillPO find(String s) throws RemoteException;
	public ArrayList<BillPO> show() throws RemoteException;

	//获取审批后的账单
	public ArrayList<BillPO> getBillExamined() throws RemoteException;


	//用于自动生成ID
	public String getBillID(String type) throws RemoteException;


	public ArrayList<String> getUserlistOfSKD() throws RemoteException;
	public ArrayList<String> getUserlistOfFKD() throws RemoteException;
	public ArrayList<String> getUserlistOfXJFYD() throws RemoteException;

	public ArrayList<String> getCustomerlistOfSKD() throws RemoteException;
	public ArrayList<String> getCustomerlistOfFKD() throws RemoteException;
	public ArrayList<String> getAccountlistOfXJFYD() throws RemoteException;

	public ArrayList<ReceivingBillPO> searchSKD(Date date1,Date date2,String username,String customer) throws RemoteException;
	public ArrayList<PaymentBillPO> searchFKD(Date date1,Date date2,String username,String customer) throws RemoteException;
	public ArrayList<CostBillPO> searchXJFYD(Date date1,Date date2,String username,String account) throws RemoteException;

	public ArrayList<CostBillPO> getXJFYDListForExamined() throws RemoteException;
	public ArrayList<PaymentBillPO> getFKDListForExamined() throws RemoteException;
	public ArrayList<ReceivingBillPO> getSKDListForExamined() throws RemoteException;

	public void finish() throws RemoteException;
	public void delAll() throws RemoteException;
}
