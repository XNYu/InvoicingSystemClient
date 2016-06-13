package userTest;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.salesbl.Sales;
import businesslogic.userbl.User;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.userBLService.UserBLService;
import VO.UserVO;


public class UserTest {
	@Before
    public void setUp() throws Exception {
		User u=new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.delAll();
		
	}

	@After
    public void tearDown(){
		User u=new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.delAll();
	}
	
	@Test
	public void addUserTest1() {
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"))==ResultMessage.Success);
	}
	
	@Test
	public void addUserTest2() {
		//重复添加
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"))==ResultMessage.Failure);
	}
	
	@Test
	public void registerTest1() {
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.register(new UserVO("123456","销售经理","马昕","1001","最高权限"))==ResultMessage.Success);
	}
	
	@Test
	public void registerTest2() {
		//重复添加
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.register(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.register(new UserVO("123456","销售经理","马昕","1001","最高权限"))==ResultMessage.Failure);
	}
	
	@Test
	public void delUserTest1() {
		//删除成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.delUser("123456")==ResultMessage.Success);
	}
	

	@Test
	public void delUserTest2() {
		//删除成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.delUser("1234")==ResultMessage.Failure);
	}
	
	@Test
	public void modifyUserTest1() {
		//修改成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.modifyUser(new UserVO("123456","销售经理","马昕","1001","最高权限"))==ResultMessage.Success);
	}
	
	@Test
	public void modifyUserTest2() {
		//ID不存在
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.modifyUser(new UserVO("12346","销售经理","马昕","1001","最高权限"))==ResultMessage.Failure);
	}
	
	
	
	@Test
	public void searchUserTest1() {
		//查找成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.searchUser("123456").getID().equals("123456"));
	}
	
	@Test
	public void searchUserTest2() {
		//ID不存在
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.searchUser("1236")==null);
	}
	
	@Test
	public void loginTest1() {
		//登陆成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.login("123456","1001").getID().equals("123456"));
	}
	
	@Test
	public void loginTest2() {
		//密码错误
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.login("123456","11")==null);
	}
	
	@Test
	public void loginTest3() {
		//ID错误
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.login("12336","1001")==null);
	}
	
	@Test
	public void showUserTest1() {
		//显示成功
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
		u.addUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.showUser().get(0).getID().equals("123456"));
	}
	
	@Test
	public void showUserTest2() {
		//未显示
		User u = new User();
		u.setUser(new UserVO("123456","销售经理","马昕","1001","最高权限"));
        assertTrue(u.showUser().size()==0);
	}
	
}
