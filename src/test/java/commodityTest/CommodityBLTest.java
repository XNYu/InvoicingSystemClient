package commodityTest;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.commoditybl.CommodityController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;


public class CommodityBLTest {
	CommodityBLService cbs = new CommodityController();

	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		cbs.addCommodityType("新分类", "全部商品分类");
		cbs.addCommodityType("子分类1", "新分类");
		cbs.addCommodityType("子分类2", "新分类");
		cbs.addCommodity("名称", "型号", 0, 2.1, 3.2, "子分类1");
	}

	@After
    public void tearDown(){
		cbs.clear();

	}
	@Test
	public void addCategory() {
		assertTrue(cbs.addCommodityType("新分类", "全部商品分类")!=ResultMessage.Success);
	}
	@Test
	public void addCategory1() {
		assertTrue(cbs.addCommodityType("新分类", "全部商品分类")!=ResultMessage.Success);
	}
	@Test
	public void addCategory2() {
		assertTrue(cbs.addCommodityType("子分类1", "新分类")!=ResultMessage.Success);
	}
	@Test
	public void addCategory3() {
		assertTrue(cbs.addCommodityType("子分类2", "新分类")!=ResultMessage.Success);
	}

	@Test
	public void addCommodity() {
		assertTrue(cbs.addCommodity("名称1", "型号1", 0, 2.0, 3.0, "全部商品分类")!=ResultMessage.Failure);
	}
	@Test
	public void addCommodity1() {
		assertTrue(cbs.addCommodity("名称1", "型号1", 0, 2.0, 3.0, "子分类1")!=ResultMessage.Failure);
	}
	@Test
	public void addCategory4() {
		assertTrue(cbs.addCommodityType("子分类4", "新分类")!=ResultMessage.Failure);
	}

	@Test
	public void addCommodity2() {
		assertTrue(cbs.addCommodity("名称1", "型号2", 0, 2.0, 3.0, "子分类1")!=ResultMessage.Failure);
	}
	@Test
	public void addCommodity3() {
		assertTrue(cbs.addCommodity("名称1", "型号1", 0, 2.0, 3.0, "子分类1")!=ResultMessage.Failure);
	}
	@Test
	public void addCommodity4() {
		assertTrue(cbs.addCommodity("名称2", "型号2", 0, 2.0, 3.0, "子分类2")!=ResultMessage.Failure);
	}
	@Test
	public void addCommodity5() {
		assertTrue(cbs.addCommodity("名称3", "型号3", 0, 2.0, 3.0, "新分类")!=ResultMessage.Success);
	}
	@Test
	public void addCommodity6() {
		assertTrue(cbs.addCommodity("名称6", "型号6", 0, 1.0, 3.0, "新分类1")!=ResultMessage.Failure);
	}
	@Test
	public void addCategory5() {
		assertTrue(cbs.addCommodityType("子分类5", "子分类1")!=ResultMessage.Success);
	}
	@Test
	public void addCategory6() {
		assertTrue(cbs.addCommodityType("子分类5", "新分类")==ResultMessage.Success);
	}
	@Test
	public void delCategory() {
		assertTrue(cbs.delCommodityType("全部商品分类")!=ResultMessage.Failure);
	}
	@Test
	public void delCategory1() {
		assertTrue(cbs.delCommodityType("新分类")!=ResultMessage.Success);
	}
	@Test
	public void delCategory2() {

		assertTrue(cbs.delCommodityType("子分类1")!=ResultMessage.Success);
	}
	@Test
	public void delCategory3() {
		assertTrue(cbs.delCommodityType("子分类5")==ResultMessage.Success);
	}
	@Test
	public void delCommodity() {
		assertTrue(cbs.delCommodity("1231")!=ResultMessage.Success);
	}
	@Test
	public void delCommodity1() {
		assertTrue(cbs.delCommodity("A0000000000003")!=ResultMessage.Success);
	}
	@Test
	public void modifyCommodity1() {
		assertTrue(cbs.modifyCommodity("A0000000000003", "asd", "asd")!=ResultMessage.Success);
	}
	@Test
	public void modifyCommodity2() {
		String s = cbs.showCommodity().get(0).getID();
		assertTrue(cbs.modifyCommodity(s, "asd", "asd")==ResultMessage.Success);
	}
	@Test
	public void modifyCommodity3() {
		String s = cbs.showCommodity().get(0).getID();
		String name = cbs.showCommodity().get(0).getName();
		assertTrue(cbs.modifyCommodity(s, name, "asd")==ResultMessage.Success);
	}
	@Test
	public void showCommodity() {

		assertTrue(cbs.showCommodity().size()!=0);
	}
	@Test
	public void showCommodity1() {

		assertTrue(cbs.showCommodity("0").size()!=0);
	}
	@Test
	public void showCommodityType() {

		assertTrue(cbs.showTree()!=null);
	}
	@Test
	public void showCommodity2() {

		assertTrue(cbs.findCommodity("0")!=null);
	}
}
