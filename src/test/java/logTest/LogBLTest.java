package logTest;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.logbl.Log;
import businesslogic.logbl.LogController;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.logBLService.LogBLService;

public class LogBLTest {
	LogBLService log = new LogController();
	LogInterface logint = new Log();
	SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss");

	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		log.delAll();
		logint.buildlog("马狗", "hello");
	}

	@After
    public void tearDown(){
		log.delAll();
	}


	@Test
	public void searchtest1() {
		assertTrue(log.searchlogs(df.format(new Date()), df.format(new Date())).size()!=0);
	}


	@Test
	public void searchtest2() {
		log.delAll();
		assertTrue(log.searchlogs(df.format(new Date()), df.format(new Date())).size()==0);
	}

	@Test
	public void buildtest() {
		assertTrue(logint.buildlog("马狗", "hello")==ResultMessage.Success);
	}

}
