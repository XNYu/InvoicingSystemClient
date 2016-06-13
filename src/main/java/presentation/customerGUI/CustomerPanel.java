package presentation.customerGUI;

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






import VO.CustomerVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.customerbl.CustomerController;
import businesslogic.userbl.User;
import businesslogic.userbl.UserController;
import businesslogic.utilitybl.CheckDouble;
import businesslogic.utilitybl.CheckNumber;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.customerBLService.CustomerBLService;
import businesslogicservice.userBLService.UserBLService;

public class CustomerPanel extends JPanel implements TableModelListener,ListSelectionListener{
	public JTable table;
	public CustomerModel tablemodel;
	private JTextField nameField;
	private JComboBox typeComboBox;
	JComboBox rankComboBox;
	JComboBox salesComboBox;
	CustomerBLService cbs=new CustomerController();
	CustomerPanel panel=this;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField postcodeField;
	private JTextField emailField;
	private JTextField quotaField;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	User user=new User();
	UserVO uservo;
	public CustomerPanel(UserVO uservo) {
		this.uservo=uservo;
		cbs.setUser(uservo);
		setSize(820, 430);
		tablemodel=new CustomerModel(uservo.getRank());	
		table=new JTable(tablemodel);
		table.getModel().addTableModelListener(this);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
		setLayout(null);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(20, 10, 780, 380);
		add(scrollpane);
		
		selectionModel=table.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel.addListSelectionListener(this);

		
		
		TableColumn typeColumn = table.getColumnModel().getColumn(1);
		JComboBox comboBox = new JComboBox(); 
		comboBox.addItem("进货商");  
		comboBox.addItem("销售商");  
		typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		
		
		TableColumn rankColumn = table.getColumnModel().getColumn(2);
		JComboBox rcomboBox = new JComboBox(); 
		rcomboBox.addItem("1");  
		rcomboBox.addItem("2");
		rcomboBox.addItem("3");
		rcomboBox.addItem("4");
		rcomboBox.addItem("5");
		rankColumn.setCellEditor(new DefaultCellEditor(rcomboBox));
		
		
		
		setVisible(true);
		table.setFillsViewportHeight(true);  
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JDialog infoDialog=new JDialog();
		infoDialog.setSize(100,80);
		infoDialog.setLocationRelativeTo(this);
		
		JPanel addPanel = new JPanel();
		addPanel.setBounds(20, 405, 780, 25);
		add(addPanel);
		addPanel.setLayout(null);
		
		nameField = new JTextField("姓名");
		nameField.setLocation(130, 0);
		nameField.setSize(65, 20);
		addPanel.add(nameField);
		nameField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		nameField.setColumns(10);
		nameField.addFocusListener(new NameAdapter());
		
		typeComboBox = new JComboBox();
		typeComboBox.setLocation(0, 0);
		typeComboBox.setSize(65, 20);
		addPanel.add(typeComboBox);
		typeComboBox.addItem("进货商");  
		typeComboBox.addItem("销售商");
		
		phoneField = new JTextField();
		phoneField.setText("电话");
		phoneField.setBounds(195, 0, 65, 20);
		addPanel.add(phoneField);
		phoneField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		phoneField.setColumns(10);
		phoneField.addFocusListener(new PhoneAdapter());
		
		addressField = new JTextField();
		addressField.setText("地址");
		addressField.setBounds(260, 0, 65, 20);
		addPanel.add(addressField);
		addressField.setColumns(10);
		addressField.addFocusListener(new AddressAdapter());
		addressField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		
		postcodeField = new JTextField();
		postcodeField.setText("邮编");
		postcodeField.setBounds(325, 0, 65, 20);
		addPanel.add(postcodeField);
		postcodeField.setColumns(10);
		postcodeField.addFocusListener(new PostcodeAdapter());
		postcodeField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		
		emailField = new JTextField();
		emailField.setText("e-mail");
		emailField.setBounds(390, 0, 65, 20);
		addPanel.add(emailField);
		emailField.setColumns(10);
		emailField.addFocusListener(new EmailAdapter());
		emailField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		
		quotaField = new JTextField();
		quotaField.setText("应收额度");
		quotaField.setBounds(455, 0, 65, 20);
		addPanel.add(quotaField);
		quotaField.setColumns(10);
		quotaField.addFocusListener(new QuotaAdapter());
		quotaField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		
		salesComboBox=new JComboBox();
		salesComboBox.setSize(65, 20);
		salesComboBox.setLocation(520, 0);
		ArrayList<UserVO> ulist=user.showUser();
		for(UserVO uvo:ulist){
			salesComboBox.addItem(uvo.getName());
		}
		addPanel.add(salesComboBox);
		/*salesmanField = new JTextField();
		salesmanField.setText("默认业务员");
		salesmanField.setBounds(560, 0, 70, 20);
		addPanel.add(salesmanField);
		salesmanField.setColumns(10);
		salesmanField.addFocusListener(new SalesmanAdapter());
		salesmanField.setHorizontalAlignment(SwingConstants.HORIZONTAL);*/
		
		
		rankComboBox = new JComboBox();
		rankComboBox.setBounds(65, 0, 65, 20);
		addPanel.add(rankComboBox);
		rankComboBox.addItem("1");
		rankComboBox.addItem("2");
		rankComboBox.addItem("3");
		rankComboBox.addItem("4");
		rankComboBox.addItem("5");
		
		JButton addButton = new JButton("ADD");
		addButton.setBounds(650, 0, 65, 20);
		addPanel.add(addButton);
		
		JButton delButton = new JButton("DEL");
		delButton.setBounds(715, 0, 65, 20);
		addPanel.add(delButton);
		
		JButton updButton = new JButton("UPD");
		updButton.setHorizontalAlignment(SwingConstants.CENTER);
		updButton.setBounds(585, 0, 65, 20);
		addPanel.add(updButton);
		
		delButton.addActionListener(new DelListener());
		addButton.addActionListener(new AddListener());
		
		addPanel.setVisible(true);
		addPanel.repaint();
		
		
		
		
		
		ArrayList<CustomerVO> voList=cbs.showCustomer();
		if(voList!=null){
			for(CustomerVO vo:voList){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getType());
				v.add(String.valueOf(vo.getRank()));
				v.add(vo.getName());
				v.add(vo.getPhone());
				v.add(vo.getAddress());
				v.add(vo.getPostcode());
				v.add(vo.getEmail());
				v.add(String.valueOf(vo.getQuota()));
				v.add(String.valueOf(vo.getPayment()));
				v.add(String.valueOf(vo.getReceiving()));
				v.add(vo.getSalesman());
				tablemodel.addRow(v);
			}
		}
		setVisible(true);
		repaint();
	}
	
	public void updateTable(){
		ArrayList<CustomerVO> customerlist=cbs.showCustomer();
		
		for(int i=0;i<table.getRowCount();){
			tablemodel.removeRow(0);
		}
		table.revalidate();
		if(customerlist!=null){
			for(CustomerVO vo:customerlist){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getType());
				v.add(String.valueOf(vo.getRank()));
				v.add(vo.getName());
				v.add(vo.getPhone());
				v.add(vo.getAddress());
				v.add(vo.getPostcode());
				v.add(vo.getEmail());
				v.add(String.valueOf(vo.getQuota()));
				v.add(String.valueOf(vo.getPayment()));
				v.add(String.valueOf(vo.getReceiving()));
				v.add(vo.getSalesman());
				tablemodel.addRow(v);
			}
		}
		table.revalidate();
		repaint();
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();  
        int column = e.getColumn();  
        TableModel model = (TableModel)e.getSource();  
        String columnName = model.getColumnName(column);  
        Object data = model.getValueAt(row, column); 
       // System.out.println(table.getValueAt(row,8));
		cbs.modifyCustomer(new CustomerVO((String)table.getValueAt(row,0),(String)table.getValueAt(row,1),
				Integer.parseInt((String)table.getValueAt(row,2)),(String)table.getValueAt(row,3),(String)table.getValueAt(row,4),(String)table.getValueAt(row,5),(String)table.getValueAt(row,6),
				(String)table.getValueAt(row,7),Double.parseDouble((String)table.getValueAt(row,8)),Double.parseDouble((String)table.getValueAt(row,9)),Double.parseDouble((String)table.getValueAt(row,10)),
				(String)table.getValueAt(row,11)));
		
		panel.setVisible(true);
		panel.repaint();
	}
	
	
	class UpdateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateTable();
		}
		
	}
	
	class DelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text="删除成功";
			boolean flag=false;
			if(selectedRows == null)
				return;
			for(int i = 0 ; i < selectedRows.length ;i++){
				CustomerVO vo=cbs.searchCustomer((String)tablemodel.getValueAt(selectedRows[i], 0));
				if(vo.getPayment()!=0||vo.getReceiving()!=0){
					flag=true;
					break;
				}
			}
			if(flag){
				text="删除失败";
				JDialog infoDialog=new JDialog();
				infoDialog.setSize(100,80);
				infoDialog.setLocationRelativeTo(panel);
				infoDialog.setVisible(true);
				JLabel infoLabel=new JLabel(text);
				infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
				infoLabel.setVisible(true);
				infoDialog.getContentPane().add(infoLabel);
				panel.setVisible(true);
				panel.repaint();
			}
			
			else{
				for(int i = 0 ; i < selectedRows.length ;i++){
					cbs.delCustomer((String)tablemodel.getValueAt(selectedRows[i], 0));
					
				}
			    for(int i = 0 ; i < selectedRows.length ;i++){
			    	tablemodel.removeRow(selectedRows[i]-i);
			    }
				JDialog infoDialog=new JDialog();
				infoDialog.setSize(100,80);
				infoDialog.setLocationRelativeTo(panel);
				infoDialog.setVisible(true);
				JLabel infoLabel=new JLabel(text);
				infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
				infoLabel.setVisible(true);
				infoDialog.getContentPane().add(infoLabel);
				panel.setVisible(true);
				panel.repaint();
					
			    table.revalidate();
				table.repaint();
			}
		}
		
	}
	
	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text="";
			String ID=null;
			int row =table.getSelectedRow();
			if(((String)salesComboBox.getSelectedItem())==null){
				text="未选择操作员";
			}
			if(CheckDouble.isAllDouble(quotaField.getText())&&CheckNumber.isNum(phoneField.getText())){
				ID=cbs.addCustomer(new CustomerVO("",(String)typeComboBox.getSelectedItem(),Integer.parseInt((String)rankComboBox.getSelectedItem()),
					nameField.getText(),phoneField.getText(),addressField.getText(),postcodeField.getText(),emailField.getText(),
					Double.parseDouble(quotaField.getText()),0.0,0.0,(String)salesComboBox.getSelectedItem()));
			}else{
				text="不合法输入 ";
	
			}
			
			if(ID!=null){
				text="添加成功";
				Vector v=new Vector();
				v.add(ID);
				v.add((String)typeComboBox.getSelectedItem());
				v.add((String)rankComboBox.getSelectedItem());
				v.add(nameField.getText());
				v.add(phoneField.getText());
				v.add(addressField.getText());
				v.add(postcodeField.getText());
				v.add(emailField.getText());
				v.add(quotaField.getText());
				v.add("0.0");
				v.add("0.0");
				v.add((String)salesComboBox.getSelectedItem());
				tablemodel.addRow(v);
				table.repaint();
				panel.setVisible(true);
				panel.repaint();
			}else{
				if(text==null)
					text.equals("");
			}
			
			JDialog infoDialog=new JDialog();
			infoDialog.setSize(100,80);
			infoDialog.setLocationRelativeTo(panel);
			infoDialog.setVisible(true);
			JLabel infoLabel=new JLabel(text);
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setVisible(true);
			infoDialog.getContentPane().add(infoLabel);
			
			panel.setVisible(true);
			panel.repaint();
		}
		
	}
	/*
	public void updateTable(){
		tablemodel=new CustomerModel();
		ArrayList<CustomerVO> voList=cbs.showCustomer();
		if(voList!=null){
			for(CustomerVO vo:voList){
				Vector v=new Vector();
				v.add(vo.getName());
				v.add(vo.getType());
				v.add(vo.getID());
				
				v.add(vo.getRank());
				tablemodel.addRow(v);
			}
		}
		panel.setVisible(true);
		panel.repaint();
	}*/
	public class NameAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (nameField.getText().equals("姓名")) {
	            nameField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (nameField.getText().equals("")) {
	            nameField.setText("姓名");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class QuotaAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (quotaField.getText().equals("应收额度")) {
	            quotaField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (quotaField.getText().equals("")) {
	            quotaField.setText("应收额度");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class PhoneAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (phoneField.getText().equals("电话")) {
	            phoneField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (phoneField.getText().equals("")) {
	            phoneField.setText("电话");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class AddressAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (addressField.getText().equals("地址")) {
	            addressField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (addressField.getText().equals("")) {
	            addressField.setText("地址");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class PostcodeAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (postcodeField.getText().equals("邮编")) {
	            postcodeField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (postcodeField.getText().equals("")) {
	            postcodeField.setText("邮编");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	public class EmailAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (emailField.getText().equals("e-mail")) {
	            emailField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (emailField.getText().equals("")) {
	            emailField.setText("e-mail");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
	
	
	
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO 自动生成的方法存根
		selectedRows=table.getSelectedRows();
	}
}
