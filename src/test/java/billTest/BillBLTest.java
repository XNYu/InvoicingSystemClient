package billTest;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import PO.AccountPO;
import PO.CustomerPO;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.UserVO;
import businesslogic.billbl.Bill;
import businesslogic.billbl.BillController;
import businesslogic.billbl.BillInterfaceForExamine;
import businesslogic.billbl.BillInterfaceForTable;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.TransferList;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.billBLService.BillBLService;

public class BillBLTest {
	BillBLService bill = new BillController(new UserVO(null, null, "asd", null, null));
	BillInterfaceForExamine billExam = new Bill(new UserVO(null, null, "asd", null, null));
	BillInterfaceForTable billTable = new Bill(new UserVO(null, null, "asd", null, null));
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	@Before
    public void setUp() throws Exception {
		Datafactory.setIP("127.0.0.1");
		bill.delAll();
		bill.createbill(new ReceivingBillVO(bill.getBillID("SKD"), "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()));
		bill.createbill(new ReceivingBillVO(bill.getBillID("SKD"), "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,false,false));
		bill.createbill(new PaymentBillVO(bill.getBillID("FKD"), "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()));
		bill.createbill(new CostBillVO(bill.getBillID("XJFYD"), "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()));
	}

	@After
    public void tearDown(){
		bill.delAll();
	}


	@Test
	public void getIDTest1() {
		assertTrue(bill.getBillID("SKD").equals("SKD-"+df.format(new Date())+"-00002"));
	}

	@Test
	public void getIDTest2() {
		assertTrue(bill.getBillID("FKD").equals("FKD-"+df.format(new Date())+"-00001"));
	}

	@Test
	public void getIDTest3() {
		assertTrue(bill.getBillID("XJFYD").equals("XJFYD-"+df.format(new Date())+"-00001"));
	}

	@Test
	public void getBillExaminedTest() {
		assertTrue(bill.getBillExamined().size()!=0);
	}

	@Test
	public void getBillExamined1Test() {
		bill.modifyBill(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00001", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,true,false));
		assertTrue(bill.getBillExamined().size()==0);
	}

	@Test
	public void createBillTest1() {
		assertTrue(bill.createbill(new ReceivingBillVO(bill.getBillID("SKD"), "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()))==ResultMessage.Success);
	}

	@Test
	public void createBillTest2() {
		assertTrue(bill.createbill(new PaymentBillVO(bill.getBillID("FKD"), "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()))==ResultMessage.Success);
	}

	@Test
	public void createBillTest3() {
		assertTrue(bill.createbill(new CostBillVO(bill.getBillID("XJFYD"), "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()))==ResultMessage.Success);
	}



	@Test
	public void modifyBillTest1() {
		assertTrue(bill.modifyBill(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00001", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,true,false))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest2() {
		assertTrue(bill.modifyBill(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00001", "炜狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest3() {
		assertTrue(bill.modifyBill(new PaymentBillVO("FKD-"+df.format(new Date())+"-00000", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,true,false))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest4() {
		assertTrue(bill.modifyBill(new PaymentBillVO("FKD-"+df.format(new Date())+"-00000", "炜狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest5() {
		assertTrue(bill.modifyBill(new CostBillVO("XJFYD-"+df.format(new Date())+"-00000", "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist(),true,true,false))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest6() {
		assertTrue(bill.modifyBill(new CostBillVO("XJFYD-"+df.format(new Date())+"-00000", "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()))==ResultMessage.Success);
	}

	@Test
	public void modifyBillTest7() {
		assertTrue(bill.modifyBill(new CostBillVO("XJFYD-"+df.format(new Date())+"-00001", "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()))==ResultMessage.Failure);
	}

	@Test
	public void modifyBillTest8() {
		assertTrue(bill.modifyBill(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00002", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,true,false))==ResultMessage.Failure);
	}

	@Test
	public void modifyBillTest9() {
		assertTrue(bill.modifyBill(new PaymentBillVO("FKD-"+df.format(new Date())+"-00001", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist(),true,true,false))==ResultMessage.Failure);
	}


	@Test
	public void showTest1() {
		assertTrue(bill.show().size()!=0);
	}

	@Test
	public void showTest2() {
		bill.delAll();
		assertTrue(bill.show().size()==0);
	}

	@Test
	public void getexamineTest1() {
		assertTrue(billExam.getFKDListForExamined().size()!=0);
	}

	@Test
	public void getexamineTest2() {
		assertTrue(billExam.getSKDListForExamined().size()!=0);
	}

	@Test
	public void getexamineTest3() {
		assertTrue(billExam.getXJFYDListForExamined().size()!=0);
	}

	@Test
	public void getUserListTest1() {
		assertTrue(billTable.getUserlistOfSKD().size()!=0);
	}

	@Test
	public void getUserListTest2() {
		assertTrue(billTable.getUserlistOfFKD().size()!=0);
	}

	@Test
	public void getUserListTest3() {
		assertTrue(billTable.getUserlistOfXJFYD().size()!=0);
	}

	@Test
	public void getCustomerListTest1() {
		assertTrue(billTable.getCustomerlistOfSKD().size()!=0);
	}

	@Test
	public void getCustomerListTest2() {
		assertTrue(billTable.getCustomerlistOfFKD().size()!=0);
	}

	@Test
	public void getAccountListTest() {
		assertTrue(billTable.getAccountlistOfXJFYD().size()!=0);
	}

	@Test
	public void searchTest1() {
		assertTrue(billTable.searchSKD(new Date(), new Date(), "马狗", "高蛋").size()!=0);
	}

	@Test
	public void searchTest2() {
		assertTrue(billTable.searchFKD(new Date(), new Date(), "马狗", "高蛋").size()!=0);
	}

	@Test
	public void searchTest3() {
		assertTrue(billTable.searchFKD(new Date(), new Date(), "马狗", "高蛋").size()!=0);
	}

	@Test
	public void examineTest1() {
		assertTrue(billExam.examine(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00001", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()), true) == ResultMessage.Success);
	}

	@Test
	public void examineTest2() {
		assertTrue(billExam.examine(new ReceivingBillVO("SKD-"+df.format(new Date())+"-00005", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()), true) == ResultMessage.Failure);
	}

	@Test
	public void examineTest3() {
		assertTrue(billExam.examine(new PaymentBillVO("FKD-"+df.format(new Date())+"-00000", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()), true) == ResultMessage.Success);
	}

	@Test
	public void examineTest4() {
		assertTrue(billExam.examine(new PaymentBillVO("FKD-"+df.format(new Date())+"-00005", "马狗", 0, new CustomerPO(null, null, 0, "高蛋", null, null, null, null, 0, 0, 0, null), new TransferList().getRtlist()), true) == ResultMessage.Failure);
	}

	@Test
	public void examineTest5() {
		assertTrue(billExam.examine(new CostBillVO("XJFYD-"+df.format(new Date())+"-00000", "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()), true) == ResultMessage.Success);
	}

	@Test
	public void examineTest6() {
		assertTrue(billExam.examine(new CostBillVO("XJFYD-"+df.format(new Date())+"-00005", "马狗", 0, new AccountPO("高蛋"), new EntryList().getRtlist()), true) == ResultMessage.Failure);
	}
}
