package salesTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import PO.CommodityPO;
import VO.CustomerVO;
import VO.UserVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityController;
import businesslogic.customerbl.Customer;
import businesslogic.salesbl.Sales;
import businesslogic.utilitybl.Datafactory;
import businesslogicservice.commodityBLService.CommodityBLService;

public class sales_totalTest {
	
	@Before
    public void setUp() throws Exception {
		CommodityBLService cbs = new CommodityController();
		Datafactory.setIP("127.0.0.1");
		Sales s=new Sales();
		s.delAll();
		s.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		Customer c=new Customer();
		c.delAll();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123000000,0,0,"郭建朋"));
		cbs.addCommodityType("新分类", "全部商品分类");
		cbs.addCommodityType("子分类1", "新分类");
		cbs.addCommodityType("子分类2", "新分类");
		cbs.addCommodity("名称", "型号", 0, 2.1, 3.2, "子分类1");
		
	}

	@After
    public void tearDown(){
		Sales s=new Sales();
		s.delAll();
	}
	
	@Test
	public void totalTest1(){
		//正确输入
		Sales sales =new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		sales.setCommodityList(comlist);
		assertTrue(sales.total()==1000);
	}
	
	@Test
	public void totalTest2(){
		//输入数量<0
		Sales sales =new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(-100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		sales.setCommodityList(comlist);
		assertTrue(sales.total()!=1000);
	}
	
	@Test
	public void totalTest3(){
		//输入价格<0
		Sales sales =new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(-10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		sales.setCommodityList(comlist);
		assertTrue(sales.total()!=1000);
	}
}
