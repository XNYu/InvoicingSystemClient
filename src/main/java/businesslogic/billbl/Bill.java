package businesslogic.billbl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import PO.BillPO;
import PO.CostBillPO;
import PO.PaymentBillPO;
import PO.ReceivingBillPO;
import VO.BillVO;
import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.UserVO;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.Datafactory;
import businesslogic.utilitybl.ResultMessage;
import dataservice.billDataService.BillDataService;


public class Bill implements BillInterfaceOfModify,BillInterfaceOfRed_flush,BillInterfaceForTable,BillInterfaceForExamine{
	private BillDataService billDataService ;
	private String userinfo = null;
	private LogInterface loginterface = new Log();



	/*
	 * 构造函数，通过datafactory获得data层的引用
	 */
	public Bill(UserVO user){
		this.setUserinfo(user.getName());//传入操作员姓名
		try {
			billDataService=Datafactory.getBillData();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//处理当前使用用户的信息
	public String getUserinfo() {
		return userinfo;
	}

	private void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}




	/*
	 * transform函数，用于执行vo和po之间的转换
	 */
	private ReceivingBillPO  transformOfReceiving(ReceivingBillVO bvo){
		return new ReceivingBillPO(bvo.getNumberID(), bvo.getUsername(), bvo.getSum(), bvo.getCus(), bvo.getTransferList(), bvo.isExamined(), bvo.isRead(), bvo.isModified());
	}

	private ReceivingBillVO  transformOfReceiving(ReceivingBillPO bpo){
		return new ReceivingBillVO(bpo.getNumberID(),  bpo.getUsername(), bpo.getSum(), bpo.getCus(), bpo.getTransferList(), bpo.isExamined(), bpo.isRead(), bpo.isModified());
	}

	private PaymentBillPO  transformOfPayment(PaymentBillVO bvo){
		return new PaymentBillPO(bvo.getNumberID(), bvo.getUsername(), bvo.getSum(), bvo.getCus(), bvo.getTransferList(), bvo.isExamined(), bvo.isRead(), bvo.isModified());
	}

	private PaymentBillVO  transformOfPayment(PaymentBillPO bpo){
		return new PaymentBillVO(bpo.getNumberID(), bpo.getUsername(), bpo.getSum(), bpo.getCus(), bpo.getTransferList(), bpo.isExamined(), bpo.isRead(), bpo.isModified());
	}

	private CostBillPO  transformOfCost(CostBillVO bvo){
		return new CostBillPO(bvo.getNumberID(), bvo.getUsername(), bvo.getSum(), bvo.getAcc(), bvo.getEntryList(), bvo.isExamined(), bvo.isRead(), bvo.isModified());
	}

	private CostBillVO  transformOfCost(CostBillPO bpo){
		return new CostBillVO(bpo.getNumberID(), bpo.getUsername(), bpo.getSum(), bpo.getAcc(), bpo.getEntryList(), bpo.isExamined(), bpo.isRead(), bpo.isModified());
	}




	/*
	 * 制定付款/收款单
	 */
	public ResultMessage createBill(BillVO bill) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			switch(bill.getNumberID().substring(0, 3)){
				case "SKD":  flag=billDataService.add(transformOfReceiving((ReceivingBillVO)bill));break;
				case "FKD":  flag=billDataService.add(transformOfPayment((PaymentBillVO)bill));break;
				case "XJF":  flag=billDataService.add(transformOfCost((CostBillVO)bill));break;
				default: flag=false;break;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(userinfo, "新建账单："+bill.getNumberID());
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}

	}



	/*
	 * 修改付款/收款单
	 */
	@Override
	public ResultMessage modifyBill(BillVO bill) {
		// TODO 自动生成的方法存根
		boolean flag=false;
		try {
			switch(bill.getNumberID().substring(0, 3)){
				case "SKD":  flag=billDataService.modify(transformOfReceiving((ReceivingBillVO)bill));break;
				case "FKD":  flag=billDataService.modify(transformOfPayment((PaymentBillVO)bill));break;
				case "XJF":  flag=billDataService.modify(transformOfCost((CostBillVO)bill));break;
				default: flag=false;break;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			loginterface.buildlog(userinfo, "修改账单："+bill.getNumberID());
			return ResultMessage.Success;
		}else{
			return ResultMessage.Failure;
		}

	}

	/*
	 * 红冲
	 */
	@Override
	public ResultMessage red_flush(BillVO bill) {
		// TODO 自动生成的方法存根

		BillVO bill_new = null;

		//将原账单的总额改为负
		try {
			switch(bill.getNumberID().substring(0, 3)){
			case "SKD":  bill_new = new ReceivingBillVO(billDataService.getBillID("SKD"), bill.getUsername(), -bill.getSum(), ((ReceivingBillVO)bill).getCus(), ((ReceivingBillVO)bill).getTransferList(), true, false, false);break;
			case "FKD":  bill_new = new PaymentBillVO(billDataService.getBillID("FKD"), bill.getUsername(), -bill.getSum(), ((PaymentBillVO)bill).getCus(), ((PaymentBillVO)bill).getTransferList(), true, false, false);break;
			case "XJF":  bill_new = new CostBillVO(billDataService.getBillID("XJFYD"), bill.getUsername(), -bill.getSum(), ((CostBillVO)bill).getAcc(), ((CostBillVO)bill).getEntryList(), true, false, false);break;

		}

		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return createBill(bill_new);
	}

	/*
	 * 显示单据列表
	 */
	public ArrayList<BillVO> show() {
		// TODO 自动生成的方法存根
		ArrayList<BillVO> list = new ArrayList<BillVO>();

		try {
			ArrayList<BillPO> plist = billDataService.show();
			for(int i=0;i<plist.size();i++){
				switch(plist.get(i).getNumberID().substring(0, 3)){
				case "SKD":  list.add(transformOfReceiving((ReceivingBillPO)plist.get(i)));break;
				case "FKD":  list.add(transformOfPayment((PaymentBillPO)plist.get(i)));break;
				case "XJF":  list.add(transformOfCost((CostBillPO)plist.get(i)));break;

				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	/*
	*结束制定单据
	*/
	public void endBillBuilding() {
		// TODO 自动生成的方法存根
		try {
			billDataService.finish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/*
	 * 获得总经理审批过的单据
	 */
	public ArrayList<BillVO> getBillExamined() {
		// TODO 自动生成的方法存根

		ArrayList<BillVO> volist = new  ArrayList<BillVO>();
		ArrayList<BillPO> polist = null ;
		try {
			polist = billDataService.getBillExamined();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<polist.size();i++){

			switch(polist.get(i).getNumberID().substring(0, 3)){
			case "SKD":  volist.add(transformOfReceiving((ReceivingBillPO)(polist.get(i))));break;
			case "FKD":  volist.add(transformOfPayment((PaymentBillPO)polist.get(i)));break;
			case "XJF":  volist.add(transformOfCost((CostBillPO)polist.get(i)));break;

			}
		}
		return volist;
	}




	public String getBillID(String type) {
		// TODO 自动生成的方法存根
		String id = null;
		try {
			id = billDataService.getBillID(type);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}


	@Override
	public ArrayList<String> getUserlistOfSKD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getUserlistOfSKD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<String> getUserlistOfFKD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getUserlistOfFKD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<String> getUserlistOfXJFYD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getUserlistOfXJFYD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<String> getCustomerlistOfSKD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getCustomerlistOfSKD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<String> getCustomerlistOfFKD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getCustomerlistOfFKD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<String> getAccountlistOfXJFYD() {
		// TODO 自动生成的方法存根
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = billDataService.getAccountlistOfXJFYD();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public ArrayList<ReceivingBillVO> searchSKD(Date date1, Date date2,
			String username, String customer) {
		// TODO 自动生成的方法存根
		ArrayList<ReceivingBillPO> list = new ArrayList<ReceivingBillPO>();
		ArrayList<ReceivingBillVO> volist = new ArrayList<ReceivingBillVO>();
		try {
			list = billDataService.searchSKD(date1, date2, username, customer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(ReceivingBillPO po:list){
			volist.add(transformOfReceiving(po));
		}
		return volist;
	}


	@Override
	public ArrayList<PaymentBillVO> searchFKD(Date date1, Date date2,
			String username, String customer) {
		// TODO 自动生成的方法存根
		ArrayList<PaymentBillPO> list = new ArrayList<PaymentBillPO>();
		ArrayList<PaymentBillVO> volist = new ArrayList<PaymentBillVO>();
		try {
			list = billDataService.searchFKD(date1, date2, username, customer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(PaymentBillPO po:list){
			volist.add(transformOfPayment(po));
		}
		return volist;
	}


	@Override
	public ArrayList<CostBillVO> searchXJFYD(Date date1, Date date2,
			String username, String account) {
		// TODO 自动生成的方法存根
		ArrayList<CostBillPO> list = new ArrayList<CostBillPO>();
		ArrayList<CostBillVO> volist = new ArrayList<CostBillVO>();
		try {
			list = billDataService.searchXJFYD(date1, date2, username, account);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(CostBillPO po:list){
			volist.add(transformOfCost(po));
		}

		return volist;
	}


	@Override
	public ArrayList<CostBillVO> getXJFYDListForExamined() {
		// TODO 自动生成的方法存根
		ArrayList<CostBillPO> list = new ArrayList<CostBillPO>();
		ArrayList<CostBillVO> volist = new ArrayList<CostBillVO>();
		try {
			list = billDataService.getXJFYDListForExamined();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(CostBillPO po:list){
			volist.add(transformOfCost(po));
		}

		return volist;
	}


	@Override
	public ArrayList<PaymentBillVO> getFKDListForExamined() {
		// TODO 自动生成的方法存根
		ArrayList<PaymentBillPO> list = new ArrayList<PaymentBillPO>();
		ArrayList<PaymentBillVO> volist = new ArrayList<PaymentBillVO>();
		try {
			list = billDataService.getFKDListForExamined();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(PaymentBillPO po:list){
			volist.add(transformOfPayment(po));
		}
		return volist;
	}


	@Override
	public ArrayList<ReceivingBillVO> getSKDListForExamined() {
		// TODO 自动生成的方法存根
		ArrayList<ReceivingBillPO> list = new ArrayList<ReceivingBillPO>();
		ArrayList<ReceivingBillVO> volist = new ArrayList<ReceivingBillVO>();
		try {
			list = billDataService.getSKDListForExamined();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(ReceivingBillPO po:list){
			volist.add(transformOfReceiving(po));
		}
		return volist;
	}


	public void delAll() {
		// TODO 自动生成的方法存根
		try {
			billDataService.delAll();;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	//审批的接口 更改isModified和isExamined
	@Override
	public ResultMessage examine(BillVO vo,boolean isPassed) {
		// TODO 自动生成的方法存根
		vo.setExamined(true);
		vo.setModified(!isPassed);
		vo.setRead(false);
		return modifyBill(vo);
	}







}
