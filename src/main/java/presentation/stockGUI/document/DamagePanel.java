package presentation.stockGUI.document;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import PO.CommodityPO;
import PO.DocumentPO;
import VO.UserVO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.document.DocumentController;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.ResultMessage;
import businesslogic.utilitybl.ShowMessageFrame;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.documentBLService.DocumentBLService;

public class DamagePanel extends JPanel implements ListSelectionListener{
	CommodityBLService cbs = new CommodityController();
	DocumentBLService dbs = (DocumentBLService) new DocumentController();
	JScrollPane overflowPane,comPane;
	DocDamageTableModel damageTableModel = new DocDamageTableModel();
	DocComTableModel comTableModel = new DocComTableModel();
	int rowNum = 0;
	JTable damageTable,comTable;
	JButton addDamageButton = new JButton("ADD"),
			searchButton;
	JTextField realAmount ;
	JTextArea searchArea;
	String searchStr="",amountStr;
	int[] selectedRows = null;
	ListSelectionModel selectionModel = null;
	LogInterface loginterface= new Log();
	UserVO uvo ;
	public DamagePanel(UserVO uvo){
		this.uvo = uvo;
		addDamageButton.setSize(150,20);
		addDamageButton.setLocation(150,230);
		addDamageButton.addActionListener(new addOverflowListener());
		this.add(addDamageButton);
		realAmount = new JTextField("real amount:");
		realAmount.setSize(150,20);
		realAmount.setLocation(0,230);
		realAmount.getDocument().addDocumentListener(new amountTextListener());
		realAmount.addFocusListener(new amountAdapter());
		this.add(realAmount);
		searchButton= new JButton("GO");
		searchButton.setSize(150,20);
		searchButton.setLocation(150, 0);
		searchButton.addActionListener(new searchListener());
		this.add(searchButton);
		searchArea = new JTextArea("search:");
		searchArea.setSize(150,20);
		searchArea.setLocation(0,0);
		searchArea.getDocument().addDocumentListener(new searchTextListener());
		searchArea.addFocusListener(new searchAdapter());
		this.add(searchArea);
		
		comTable = new JTable(comTableModel);
//		comTable.getColumnModel().getColumn(1).setPreferredWidth(10);
		comPane = new JScrollPane(comTable);
		comPane.setVisible(true);
		comPane.setSize(780,200);
		comPane.setLocation(0, 20);
		comTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel.addListSelectionListener(this);
		this.add(comPane);

		ArrayList<CommodityPO> poList=cbs.showCommodity();
		if(poList!=null){
			for(CommodityPO po:poList){
				Vector v=new Vector();
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getReportAmount());
				comTableModel.addRow(v);
			}
		}
		//====================================================================
		damageTable = new JTable(damageTableModel);
		damageTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		ArrayList<DocumentPO> docList=dbs.showDocument("DAMAGE");
		if(docList!=null){
			for(DocumentPO po:docList){
				Vector v = new Vector();
				v.add(po.getDocType());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getId());
				v.add(po.getSystemAmount());
				v.add(po.getRealAmount());
				v.add(po.getCreatedDate());
				v.add(po.isExamined());
				damageTableModel.addRow(v);
			}
		}
//		stockTable.getModel().addTableModelListener(this);
		overflowPane = new JScrollPane(damageTable);
//		overflowPane.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		overflowPane.setVisible(true);
		overflowPane.setLocation(0,250);
		overflowPane.setSize(780,200);

		this.add(overflowPane);
		this.setLayout(null);
		this.setSize(780,450);
		this.setLocation(40,100);
	}
	public class amountAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (realAmount.getText().equals("real amount:")) {
				realAmount.setText("");
	        }
        }
        public void focusLost(FocusEvent e) {
        	if (realAmount.getText().equals("")) {
        		realAmount.setText("real amount:");
            }
        }
		
	}
	public class searchAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (searchArea.getText().equals("search:")) {
				searchArea.setText("");
	        }
        }
        public void focusLost(FocusEvent e) {
        	if (searchArea.getText().equals("")) {
        		searchArea.setText("search:");
            }
        }
		
	}
	public class amountTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			amountStr = realAmount.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			amountStr = realAmount.getText().trim();
			
		}
	    
	}
	public class searchTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}
	    
	}
	public class addOverflowListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			//judge amount is correct
			if(isNumber(amountStr)){
				int amount = Integer.parseInt(amountStr);
				if((   (int)comTableModel.getValueAt(selectedRows[0], 3)  )  <=amount){
					new ShowMessageFrame("请出入正确数量");
					return;
				}
				ResultMessage rm = dbs.createDamage((String)comTableModel.getValueAt(selectedRows[0], 1), 
						(String)comTableModel.getValueAt(selectedRows[0], 2), 
						(String)comTableModel.getValueAt(selectedRows[0], 0)
						, (int)comTableModel.getValueAt(selectedRows[0], 3), amount);
				if(rm==ResultMessage.Failure)
					return;
				cbs.changeAmount((String) comTableModel.getValueAt(selectedRows[0], 0),"DOCUMRNT", amount);
				comTableModel.setValueAt(amount, selectedRows[0], 3);
				for(int i =damageTable.getRowCount();i>0;i-- ){
					comTableModel.removeRow(0);
				}
				ArrayList<DocumentPO> list2 = dbs.showDocument("DAMAGE");
				for(DocumentPO po:list2){
					Vector v = new Vector();
					v.add(po.getDocType());
					v.add(po.getName());
					v.add(po.getType());
					v.add(po.getId());
					v.add(po.getSystemAmount());
					v.add(po.getRealAmount());
					v.add(po.getCreatedDate());
					v.add(po.isExamined());
					damageTableModel.addRow(v);
				}
				loginterface.buildlog(uvo.getName()	, "新建报损单："+"id"+(String)comTableModel.getValueAt(selectedRows[0], 0)
						+"系统数量:"+String.valueOf(comTableModel.getValueAt(selectedRows[0], 3))+
						"真实数量:"+amount);
				damageTable.revalidate();
				damageTable.repaint();
			}else{
				new ShowMessageFrame("请输入正确数量");
			}
		}
		
	}
	public class searchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
//			System.out.println(comTable.getRowCount());
			if(searchStr.equals("")||searchStr.equals("search:"))
				return;
			for(int i =comTable.getRowCount();i>0;i-- ){
				comTableModel.removeRow(0);
			}
			ArrayList<CommodityPO> comSearch = cbs.findCommodity(searchStr);
			for(CommodityPO po:comSearch){
				Vector v=new Vector();
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getReportAmount());
				comTableModel.addRow(v);
			}
			comTable.revalidate();
			comTable.repaint();
		}
		
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		selectedRows=comTable.getSelectedRows();

	}
	public static boolean isInteger(String value) {
		  try {
		   Integer.parseInt(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
	}
   public static boolean isDouble(String value) {
		  try {
		   Double.parseDouble(value);
		   if (value.contains("."))
		    return true;
		   return false;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
   public static boolean isNumber(String value) {
  	  return isInteger(value) || isDouble(value);
  	 }
	
}

