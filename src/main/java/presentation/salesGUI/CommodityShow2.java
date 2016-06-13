package presentation.salesGUI;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import PO.CommodityPO;
public class CommodityShow2 extends JFrame{
	ArrayList<CommodityPO> commoditylist;
	JTable table;
	CommodityModel2 tablemodel;
	public CommodityShow2(ArrayList<CommodityPO> poList){
		setBounds(400,200,500,400);
		setVisible(true);
		commoditylist=poList;
		tablemodel=new CommodityModel2();
		table=new JTable(tablemodel);
		JScrollPane scrollpane=new JScrollPane(table);
		add(scrollpane);
		
		if(commoditylist!=null){
			for(CommodityPO po:commoditylist){
				Vector v=new Vector();
				System.out.println(po.getID());
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getImpPrice());
				v.add(po.getLeaveDate());
				tablemodel.addRow(v);
			}
		}
		
		table.revalidate();
		repaint();
	}
	
	public static void main(String [] args){
		new CommodityShow2(null);
	}
}
