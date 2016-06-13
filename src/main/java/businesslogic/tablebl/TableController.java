package businesslogic.tablebl;

import java.util.ArrayList;
import java.util.Date;

import VO.CostBillVO;
import PO.DocumentPO;
import PO.PaymentBillPO;
import PO.ReceivingBillPO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.SalesVO;
import businesslogicservice.tableBLService.TableBLService;

public class TableController implements TableBLService{
	Table t=new Table();
	
	
	public ArrayList<SalesVO> showSales(){
		return t.showSales();
	}
	
	public ArrayList<SalesVO> showSalesReturn(){
		return t.showSalesReturn();
	}
	
	public ArrayList<SalesVO> showImport(){
		return t.showImport();
	}
	
	public ArrayList<SalesVO> showImportReturn(){
		return t.showImportReturn();
	}
	
	public ArrayList<ReceivingBillVO> showSKD(Date startTime,Date endTime,String username, String customer){
		return t.showSKD(startTime,endTime,username,customer);
	}
	
	public ArrayList<PaymentBillVO> showFKD(Date startTime,Date endTime,String username, String customer){
		return t.showFKD(startTime,endTime,username,customer);
	}
	
	public ArrayList<CostBillVO> showXJFYD(Date startTime,Date endTime,String username, String account){
		return t.showXJFYD(startTime,endTime,username,account);
	}
	
	public ArrayList<DocumentPO> showStocks(String type,Date startTime,Date endTime,String username,String cangku){
		return t.showStocks(type,startTime,endTime,username,cangku);//type:OVERFLOW,DAMAGE,REPORT
	}

	public double showIncome(Date d1,Date d2) {
		return t.checkIncome(d1, d2);
	}

	public double showOutcome(Date d1,Date d2) {
		return t.checkOutcome(d1, d2);
	}

	public double showProfit(Date d1,Date d2) {
		return t.check(d1,d2);
	}

	@Override
	public ArrayList<String> getUser(String type,Date d1, Date d2) {
		
		return t.getUser(type,d1,d2);
	}

	@Override
	public ArrayList<String> getCustomer(String type,Date d1, Date d2) {
		return t.getCustomer(type,d1,d2);
	}
	
	
	
}
