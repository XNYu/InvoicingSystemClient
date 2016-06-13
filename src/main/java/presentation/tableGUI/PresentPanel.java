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
import PO.CommodityPO;
import PO.EntryPO;
import PO.PaymentBillPO;
import PO.PresentPO;
import PO.ReceivingBillPO;
import PO.TransferPO;
import VO.CostBillVO;
import VO.ReceivingBillVO;
import businesslogic.billbl.Bill;
import businesslogic.salesbl.Sales;
import businesslogic.tablebl.TableController;
import businesslogicservice.tableBLService.TableBLService;

public class PresentPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table,table2;
	PresentModel tablemodel1;
	ComTableModel tablemodel2;
	JComboBox<String> customerBox;
	PresentPanel panel=this;
	TableBLService tbs=new TableController();
	Sales sale;
	DateChooser mp1;
	DateChooser mp2;
	ArrayList<String> customer=new ArrayList<String>();
	ArrayList<PresentPO> plist;
	ArrayList<CommodityPO> comlist=null;
	
	public PresentPanel(){
		setSize(850, 460);
		setLayout(null);
		tablemodel1=new PresentModel();
		
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 0, 820, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JButton btnNewButton_1 = new JButton("检索");
	 	btnNewButton_1.addActionListener(new searchListener());
	 	btnNewButton_1.setBounds(707, 0, 113, 30);
	 	searchPanel.add(btnNewButton_1);
	 	
	 	//customer=
	 	
		JLabel label = new JLabel("起始日期");
		label.setBounds(0, 6, 72, 18);
		searchPanel.add(label);
		
		JLabel label_1 = new JLabel("终止日期");
		label_1.setBounds(194, 5, 72, 18);
		searchPanel.add(label_1);
		
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
		
		JLabel label_3 = new JLabel("顾客");
		label_3.setBounds(388, 6, 59, 18);
		searchPanel.add(label_3);
		
		customerBox = new JComboBox<String>();
		customerBox.setBounds(435, 0, 161, 30);
		searchPanel.add(customerBox);
	 	updateBox();
	 	
		table=new JTable(tablemodel1);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener(table));
		
		table2=new JTable(tablemodel2);
		table2.getModel().addTableModelListener(this);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(14, 38, 186, 402);
		add(scrollpane);
		
		JScrollPane scrollpane2=new JScrollPane(table2);
		scrollpane2.setBounds(214, 38, 620, 402);
		add(scrollpane2);
		
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
	}
	public void updateBox(){
		for(String u:customer)
			customerBox.addItem(u);
	}
	
	/*public void updateTable(){
		tablemodel1=new PresentModel();
		if(plist!=null){
			for(PresentPO po:plist){
				Vector v=new Vector();
				v.add(po.getCustomer());
				v.add(po.getAcc().getName());
				v.add(po.getUsername());
				v.add(po.getSum());
			}
		}
		panel.setVisible(true);
		panel.repaint();
	}
	*/
	class selectListener implements ListSelectionListener {
		JTable table;
        selectListener(JTable table) {
            this.table = table;
        }
        
        public void valueChanged(ListSelectionEvent e) {
            int row = table.getSelectedRow();
            PresentPO po=plist.get(row-1);
            comlist=po.getCommoditylist();
            updateCom(comlist);
        }
	}
	
	public void updateCom(ArrayList<CommodityPO> comlist){
		for(CommodityPO po:comlist){
			Vector v=new Vector();
			v.add(po.getID());
			v.add(po.getName());
			v.add(po.getType());
			v.add(po.getAmount());
			tablemodel2.addRow(v);
		}
	}
	
	class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			/*Date d1=mp1.getDate();
			Date d2=mp2.getDate();
			String customer=(String)customerBox.getSelectedItem();
			
			updateTable();*/
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
