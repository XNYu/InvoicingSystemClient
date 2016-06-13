package presentation.initialGUI;

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
import businesslogic.customerbl.CustomerController;
import businesslogicservice.customerBLService.CustomerBLService;

public class CustomerShow extends JPanel implements TableModelListener,ListSelectionListener{
	public JTable table;
	public CustomerModel tablemodel;
	private JTextField nameField;
	private JComboBox typeComboBox;
	JComboBox rankComboBox;
	CustomerBLService cbs=new CustomerController();
	CustomerShow panel=this;
	private JTextField phoneField;
	private JTextField addressField;
	private JTextField postcodeField;
	private JTextField emailField;
	private JTextField quotaField;
	private JTextField salesmanField;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	public CustomerShow() {
		setSize(820, 430);
		tablemodel=new CustomerModel();
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
		nameField.setLocation(140, 0);
		nameField.setSize(70, 20);
		addPanel.add(nameField);
		nameField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		nameField.setColumns(10);
		nameField.addFocusListener(new NameAdapter());

		typeComboBox = new JComboBox();
		typeComboBox.setLocation(0, 0);
		typeComboBox.setSize(70, 20);
		addPanel.add(typeComboBox);
		typeComboBox.addItem("进货商");
		typeComboBox.addItem("销售商");

		phoneField = new JTextField();
		phoneField.setText("电话");
		phoneField.setBounds(210, 0, 70, 20);
		addPanel.add(phoneField);
		phoneField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		phoneField.setColumns(10);
		phoneField.addFocusListener(new PhoneAdapter());

		addressField = new JTextField();
		addressField.setText("地址");
		addressField.setBounds(280, 0, 70, 20);
		addPanel.add(addressField);
		addressField.setColumns(10);
		addressField.addFocusListener(new AddressAdapter());
		addressField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

		postcodeField = new JTextField();
		postcodeField.setText("邮编");
		postcodeField.setBounds(350, 0, 70, 20);
		addPanel.add(postcodeField);
		postcodeField.setColumns(10);
		postcodeField.addFocusListener(new PostcodeAdapter());
		postcodeField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

		emailField = new JTextField();
		emailField.setText("e-mail");
		emailField.setBounds(420, 0, 70, 20);
		addPanel.add(emailField);
		emailField.setColumns(10);
		emailField.addFocusListener(new EmailAdapter());
		emailField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

		quotaField = new JTextField();
		quotaField.setText("应收额度");
		quotaField.setBounds(490, 0, 70, 20);
		addPanel.add(quotaField);
		quotaField.setColumns(10);
		quotaField.addFocusListener(new QuotaAdapter());
		quotaField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

		salesmanField = new JTextField();
		salesmanField.setText("默认业务员");
		salesmanField.setBounds(560, 0, 70, 20);
		addPanel.add(salesmanField);
		salesmanField.setColumns(10);
		salesmanField.addFocusListener(new SalesmanAdapter());
		salesmanField.setHorizontalAlignment(SwingConstants.HORIZONTAL);

		rankComboBox = new JComboBox();
		rankComboBox.setBounds(70, 0, 70, 20);
		addPanel.add(rankComboBox);
		rankComboBox.addItem("1");
		rankComboBox.addItem("2");
		rankComboBox.addItem("3");
		rankComboBox.addItem("4");
		rankComboBox.addItem("5");

		JButton addButton = new JButton("ADD");
		addButton.setBounds(630, 0, 75, 20);
		addPanel.add(addButton);

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




	

	class AddListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text;
			int row =table.getSelectedRow();
			String ID=cbs.addCustomer(new CustomerVO("",(String)typeComboBox.getSelectedItem(),Integer.parseInt((String)rankComboBox.getSelectedItem()),
					nameField.getText(),phoneField.getText(),addressField.getText(),postcodeField.getText(),emailField.getText(),
					Double.parseDouble(quotaField.getText()),0.0,0.0,salesmanField.getText()));

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
				v.add(salesmanField.getText());
				tablemodel.addRow(v);
				table.repaint();
				panel.setVisible(true);
				panel.repaint();
			}else{
				text="添加失败";

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

	public class SalesmanAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (salesmanField.getText().equals("默认业务员")) {
	            salesmanField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (salesmanField.getText().equals("")) {
	            salesmanField.setText("默认业务员");
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

