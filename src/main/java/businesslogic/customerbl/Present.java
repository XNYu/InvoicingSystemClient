package businesslogic.customerbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dataservice.customerDataService.CustomerDataService;

import businesslogic.commoditybl.Commodity;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.stockbl.Stock;
import businesslogic.stockbl.StockController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.stockBLService.StockBLService;
import PO.CommodityPO;
import PO.PresentPO;
import VO.UserVO;

public class Present {
	PresentPO po;
	CustomerDataService cds;
	StockBLService sbs=new StockController();
	UserVO uvo;
	private LogInterface loginterface = new Log();
	public void setUser(UserVO u){
		uvo=u;
	}
	public Present(){
		try {
			
			cds=Datafactory.getCustomerData();
			cds.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ResultMessage givePresent(PresentPO present){
		try {
			DecimalFormat df=new DecimalFormat("00000");
			System.out.println("present"+ cds.readNum());
			present.ID=df.format(cds.readPresentNum()+1);
			cds.givePresent(present);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(uvo);
		loginterface.buildlog(uvo.getName(), "赠送礼品："+present.getID());
		return ResultMessage.Success;
	}
	
	public ArrayList<PresentPO> showPresent(){
		try {
			return cds.showPresent();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void examine(String ID,String examine){
		
		ArrayList<CommodityPO> clist=null;
		ArrayList<PresentPO> plist=null;
		PresentPO presentpo=null;
		try {
			plist = cds.showPresent();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(PresentPO ppo:plist){
			if(ID.equals(ppo.getID())){
				presentpo=ppo;
				clist=ppo.getCommoditylist();
				break;
			}
		}
		if(examine.equals("审批未通过")){
			presentpo.setExamine("审批未通过");
			try {
				cds.modifyPresent(presentpo);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		Commodity c=new Commodity();
		for(CommodityPO cpo:clist){
			if(c.ensureAmount(cpo.getID(), cpo.getAmount())==false){
				
				presentpo.setExamine("审批未通过");
				try {
					cds.modifyPresent(presentpo);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		
		for(CommodityPO cpo:clist){
			sbs.createStock(cpo.getName(), cpo.getType(), cpo.getID(), "STOCKOUT", "", cpo.getAmount(), cpo.getImpPrice());
		}
		presentpo.setExamine("审批通过");
		try {
			cds.modifyPresent(presentpo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
