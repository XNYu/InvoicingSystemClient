package businesslogicservice.stockBLService;

import java.util.ArrayList;

import businesslogic.utilitybl.ResultMessage;
import PO.*;

public interface StockBLService {

	public ArrayList<StockPO> init();
	public ArrayList<StockPO> showStock(String start, String end);
	public ArrayList<String> calculate(ArrayList<StockPO> list);
	public ResultMessage exportExcel(ArrayList<StockPO> list);
	public ResultMessage createStock(String name,String type,String id,String operateType,String leaveDate,int amount,double price);
	public void clear();
}
