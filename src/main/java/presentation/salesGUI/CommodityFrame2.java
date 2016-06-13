package presentation.salesGUI;


import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import PO.CommodityPO;

public class CommodityFrame2 extends JFrame{
	ArrayList<CommodityPO> commodityList;
	CommodityPanel2 panel;
	public CommodityFrame2(ArrayList<CommodityPO> poList){
		commodityList=poList;
		
		setLayout(null);
		setBounds(100,100,880,430);
		try {
			panel = new CommodityPanel2(commodityList);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(panel);
		panel.setVisible(true);
		setVisible(true);
	}
	public static void main(String [] args){
		new CommodityFrame2(null);
	}
}