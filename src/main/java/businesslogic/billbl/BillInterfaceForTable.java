package businesslogic.billbl;

import java.util.ArrayList;
import java.util.Date;

import VO.CostBillVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;

public interface BillInterfaceForTable {
	public ArrayList<String> getUserlistOfSKD();
	public ArrayList<String> getUserlistOfFKD();
	public ArrayList<String> getUserlistOfXJFYD();
	public ArrayList<String> getCustomerlistOfSKD();
	public ArrayList<String> getCustomerlistOfFKD();
	public ArrayList<String> getAccountlistOfXJFYD();
	public ArrayList<ReceivingBillVO> searchSKD(Date date1,Date date2,String username,String customer);
	public ArrayList<PaymentBillVO> searchFKD(Date date1,Date date2,String username,String customer);
	public ArrayList<CostBillVO> searchXJFYD(Date date1,Date date2,String username,String account);
}
