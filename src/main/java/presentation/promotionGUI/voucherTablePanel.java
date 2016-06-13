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
import presentation.userGUI.UserModel;
import VO.PromotionVO;
import VO.UserVO;
import businesslogic.promotionbl.Promotion;
import businesslogic.promotionbl.PromotionController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.promotionBLService.PromotionBLService;
import VO.PromotionVO.types;

import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class voucherTablePanel extends JPanel implements TableModelListener{
	JTable table;
	voucherModel tableModel;
	voucherTablePanel panel = this;
	JComboBox<String> rankComboBox;
	PromotionBLService pbs;
	private JTextField priceField;
	private JTextField totalPriceField;
	final DateChooser mp1;
	final DateChooser mp2;
	
	public voucherTablePanel(UserVO user){
		setSize(850, 600);
		tableModel=new voucherModel();
		table=new JTable(tableModel);
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
		modifyButton.setBounds(730, 24, 100, 30);
		modifyButton.addActionListener(new ModifyListener());
		add(modifyButton);
		
		JButton delButton = new JButton("终止");
		delButton.setBounds(730, 68, 100, 30);
		add(delButton);
		
		JPanel addPanel = new JPanel();
		addPanel.setBounds(10, 455, 820, 69);
		add(addPanel);
		addPanel.setLayout(null);
		
		rankComboBox = new JComboBox<String>();
		rankComboBox.setSize(65, 30);
		rankComboBox.setLocation(401, 39);
		addPanel.add(rankComboBox);
		rankComboBox.addItem("1");  
		rankComboBox.addItem("2");
		rankComboBox.addItem("3");  
		rankComboBox.addItem("4");  
		rankComboBox.addItem("5");  
		

		JLabel label = new JLabel("代金券");
		label.setBounds(0, 5, 72, 27);
		addPanel.add(label);
		
		JLabel label_1 = new JLabel("用户等级");
		label_1.setBounds(337, 41, 72, 27);
		addPanel.add(label_1);
		
		JLabel label_2 = new JLabel("起始日期");
		label_2.setBounds(0, 41, 72, 27);
		addPanel.add(label_2);
		
		JLabel label_3 = new JLabel("终止日期");
		label_3.setBounds(165, 41, 72, 27);
		addPanel.add(label_3);
		
		JButton addButton = new JButton("添加策略");
		addButton.setBounds(722,41,98,27);
		addButton.addActionListener(new AddListener());
		addPanel.add(addButton);
		
		JLabel label_5 = new JLabel("总价");
		label_5.setBounds(184, 2, 72, 27);
		addPanel.add(label_5);
		
		priceField = new JTextField("");
		priceField.setHorizontalAlignment(SwingConstants.CENTER);
		priceField.setColumns(10);
		priceField.setBounds(63, 3, 98, 30);
		addPanel.add(priceField);
		
		totalPriceField = new JTextField("");
		totalPriceField.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceField.setColumns(10);
		totalPriceField.setBounds(225, 2, 98, 30);
		addPanel.add(totalPriceField);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 24, 700, 400);
		add(scrollPane);
		
		mp1 = new DateChooser("yyyy-MM-dd");  
		mp1.setBounds(63, 39, 98, 30);
		addPanel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});

		mp2 = new DateChooser("yyyy-MM-dd");  
		mp2.setBounds(227, 39, 96, 30);
		addPanel.add(mp2);
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
		
		delButton.addActionListener(new DelListener());
		
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList){
				if(vo.getType()==types.v){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(Double.toString(vo.getVoucher()));
				v.add(Integer.toString(vo.getRank()));
				v.add(Integer.toString(vo.getTotalPrice()));
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));
				tableModel.addRow(v);
			}
		}}
		
		setVisible(true);
		repaint();
		
	}
	
	public void updateTable(){
		tableModel=new voucherModel();
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList)
				if(vo.getType()==types.v){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getVoucher());
				v.add(vo.getRank());
				v.add(vo.getTotalPrice());
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));
				tableModel.addRow(v);
			}
		}
		panel.setVisible(true);
		panel.revalidate();
		panel.repaint();

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
			if(!judgeNum((String)table.getValueAt(row,3))){
				utility.setInfo("请输入正确的总价",panel);
				return;
			}
			if(!judgeDouble((String)table.getValueAt(row,1))){
				utility.setInfo("请输入正确的金额",panel);
				return;
			}if(!judgeDouble((String)table.getValueAt(row,4))&&!judgeDouble((String)table.getValueAt(row,5))){
				utility.setInfo("请输入正确的日期", panel);
				return;}
			Date d1=utility.stringToDate((String)table.getValueAt(row,4));
			Date d2=utility.stringToDate((String)table.getValueAt(row,5));
			double voucher=Double.parseDouble((String)table.getValueAt(row,1));
			int rank=Integer.parseInt((String)table.getValueAt(row,2));
			int totalP=Integer.parseInt((String)table.getValueAt(row,3));
			String ID=(String)table.getValueAt(row,0);
			
			
			ResultMessage result=pbs.modifyPromotion(new PromotionVO(ID, rank, totalP, types.v, null, 0, voucher, null, 0,d1,d2));
			if(result.equals(ResultMessage.Success)){
				text="修改成功";
			}else{
				text="修改失败";
				updateTable();
				
			}
			
			utility.setInfo(text, panel);
			
			panel.setVisible(true);
			panel.repaint();
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
			if(priceField.getText().isEmpty()){
				utility.setInfo("请输入优惠券金额",panel);
				return;
			}
			if(totalPriceField.getText().isEmpty()){
				utility.setInfo("请输入总价",panel);
				return;
			}
			if(!judgeNum(totalPriceField.getText())){
				utility.setInfo("请输入正确的总价",panel);
				return;
			}
			if(!judgeDouble(priceField.getText())){
				utility.setInfo("请输入正确的金额",panel);
				return;
			}
			
			double voucher=Double.parseDouble(priceField.getText());
			int rank=Integer.parseInt((String) rankComboBox.getSelectedItem());
			int totalP=Integer.parseInt(totalPriceField.getText());
			String ID=pbs.getID();
			//得到策略内容信息
			PromotionVO vo=new PromotionVO(ID, rank, totalP,types.v,null, 0, voucher,null,0, d1,d2);
			ResultMessage result=pbs.addPromotion(vo);
			if(result.equals(ResultMessage.Success)){
				text="添加成功";
				Vector v=new Vector();
				v.add(ID);
				v.add(priceField.getText());
				v.add(rankComboBox.getSelectedItem());
				v.add(totalPriceField.getText());
				v.add(utility.dateToString(d1));
				v.add(utility.dateToString(d2));
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
	
	boolean judgeNum(String s){
		 try {
            int num = Integer.parseInt(s);
        } catch (Exception ex) {
            return false;
        }
		 return true;
	}


}
