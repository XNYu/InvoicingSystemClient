package presentation.salesGUI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import businesslogic.salesbl.SalesController;
import businesslogicservice.salesBLService.SalesBLService;
import PO.CommodityPO;
import VO.PromotionVO;

public class CommodityFrame extends JFrame{
	ArrayList<CommodityPO> commodityList;
	CommodityPanel panel;
	ArrayList<PromotionVO> promotionlist;
	String customer;
	public CommodityFrame(ArrayList<CommodityPO> poList,ArrayList<PromotionVO> promotionlist,String customer){
		this.promotionlist=promotionlist;
		commodityList=poList;
		this.customer=customer;
		if(commodityList==null)
			commodityList=new ArrayList<CommodityPO>();
		setLayout(null);
		setBounds(100,100,880,430);
		try {
			panel = new CommodityPanel(commodityList,promotionlist,customer);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(panel);
		panel.setVisible(true);
		setVisible(true);
	}
	
}
