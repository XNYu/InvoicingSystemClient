package presentation.promotionGUI;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import presentation.promotionGUI.*;
import VO.PromotionVO;
import VO.UserVO;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.promotionbl.Promotion;
import businesslogic.promotionbl.PromotionController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.promotionBLService.PromotionBLService;
import VO.PromotionVO.types;

import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class discountTablePanel extends JPanel implements TableModelListener{
	JTable table;
	discountModel tableModel;
	JComboBox<String> rankComboBox;
	PromotionBLService pbs;
	discountTablePanel panel=this;
	private JTextField discountField;
	final DateChooser mp1;
	final DateChooser mp2;
	UserVO user;
	
	public discountTablePanel(UserVO user){
		setSize(850, 600);
		tableModel=new discountModel();
		table=new JTable(tableModel);
		this.user=user;
		pbs=new PromotionController(user);
		
		TableColumn rankColumn = table.getColumnModel().getColumn(2);
		JComboBox<String> rcomboBox = new JComboBox<String>(); 
		rcomboBox.addItem("1");  
		rcomboBox.addItem("2");
		rcomboBox.addItem("3");  
		rcomboBox.addItem("4");  
		rcomboBox.addItem("5");  
		rankColumn.setCellEditor(new DefaultCellEditor(rcomboBox));
		
		table.getModel().addTableModelListener(this);
		setLayout(null);
		
		setVisible(true);
		table.setFillsViewportHeight(true);  
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JDialog infoDialog=new JDialog();
		infoDialog.setSize(100,80);
		infoDialog.setLocationRelativeTo(this);
		
		JButton modifyButton = new JButton("修改");
		modifyButton.setBounds(730, 22, 100, 30);
		modifyButton.addActionListener(new ModifyListener());
		add(modifyButton);
		
		JButton delButton = new JButton("终止");
		delButton.setBounds(730, 66, 100, 30);
		add(delButton);
		
		JPanel addPanel = new JPanel();
		addPanel.setBounds(10, 453, 729, 40);
		add(addPanel);
		addPanel.setLayout(null);
		
		rankComboBox = new JComboBox<String>();
		rankComboBox.setSize(65, 30);
		rankComboBox.setLocation(212, 0);
		addPanel.add(rankComboBox);
		rankComboBox.addItem("1");  
		rankComboBox.addItem("2");
		rankComboBox.addItem("3");  
		rankComboBox.addItem("4");  
		rankComboBox.addItem("5");  
		
		JLabel label = new JLabel("折扣值");
		label.setBounds(0, 2, 72, 27);
		addPanel.add(label);
		
		JLabel label_1 = new JLabel("用户等级");
		label_1.setBounds(148, 2, 72, 27);
		addPanel.add(label_1);
		
		JButton addButton = new JButton("添加策略");
		addButton.setBounds(609,0,120,30);
		addButton.addActionListener(new AddListener());
		addPanel.add(addButton);
		
		JLabel label_2 = new JLabel("起始日期");
		label_2.setBounds(279, 2, 72, 27);
		addPanel.add(label_2);
		
		JLabel label_3 = new JLabel("终止日期");
		label_3.setBounds(444, 2, 72, 27);
		addPanel.add(label_3);
		
		discountField = new JTextField("");
		discountField.setHorizontalAlignment(SwingConstants.CENTER);
		discountField.setColumns(10);
		discountField.setBounds(49, 0, 98, 30);
		discountField.addKeyListener(new KeyAdapter());
		addPanel.add(discountField);
		
		mp1 = new DateChooser("yyyy-MM-dd");  
		mp1.setBounds(341, 0, 100, 30);
		addPanel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});

		mp2 = new DateChooser("yyyy-MM-dd");  
		mp2.setBounds(506, 0, 100, 30);
		addPanel.add(mp2);
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 22, 700, 400);
		add(scrollPane);
		
		delButton.addActionListener(new DelListener());
		
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList){
				if(vo.getType()==types.d){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(Double.toString(vo.getDiscount()));
				v.add(Integer.toString(vo.getRank()));
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));
				tableModel.addRow(v);
			}
		}}
		
	}
	
	public void updateTable(){
		while(tableModel.getRowCount()>0){
			 tableModel.removeRow(tableModel.getRowCount()-1);
		}
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList)
				if(vo.getType()==types.d){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(Double.toString(vo.getDiscount()));
				v.add(Integer.toString(vo.getRank()));
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));
				tableModel.addRow(v);
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
        
	}
	
	class ModifyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text;
			int row =table.getSelectedRow();
			if(row==-1||table.getRowCount()==0)
				return;
			if(!judgeDouble((String)table.getValueAt(row,3))&&!judgeDouble((String)table.getValueAt(row,4))){
				utility.setInfo("请输入正确的日期", panel);
				return;}
			if(!judgeDouble((String)table.getValueAt(row,1))){
				utility.setInfo("请输入正确的折扣", panel);
				return;}
			Date d1=utility.stringToDate((String)table.getValueAt(row,3));
			Date d2=utility.stringToDate((String)table.getValueAt(row,4));
			double discount=Double.parseDouble((String)table.getValueAt(row,1));
			int rank=Integer.parseInt((String)table.getValueAt(row,2));
			String ID=(String)table.getValueAt(row,0);
			
			if(discount<=0.0||discount>=1.0){
				utility.setInfo("请输入正确的折扣",panel);
				return;
			}
			
			
			ResultMessage result=pbs.modifyPromotion(new PromotionVO(ID, rank, 0, types.d, null, discount, 0, null,0, d1,d2));
			if(result.equals(ResultMessage.Success)){
				text="修改成功";
			}else{
				text="修改失败";
				updateTable();
			}
			updateTable();
			
			utility.setInfo(text, panel);
			
			
		}
		
	}
	class DelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String text;
			int row =table.getSelectedRow();
			if(row==-1||table.getRowCount()==0)
				return;
			ResultMessage result=pbs.cancelPromotion(pbs.searchPromotion((String)table.getValueAt(row, 0)));
			if(result.equals(ResultMessage.Success)){
				text="删除成功";
				tableModel.removeRow(row);
				table.repaint();
			}else{
				text="删除失败";
			}
			//跳出提示窗口
			utility.setInfo(text, panel);
			panel.setVisible(true);
			panel.repaint();
		}
		
	}
	class AddListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text;
			Date d1=mp1.select.getTime();
			Date d2=mp2.select.getTime();
			if(discountField.getText().isEmpty()){
				utility.setInfo("请输入折扣值",panel);
				return;
			}
			if(!judgeDouble(discountField.getText())){
				utility.setInfo("请输入正确的折扣",panel);
				return;
			}
			double discount=Double.parseDouble(discountField.getText());
			if(discount<=0.0||discount>=1.0){
				utility.setInfo("请输入正确的折扣",panel);
				return;}
			int rank=Integer.parseInt((String) rankComboBox.getSelectedItem());
			String ID=pbs.getID();
			//得到策略内容信息
			ResultMessage result=pbs.addPromotion(new PromotionVO(ID, rank, 0, types.d, null, discount, 0, null,0,d1,d2));
			if(result.equals(ResultMessage.Success)){
				text="添加成功";
				Vector v=new Vector();
				v.add(ID);
				v.add(discountField.getText());
				v.add(rankComboBox.getSelectedItem());
				v.add(utility.dateToString(d1));
				v.add(utility.dateToString(d2));
				//v.add(endDField.getText());
				tableModel.addRow(v);
				table.repaint();
				panel.setVisible(true);
				panel.repaint();
			}else{
				text="添加失败";
				
			}
			
			utility.setInfo(text, panel);
			
			panel.setVisible(true);
			panel.repaint();
		}
		
	}
	
	boolean judgeDouble(String s){
		 try {
             double num = Double.parseDouble(s);
         } catch (Exception ex) {
             return false;
         }
		 return true;
	}
	



}