package customerTest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.customerbl.Customer;
import businesslogic.customerbl.CustomerController;
import businesslogic.salesbl.Sales;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.customerBLService.CustomerBLService;
import VO.CustomerVO;
import VO.UserVO;


public class CustomerTest {
	@Before
    public void setUp() throws Exception {
		Customer c=new Customer();
		c.delAll();
	}

	@After
    public void tearDown(){
		Customer c=new Customer();
		c.delAll();
	}
	
	@Test
	public void addCustomerTest1() {
		//正确
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"))!=null);
	}
	
	@Test
	public void addCustomerTest2() {
		//重复添加
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
        assertTrue(c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"))==null);
	}
	
	@Test
	public void delCustomerTest1() {
		//删除成功
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.delCustomer("00001").equals(ResultMessage.Success));
	}
	
	@Test
	public void delCustomerTest2() {
		//删除不存在ID
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(c.delCustomer("00001").equals(ResultMessage.Failure));
	}
	
	@Test
	public void modifyCustomerTest1() {
		//修改成功
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
        assertTrue(c.modifyCustomer(new CustomerVO("00001", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"))==ResultMessage.Success);
	}
	
	@Test
	public void modifyCustomerTest2() {
		//不存在的客户ID
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(c.modifyCustomer(new CustomerVO("00001", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"))==ResultMessage.Failure);
	}
	
	
	
	@Test
	public void searchCustomerTest1() {
		//查找成功
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.searchCustomer("00001").getID().equals("00001"));
	}
	
	@Test
	public void searchCustomerTest2() {
		//未查找到
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(c.searchCustomer("0001") == null);
	}
	
	@Test
	public void showCustomerTest1() {
		//展示成功
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.showCustomer().size()==1);
	}
	
	@Test
	public void showCustomerTest2() {
		//无符合条件项
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		assertTrue(c.showCustomer().size()==0);
	}
	
	@Test
	public void vagueFindTest1() {
		//无符合条件项
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.vagueFind("123").size()==0);
	}
	
	@Test
	public void vagueFindTest2() {
		//查找ID
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.vagueFind("001").size()==1);
	}
	
	@Test
	public void vagueFindTest3() {
		//查找姓名
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		assertTrue(c.vagueFind("马").size()==1);
	}
	
	@Test
	public void changeReceiveTest1() {
		//正数
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		Customer cus=new Customer();
		cus.changeReceive("马昕",100);
		assertTrue(c.vagueFind("马昕").get(0).getReceiving()==100);
	}
	
	@Test
	public void changeReceiveTest2() {
		//负数
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		Customer cus=new Customer();
		cus.changeReceive("马昕",-100);
		assertTrue(c.vagueFind("马昕").get(0).getReceiving()==-100);
	}
	
	@Test
	public void changePayTest1() {
		//正数
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		Customer cus=new Customer();
		cus.changePay("马昕",100);
		assertTrue(c.vagueFind("马昕").get(0).getPayment()==100);
	}
	
	@Test
	public void changePayTest2() {
		//负数
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		Customer cus=new Customer();
		cus.changePay("马昕",-100);
		assertTrue(c.vagueFind("马昕").get(0).getPayment()==-100);
	}
	
	@Test
	public void getRankTest1() {
		//负数
		CustomerBLService c = new CustomerController();
		c.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		c.addCustomer(new CustomerVO("", "进货商", 1, "马昕","123", "123", "123", "123",123,0,0,"郭建朋"));
		Customer cus=new Customer();
		cus.changePay("马昕",-100);
		assertTrue(c.vagueFind("马昕").get(0).getRank()==1);
	}
	
}
