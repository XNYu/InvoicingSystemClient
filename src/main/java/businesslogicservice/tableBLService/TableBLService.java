package businesslogicservice.tableBLService;

import java.util.ArrayList;
import java.util.Date;

import PO.*;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.SalesVO;

public interface TableBLService{
	public ArrayList<SalesVO> showSales();
	public ArrayList<SalesVO> showSalesReturn();
	public ArrayList<SalesVO> showImport();
	public ArrayList<SalesVO> showImportReturn();
	public ArrayList<ReceivingBillVO> showSKD(Date startTime,Date endTime,String username,String customer);
	public ArrayList<PaymentBillVO> showFKD(Date startTime,Date endTime,String username,String customer);
	public ArrayList<CostBillVO> showXJFYD(Date startTime,Date endTime,String username,String account);
	public ArrayList<DocumentPO> showStocks(String type,Date startTime,Date endTime,String username,String cangku);
	public double showIncome(Date d1,Date d2);
	public double showOutcome(Date d1,Date d2);
	public double showProfit(Date d1,Date d2);
	public ArrayList<String> getUser(String type,Date d1,Date d2);
	public ArrayList<String> getCustomer(String type,Date d1,Date d2);
}
