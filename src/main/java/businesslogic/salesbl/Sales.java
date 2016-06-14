package businesslogic.salesbl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import dataservice.salesDataService.SalesDataService;
import PO.CommodityPO;
import PO.PresentPO;
import PO.SalesPO;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.PromotionVO.types;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.customerbl.Customer;
import businesslogic.customerbl.Present;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.promotionbl.Promotion;
import businesslogic.stockbl.StockController;
import businesslogic.userbl.User;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.stockBLService.StockBLService;

public class Sales {
	Commodity commodity=new Commodity();
	CommodityList commodityList;
	Promotion promotion=new Promotion();
	Customer customer=new Customer();
	private LogInterface loginterface = new Log();
	StockBLService sbs=new StockController();
	ArrayList<SalesVO> salesList=new ArrayList<SalesVO>();
	SalesPO po;
	SalesDataService sds;
	Present present=new Present();
	UserVO uvo;
	
	public void setUser(UserVO u){
		uvo=u;
		customer.setUser(u);
		present.setUser(u);
		promotion.setUser(u);
	}
	public Sales(){
		try {
			
			sds=Datafactory.getSalesData();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CustomerVO> showImportCustomer(){
		ArrayList<CustomerVO> rlist=new ArrayList<CustomerVO>();
		ArrayList<CustomerVO> clist=customer.showCustomer();
		for(CustomerVO v:clist){
			if(v.getType().equals("进货商")){
				rlist.add(v);
			}
		}
		return rlist;
	}
	
	public ArrayList<CustomerVO> showSalesCustomer(){
		ArrayList<CustomerVO> rlist=new ArrayList<CustomerVO>();
		ArrayList<CustomerVO> clist=customer.showCustomer();
		for(CustomerVO v:clist){
			if(v.getType().equals("销售商")){
				rlist.add(v);
			}
		}
		return rlist;
	}
	
	public String makeImport(SalesVO vo){
		if(vo.getCommodityList().size()==0){
			return null;
		}
		setCommodityList(vo.getCommodityList());
		transformVO(vo);
		po.setSum(total());
		po.setID("JHD-"+getIDPostfix());
		String ID="JHD-"+getIDPostfix();
		/*if(!commodity.modifyCommodity(po.getCommodityList(),"in")){
			return ResultMessage.Success;
		}*/
		boolean flag=false;
		try {
			sds.showImport();
			flag = sds.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "制定进货单："+po.getID());
			return ID;
		}else{
			return null;
		}
	}
	public String makeImportReturn(SalesVO vo){
		ArrayList<CommodityPO> clist=vo.getCommodityList();
		if(!vo.getExamine().equals("审批通过")){
			return null;
		}
		if(vo.getCommodityList().size()==0){
			return null;
		}
		for(CommodityPO c:clist){
			if(c.getAmount()<=0){
				return null;
			}
		}
		try {
			po=sds.find(vo.getID());
			if(po==null)
				return null;
			po.th=false;
			po.setType("ImportReturn");
			sds.modify(po);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		po.setID("JHTHD-"+getIDPostfix());
		String ID="JHTHD-"+getIDPostfix();
		/*if(!commodity.modifyCommodity(po.getCommodityList(),"out")){
			return ResultMessage.Success;
		}*/
		boolean flag=false;
		try {
			sds.showImportReturn();
			flag = sds.add(po);
			po.th=false;
			po.setExamine("未审批");
			sds.modifySales(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "制定进货退货单："+po.getID());
			return ID;
		}else{
			return null;
		}
	}
	public String makeSales(SalesVO vo){
		if(vo.getCommodityList()!=null){
			setCommodityList(vo.getCommodityList());
		}else{
			System.out.println("空的商品列表");
			return null;
		}
		if(vo.getCommodityList().size()==0){
			return null;
		}
		transformVO(vo);
		po.setSum(total());
		po.setID("XSD-"+getIDPostfix());
		String ID="XSD-"+getIDPostfix();
		
		
		ArrayList<PromotionVO> promotionlist=po.getPromotionlist();
		ArrayList<CommodityPO> commodityList=po.getCommodityList();
		double discount=0;
		for(PromotionVO pvo:promotionlist){
			if(vo.getType().equals(types.v)){
				
			}else if(pvo.getType().equals(types.d)){
				discount=discount+po.getSum()*(1-pvo.getDiscount());
				break;
			}else if(pvo.getType().equals(types.g)){
				ArrayList<CommodityPO> list=new ArrayList<CommodityPO>();
				ArrayList<CommodityPO> giftlist=pvo.getGiftList();
				String cID=null;
				int num=0;
				for(CommodityPO po:giftlist){
					if(!po.getID().equals(cID)){
						list.add(po);
						num=1;
						po.setAmount(num);
						cID=po.getID();
					}else{
						num++;
						list.get(list.size()-1).setAmount(num);
					}
				}
				pvo.setGiftList(list);
			}else if(pvo.getType().equals(types.p)){
				ArrayList<CommodityPO> packlist=pvo.getpackList();
				ArrayList<CommodityPO> list=new ArrayList<CommodityPO>();
				String cID=null;
				int num=0;
				for(CommodityPO po:packlist){
					if(!po.getID().equals(cID)){
						list.add(po);
						num=1;
						po.setImpPrice(num);
						cID=po.getID();
					}else{
						num++;
						list.get(list.size()-1).setImpPrice(num);
					}
				}
				pvo.setPackList(list);
				for(CommodityPO po:commodityList){
					for(CommodityPO pack:list){
						if(pack.getID().equals(po.getID())){
							discount=discount+pack.getImpPrice()*(po.getImpPrice()*(1-pvo.getPackDiscount()));
						}
					}
				}
				break;
			}
		}
		DecimalFormat df = new DecimalFormat(".00");
		discount=Double.parseDouble(df.format(discount));
		if(uvo.getType().equals("销售经理")){
			if(discount>5000){
				discount=5000;
			}
		}
		if(uvo.getType().equals("销售员")){
			if(discount>1000){
				discount=1000;
			}
		}
		po.setDiscount(discount);
		po.setAfterDiscount(po.getSum()-po.getDiscount());
		ArrayList<CustomerVO> customerlist=customer.vagueFind(po.getCustomer());
		CustomerVO tempc = null;
		for(CustomerVO cvo:customerlist){
			System.out.println(cvo.getName());
			if(cvo.getName().equals(po.getCustomer())){
				tempc=cvo;
				break;
			}
			
		}
		if((tempc.getReceiving()+po.getAfterDiscount())>tempc.getQuota()){
			return null;
		}
		
		/*if(!commodity.modifyCommodity(po.getCommodityList(),"out")){
			return ResultMessage.Success;
		}*/
		boolean flag=false;
		try {
			sds.showSales();
			flag = sds.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flag==true){
			//examine(ID,"审批通过");
			loginterface.buildlog(uvo.getName(), "制定销售单："+po.getID());
			return ID;
		}else{
			return null;
		}
	}
	
	public String makeSalesReturn(SalesVO vo){
		ArrayList<CommodityPO> clist=vo.getCommodityList();
		if(!vo.getExamine().equals("审批通过")){
			return null;
		}
		if(vo.getCommodityList().size()==0){
			return null;
		}
		for(CommodityPO c:clist){
			if(c.getAmount()<=0){
				return null;
			}
		}
		try {
			po=sds.find(vo.getID());
			if(po==null)
				return null;
			po.th=false;
			po.setType("SalesReturn");
			sds.modify(po);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		po.setID("XSTHD-"+getIDPostfix());
		String ID="XSTHD-"+getIDPostfix();
		/*if(!commodity.modifyCommodity(po.getCommodityList(),"in")){
			return ResultMessage.Success;
		}*/
		boolean flag=false;
		try {
			sds.showSalesReturn();
			flag = sds.add(po);
			po.setExamine("未审批");
			sds.modify(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(uvo.getName(), "制定销售退货单："+po.getID());
			return ID;
		}else{
			return null;
		}
	}
	public void setCommodityList(ArrayList<CommodityPO> list){
		
		commodityList=new CommodityList(list);
	}
	public void transformVO(SalesVO vo){
		po=new SalesPO(vo.getID(), vo.getType(), vo.getCustomer(), vo.getStock(),vo.getOperator(),vo.getSalesman(),vo.getCommodityList(), vo.getSum(),
				vo.getDiscount(),vo.getAfterDiscount(),vo.getVoucher(), vo.getDocument(),vo.getPromotionlist(),vo.getExamine());
		po.th=vo.th;
	}
	public SalesVO transformPO(SalesPO po){
		SalesVO VO=new SalesVO(po.getID(), po.getType(), po.getCustomer(), po.getStock(),po.getOperator(),po.getSalesman(),po.getCommodityList(), po.getSum(),
				po.getDiscount(),po.getAfterDiscount(),po.getVoucher(), po.getDocument(),po.getPromotionlist(),po.getExamine());
		VO.th=po.th;
		return VO;
	}
	public double total(){
		return commodityList.total();
	}
	
	public String getIDPostfix(){
		String IDPostfix="";
		Date dt=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
	    String date=sdf.format(dt);
	    DecimalFormat df=new DecimalFormat("00000");
	    int num=35;
	    if(po.getType().equals("Import")){
	    	num=getNumOfJHD(date);
	    }else if(po.getType().equals("ImportReturn")){
	    	num=getNumOfJHTHD(date);
	    }else if(po.getType().equals("Sales")){
	    	num=getNumOfXSD(date);
	    }else if(po.getType().equals("SalesReturn")){
	    	num=getNumOfXSTHD(date);
	    }
	    String snum=df.format(num);
	    IDPostfix=date+"-"+snum;
	    return IDPostfix;
	}
	
	public int getNumOfJHD(String date){
		try {
			return sds.getNumOfJHD(date);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getNumOfJHTHD(String date){
		try {
			return sds.getNumOfJHTHD(date);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getNumOfXSD(String date){
		try {
			return sds.getNumOfXSD(date);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int getNumOfXSTHD(String date){
		try {
			return sds.getNumOfXSTHD(date);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<SalesVO> showSales(){
		try {
			ArrayList<SalesPO> poList=sds.showSales();
			ArrayList<SalesVO> voList=new ArrayList<SalesVO>();
			for(SalesPO PO:poList){
				voList.add(transformPO(PO));
			}
			return voList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<SalesVO> showImportReturn(){
		try {
			ArrayList<SalesPO> poList=sds.showImportReturn();
			ArrayList<SalesVO> voList=new ArrayList<SalesVO>();
			for(SalesPO PO:poList){
				voList.add(transformPO(PO));
			}
			return voList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<SalesVO> showSalesReturn(){
		try {
			ArrayList<SalesPO> poList=sds.showSalesReturn();
			ArrayList<SalesVO> voList=new ArrayList<SalesVO>();
			for(SalesPO PO:poList){
				voList.add(transformPO(PO));
			}
			return voList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<SalesVO> showImport(){
		try {
			ArrayList<SalesPO> poList=sds.showImport();
			ArrayList<SalesVO> voList=new ArrayList<SalesVO>();
			for(SalesPO PO:poList){
				voList.add(transformPO(PO));
			}
			return voList;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void endMakeSales(){
		try {
			sds.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<SalesVO> searchSales(String type,String Customer,String salesman,String stock,Date startDate,Date endDate){
		ArrayList<SalesPO> list=new ArrayList<SalesPO>();
		try {
			if(type.equals("销售单")){
				list=sds.showSales();
			}else if(type.equals("销售退货单")){
				list=sds.showSalesReturn();
			}else if(type.equals("进货单")){
				list=sds.showImport();
			}else if(type.equals("进货退货单")){
				list=sds.showImportReturn();
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<SalesPO> removelist=new ArrayList<SalesPO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
		String sd=sdf.format(startDate);
		String ed=sdf.format(endDate);
		int sdate=Integer.parseInt(sd);
		int edate=Integer.parseInt(ed);
		for(SalesPO spo:list){	
			String[] tmp=spo.getID().split("-");
			String d=tmp[1];
			int date=Integer.parseInt(d);
			
			if(Customer!=null&&!spo.getCustomer().equals(Customer)){
				removelist.add(spo);
			}else if(salesman!=null&&!spo.getSalesman().equals(salesman)){
				removelist.add(spo);
			}else if(stock!=null&&!spo.getStock().equals(stock)){
				removelist.add(spo);
			}else if(!(date>=sdate&&date<=edate)){
				removelist.add(spo);
			}
		}
		list.removeAll(removelist);
		
		ArrayList<SalesVO> returnlist=new ArrayList<SalesVO>();
		for(SalesPO spo:list){
			returnlist.add(transformPO(spo));
		}
		
		return returnlist;
	}
	
	public ArrayList<PromotionVO> showAvailablePromotion(SalesVO vo){
		
		return promotion.availablePromotion(vo);
	}
	
	public void examine(String ID,String examine){

		
		try {
			po=sds.find(ID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setUser(new UserVO("","销售经理",po.getOperator(),"123","普通权限"));
		


		if(examine.equals("审批未通过")){
			po.setExamine("审批未通过");
			try {
				sds.modifySales(po);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(examine.equals("审批通过")){
			
			ArrayList<CommodityPO> clist=po.getCommodityList();
			if((po.getType().equals("ImportReturn")||po.getType().equals("Sales"))){
				Commodity c=new Commodity();
				boolean enough=true;
				for(CommodityPO cpo:clist){
					if(c.ensureAmount(cpo.getID(), cpo.getAmount())==false){
						enough=false;
						po.setExamine("审批未通过");
						try {
							sds.modifySales(po);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
				}
			}
			po.setExamine("审批通过");
			try {
				
				sds.modifySales(po);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(po.getType().equals("Sales")||po.getType().equals("ImportReturn")){
				for(CommodityPO cp:clist){
					sbs.createStock(cp.getName(), cp.getType(), cp.getID(), "STOCKOUT", "", cp.getAmount(), cp.getImpPrice());
				}
			}
			if(po.getType().equals("SalesReturn")||po.getType().equals("Import")){
				for(CommodityPO cp:clist){
					String text="";
					if(po.getType().equals("Import")){
						text=cp.getLeaveDate();
					}
					System.out.println("commoditytype"+cp.getID());
					sbs.createStock(cp.getName(), cp.getType(), cp.getID(), "STOCKIN",text, cp.getAmount(), cp.getImpPrice());
				}
			}
			if(po.getType().equals("Sales")||po.getType().equals("ImportReturn")){
				customer.changeReceive(po.getCustomer(), po.getSum()-po.getDiscount()-po.getVoucher());
			}
			if(po.getType().equals("SalesReturn")||po.getType().equals("Import")){
				customer.changePay(po.getCustomer(), po.getSum()-po.getDiscount()-po.getVoucher());
			}
			ArrayList<PromotionVO> promotionlist=po.getPromotionlist();
			if(promotionlist!=null){
				for(PromotionVO promotion:promotionlist){
					if(promotion.getType().equals(types.g)){
						present.givePresent(new PresentPO(promotion.getGiftList(),po.getCustomer()));
					}
				}
			}
		}
	}
	

	public double getSalesIncome(Date d1,Date d2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
		String sd=sdf.format(d1);
		String ed=sdf.format(d2);
		int sdate=Integer.parseInt(sd);
		int edate=Integer.parseInt(ed);
		salesList=showSales();
		double salessum=0;
		for(SalesVO svo:salesList){
			if(svo.getExamine().equals("审批通过")){
				salessum+=(svo.getAfterDiscount()-svo.getVoucher());
			}
		}
		salesList=showSalesReturn();
		for(SalesVO svo:salesList){
			if(svo.getExamine().equals("审批通过")){
				salessum-=(svo.getAfterDiscount()-svo.getVoucher());
			}
		}
		System.out.println("salesincome "+salessum);
		return salessum;
	}
	
	public double getDiscount(Date d1,Date d2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
		String sd=sdf.format(d1);
		String ed=sdf.format(d2);
		int sdate=Integer.parseInt(sd);
		int edate=Integer.parseInt(ed);
		salesList=showSales();
		double salessum=0;
		for(SalesVO spo:salesList){
			if(spo.getExamine().equals("审批通过")){
				salessum+=spo.getDiscount();
			}
		}
		salesList=showSalesReturn();
		for(SalesVO svo:salesList){
			if(svo.getExamine().equals("审批通过")){
				salessum-=(svo.getDiscount());
			}
		}
		return salessum;
	}
	
	public double getImportOutcome(Date d1,Date d2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
		String sd=sdf.format(d1);
		String ed=sdf.format(d2);
		int sdate=Integer.parseInt(sd);
		int edate=Integer.parseInt(ed);
		
		double salessum=0;
		salesList=showImport();
		for(SalesVO svo:salesList){
			if(svo.getExamine().equals("审批通过")){
				salessum+=svo.getSum();
			}
		}
		salesList=showImportReturn();
		for(SalesVO svo:salesList){
			if(svo.getExamine().equals("审批通过")){
				salessum-=svo.getSum();
			}
		}
		
		return salessum;
	}
	

	public void delAll(){
		try {
			sds.delAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
