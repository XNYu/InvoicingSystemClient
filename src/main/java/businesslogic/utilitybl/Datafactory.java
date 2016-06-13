package businesslogic.utilitybl;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import dataservice.accountDataService.AccountDataService;
import dataservice.billDataService.BillDataService;
import dataservice.commodityDataService.CommodityDataService;
import dataservice.customerDataService.CustomerDataService;

import dataservice.documentDataService.DocumentDataService;
import dataservice.examineDataService.ExamineDataService;
import dataservice.initialDataService.InitialDataService;
import dataservice.logDataService.LogDataService;
import dataservice.promotionDataService.PromotionDataService;
import dataservice.salesDataService.SalesDataService;
import dataservice.stockDataService.StockDataService;
import dataservice.userDataService.UserDataService;

public class Datafactory implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	static String ip="127.0.0.1";
	static String port="6666";
	public static void setIP(String i){
		ip=i;
	}
	public static void setPort(String i){
		port = i;
	}
	/*public Datafactory(){
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ip=addr.getHostAddress().toString();//获得本机IP

	}*/
	public static UserDataService getUserData() throws RemoteException {
		// TODO Auto-generated method stub

		try {
			if(port==null||ip==null)
				return null;
			return (UserDataService)Naming.lookup("rmi://"+ip+":"+port+"/uds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static CustomerDataService getCustomerData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (CustomerDataService)Naming.lookup("rmi://"+ip+":"+port+"/cds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static SalesDataService getSalesData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (SalesDataService)Naming.lookup("rmi://"+ip+":"+port+"/sds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static StockDataService getStockData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (StockDataService)Naming.lookup("rmi://"+ip+":"+port+"/stockds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static DocumentDataService getDocumentData() throws RemoteException {
		try {
			return (DocumentDataService)Naming.lookup("rmi://"+ip+":"+port+"/documentds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static AccountDataService getAccountData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (AccountDataService)Naming.lookup("rmi://"+ip+":"+port+"/ads");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static PromotionDataService getPromotionData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (PromotionDataService)Naming.lookup("rmi://"+ip+":"+port+"/pds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static LogDataService getLogData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			if(port==null||ip==null)
				return null;
			return (LogDataService)Naming.lookup("rmi://"+ip+":"+port+"/lds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static InitialDataService getInitialData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (InitialDataService)Naming.lookup("rmi://"+ip+":"+port+"/ids");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static BillDataService getBillData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (BillDataService)Naming.lookup("rmi://"+ip+":"+port+"/bds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static ExamineDataService getExamineData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (ExamineDataService)Naming.lookup("rmi://"+ip+":"+port+"/eds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

	
	public static CommodityDataService getCommodityData() throws RemoteException {
		// TODO Auto-generated method stub
		try {
			return (CommodityDataService)Naming.lookup("rmi://"+ip+":"+port+"/commodityds");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}

}
