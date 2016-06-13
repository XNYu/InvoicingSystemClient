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
import presentation.examineGUI.SalesPanel.noExamineListener;
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

public class ImportReturnPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	ImportModel tablemodel;
	SalesBLService sbs=new SalesController();
	ImportReturnPanel panel=this;
	ArrayList<SalesVO> importlist=new ArrayList<SalesVO>();
	ArrayList<SalesVO> templist=new ArrayList<SalesVO>();
	TableBLService tbs=new TableController();
	Sales sale=new Sales();
	
	public ImportReturnPanel() {
		setSize(850, 460);
		tablemodel=new ImportModel();	
		setLayout(null);
	 	
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(14, 13, 820, 434);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JLabel label = new JLabel("请选择要审批的单据");
		label.setBounds(14, 0, 173, 43);
		searchPanel.add(label);
		
		JButton button = new JButton("审批未通过");
	 	button.addActionListener(new noExamineListener());
	 	button.setBounds(588, 6, 113, 30);
	 	searchPanel.add(button);
	 	
		JButton btnNewButton_1 = new JButton("确认审批");
	 	btnNewButton_1.addActionListener(new examineListener());
	 	btnNewButton_1.setBounds(707, 6, 113, 30);
	 	searchPanel.add(btnNewButton_1);

		table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		table.getSelectionModel().addListSelectionListener(new selectListener());
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(0, 43, 820, 391);
		searchPanel.add(scrollpane);
		
		importlist=sale.showImportReturn();
		updateTable();
		
	}
	public void updateTable(){
		importlist=sale.showImportReturn();
		templist.removeAll(templist);
	 	while(tablemodel.getRowCount()>0){
			 tablemodel.removeRow(tablemodel.getRowCount()-1);
		}
		table.revalidate();
		if(importlist!=null){
			for(SalesVO vo:importlist){
				if(vo.getExamine().equals("未审批")){
				templist.add(vo);
				Vector v=new Vector();
				v.add(false);
				v.add(vo.getID());
				v.add(vo.getCustomer());
				v.add(vo.getStock());
				v.add(vo.getOperator());
				v.add(String.valueOf(vo.getSum()));
				v.add(vo.getDocument());
				v.add(vo.getExamine());
				tablemodel.addRow(v);
				}
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