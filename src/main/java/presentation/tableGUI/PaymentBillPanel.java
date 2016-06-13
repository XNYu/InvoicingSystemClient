package presentation.tableGUI;

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
import presentation.tableGUI.ReceivingBillPanel.redFlushListener;
import VO.PaymentBillVO;
import VO.UserVO;
import PO.TransferPO;
import businesslogic.billbl.Bill;
import businesslogic.tablebl.TableController;
import businesslogicservice.tableBLService.TableBLService;

public class PaymentBillPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table,table2;
	BillModel tablemodel1;
	TransferModel tablemodel2;
	JComboBox<String> customerBox;
	JComboBox<String> userBox;
	PaymentBillPanel panel=this;
	TableBLService tbs=new TableController();
	ArrayList<TransferPO> transferlist=null;
	Bill bill;
	DateChooser mp1;
	DateChooser mp2;
	JLabel sumLabel;
	ArrayList<String> customer=new ArrayList<String>();
	ArrayList<String> user=new ArrayList<String>();
	ArrayList<PaymentBillVO> blist=new ArrayList<PaymentBillVO>();
	ArrayList<PaymentBillVO> templist=new ArrayList<PaymentBillVO>();
	
	public PaymentBillPanel(UserVO us){
		bill=new Bill(us);
		setSize(850, 460);
		setLayout(null);
		tablemodel1=new BillModel();
		tablemodel2=new TransferModel();
		
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 0, 820, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JButton btnNewButton_1 = new JButton("检索");
	 	btnNewButton_1.addActionListener(new searchListener());
	 	btnNewButton_1.setBounds(707, 0, 113, 30);
	 	searchPanel.add(btnNewButton_1);
		
		customer=bill.getCustomerlistOfFKD();
	 	user=bill.getUserlistOfFKD();
	 	
	 	customerBox = new JComboBox<String>();
	 	customerBox.setSize(113, 30);
	 	customerBox.setLocation(434, 0);
	 	searchPanel.add(customerBox);
	 	
		JLabel label = new JLabel("起始日期");
		label.setBounds(0, 6, 72, 18);
		searchPanel.add(label);
		
		JLabel label_1 = new JLabel("终止日期");
		label_1.setBounds(194, 5, 72, 18);
		searchPanel.add(label_1);
		
		JLabel label_2 = new JLabel("客户");
		label_2.setBounds(389, 3, 63, 24);
		searchPanel.add(label_2);
		
	 	mp1 = new DateChooser("yyyy-MM-dd");  
		mp1.setBounds(67, 0, 113, 30);
		searchPanel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});
		
		mp2 = new DateChooser("yyyy-MM-dd");  
		mp2.setBounds(261, 0, 113, 30);
		searchPanel.add(mp2);
		
		JLabel label_3 = new JLabel("操作员");
		label_3.setBounds(553, 6, 59, 18);
		searchPanel.add(label_3);
		
		userBox = new JComboBox<String>();
		userBox.setBounds(600, 0, 79, 30);
		searchPanel.add(userBox);
	 	updateBox();
	 	
		table=new JTable(tablemodel1);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener(table));
		
		table2=new JTable(tablemodel2);
		table2.getModel().addTableModelListener(this);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(14, 38, 400, 402);
		add(scrollpane);
		
		JScrollPane scrollpane2=new JScrollPane(table2);
		scrollpane2.setBounds(430, 38, 404, 402);
		add(scrollpane2);
		
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
		
		if(us.getType().equals("财务人员")){
			JButton redFlushButton = new JButton("红冲");
		 	redFlushButton.addActionListener(new redFlushListener());
		 	redFlushButton.setBounds(710, 400, 113, 30);
		 	panel.add(redFlushButton);
		 	
			scrollpane.setBounds(14, 38, 400, 355);
			add(scrollpane);
			
			scrollpane2.setBounds(430, 38, 404, 355);
			add(scrollpane2);
		}
	}
	public void updateBox(){
		for(String c:customer)
			customerBox.addItem(c);
		for(String u:user)
			userBox.addItem(u);
	}
	public void updateTable(){
		tablemodel1=new BillModel();
		while(tablemodel1.getRowCount()>0){
			 tablemodel1.removeRow(tablemodel1.getRowCount()-1);
		}
		if(blist!=null){
			for(PaymentBillVO po:blist){
				templist.add(po);
				Vector v=new Vector();
				v.add(po.getNumberID());
				v.add(po.getCus().getName());
				v.add(po.getUsername());
				v.add(po.getSum());
				tablemodel1.addRow(v);
			}
		}
		table.revalidate();
		repaint();
	}
	
	
	class selectListener implements ListSelectionListener {
		JTable table;
        selectListener(JTable table) {
            this.table = table;
        }
        public void valueChanged(ListSelectionEvent e) {
            int row = table.getSelectedRow();
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
	
	class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Date d1=mp1.getDate();
			Date d2=mp2.getDate();
			String user=(String)userBox.getSelectedItem();
			String customer=(String)customerBox.getSelectedItem();
			blist=bill.searchFKD(d1, d2, user, customer);
			updateTable();
		}
	}
	
	class redFlushListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			boolean j=false;
			if(row!=-1){
				bill.red_flush(templist.get(row));
				j=true;
			}
			if(j==true) utility.setInfo("红冲成功", panel);
			if(j==false) utility.setInfo("请选择要红冲的项目", panel);
			
			Date d1=mp1.getDate();
			Date d2=mp2.getDate();
			String user=(String)userBox.getSelectedItem();
			String customer=(String)customerBox.getSelectedItem();
			blist=bill.searchFKD(d1, d2, user, customer);
			updateTable();
			
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
