package presentation.promotionGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


import presentation.userGUI.UserModel;
import PO.CommodityPO;
import VO.PromotionVO;
import VO.UserVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityController;
import businesslogic.promotionbl.Promotion;
import businesslogic.promotionbl.PromotionController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.promotionBLService.PromotionBLService;
import VO.PromotionVO.types;

import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class packTablePanel extends JPanel implements TableModelListener{
	JTable table,table2;
	packModel tableModel;
	PackTableModel tableModel2;
	JComboBox<String> rankComboBox;
	PromotionBLService pbs;
	CommodityBLService cbs=new CommodityController();
	packTablePanel panel=this;
	Promotion pro;
	ArrayList<CommodityPO> list;
	final DateChooser mp1;
	final DateChooser mp2;
	boolean commodityShowed=false;
	private JTextField textField;
	
	
	public packTablePanel(UserVO user){
		setSize(850, 600);
		tableModel=new packModel();
		table=new JTable(tableModel);
		pbs=new PromotionController(user);
		
		tableModel2=new PackTableModel();
		table2=new JTable(tableModel2);

		table.getModel().addTableModelListener(this);
		table.setFillsViewportHeight(true);  
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		table2.getModel().addTableModelListener(this);
		table2.setFillsViewportHeight(true);  
		table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		setLayout(null);
		setVisible(true);
		
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
		addPanel.setBounds(10, 453, 820, 40);
		add(addPanel);
		addPanel.setLayout(null);
		
		JButton addButton = new JButton("添加策略");
		addButton.setBounds(722,0,98,30);
		addButton.addActionListener(new AddListener());
		addPanel.add(addButton);
		
		JLabel label_2 = new JLabel("起始日期");
		label_2.setBounds(207, 2, 72, 27);
		addPanel.add(label_2);
		
		JLabel label_3 = new JLabel("终止日期");
		label_3.setBounds(390, 2, 72, 27);
		addPanel.add(label_3);
		
		JButton listButton = new JButton("选择商品");
		listButton.addActionListener(new ListListener());
		listButton.setBounds(569, 0, 127, 30);
		addPanel.add(listButton);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 22, 700, 190);
		add(scrollPane);
		
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(10, 252, 700, 160);
		add(scrollPane2);
		
		JButton showListButton = new JButton("显示商品");
		showListButton.setBounds(732, 252, 98, 30);
		showListButton.addActionListener(new showGiftListener());
		add(showListButton);
		
		delButton.addActionListener(new DelListener());
		
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList){
				if(vo.getType()==types.p){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(Double.toString(vo.getPackDiscount()));
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));
				tableModel.addRow(v);
			}
		}}
		mp1 = new DateChooser("yyyy-MM-dd");  
		mp1.setBounds(282, 0, 100, 30);
		addPanel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});

		mp2 = new DateChooser("yyyy-MM-dd");  
		mp2.setBounds(457, 0, 100, 30);
		addPanel.add(mp2);
		
		JLabel label = new JLabel("特价包折扣");
		label.setBounds(0, 2, 82, 27);
		addPanel.add(label);
		
		textField = new JTextField();
		textField.setBounds(92, 0, 86, 30);
		addPanel.add(textField);
		textField.setColumns(10);
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});
		
		setVisible(true);
		repaint();
		
	}

	 
	 
	public void updateTable(){
		tableModel=new packModel();
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList)
				if(vo.getType()==types.p){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(Double.toString(vo.getPackDiscount()));
				v.add(utility.dateToString(vo.getStartTime()));
				v.add(utility.dateToString(vo.getEndTime()));

				tableModel.addRow(v);
			}
		}
		panel.setVisible(true);
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
	
	class ListListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			while(tableModel2.getRowCount()>0){
				 tableModel2.removeRow(tableModel2.getRowCount()-1);
			}//清空表格
			table2.revalidate();//刷新表格 
			list=cbs.showCommodity();
			if(list!=null){
				for(CommodityPO cvo:list){
					Vector v=new Vector();
					v.add(cvo.getID());
					v.add(cvo.getName());
					v.add(cvo.getType());
					v.add("1");
					tableModel2.addRow(v);
				}
			}
			commodityShowed=true;
			table2.revalidate();
			panel.repaint();
		
		
		}
		
		
		
	}
	
	class showGiftListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			while(tableModel2.getRowCount()>0){
				 tableModel2.removeRow(tableModel2.getRowCount()-1);
			}//清空表格
			table2.revalidate();//刷新表格 
			
			int row =table.getSelectedRow();
			if(row==-1){
				utility.setInfo("请选择销售策略!",panel);
				return;
			}
			else if(table.getRowCount()==0)
				return;
			PromotionVO pvo=pbs.searchPromotion((String)table.getValueAt(row,0));
			ArrayList<CommodityPO> list2=pvo.getpackList();
			if(list2!=null){
				int num=1;
				String ID=null;
				for(CommodityPO cvo:list2){
					if(!cvo.getID().equals(ID)){
						Vector v=new Vector();
						v.add(cvo.getID());
						v.add(cvo.getName());
						v.add(cvo.getType());
						v.add("1");
						tableModel2.addRow(v);
						ID=cvo.getID();
						num=1;
					}
					else{
						num++;
						table2.setValueAt(Integer.toString(num), table2.getRowCount()-1, 3);
					}
				}
			}
			
			commodityShowed=false;
			table2.revalidate();
			panel.repaint();
			}
		
	}
	
	class ModifyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text;
			int row =table.getSelectedRow();
			if(row==-1||table.getRowCount()==0)
				return;
			if(!judgeDouble((String)table.getValueAt(row,3))&&!judgeDouble((String)table.getValueAt(row,2))){
				utility.setInfo("请输入正确的日期", panel);
				return;}
			if(!judgeDouble((String)table.getValueAt(row,1))){
				utility.setInfo("请输入正确的折扣", panel);
				return;}
			Date d1=utility.stringToDate((String)table.getValueAt(row,2));
			Date d2=utility.stringToDate((String)table.getValueAt(row,3));
			String ID=(String)table.getValueAt(row,0);
			double packDiscount=Double.parseDouble((String)table.getValueAt(row, 1));
			if(packDiscount<=0.0||packDiscount>=1.0){
				utility.setInfo("请输入正确的折扣",panel);
				return;}
			
			
			ArrayList<CommodityPO> tempList = new ArrayList<CommodityPO>();
			if(commodityShowed=true){
				int[] rows=table2.getSelectedRows();
				for (int i=0;i< rows.length;i++){
					ArrayList<CommodityPO> tList=cbs.findCommodity((String)table2.getValueAt(rows[i],0));
					int num=Integer.parseInt((String)table2.getValueAt(rows[i], 3));
					for(int j=0;j<num;j++){
						CommodityPO po =tList.get(0);
						tempList.add(po);
					}
				}
			}
			else if(pbs.searchPromotion(ID).getpackList()!=null){
				tempList=pbs.searchPromotion(ID).getpackList();
			}
			
			
			ResultMessage result=pbs.modifyPromotion(new PromotionVO(ID, 0, 0, types.p, null, 0, 0, tempList, packDiscount,d1,d2));
			if(result.equals(ResultMessage.Success)){
				text="修改成功";
			}else{
				text="修改失败";
				updateTable();
				
			}
			commodityShowed=false;
			updateTableTwo();
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
			commodityShowed=false;
			updateTableTwo();
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
			String ID=pbs.getID();
			if(textField.getText().isEmpty()){
				utility.setInfo("请输入特价包折扣",panel);
				return;
			}
			if(!judgeDouble(textField.getText())){
				utility.setInfo("请输入正确的折扣", panel);
				return;}
			if(commodityShowed==false){
				utility.setInfo("请选择商品",panel);
				return;
			}
			double packDiscount=Double.parseDouble(textField.getText());
			if(packDiscount<=0.0||packDiscount>=1.0){
				utility.setInfo("请输入正确的折扣",panel);
				return;}
			ArrayList<CommodityPO> tempList = new ArrayList<CommodityPO>();
			int[] rows=table2.getSelectedRows();
			for (int i=0;i< rows.length;i++){
				ArrayList<CommodityPO> tList=cbs.findCommodity((String)table2.getValueAt(rows[i],0));
				int num=Integer.parseInt((String)table2.getValueAt(rows[i], 3));
				for(int j=0;j<num;j++){
					CommodityPO po =tList.get(0);
					tempList.add(po);
				}
			}
			
			
			//得到策略内容信息
			ResultMessage result=pbs.addPromotion(new PromotionVO(ID, 0, 0, types.p, null , 0, 0, tempList,packDiscount, d1,d2));
			if(result.equals(ResultMessage.Success)){
				text="添加成功";
				Vector v=new Vector();
				v.add(ID);
				v.add(Double.toString(packDiscount));
				v.add(utility.dateToString(d1));
				v.add(utility.dateToString(d2));
				tableModel.addRow(v);
				table.repaint();
				panel.setVisible(true);
				panel.repaint();
			}else{
				text="添加失败";
				
			}
			
			utility.setInfo(text,panel);
			commodityShowed=false;
			updateTableTwo();
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
	
	
	public void updateTableTwo(){
		while(tableModel2.getRowCount()>0){
			 tableModel2.removeRow(tableModel2.getRowCount()-1);
		}
		table2.revalidate();
		panel.repaint();
	}
}
