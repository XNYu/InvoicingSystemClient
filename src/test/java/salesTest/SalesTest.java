package salesTest;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import PO.AccountPO;
import PO.CommodityPO;
import PO.CustomerPO;
import VO.CostBillVO;
import VO.CustomerVO;
import VO.PaymentBillVO;
import VO.PromotionVO;
import VO.ReceivingBillVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.TransferList;
import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityController;
import businesslogic.customerbl.Customer;
import businesslogic.salesbl.Sales;
import businesslogic.salesbl.SalesController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;

public class SalesTest{
	CommodityBLService cbs = new CommodityController();
	@Before
    public void setUp() throws Exception {
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
	public void makeImportTest1() {
		SalesBLService  a= new SalesController();
		//空的商品列表
		ArrayList<CommodityPO> commodityList=new ArrayList<CommodityPO>();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		
        assertTrue(a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","",commodityList,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeImportTest2() {
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		
        assertTrue(a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"))!=null);
	}
	
	@Test
	public void makeImportReturnTest1() {
		SalesBLService  a= new SalesController();
		ArrayList<CommodityPO> commodityList=new ArrayList<CommodityPO>();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		//空的商品列表
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeImportReturn(new SalesVO("", "","马昕", "仓库1",
    			"","",commodityList,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeImportReturnTest2() {
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//无ID
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeImportReturn(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeImportReturnTest3() {
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		Sales s=new Sales();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
        assertTrue(a.makeImportReturn(s.showImport().get(0))!=null);
	}
	
	@Test
	public void makeSalesTest1() {
		SalesBLService  a= new SalesController();
		//空的商品列表
		ArrayList<CommodityPO> commodityList=new ArrayList<CommodityPO>();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",commodityList,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeSalesTest2() {
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"))!=null);
	}
	
	
	@Test
	public void makeSalesReturnTest1() {
		SalesBLService  a= new SalesController();
		//空的商品列表
		ArrayList<CommodityPO> commodityList=new ArrayList<CommodityPO>();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeSalesReturn(new SalesVO("", "","马昕", "仓库1",
    			"","",commodityList,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeSalesReturnTest2() {

		Commodity commodity=new Commodity();
	
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//无ID
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(a.makeSalesReturn(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"))==null);
	}
	
	@Test
	public void makeSalesReturnTest3() {
		
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		Sales s=new Sales();
		
        assertTrue(a.makeSalesReturn(s.showSales().get(0))!=null);
	}
	
	
	@Test
	public void showSalesTest() {
		// TODO Auto-generated method stub
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		Sales s=new Sales();
		ArrayList<SalesVO> slist=s.showSales();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}

	@Test
	public void showImportTest() {
		// TODO Auto-generated method stub
		Sales s=new Sales();
		
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.showImport();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}

	@Test
	public void showSalesReturnTest() {
		// TODO Auto-generated method stub
		Sales s=new Sales();
		
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		a.makeSalesReturn(s.showSales().get(0));
		ArrayList<SalesVO> slist=s.showSalesReturn();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}

	@Test
	public void showImportReturnTest() {
		// TODO Auto-generated method stub
		Sales s=new Sales();
		
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		a.makeImportReturn(s.showImport().get(0));
		ArrayList<SalesVO> slist=s.showImportReturn();
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}
	
	@Test
	public void searchSalesTest1(){
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		ArrayList<SalesVO> slist=s.searchSales("销售单",null,null,null,new Date(),new Date());
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}
	
	@Test
	public void searchSalesTest2(){
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单","马昕",null,null,new Date(),new Date());
		assertTrue(slist.get(slist.size()-1).getCustomer().equals("马昕"));
	}
	
	@Test
	public void searchSalesTest3(){
		//未找到客户
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单","123",null,null,new Date(),new Date());
		assertTrue(slist.size()==0);
	}
	
	@Test
	public void searchSalesTest4(){
		//查找业务员
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单",null,"马昕",null,new Date(),new Date());
		assertTrue(slist.get(slist.size()-1).getSalesman().equals("马昕"));
	}
	
	@Test
	public void searchSalesTest5(){
		//未找到业务员
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单",null,"马",null,new Date(),new Date());
		assertTrue(slist.size()==0);
		
	}
	
	@Test
	public void searchSalesTest6(){
		//查找仓库
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单",null,null,"仓库1",new Date(),new Date());
		assertTrue(slist.get(slist.size()-1).getStock().equals("仓库1"));
		
	}
	
	@Test
	public void searchSalesTest7(){
		//未找到仓库
		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		ArrayList<SalesVO> slist=s.searchSales("销售单",null,null,"仓库",new Date(),new Date());
		assertTrue(slist.size()==0);
		
	}
	
	@Test
	public void getNumOfXSDTest(){

		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		SimpleDateFormat sdf=new SimpleDateFormat();
		assertTrue(s.getNumOfXSD(sdf.format(new Date()))==1);
		
	}
	
	@Test
	public void getNumOfJHDTest(){

		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		
		SimpleDateFormat sdf=new SimpleDateFormat();
		assertTrue(s.getNumOfJHD(sdf.format(new Date()))==1);
		
	}
	
	@Test
	public void getNumOfJHTHDTest(){

		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeImport(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		a.makeImportReturn(s.showImport().get(0));
		SimpleDateFormat sdf=new SimpleDateFormat();
		assertTrue(s.getNumOfJHTHD(sdf.format(new Date()))==1);
		
	}
	
	@Test
	public void getNumOfXSTHDTest(){

		Sales s=new Sales();
		Commodity commodity=new Commodity();
		ArrayList<CommodityPO> poList=commodity.showCom();
		CommodityPO com=poList.get(0);
		com.setAmount(100);
		com.setImpPrice(10);
		ArrayList<CommodityPO> comlist=new ArrayList<CommodityPO>();
		comlist.add(com);
		//正确
		SalesBLService  a= new SalesController();
		ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
		a.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		a.makeSales(new SalesVO("", "","马昕", "仓库1",
    			"","马昕",comlist,0,0, 0,100, "",promotionlist,"审批通过"));
		a.makeSalesReturn(s.showSales().get(0));
		SimpleDateFormat sdf=new SimpleDateFormat();
		assertTrue(s.getNumOfXSTHD(sdf.format(new Date()))==1);
		
	}
}
