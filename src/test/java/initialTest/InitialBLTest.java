package initialTest;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import PO.CommodityPO;
import VO.AccountVO;
import VO.CustomerVO;
import VO.InitialVO;
import VO.UserVO;
import businesslogic.initialbl.InitialController;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.initialBLService.InitialBLService;

public class InitialBLTest {
	InitialBLService ini = new InitialController(new UserVO(null, null, "asd", null, null));
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	@Before
	public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		ini.delAll();
	}

	@After
	public void tearDown() throws Exception {
		ini.delAll();
	}

	@Test
	public void newInitialtest() {
		assertTrue(ini.createInitial(new InitialVO("", new ArrayList<CommodityPO>(),
				new ArrayList<CustomerVO>(), new ArrayList<AccountVO>()))==ResultMessage.Success);
	}

	@Test
	public void find1test() {
		ini.createInitial(new InitialVO(df.format(new Date()), new ArrayList<CommodityPO>(),new ArrayList<CustomerVO>(), new ArrayList<AccountVO>()));
		assertTrue(ini.search(df.format(new Date()))!=null);
	}

	@Test
	public void find2test() {
		assertTrue(ini.search("2012/12/13")==null);
	}

}
