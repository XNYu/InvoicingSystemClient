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
import presentation.promotionGUI.utility;
import PO.CommodityPO;
import PO.SalesPO;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.SalesVO;
import businesslogic.customerbl.CustomerController;
import businesslogic.salesbl.Sales;
import businesslogic.salesbl.SalesController;
import businesslogic.tablebl.TableController;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;
import businesslogicservice.tableBLService.TableBLService;

import javax.swing.JTextArea;

public class SalesReturnPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	SalesModel tablemodel;
	SalesBLService sbs=new SalesController();
	SalesReturnPanel panel=this;
	ArrayList<SalesVO> templist=new ArrayList<SalesVO>();	
	ArrayList<SalesVO> saleslist=new ArrayList<SalesVO>();
	TableBLService tbs=new TableController();
	Sales sale=new Sales();

	public SalesReturnPanel() {
		setSize(850, 460);
		tablemodel=new SalesModel();	
		setLayout(null);
	 	
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 0, 822, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JLabel label = new JLabel("请选择要审批的单据");
		label.setBounds(14, 0, 173, 43);
		searchPanel.add(label);
	 	

	 	
	 	JButton btnNewButton_1 = new JButton("确认审批");
	 	btnNewButton_1.addActionListener(new examineListener());
	 	btnNewButton_1.setBounds(707, 6, 113, 30);
	 	searchPanel.add(btnNewButton_1);
	 	
	 	JButton button = new JButton("审批未通过");
	 	button.addActionListener(new noExamineListener());
	 	button.setBounds(588, 6, 113, 30);
	 	searchPanel.add(button);

	 	table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener());
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(14, 38, 822, 402);
		add(scrollpane);
		
	 	saleslist=sale.showSalesReturn();
		updateTable();
		
		
	}
	public void updateTable(){
	 	saleslist=sale.showSalesReturn();
	 	templist.removeAll(templist);
	 	while(tablemodel.getRowCount()>0){
			 tablemodel.removeRow(tablemodel.getRowCount()-1);
		}
		table.revalidate();
		if(saleslist!=null){
			for(SalesVO vo:saleslist){
				if(vo.getExamine().equals("未审批"))
				{
					templist.add(vo);
					Vector v=new Vector();
					v.add(false);
					v.add(vo.getID());
					v.add(vo.getCustomer());
					v.add(vo.getStock());
					v.add(vo.getOperator());
					v.add(vo.getSalesman());
					v.add(String.valueOf(vo.getSum()));
					v.add(String.valueOf(vo.getDiscount()));
					v.add(String.valueOf(vo.getAfterDiscount()));
					v.add(String.valueOf(vo.getVoucher()));
					v.add(vo.getDocument());
					v.add(vo.getExamine());
					tablemodel.addRow(v);}
			}
		}
		table.revalidate();
		repaint();
	}
	
	class selectListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int row = table.getSelectedRow();
			table.setValueAt(true, row, 0);
		}
	}
	
	class examineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int row=table.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)table.getValueAt(i, 0)==true){
					sale.examine((String)table.getValueAt(i, 1), "审批通过");
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
			int row=table.getRowCount();
			boolean j=false;
			for(int i=0;i<row;i++){
				if((boolean)table.getValueAt(i, 0)==true){
					sale.examine((String)table.getValueAt(i, 1), "审批未通过");
					j=true;
				}
			}
			if(j==true) utility.setInfo("您所选的单据未通过审批", panel);
			if(j==false) utility.setInfo("请选择要审批的单据", panel);
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