package businesslogic.salesbl;

import java.util.ArrayList;

import VO.CustomerVO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.salesBLService.SalesBLService;

public class SalesController implements SalesBLService{
	Sales sales=new Sales();
	
	public void setUser(UserVO u){
		sales.setUser(u);
	}
	@Override
	public String makeImport(SalesVO vo) {
		return sales.makeImport(vo);
	}

	@Override
	public String makeImportReturn(SalesVO vo) {
		// TODO Auto-generated method stub
		return sales.makeImportReturn(vo);
	}

	@Override
	public String makeSales(SalesVO vo) {
		// TODO Auto-generated method stub
		return sales.makeSales(vo);
	}

	@Override
	public String makeSalesReturn(SalesVO vo) {
		// TODO Auto-generated method stub
		return sales.makeSalesReturn(vo);
	}

	@Override
	public ArrayList<SalesVO> showSales() {
		// TODO Auto-generated method stub
		return sales.showSales();
	}

	@Override
	public ArrayList<SalesVO> showImport() {
		// TODO Auto-generated method stub
		return sales.showImport();
	}

	@Override
	public ArrayList<SalesVO> showSalesReturn() {
		// TODO Auto-generated method stub
		return sales.showSalesReturn();
	}

	@Override
	public ArrayList<SalesVO> showImportReturn() {
		// TODO Auto-generated method stub
		return sales.showImportReturn();
	}
	
	@Override
	public void endMakeSales() {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<PromotionVO> showAvailablePromotion(SalesVO vo){
		return sales.showAvailablePromotion(vo);
	}
	
	public ArrayList<CustomerVO> showImportCustomer(){
		return sales.showImportCustomer();
	}
	public ArrayList<CustomerVO> showSalesCustomer(){
		return sales.showSalesCustomer();
	}
}
