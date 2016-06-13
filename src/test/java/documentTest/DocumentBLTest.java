package documentTest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import PO.AccountPO;
import PO.CustomerPO;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.TransferList;
import businesslogic.commoditybl.CommodityController;
import businesslogic.document.DocumentController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.documentBLService.DocumentBLService;


public class DocumentBLTest {
	DocumentBLService dbs = new  DocumentController();
	CommodityBLService cbs = new CommodityController();
	
	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		cbs.addCommodityType("全部商品分类", "新分类");
		cbs.addCommodity("asd", "asd", 0, 1.1, 2.2, "新分类");
		String id = cbs.showCommodity().get(0).getID();
		dbs.createOverflow("asd", "asd", id, 0, 2);
		dbs.createDamage("asd", "asd", id, 2, 1);
		dbs.createReport("asd", "asd", id, 20);
		

	
	}

	@After
    public void tearDown(){
		cbs.clear();
	}
	@Test
	public void showDocument(){
		assertTrue(dbs.showAllDocument().size()!=0);
	}
	@Test
	public void showDocument1(){
		assertTrue(dbs.showDocument("OVERFLOW").size()!=0);
	}
	@Test
	public void showDocument2(){
		assertTrue(dbs.showDocument("DAMAGE").size()!=0);
	}
	@Test
	public void showDocument3(){
		assertTrue(dbs.showDocument("REPORT").size()!=0);
	}

}
