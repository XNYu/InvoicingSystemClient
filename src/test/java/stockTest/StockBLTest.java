package stockTest;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.commoditybl.CommodityController;
import businesslogic.stockbl.StockController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.stockBLService.StockBLService;


public class StockBLTest {
	StockBLService sbs = new StockController();
	CommodityBLService cbs = new CommodityController();
	
	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		cbs.addCommodityType("全部商品分类", "新分类");
		cbs.addCommodity("asd", "asd", 0, 1.1, 2.2, "新分类");
		String id = cbs.showCommodity().get(0).getID();
		sbs.createStock("asd", "asd", id, "STOCKIN", "20141212", 12, 5.0);
		sbs.createStock("asd", "asd", id, "STOCKOUT", "20141212", 12, 5.0);
		sbs.createStock("asd", "asd", id, "IMP", "20141212", 12, 5.0);
		sbs.createStock("asd", "asd", id, "EXP", "20141212", 12, 5.0);
		
	}

	@After
    public void tearDown(){
		
	}
	@Test
	public void showStock(){
		assertTrue(sbs.showStock("0", "0").size()==0);
	}
	@Test
	public void showStock1(){
		assertTrue(sbs.showStock("0", "99999999").size()!=4);
	}
	@Test
	public void showStock2(){
		assertTrue(sbs.showStock("333","111").size()==0);
	}

}
