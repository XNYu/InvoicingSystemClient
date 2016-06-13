package presentation.examineGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import presentation.examineGUI.ImportPanel.noExamineListener;
import presentation.promotionGUI.DateChooser;
import presentation.promotionGUI.utility;
import presentation.stockGUI.document.*;
import PO.CommodityPO;
import PO.DocumentPO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.document.DocumentController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.documentBLService.DocumentBLService;

public class DamagePanel extends JPanel implements TableModelListener,ListSelectionListener{
	CommodityBLService cbs = new CommodityController();
	DocumentBLService dbs = (DocumentBLService) new DocumentController();
	JScrollPane damagePane;
	DocDamageTableModel damageTableModel;
	int rowNum = 0;
	JTable damageTable;
	DamagePanel panel=this;
	int[] selectedRows = null;
	ListSelectionModel selectionModel = null;
	
	public DamagePanel(){
		setSize(850, 460);
		damageTableModel=new DocDamageTableModel();	
		setLayout(null);
		
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(0, 0, 820, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("确认审批");
	 	btnNewButton_1.addActionListener(new examineListener());
	 	btnNewButton_1.setBounds(707, 0, 113, 30);
	 	searchPanel.add(btnNewButton_1);
	 	
	 	JButton button = new JButton("审批未通过");
	 	button.addActionListener(new noExamineListener());
	 	button.setBounds(586, 0, 113, 30);
	 	searchPanel.add(button);
		
		damageTable=new JTable(damageTableModel);
		damageTable.getModel().addTableModelListener(this);
		damageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		damagePane = new JScrollPane(damageTable);
		damagePane.setVisible(true);
		damagePane.setLocation(0,44);
		damagePane.setSize(820,416);
		

		add(damagePane);
		setLocation(40,100);
		updateTable();
		
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateTable(){
		damageTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		while(damageTableModel.getRowCount()>0){
			 damageTableModel.removeRow(damageTableModel.getRowCount()-1);
		}
		
		ArrayList<DocumentPO> docList=dbs.showDocument("DAMAGE");
		
		if(docList!=null){
			for(DocumentPO po:docList)
				if(!po.isExamined()){
				Vector v = new Vector();
				v.add(false);
				v.add(po.getDocType());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getDocumentID());
				v.add(po.getSystemAmount());
				v.add(po.getRealAmount());
				v.add(po.getCreatedDate());
				damageTableModel.addRow(v);
			}
		}
		damageTable.revalidate();
		repaint();
	}
	
	class examineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=damageTable.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)damageTable.getValueAt(i, 0)==true){
					dbs.examineDocument((String)damageTable.getValueAt(i, 4),true);
					j=true;
				}
			}
			if(j==true) utility.setInfo("审批成功", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
		}
	}
	
	class noExamineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=damageTable.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)damageTable.getValueAt(i, 0)==true){
					dbs.examineDocument((String)damageTable.getValueAt(i, 4),false);
					j=true;
				}
			}
			if(j==true) utility.setInfo("您所选的单据未通过审批", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}

