package tableTest;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import VO.PromotionVO;
import VO.PromotionVO.types;
import businesslogic.billbl.Bill;
import businesslogic.document.Document;
import businesslogic.salesbl.Sales;
import businesslogic.stockbl.Stock;
import businesslogic.tablebl.Table;
import businesslogic.tablebl.TableController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.tableBLService.TableBLService;

public class TableBLTest {
	TableBLService t=new TableController();
	Date startTime,endTime;
	String username,customer,account,type,cangku;
	@Before
	public void setUp() throws Exception {
		
		startTime=new Date();
		endTime=new Date();
		username=customer=account=type=cangku=null;
	}
	
	@Test
	public void showSalesTest(){
		assertTrue(t.showSales()!=null);
	}
	
	@Test
	public void showSalesReturnTest(){
		assertTrue(t.showSalesReturn()!=null);
	}

	@Test
	public void showImportTest(){
		assertTrue(t.showImport()!=null);
	}

	@Test
	public void showImportReturnTest(){
		assertTrue(t.showImportReturn()!=null);
	}
	
	@Test
	public void showSKDTest(){
		assertTrue(t.showSKD(startTime, endTime, username, customer)!=null);
	}
	
	@Test
	public void showFKDTest(){
		assertTrue(t.showFKD(startTime, endTime, username, customer)!=null);
	}
	
	@Test
	public void showXJFYDTest(){
		assertTrue(t.showXJFYD(startTime, endTime, username, account)!=null);
	}
	
	@Test
	public void showStocksTest(){
		assertTrue(t.showStocks(type, startTime, endTime, username, cangku)!=null);
	}
	
	
	@Test
	public void showIncomeTest(){
		assertTrue(t.showIncome(startTime, endTime)!=99999999);
	}
	
	@Test
	public void showOutcomeTest(){
		assertTrue(t.showOutcome(startTime, endTime)!=99999999);
	}
	
	@Test
	public void showProfitTest(){
		assertTrue(t.showProfit(startTime, endTime)!=99999999);
	}
	
	@Test
	public void getUserTest(){
		assertTrue(t.getUser("SKD", startTime, endTime)!=null);
	}
	
	@Test
	public void getCustomerTest(){
		assertTrue(t.getCustomer("SKD", startTime, endTime)!=null);
	}
	
	
}
