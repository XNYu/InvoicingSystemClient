package presentation.examineGUI;

import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import presentation.promotionGUI.DateChooser;
import presentation.promotionGUI.utility;
import PO.CommodityPO;
import PO.EntryPO;
import PO.PaymentBillPO;
import PO.PresentPO;
import PO.ReceivingBillPO;
import PO.TransferPO;
import VO.CostBillVO;
import VO.ReceivingBillVO;
import businesslogic.billbl.Bill;
import businesslogic.customerbl.Present;
import businesslogic.salesbl.Sales;
import businesslogic.tablebl.TableController;
import businesslogicservice.tableBLService.TableBLService;

public class PresentPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table,table2;
	PresentModel tablemodel1;
	ComTableModel tablemodel2;
	PresentPanel panel=this;
	TableBLService tbs=new TableController();
	Present pr=new Present();
	ArrayList<String> customer=new ArrayList<String>();
	ArrayList<PresentPO> plist=new ArrayList<PresentPO>();
	ArrayList<CommodityPO> comlist=null;
	
	public PresentPanel(){
		setSize(850, 460);
		setLayout(null);
		tablemodel1=new PresentModel();
		tablemodel2=new ComTableModel();
		
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 0, 820, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JButton btnNewButton_1 = new JButton("确认审批");
	 	btnNewButton_1.addActionListener(new examineListener());
	 	btnNewButton_1.setBounds(707, 0, 113, 30);
	 	searchPanel.add(btnNewButton_1);
	 	
	 	JButton button = new JButton("审批未通过");
	 	button.addActionListener(new noExamineListener());
	 	button.setBounds(580, 0, 113, 30);
	 	searchPanel.add(button);
	 	
		table=new JTable(tablemodel1);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener());
		
		table2=new JTable(tablemodel2);
		table2.getModel().addTableModelListener(this);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(14, 38, 278, 402);
		add(scrollpane);
		
		JScrollPane scrollpane2=new JScrollPane(table2);
		scrollpane2.setBounds(306, 38, 528, 402);
		add(scrollpane2);
		
		updateTable();
	}

	
	public void updateTable(){
		plist=pr.showPresent();
		while(tablemodel1.getRowCount()>0){
			 tablemodel1.removeRow(tablemodel1.getRowCount()-1);
		}
		if(plist!=null){
			for(PresentPO po:plist){
			if(po.getExamine().equals("未审批")){
				Vector v=new Vector();
				v.add(false);
				v.add(po.getID());
				v.add(po.getCustomer());
				v.add(po.getCreatedDate());
				tablemodel1.addRow(v);
			}}
		}
		table.revalidate();
		repaint();
	}

	class selectListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            int row = table.getSelectedRow();
            PresentPO po=plist.get(row);
            comlist=po.getCommoditylist();
            updateCom(comlist);
        }
	}
	
	
	public void updateCom(ArrayList<CommodityPO> comlist){
		while(tablemodel2.getRowCount()>0){
			 tablemodel2.removeRow(tablemodel2.getRowCount()-1);
		}
		for(CommodityPO po:comlist){
			Vector v=new Vector();
			v.add(po.getID());
			v.add(po.getName());
			v.add(po.getType());
			v.add(po.getAmount());
			tablemodel2.addRow(v);
		}
		table2.revalidate();
		repaint();
	}
	
	public void updateCom(){
		while(tablemodel2.getRowCount()>0){
			 tablemodel2.removeRow(tablemodel2.getRowCount()-1);
		}
		table2.revalidate();
		repaint();
	}
	class examineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=table.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)table.getValueAt(i, 0)==true){
					pr.examine((String)table.getValueAt(i, 1),"审批通过");
					j=true;
				}
			}
			if(j==true) utility.setInfo("审批成功", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
			updateCom();
		}
	}
	
	class noExamineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=table.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)table.getValueAt(i, 0)==true){
					pr.examine((String)table.getValueAt(i, 1),"审批未通过");
					j=true;
				}
			}
			if(j==true) utility.setInfo("您所选的单据未通过审批", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
			updateCom();
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
	}
}
