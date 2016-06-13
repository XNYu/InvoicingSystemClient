package presentation.userGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JFrame;
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
import VO.UserVO;
import businesslogic.userbl.UserController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.userBLService.UserBLService;

public class UserPanel extends JPanel implements TableModelListener,ListSelectionListener{
	JTable table;
	UserModel tablemodel;
	private JTextField nameField;
	private JTextField idField;
	private JTextField passwordField;
	private JComboBox typeComboBox;
	JComboBox rankComboBox;
	UserBLService ubs=new UserController();
	UserPanel panel=this;
	ListSelectionModel selectionModel;
	int[] selectedRows=null;
	UserVO uvo;
	public UserPanel(UserVO uvo) {
		this.uvo=uvo;
		ubs.setUser(uvo);
		setSize(820, 430);
		tablemodel=new UserModel();	
		table=new JTable(tablemodel);
		//fuck
		
		selectionModel=table.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel.addListSelectionListener(this);
		TableColumn typeColumn = table.getColumnModel().getColumn(1);
		JComboBox comboBox = new JComboBox(); 
		comboBox.addItem("库存管理人员");  
		comboBox.addItem("销售员");  
		comboBox.addItem("销售经理");  
		comboBox.addItem("财务人员");  
		comboBox.addItem("总经理");
		comboBox.addItem("管理员");
		typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		TableColumn rankColumn = table.getColumnModel().getColumn(4);
		JComboBox rcomboBox = new JComboBox(); 
		rcomboBox.addItem("普通权限");  
		rcomboBox.addItem("最高权限");  
		rankColumn.setCellEditor(new DefaultCellEditor(rcomboBox));
		
		table.getModel().addTableModelListener(this);
		setLayout(null);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(20, 10, 780, 380);
		add(scrollpane);
		
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
		nameField.setSize(120, 20);
		addPanel.add(nameField);
		nameField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		nameField.setColumns(10);
		nameField.addFocusListener(new NameAdapter());
		
		typeComboBox = new JComboBox();
		typeComboBox.setLocation(120, 0);
		typeComboBox.setSize(120, 20);
		addPanel.add(typeComboBox);
		typeComboBox.addItem("库存管理人员");  
		typeComboBox.addItem("销售员");  
		typeComboBox.addItem("销售经理");  
		typeComboBox.addItem("财务人员");  
		typeComboBox.addItem("总经理");  
		typeComboBox.addItem("管理员");
		
		idField = new JTextField("ID");
		idField.setSize(120, 20);
		idField.setLocation(240, 0);
		addPanel.add(idField);
		idField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		idField.addFocusListener(new IDAdapter());
		idField.setColumns(10);
		
		passwordField = new JTextField("password");
		passwordField.setSize(120, 20);
		passwordField.setLocation(360, 0);
		addPanel.add(passwordField);
		passwordField.setHorizontalAlignment(SwingConstants.HORIZONTAL);
		passwordField.setColumns(10);
		
		JButton addButton = new JButton("ADD");
		addButton.setBounds(600, 0, 90, 20);
		addPanel.add(addButton);
		
		JButton delButton = new JButton("DEL");
		delButton.setBounds(690, 0, 90, 20);
		addPanel.add(delButton);
		
		rankComboBox = new JComboBox();
		rankComboBox.setBounds(480, 0, 120, 20);
		addPanel.add(rankComboBox);
		rankComboBox.addItem("普通权限");
		rankComboBox.addItem("最高权限");
		delButton.addActionListener(new DelListener());
		addButton.addActionListener(new AddListener());
		passwordField.addFocusListener(new PasswordAdapter());
		
		ArrayList<UserVO> voList=ubs.showUser();
		if(voList!=null){
			for(UserVO vo:voList){
				Vector v=new Vector();
				v.add(vo.getName());
				v.add(vo.getType());
				v.add(vo.getID());
				v.add(vo.getPassword());
				v.add(vo.getRank());
				tablemodel.addRow(v);
			}
		}
		setVisible(true);
		repaint();
		ubs.addUser(new UserVO("admin","管理员","马昕","123456","普通权限"));
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();  
        int column = e.getColumn();  
        TableModel model = (TableModel)e.getSource();  
        String columnName = model.getColumnName(column);  
        Object data = model.getValueAt(row, column); 
		ubs.modifyUser(new UserVO((String)table.getValueAt(row,2),(String)table.getValueAt(row,1),
				(String)table.getValueAt(row,0),(String)table.getValueAt(row,3),(String)table.getValueAt(row,4)));
		
		panel.setVisible(true);
		panel.repaint();
	}
	
	class DelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text="删除成功";
			
			boolean flag=false;
			if(selectedRows == null){
				System.out.println("sad");
				return;
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
					ubs.deleteUser((String)tablemodel.getValueAt(selectedRows[i], 2));
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
			String text;
			
			ResultMessage result=ubs.addUser(new UserVO(idField.getText(),(String)typeComboBox.getSelectedItem(),
					nameField.getText(),passwordField.getText(),(String)rankComboBox.getSelectedItem()));
			if(result.equals(ResultMessage.Success)){
				text="添加成功";
				Vector v=new Vector();
				v.add(nameField.getText());
				v.add((String)typeComboBox.getSelectedItem());
				v.add(idField.getText());
				v.add(passwordField.getText());
				v.add((String)rankComboBox.getSelectedItem());
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
	
	public void updateTable(){
		tablemodel=new UserModel();
		ArrayList<UserVO> voList=ubs.showUser();
		if(voList!=null){
			for(UserVO vo:voList){
				Vector v=new Vector();
				v.add(vo.getName());
				v.add(vo.getType());
				v.add(vo.getID());
				v.add(vo.getPassword());
				v.add(vo.getRank());
				tablemodel.addRow(v);
			}
		}
		panel.setVisible(true);
		panel.repaint();
	}
	
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
	
	public class IDAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (idField.getText().equals("ID")) {
	            idField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (idField.getText().equals("")) {
	            idField.setText("ID");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}
		
	public class PasswordAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (passwordField.getText().equals("password")) {
	           passwordField.setText("");
	        }
			panel.setVisible(true);
			panel.repaint();
        }

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if (passwordField.getText().equals("")) {
	            passwordField.setText("password");
	        }
			panel.setVisible(true);
			panel.repaint();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		selectedRows=table.getSelectedRows();
	}
}
