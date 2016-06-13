package presentation.salesGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
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
import PO.CommodityPO;
import PO.SalesPO;
import VO.CustomerVO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.customerbl.CustomerController;
import businesslogic.salesbl.SalesController;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;

import javax.swing.JTextArea;

public class SalesReturnPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	SalesModel tablemodel;
	JComboBox customerBox;
	SalesBLService sbs=new SalesController();
	SalesReturnPanel panel=this;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	private JComboBox cComboBox;
	private JTextField stockField;
	private JTextField operatorField;
	private JTextField salesmanField;
	private JTextField voucherField;
	private JTextField documentField;
	ArrayList<CommodityPO> commoditylist;
	ArrayList<SalesVO> saleslist=new ArrayList<SalesVO>();
	CommodityButtonColumn cbutton;
	UserVO uvo;
	public SalesReturnPanel(UserVO uvo) {
		this.uvo=uvo;
		sbs.setUser(uvo);
		setSize(820, 460);
		tablemodel=new SalesModel();	
		table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
		setLayout(null);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(20, 0, 780, 380);
		add(scrollpane);
		
		selectionModel=table.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel.addListSelectionListener(this);
	 	
	 	JPanel addpanel = new JPanel();
	 	addpanel.setBounds(20, 385, 780, 50);
	 	add(addpanel);
	 	addpanel.setLayout(null);
	 	
	
	 	JButton refreshButton = new JButton("刷新");
	 	refreshButton.setBounds(690, 0, 90, 20);
	 	addpanel.add(refreshButton);
	 	refreshButton.addActionListener(new FreshListener());
	 	
	 	saleslist=sbs.showSalesReturn();
		cbutton=new CommodityButtonColumn(table,5,saleslist);
		
		updateTable();
		
	}
	public void updateTable(){
		saleslist=sbs.showSalesReturn();
		cbutton.setVoList(saleslist);
		for(int i=0;i<table.getRowCount();){
			tablemodel.removeRow(0);
		}
		table.revalidate();
		if(saleslist!=null){
			for(SalesVO vo:saleslist){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getCustomer());
				v.add(vo.getStock());
				v.add(vo.getOperator());
				v.add(vo.getSalesman());
				v.add("");
				v.add(String.valueOf(vo.getSum()));
				v.add(String.valueOf(vo.getDiscount()));
				v.add(String.valueOf(vo.getAfterDiscount()));
				v.add(String.valueOf(vo.getVoucher()));
				
				v.add(vo.getDocument());
				v.add(vo.getExamine());
				tablemodel.addRow(v);
			}
		}
		table.revalidate();
		repaint();
	}
	
	public class FreshListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateTable();
		}
		
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		selectedRows=table.getSelectedRows();
	}
	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
