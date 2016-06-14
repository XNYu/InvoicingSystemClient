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
import VO.PromotionVO.types;
import VO.UserVO;
import businesslogic.customerbl.CustomerController;
import businesslogic.salesbl.SalesController;
import businesslogic.utilitybl.CheckDouble;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;

import javax.swing.JTextArea;

public class SalesPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	SalesModel tablemodel;
	SalesBLService sbs=new SalesController();
	CustomerBLService cbs=new CustomerController();
	SalesPanel panel=this;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	private JComboBox cComboBox;
	private JTextField stockField;
	private JTextField operatorField;
	private JTextField salesmanField;
	private JTextField voucherField;
	private JTextField documentField;
	ArrayList<CommodityPO> commoditylist=new ArrayList<CommodityPO>();
	ArrayList<SalesVO> saleslist=new ArrayList<SalesVO>();
	CommodityButtonColumn cbutton;
	ArrayList<PromotionVO> promotionlist=new ArrayList<PromotionVO>();
	UserVO uvo;
	public SalesPanel(UserVO uvo) {
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
	 	
	 	JButton addButton = new JButton("提交");
	 	addButton.setBounds(690, 0, 90, 20);
	 	addpanel.add(addButton);
	 	addButton.addActionListener(new AddListener());
	 	
	 	cComboBox = new JComboBox();
	 	cComboBox.setBounds(0, 0, 100, 20);
	 	ArrayList<CustomerVO> clist=sbs.showSalesCustomer();
		for(CustomerVO cvo:clist){
			cComboBox.addItem(cvo.getName());
		}
	 	addpanel.add(cComboBox);
	 	
	 	JButton commodityButton = new JButton("出库商品");
	 	commodityButton.setBounds(100, 0, 100, 20);
	 	addpanel.add(commodityButton);
	 	commodityButton.addActionListener(new CommodityListener());
	 	
	 	stockField = new JTextField();
	 	stockField.setText("仓库");
	 	stockField.setBounds(200, 0, 100, 20);
	 	addpanel.add(stockField);
	 	stockField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	stockField.setColumns(10);
	 	stockField.addFocusListener(new StockAdapter());
	 	
	 	operatorField = new JTextField();
	 	operatorField.setText("操作员");
	 	operatorField.setBounds(300, 0, 100, 20);
	 	addpanel.add(operatorField);
	 	operatorField.setColumns(10);
	 	operatorField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	operatorField.addFocusListener(new OperatorAdapter());
	 	
	 	salesmanField = new JTextField();
	 	salesmanField.setText("业务员");
	 	salesmanField.setBounds(400, 0, 100, 20);
	 	addpanel.add(salesmanField);
	 	salesmanField.setColumns(10);
	 	salesmanField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	salesmanField.addFocusListener(new SalesmanAdapter());
	 	
	 	voucherField = new JTextField();
	 	voucherField.setText("代金券金额");
	 	voucherField.setBounds(500, 0, 100, 20);
	 	addpanel.add(voucherField);
	 	voucherField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	voucherField.addFocusListener(new VoucherAdapter());
	 	
	 	documentField = new JTextField();
	 	documentField.setText("备注");
	 	documentField.setBounds(0, 25, 690, 20);
	 	addpanel.add(documentField);
	 	documentField.setColumns(10);
	 	documentField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	documentField.addFocusListener(new DocumentAdapter());
	 	
	 	JButton refreshButton = new JButton("刷新");
	 	refreshButton.setBounds(600, 0, 90, 20);
	 	addpanel.add(refreshButton);
	 	refreshButton.addActionListener(new FreshListener());
	 	
	 	JButton returnButton;
	 	returnButton = new JButton("退货");
	 	returnButton.setBounds(690, 25, 90, 20);
	 	addpanel.add(returnButton);
	 	returnButton.addActionListener(new ReturnListener());
	 	
	 	saleslist=sbs.showSales();
		cbutton=new CommodityButtonColumn(table,5,saleslist);
		
		updateTable();
		
	}
	public void updateTable(){
		saleslist=sbs.showSales();
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
	
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text=null;
			int row =table.getSelectedRow();
			String ID=null;
			if(cComboBox.getSelectedItem()==null){
				text="没有选择客户";
			}else if(CheckDouble.isAllDouble(voucherField.getText())){
				ID=sbs.makeSales(new SalesVO("","Sales",(String)cComboBox.getSelectedItem(),stockField.getText(),
				operatorField.getText(),salesmanField.getText(),commoditylist,0,0,
				0,Double.parseDouble(voucherField.getText()),documentField.getText(),promotionlist,"未审批"));
				System.out.println("销售商:"+(String)cComboBox.getSelectedItem());
				System.out.println("仓库:"+stockField.getText());
			//	System.out.println(
				commoditylist.clear();
			}else{
				text="不合法输入";
			}
			
			if(ID!=null){
				text="销售成功";
				updateTable();
			}else if(text==null){
				text="销售失败";
			}
			
			
			JDialog infoDialog=new JDialog();
			infoDialog.setSize(100,80);
			infoDialog.setLocationRelativeTo(panel);
			infoDialog.setVisible(true);
			JLabel infoLabel=new JLabel(text);
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setVisible(true);
			infoDialog.getContentPane().add(infoLabel);
			
			updateTable();
			panel.setVisible(true);
			panel.repaint();
			
		}
		
	}
	
	class CommodityListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		   CommodityFrame cf= new CommodityFrame(commoditylist,promotionlist,(String)cComboBox.getSelectedItem());
		   cf.setLocationRelativeTo(null);
		}
		
	}
	
	class ReturnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		    String ID=null;
			for(int i=0;i<selectedRows.length;++i){
		    	SalesVO vo=saleslist.get(selectedRows[i]);
		    	if(vo.th){
		    		vo.setType("SalesReturn");
		    		vo.th=false;
		    		ID=sbs.makeSalesReturn(vo);
		    	}else{
		    		JDialog infoDialog=new JDialog();
					infoDialog.setSize(100,80);
					infoDialog.setLocationRelativeTo(panel);
					infoDialog.setVisible(true);
					JLabel infoLabel=new JLabel("退货失败");
					infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
					infoLabel.setVisible(true);
					infoDialog.getContentPane().add(infoLabel);
					return;
		    	}
		    	
		    }
			String text=null;
			if(ID==null){
				text="退货失败";
			}else{
				text="退货成功";
			}
		    JDialog infoDialog=new JDialog();
			infoDialog.setSize(100,80);
			infoDialog.setLocationRelativeTo(panel);
			infoDialog.setVisible(true);
			JLabel infoLabel=new JLabel(text);
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setVisible(true);
			infoDialog.getContentPane().add(infoLabel);
			
		}
		
	}
	
	public class FreshListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateTable();
		}
		
	}
	
	
	
	public class StockAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (stockField.getText().equals("仓库")) {
	            stockField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (stockField.getText().equals("")) {
	            stockField.setText("仓库");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class OperatorAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (operatorField.getText().equals("操作员")) {
				if(uvo!=null){
	            	operatorField.setText(uvo.getName());
	            }else{
	            	operatorField.setText("");
	            }
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (operatorField.getText().equals("")) {
	            operatorField.setText("操作员");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class SalesmanAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (!salesmanField.getText().equals("")) {
	            if(!cComboBox.getSelectedItem().equals("")){
	            	ArrayList<CustomerVO> salelist=cbs.vagueFind((String)cComboBox.getSelectedItem());
	            	for(CustomerVO v:salelist){
	            		if(v.getName().equals((String)cComboBox.getSelectedItem())){
	            			salesmanField.setText(v.getSalesman());
	            		}
	            	}
	            }else{
	            	salesmanField.setText("");
	            }
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (salesmanField.getText().equals("")) {
	            salesmanField.setText("业务员");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class VoucherAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (voucherField.getText().equals("代金券金额")) {
	            voucherField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (voucherField.getText().equals("")) {
	            voucherField.setText("代金券金额");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class DocumentAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (documentField.getText().equals("备注")) {
	            documentField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (documentField.getText().equals("")) {
	            documentField.setText("备注");
	        }
			panel.setVisible(true);
			panel.repaint();
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
