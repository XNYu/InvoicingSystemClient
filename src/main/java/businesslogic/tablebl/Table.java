package businesslogic.tablebl;

import java.util.ArrayList;
import java.util.Date;

import PO.CostBillPO;
import PO.DocumentPO;
import PO.PaymentBillPO;
import PO.ReceivingBillPO;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.SalesVO;
import businesslogic.billbl.Bill;
import businesslogic.billbl.BillController;
import businesslogic.document.Document;
import businesslogic.salesbl.*;
import businesslogic.stockbl.Stock;
import businesslogicservice.billBLService.BillBLService;
import businesslogicservice.salesBLService.SalesBLService;
//import businesslogic.stockbl.*;
public class Table {
	Sales sale=new Sales();
	Bill bill;
	Stock stock=new Stock();
	Document docu=new Document();
	
	public double check(Date d1,Date d2){
		double income=this.checkIncome(d1,d2);
		double outcome=this.checkOutcome(d1, d2);
		return this.checkProfit(income, outcome);
	}
	
	public double checkIncome(Date d1,Date d2){
		double income = 0;
		double salesIncome = sale.getSalesIncome(d1,d2);// 销售总收入
		double overflowIncome=docu.overflowIncome();
		income = salesIncome+overflowIncome;		
		
		return income;
	}
	
	public double checkOutcome(Date d1,Date d2){
		double outcome = 0;
		double importOutcome = sale.getImportOutcome(d1,d2);//
		double damageOutcome=docu.damageOutcome();
		double presentOutcome=0;// = sale.getPresentTotalPrice(d1,d2);//
		outcome = importOutcome+damageOutcome+presentOutcome;
		
		return outcome;
	}
	
	public double checkDiscount(Date d1,Date d2){
		double discount= sale.getDiscount(d1,d2); //折扣量
		return discount;
	}
	
	public double checkProfit(double in,double out){
		double profit = in-out;
		return profit;
	}

	public ArrayList<SalesVO> showSales(){
		ArrayList<SalesVO> list=new ArrayList<SalesVO>();
		return list;
	}
	
	public ArrayList<SalesVO> showSalesReturn(){
		ArrayList<SalesVO> list=new ArrayList<SalesVO>();
		return list;
	}
	
	public ArrayList<SalesVO>  showImport(){
		ArrayList<SalesVO> list=new ArrayList<SalesVO>();
		return list;
	}
	public ArrayList<SalesVO> showImportReturn(){
		ArrayList<SalesVO> list=new ArrayList<SalesVO>();
		return list;
	}
	
	public ArrayList<DocumentPO> showStocks(String type, Date startTime,Date endTime, String clerk, String cangku) {
		ArrayList<DocumentPO> list=new ArrayList<DocumentPO>();
		
		return list;
	}

	public ArrayList<CostBillVO> showXJFYD(Date startTime, Date endTime,String username, String account) {
		ArrayList<CostBillVO> list=new ArrayList<CostBillVO>();
		return list;
	}

	public ArrayList<PaymentBillVO> showFKD(Date startTime, Date endTime,String username, String customer) {
		ArrayList<PaymentBillVO> list=new ArrayList<PaymentBillVO>();
		return list;
	}

	public ArrayList<ReceivingBillVO> showSKD(Date startTime, Date endTime,String username, String customer) {
		ArrayList<ReceivingBillVO> list = new ArrayList<ReceivingBillVO>();
		return list;
	}
	
	public ArrayList<String> getUser(String type,Date d1,Date d2){
		ArrayList<String> userList=new ArrayList<String>();
		/*switch(type){
		case "SKD":userList=bill.getUserlistOfSKD();
		case "FKD":userList=bill.getUserlistOfFKD();
		case "XJFYD":userList=bill.getUserlistOfXJFYD();
		}*/
		return userList;
	}
	
	public ArrayList<String> getCustomer(String type,Date d1,Date d2){
		ArrayList<String> customerList=new ArrayList<String>();
		/*switch(type){
		case "SKD":customerList=bill.getCustomerlistOfSKD();
		case "FKD":customerList=bill.getCustomerlistOfFKD();
		case "XJFYD":customerList=bill.getAccountlistOfXJFYD();
		}*/
		return customerList;
	}
	
}

