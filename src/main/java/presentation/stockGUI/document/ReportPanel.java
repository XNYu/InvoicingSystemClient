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
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.documentBLService.DocumentBLService;

public class ReportPanel extends JPanel implements ListSelectionListener{
	CommodityBLService cbs = new CommodityController();
	DocumentBLService dbs = (DocumentBLService) new DocumentController();
	JScrollPane reportPane,comPane;
	DocReportTableModel reportTableModel = new DocReportTableModel();
	DocComTableModel comTableModel = new DocComTableModel();
	int rowNum = 0; 
	JTable reportTable,comTable; 
	JButton addReportButton = new JButton("ADD"),
			searchButton;
	JTextField reportAmount ;
	JTextArea searchArea;
	String searchStr="",amountStr;
	int[] selectedRows = null;
	ListSelectionModel selectionModel = null;
	LogInterface loginterface= new Log();
	UserVO uvo;
	public ReportPanel(UserVO uvo){
		this.uvo = uvo;
		addReportButton.setSize(150,20);
		addReportButton.setLocation(150,230);
		addReportButton.addActionListener(new addOverflowListener());
		this.add(addReportButton);
		reportAmount = new JTextField("report amount:");
		reportAmount.setSize(150,20);
		reportAmount.setLocation(0,230);
		reportAmount.getDocument().addDocumentListener(new amountTextListener());
		reportAmount.addFocusListener(new amountAdapter());
		this.add(reportAmount);
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
		reportTable = new JTable(reportTableModel);
		reportTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		ArrayList<DocumentPO> docList=dbs.showDocument("REPORT");
		if(docList!=null){
			for(DocumentPO po:docList){
				Vector v = new Vector();
				v.add(po.getDocType());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getId());
				v.add(po.getReportAmount());
				v.add(po.getCreatedDate());
				v.add(po.isExamined());
				reportTableModel.addRow(v);
			}
		}
//		stockTable.getModel().addTableModelListener(this);
		reportPane = new JScrollPane(reportTable);
//		overflowPane.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		reportPane.setVisible(true);
		reportPane.setLocation(0,250);
		reportPane.setSize(780,200);

		this.add(reportPane);
		this.setLayout(null);
		this.setSize(780,450);
		this.setLocation(40,100);
	}
	public class amountAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (reportAmount.getText().equals("report amount:")) {
				reportAmount.setText("");
	        }
        }
        public void focusLost(FocusEvent e) {
        	if (reportAmount.getText().equals("")) {
        		reportAmount.setText("report amount:");
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
			amountStr = reportAmount.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			amountStr = reportAmount.getText().trim();
			
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
//				if(amount<=((int)comTableModel.getValueAt(selectedRows[0], 3)))
//					return;
				ResultMessage rm = dbs.createReport((String)comTableModel.getValueAt(selectedRows[0], 1), 
						(String)comTableModel.getValueAt(selectedRows[0], 2), 
						(String)comTableModel.getValueAt(selectedRows[0], 0),  amount);
				if(rm==ResultMessage.Failure)
					return;
				cbs.changeReport((String) comTableModel.getValueAt(selectedRows[0], 0), amount);
				comTableModel.setValueAt(amount, selectedRows[0], 4);
				for(int i =reportTable.getRowCount();i>0;i-- ){
					comTableModel.removeRow(0);
				}
				ArrayList<DocumentPO> list2 = dbs.showDocument("REPORT");
				for(DocumentPO po:list2){
					Vector v = new Vector();
					v.add(po.getDocType());
					v.add(po.getName());
					v.add(po.getType());
					v.add(po.getId());
					v.add(po.getReportAmount());
					v.add(po.getCreatedDate());
					v.add(po.isExamined());
					reportTableModel.addRow(v);
				}
				loginterface.buildlog(uvo.getName()	, "新建报警单："+"id"+(String)comTableModel.getValueAt(selectedRows[0], 0)
						+"系统报警数量:"+String.valueOf(comTableModel.getValueAt(selectedRows[0], 4))+
						"真实数量:"+amount);
				reportTable.revalidate();
				reportTable.repaint();
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