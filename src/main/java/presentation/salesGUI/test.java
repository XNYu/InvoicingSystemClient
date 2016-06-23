package presentation.salesGUI;

import java.util.ArrayList;

import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityController;
import businesslogic.salesbl.SalesController;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.salesBLService.SalesBLService;
import PO.CommodityPO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
public class test {
	SalesBLService sbs=new SalesController();
	String xxs="1",stock="1",operator="1",salesman="1",document="1";
	static ArrayList<CommodityPO> commoditylist=null;
	static ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
	static CommodityController cc = new CommodityController();
	static ArrayList<SalesVO> as;
	
	CommodityBLService cbs = new CommodityController();
	
	public static void main(String[] args) {
		ArrayList<CommodityPO> newlist = new ArrayList<CommodityPO>();
		test t = new test();
		commoditylist = new ArrayList<CommodityPO>();
		newlist = cc.showCommodity();
		for(CommodityPO cp:newlist){
		System.out.println(cp.getName());
		CommodityPO cpo = cp;
		cpo.setAmount(2);
		commoditylist.add(cpo);}
		
		t.make();
		
		LineChart_AWT chart = new LineChart_AWT(
			      "School Vs Years" ,
			      "Numer of Schools vs years",as);

			      chart.pack( );
			      RefineryUtilities.centerFrameOnScreen( chart );
			      chart.setVisible( true );
	}
	
	public void make(){
		sbs.setUser(new UserVO("123","销售员","马昕","123","最高权限"));
		String ID=sbs.makeSales(new SalesVO("","Sales",xxs,stock,
				operator,salesman,commoditylist,0,0,
				0,0,document,promotionlist,"已审批"));
		as =sbs.showSales();
		for(SalesVO s:as){
			System.out.println(s.getStock());
		}
		
		ArrayList<CommodityPO> cmpp=cbs.showCommodity();
		for(CommodityPO cmp:cmpp){
			System.out.println(cmp.getName()+cmp.getType());
		}
		System.out.println(ID);
	}
}

