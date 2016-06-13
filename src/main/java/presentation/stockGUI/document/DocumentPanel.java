package presentation.stockGUI.document;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import VO.UserVO;
import businesslogic.logbl.LogInterface;
import businesslogic.stockbl.StockController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.stockBLService.StockBLService;

public class DocumentPanel extends JPanel implements Serializable {
	String [] type = {"库存赠送单","库存报溢单","库存报损单","库存报警单"};
	JComboBox docTypeBox = new JComboBox(type);
	StockBLService sbs = new StockController();
	OverflowPanel overflowPanel;
	DamagePanel damagePanel ;
	ReportPanel reportPanel;
	PresentPanel presentPanel ;

	public DocumentPanel(UserVO uvo){
		overflowPanel= new OverflowPanel(uvo);
		damagePanel = new DamagePanel(uvo);
		reportPanel = new ReportPanel(uvo);
		presentPanel = new PresentPanel();
		reportPanel.setVisible(false);
		overflowPanel.setVisible(false);
		damagePanel.setVisible(false);
		presentPanel.setVisible(true);
		docTypeBox.setSize(150,20);
		docTypeBox.setLocation(40, 40);
		docTypeBox.setEditable(false);
		docTypeBox.addItemListener(new typeBoxListener());
		this.add(docTypeBox);
		this.add(reportPanel);
		this.add(damagePanel);
		this.add(overflowPanel);
		this.add(presentPanel);
		this.setLayout(null);
		this.setSize(850, 600);
		this.setLocation(0, 0);
	}
	public class typeBoxListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				String docType = (String)e.getItem();
				switch(docType){
				case "库存赠送单":
					reportPanel.setVisible(false);
					overflowPanel.setVisible(false);
					damagePanel.setVisible(false);
					presentPanel.setVisible(true);
					break;
				case "库存报溢单":
					reportPanel.setVisible(false);
					overflowPanel.setVisible(true);
					damagePanel.setVisible(false);
					presentPanel.setVisible(false);
					break;
				case "库存报损单":
					reportPanel.setVisible(false);
					overflowPanel.setVisible(false);
					damagePanel.setVisible(true);
					presentPanel.setVisible(false);
					break;
				case "库存报警单":
					reportPanel.setVisible(true);
					overflowPanel.setVisible(false);
					damagePanel.setVisible(false);
					presentPanel.setVisible(false);
					break;
				}
					
			}

		}
		
	}

}
