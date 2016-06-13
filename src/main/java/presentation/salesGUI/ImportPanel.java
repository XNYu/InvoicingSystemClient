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
import businesslogic.utilitybl.CheckDouble;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.salesBLService.SalesBLService;

import javax.swing.JTextArea;

public class ImportPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	ImportModel tablemodel;
	JComboBox customerBox;
	SalesBLService sbs=new SalesController();
	ImportPanel panel=this;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	private JComboBox cComboBox;
	private JTextField stockField;
	private JTextField operatorField;
	private JTextField documentField;
	ArrayList<CommodityPO> commoditylist=new ArrayList<CommodityPO>();
	ArrayList<SalesVO> importlist=new ArrayList<SalesVO>();
	CommodityButtonColumn2 cbutton;
	UserVO uvo;
	public ImportPanel(UserVO uvo) {
		this.uvo=uvo;
		sbs.setUser(uvo);
		setSize(820, 460);
		tablemodel=new ImportModel();	
		table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
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
	 	cComboBox.setBounds(0, 0, 150, 20);
	 	ArrayList<CustomerVO> clist=sbs.showImportCustomer();
		for(CustomerVO cvo:clist){
			cComboBox.addItem(cvo.getName());
		}
	 	addpanel.add(cComboBox);
	 	
	 	JButton commodityButton = new JButton("入库商品");
	 	commodityButton.setBounds(150, 0, 150, 20);
	 	addpanel.add(commodityButton);
	 	commodityButton.addActionListener(new CommodityListener());
	 	
	 	stockField = new JTextField();
	 	stockField.setText("仓库");
	 	stockField.setBounds(300, 0, 150, 20);
	 	addpanel.add(stockField);
	 	stockField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	stockField.setColumns(10);
	 	stockField.addFocusListener(new StockAdapter());
	 	
	 	operatorField = new JTextField();
	 	operatorField.setText("操作员");
	 	operatorField.setBounds(450, 0, 150, 20);
	 	addpanel.add(operatorField);
	 	operatorField.setColumns(10);
	 	operatorField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
	 	operatorField.addFocusListener(new OperatorAdapter());
	 	
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
	 	
	 	importlist=sbs.showImport();
		cbutton=new CommodityButtonColumn2(table,4,importlist);
		updateTable();
		
	}
	public void updateTable(){
		importlist=sbs.showImport();
		cbutton.setVoList(importlist);
		for(int i=0;i<table.getRowCount();){
			tablemodel.removeRow(0);
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
	
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String ID=null;
			String text=null;
			int row =table.getSelectedRow();
			if(cComboBox.getSelectedItem()==null){
				text="没有选择客户";
			}else {
				ID=sbs.makeImport(new SalesVO("","Import",(String)cComboBox.getSelectedItem(),stockField.getText(),
					operatorField.getText(),"",commoditylist,0,0,
					0,0,documentField.getText(),null,"未审批"));
				commoditylist.clear();
			}
			
			if(ID!=null){
				text="进货成功";
				updateTable();
			}else if(text==null){
				text="进货失败";
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
			importlist=sbs.showImport();
			if(commoditylist==null)
				commoditylist=new ArrayList<CommodityPO>();
			
		    new CommodityFrame2(commoditylist);
		}
		
	}
	
	class ReturnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		   String ID=null;
			for(int i=0;i<selectedRows.length;++i){
		    	SalesVO vo=importlist.get(selectedRows[i]);
		    	if(vo.th){
		    		vo.setType("ImportReturn");
		    		vo.th=false;
		    		ID=sbs.makeImportReturn(vo);
		    		
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
