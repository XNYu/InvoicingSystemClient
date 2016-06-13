package businesslogic.stockbl;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;

import PO.StockPO;
import VO.StockVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.utilitybl.ExportExcel;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.stockBLService.StockBLService;
public class StockController implements StockBLService,Serializable{
	private static final long serialVersionUID = 1L;

	Stock stock = new Stock();
	Commodity commodity = new Commodity();
	ExportExcel outputExcel = new ExportExcel<StockPO>();
	public ResultMessage createStock(String name,String type,String id,String operateType,
			                    String leaveDate,int amount,double price){
		boolean flag = false;
		String i = stock.getBatchID();
		StockPO stoPO = new StockPO(name, type, id, operateType, leaveDate, i, amount, price);
//		StockPO stoPO = stock.transform(stoVO);
		if(stock.createStock(stoPO)){
			commodity.changeAmount(id, operateType, amount);
			return ResultMessage.Success;
			
		}
		else
			return ResultMessage.Failure;
		
	}
	
	
	@Override
	public ArrayList<StockPO> init() {
		// TODO 自动生成的方法存根
		return stock.init();
	}

	@Override
	public ArrayList<StockPO> showStock(String start, String end) {
		// TODO 自动生成的方法存根
		return stock.showStock(start, end);
	}

	@Override
	public ArrayList<String> calculate(ArrayList<StockPO> list) {
		
		return stock.calculate(list);
	}

	@Override
	public ResultMessage exportExcel(ArrayList<StockPO> list) {
		boolean flag = false;
		
		//名称，型号，库存数量，库存均价，批次，批号，出厂日期
		String [] headers =  { "名称", "型号", "库存数量", "库存均价", "批次" ,"批号","出产日期"};  
		ArrayList<StockPO> dataset = new ArrayList<StockPO>();
//		dataset.add(e)
//		outputExcel.exportExcel(dataset, out);
//		
		if(flag)
			return ResultMessage.Success;
		return ResultMessage.Failure;
	}
	public static void main(String[] args){
    	StockController s = new StockController();
    	s.createStock("tt","1","A00000000000","STOCKIN","leaveDate",300,6);
    }


	@Override
	public void clear() {
		stock.clear();
		
	}

}