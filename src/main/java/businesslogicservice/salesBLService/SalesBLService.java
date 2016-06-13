package businesslogicservice.salesBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;

public interface SalesBLService {
	public String makeImport(SalesVO vo);
	public String makeImportReturn(SalesVO vo);
	public String makeSales(SalesVO vo);
	public String makeSalesReturn(SalesVO vo);
	public ArrayList<SalesVO> showSales();
	public ArrayList<SalesVO> showImport();
	public ArrayList<SalesVO> showSalesReturn();
	public ArrayList<SalesVO> showImportReturn();
	public void endMakeSales();
	public ArrayList<PromotionVO> showAvailablePromotion(SalesVO vo);
	public ArrayList<CustomerVO> showImportCustomer();
	public ArrayList<CustomerVO> showSalesCustomer();
	public void setUser(UserVO u);
	
}
