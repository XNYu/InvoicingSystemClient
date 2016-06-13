package presentation.tableGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import presentation.customerGUI.CustomerModel;
import presentation.customerGUI.CustomerPanel;
import presentation.customerGUI.CustomerPanel.AddressAdapter;
import presentation.customerGUI.CustomerPanel.EmailAdapter;
import presentation.customerGUI.CustomerPanel.NameAdapter;
import presentation.customerGUI.CustomerPanel.PhoneAdapter;
import presentation.customerGUI.CustomerPanel.PostcodeAdapter;
import presentation.customerGUI.CustomerPanel.QuotaAdapter;
import presentation.promotionGUI.DateChooser;
import PO.CommodityPO;
import PO.SalesPO;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.customerbl.CustomerController;
import businesslogic.salesbl.Sales;
import businesslogic.salesbl.SalesController;
import businesslogic.tablebl.TableController;
import businesslogic.userbl.User;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;
import businesslogicservice.tableBLService.TableBLService;

import javax.swing.JTextArea;

public class ImportReturnPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	ImportModel tablemodel;
	JComboBox<String> customerBox,userBox;
	SalesBLService sbs=new SalesController();
	ImportReturnPanel panel=this;
	ArrayList<SalesVO> importlist=new ArrayList<SalesVO>();
	final DateChooser mp1;
	final DateChooser mp2;
	TableBLService tbs=new TableController();
	Sales sale=new Sales();
	User us=new User();
	ArrayList<CustomerVO> customerList=new ArrayList<CustomerVO>();
	ArrayList<UserVO> userlist=new ArrayList<UserVO>();
	
	public ImportReturnPanel() {
		setSize(850, 460);
		tablemodel=new ImportModel();	
		setLayout(null);
	 	
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 13, 820, 434);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JButton btnNewButton_1 = new JButton("检索");
	 	btnNewButton_1.addActionListener(new searchListener());
	 	btnNewButton_1.setBounds(709, 0, 111, 30);
	 	searchPanel.add(btnNewButton_1);
		
	 	customerBox = new JComboBox<String>();
	 	customerBox.setSize(113, 30);
	 	customerBox.setLocation(456, 0);
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
		label_3.setBounds(575, 6, 59, 18);
		searchPanel.add(label_3);
		
		userBox = new JComboBox<String>();
		userBox.setBounds(622, 0, 79, 30);
		searchPanel.add(userBox);
		
		table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(0, 43, 820, 391);
		searchPanel.add(scrollpane);
		
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
	 	updateBox();
	}
	public void updateTable(){
		while(tablemodel.getRowCount()>0){
			 tablemodel.removeRow(tablemodel.getRowCount()-1);
		}
		table.revalidate();
		if(importlist!=null){
			for(SalesVO vo:importlist){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getCustomer());
				v.add(vo.getStock());
				v.add(vo.getOperator());
				v.add("");
				v.add(String.valueOf(vo.getSum()));
				v.add(vo.getDocument());
				v.add(vo.getExamine());
				tablemodel.addRow(v);
			}
		}
		table.revalidate();
		repaint();
	}
	
	public void updateBox(){
		customerList=sbs.showImportCustomer();
		for(CustomerVO cvo:customerList)
			customerBox.addItem(cvo.getName());
		userlist=us.showUser();
		for(UserVO vo:userlist)
			userBox.addItem(vo.getName());
	}
	
	class searchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String customer;
			Date d1=mp1.select.getTime();
			Date d2=mp2.select.getTime();
			customer=(String) customerBox.getSelectedItem();
			
			importlist=sale.searchSales("进货退货单",customer,null,null,d1,d2);
			updateTable();
		}
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}




