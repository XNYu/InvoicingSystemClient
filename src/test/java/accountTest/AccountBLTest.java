package accountTest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import VO.AccountVO;
import VO.UserVO;
import businesslogic.accountbl.Account;
import businesslogic.accountbl.AccountController;
import businesslogic.accountbl.AccountInterfaceForBill;
import businesslogic.accountbl.AccountInterfaceForInitial;
import businesslogic.accountbl.AccountInterfaceOfChangeMoney;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.accountBLService.AccountBLService;

public class AccountBLTest {
	AccountBLService a = new AccountController(new UserVO(null, null, "asd", null, null));
	AccountInterfaceForBill accBill = new Account(new UserVO(null, null, "asd", null, null));
	AccountInterfaceForInitial accInitial = new Account(new UserVO(null, null, "asd", null, null));
	AccountInterfaceOfChangeMoney accChange= new Account(new UserVO(null, null, "asd", null, null));
	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		ArrayList<AccountVO> list = a.show();
		for(AccountVO vo:list){
			a.deleteAccount(vo.getName());
		}
		a.addAccount("马狗");
		a.addAccount("小蛇");
		a.addAccount("郭建朋");
		a.addAccount("昊总");
    }

	@After
    public void tearDown(){
		ArrayList<AccountVO> list = a.show();
		for(AccountVO vo:list){
			a.deleteAccount(vo.getName());
		}
    }


	@Test
	public void addAccountTest1() {
        assertTrue(a.addAccount("0001")==ResultMessage.Failure);
	}

	@Test
	public void addAccountTest2() {
        assertTrue(a.addAccount("asdda")==ResultMessage.Failure);
	}

	@Test
	public void addAccountTest3() {
        assertTrue(a.addAccount("高蛋")==ResultMessage.Success);
	}

	@Test
	public void addAccountTest4() {
        assertTrue(a.addAccount("马狗")==ResultMessage.Failure);
	}


	@Test
	public void delAccountTest1() {
        assertTrue(a.deleteAccount("炜神")==ResultMessage.Failure);
	}

	@Test
	public void delAccountTest2() {
        assertTrue(a.deleteAccount("昊总")==ResultMessage.Success);
	}

	@Test
	public void delAccountTest3() {
		a.deleteAccount("昊总");
        assertTrue(a.deleteAccount("昊总")==ResultMessage.Failure);
	}

	@Test
	public void delAccountTest4() {
        assertTrue(a.deleteAccount("asd")==ResultMessage.Failure);
	}

	@Test
	public void delAccountTest5() {
        assertTrue(a.deleteAccount("0001")==ResultMessage.Failure);
	}

	@Test
	public void modifyAccountTest1() {
        assertTrue(a.modifyAccount("郭建朋", "0002")==ResultMessage.Failure);
	}

	@Test
	public void modifyAccountTest2() {
        assertTrue(a.modifyAccount("郭建朋", "asd")==ResultMessage.Failure);
	}

	@Test
	public void modifyAccountTest3() {
        assertTrue(a.modifyAccount("郭建朋", "郭建朋")==ResultMessage.Success);
	}

	@Test
	public void modifyAccountTest4() {
        assertTrue(a.modifyAccount("郭建朋", "小黑")==ResultMessage.Success);
	}

	@Test
	public void modifyAccountTest5() {
        assertTrue(a.modifyAccount("郭建朋", "小蛇")==ResultMessage.Failure);
	}

	@Test
	public void modifyAccountTest6() {
        assertTrue(a.modifyAccount("炜神", "小蛇")==ResultMessage.Failure);
	}

	@Test
	public void searchAccountTest1() {
		assertTrue(a.searchAccount("0001").size() == 0);
	}

	@Test
	public void searchAccountTest2() {
		assertTrue(a.searchAccount("asd").size() == 0);
	}

	@Test
	public void searchAccountTest3() {
		assertTrue(a.searchAccount("高蛋").size() == 0);
	}

	@Test
	public void searchAccountTest4() {
		assertTrue(a.searchAccount("马狗").size() != 0);
	}

	@Test
	public void searchAccountTest5() {
		assertTrue(a.searchAccount("狗").size() != 0);
	}

	@Test
	public void showAccountTest() {
		assertTrue(a.show().size() == 4);
	}

	@Test
	public void showAccountTest1() {
		assertTrue(accBill.showAccount().size() == 4);
	}

	@Test
	public void getAccountTest1() {
		assertTrue(accBill.getAccountPO("asd") == null);
	}

	@Test
	public void getAccountTest2() {
		assertTrue(accBill.getAccountPO("0001") == null);
	}

	@Test
	public void getAccountTest3() {
		assertTrue(accBill.getAccountPO("高蛋") == null);
	}

	@Test
	public void getAccountTest4() {
		assertTrue(accBill.getAccountPO("马狗") != null);
	}

	@Test
	public void changeMoneyTest1() {
		assertTrue(accChange.changeMoney("asd", 10.0) == ResultMessage.Failure);
	}

	@Test
	public void changeMoneyTest2() {
		assertTrue(accChange.changeMoney("0001", 10.0) == ResultMessage.Failure);
	}

	@Test
	public void changeMoneyTest3() {
		assertTrue(accChange.changeMoney("郭建朋", 10.0) == ResultMessage.Success);
	}

	@Test
	public void changeMoneyTest4() {
		assertTrue(accChange.changeMoney("炜神", 10.0) == ResultMessage.Failure);
	}
}
