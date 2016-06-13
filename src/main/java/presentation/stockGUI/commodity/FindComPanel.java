package presentation.stockGUI.commodity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import PO.CommodityPO;

public class FindComPanel extends JPanel{

	
	ComTableModel comTableModel;
	JTable comTable;
	protected JButton exitButton,vanishButton;
	JScrollPane jsp ;
	
	public FindComPanel(ArrayList<CommodityPO> poList){
		
		this.setSize(780,430);

		this.setLocation(40,100);
		this.setLayout(null);

        comTableModel = new ComTableModel();
		comTable = new JTable(comTableModel);
		comTable.getColumnModel().getColumn(0).setPreferredWidth(120);
		if(poList!=null){
			for(CommodityPO po:poList){
				Vector v=new Vector();
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getImpPrice());
				v.add(po.getExpPrice());
				v.add(po.getRecentImpPrice());
				v.add(po.getRecentExpPrice());
				comTableModel.addRow(v);
			}
		}
		jsp = new JScrollPane(comTable);
		jsp.setSize(780,430);
		jsp.setLocation(0,0);
		jsp.setVisible(true);
		this.add(jsp);
		this.setVisible(true);
	}
	
}
