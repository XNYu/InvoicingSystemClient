package businesslogic.stockbl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.*;
import VO.*;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import dataservice.stockDataService.StockDataService;


public class Stock implements Serializable{
	private static final long serialVersionUID = 1L;
	StockDataService stds;
	public Stock(){
		try {
			stds = Datafactory.getStockData();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//clear data for junit test
	public void clear(){
		try {
			stds.clear();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//calculate the data 
	public ArrayList<String> calculate(ArrayList<StockPO> list){
		int stockOutAmount=0,
				stockInAmount=0,
				expAmount=0,
				impAmount=0;
		double stockOutPrice = 0.0,
				stockInPrice = 0.0,
				expPrice = 0.0,
				impPrice = 0.0;
		
		String operateType;
		for(StockPO v :list){
			operateType = v.getOperateType();
			if(operateType.equals("STOCKIN")){
				stockInPrice+=v.getPrice();
				stockInAmount+=v.getAmount();
				continue;
				}
			if(operateType.equals("STOCKOUT")){
				stockOutPrice+=v.getPrice();
				stockOutAmount+=v.getAmount();
				continue;
				}
			if(operateType.equals("EXP")){
				expPrice +=v.getPrice();
				expAmount+=v.getAmount();
				continue;
				}
			if(operateType.equals("IMP")){
				impPrice+=v.getPrice();
				impAmount+=v.getAmount();
				}
		}
		ArrayList<String> back = new ArrayList<String>();
		back.add(String.valueOf(stockOutAmount));
		back.add(String.valueOf(stockOutPrice));
		back.add(String.valueOf(stockInAmount));
		back.add(String.valueOf(stockInPrice));
		back.add(String.valueOf(expAmount));
		back.add(String.valueOf(expPrice));
		back.add(String.valueOf(impAmount));
		back.add(String.valueOf(expPrice));
		return back;
	}
	
	//create a stock
	public boolean createStock(StockPO stoPO){
		try {
			return stds.createStock(stoPO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	//auto-generated the batch id
	public String getBatchID(){
		try {
			return stds.getBatchID();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<StockPO> init(){
		try {
			return stds.init();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	//show stock data
	public ArrayList<StockPO> showStock(String start ,String end){
		try {
			return stds.showStock(start, end);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

}
