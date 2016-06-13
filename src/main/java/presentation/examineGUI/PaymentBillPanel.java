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
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.UserVO;
import PO.TransferPO;
import businesslogic.billbl.Bill;
import businesslogic.billbl.TransferList;
import businesslogic.tablebl.TableController;
import businesslogicservice.tableBLService.TableBLService;

public class PaymentBillPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table,table2;
	BillModel tablemodel1;
	TransferModel tablemodel2;
	PaymentBillPanel panel=this;
	TableBLService tbs=new TableController();
	ArrayList<TransferPO> transferlist=null;
	Bill bill=new Bill(new UserVO(null, null, "yyhh0000", null, null));
	JLabel sumLabel;
	ArrayList<PaymentBillVO> blist=new 	ArrayList<PaymentBillVO>();
	ArrayList<PaymentBillVO> templist=new 	ArrayList<PaymentBillVO>();
	public PaymentBillPanel(){
		setSize(850, 460);
		setLayout(null);
		tablemodel1=new BillModel();
		tablemodel2=new TransferModel();
		
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
	 	button.setBounds(590, 0, 113, 30);
	 	searchPanel.add(button);
	 	
	 	JLabel label = new JLabel("请选择要审批的单据");
	 	label.setBounds(0, 6, 179, 18);
	 	searchPanel.add(label);
		
	 	table=new JTable(tablemodel1);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener());
		
		table2=new JTable(tablemodel2);
		table2.getModel().addTableModelListener(this);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(14, 38, 400, 402);
		add(scrollpane);
		
		JScrollPane scrollpane2=new JScrollPane(table2);
		scrollpane2.setBounds(430, 38, 404, 402);
		add(scrollpane2);
		
		updateTable();
	}
	
	public void updateTable(){
		blist=bill.getFKDListForExamined();
		while(tablemodel1.getRowCount()>0){
			 tablemodel1.removeRow(tablemodel1.getRowCount()-1);
		}
		table.revalidate();
		if(blist!=null){
			for(PaymentBillVO vo:blist){
			if(vo.isExamined()==false){
				Vector v=new Vector();
				templist.add(vo);
				v.add(false);
				v.add(vo.getNumberID());
				v.add(vo.getCus().getName());
				v.add(vo.getUsername());
				v.add(vo.getSum());
				tablemodel1.addRow(v);
			}
		}
	}
		table.revalidate();
		repaint();
	
	}
	
	
	class selectListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
        	int row = table.getSelectedRow();
			table.setValueAt(true, row, 0);
        	
            PaymentBillVO po=blist.get(row);
            transferlist=po.getTransferList();
            updateTransfer(transferlist);
        }
	}
	
	public void updateTransfer(ArrayList<TransferPO> transferList){
		table2.revalidate();
		while(tablemodel2.getRowCount()>0){
			 tablemodel2.removeRow(tablemodel2.getRowCount()-1);
		}
		for(TransferPO trans:transferlist){
			Vector v=new Vector();
			v.add(trans.acc.getName());
			v.add(Double.toString(trans.getMoney()));
			v.add(trans.getNotes());
			tablemodel2.addRow(v);
		}
		table2.revalidate();
		repaint();
	}
	public void updateTransfer(){
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
					bill.examine(templist.get(i), true);
					j=true;
				}
			}
			if(j==true) utility.setInfo("审批成功", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
			updateTransfer();
		}
	}
	
	class noExamineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=table.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)table.getValueAt(i, 0)==true){
					bill.examine(templist.get(i), false);
					j=true;
				}
			}
			if(j==true) utility.setInfo("您所选的单据未通过审批", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
			updateTable();
			updateTransfer();
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
