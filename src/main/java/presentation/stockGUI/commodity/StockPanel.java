package presentation.stockGUI.commodity;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import PO.StockPO;
import VO.UserVO;
import businesslogic.stockbl.StockController;
import businesslogic.utilitybl.DateHelper;
import businesslogic.utilitybl.ShowMessageFrame;
import businesslogicservice.stockBLService.StockBLService;

public class StockPanel extends JPanel implements Serializable{
	private static final long serialVersionUID = 1L;
	StockBLService sbs = new StockController();
	JScrollPane stoScrollPane;
	JTable stockTable;
	StockTableModel stockTableModel;
	JTextField startDateText,endDateText;
	JTextField jt1,jt2,jt3,jt4,amountjt1,amountjt2,
	            stockInjt,stockOutjt,impjt,expjt,
	            stockInjt2,stockOutjt2,impjt2,expjt2;
	JButton searchButton;
	String startDate="",endDate="";
//	DateChooser startChooser,endChooser;
	public StockPanel(UserVO uvo){
		startDateText = new JTextField("起始日期");
		startDateText.setEditable(true);
		startDateText.getDocument().addDocumentListener(new StartDateTextListener());
		startDateText.addFocusListener(new startAdapter());
		startDateText.setSize(110,22);
		startDateText.setLocation(480,120);
		this.add(startDateText);
		endDateText = new JTextField("终止日期");
		endDateText.setEditable(true);
		endDateText.getDocument().addDocumentListener(new EndDateTextListener());
		endDateText.addFocusListener(new endAdapter());
		endDateText.setSize(110,22);
		endDateText.setLocation(590,120);
		this.add(endDateText);
		searchButton = new JButton("SEARCH");
		searchButton.setSize(118,22);
		searchButton.setLocation(700, 120);
		searchButton.addActionListener(new searchListener());
		this.add(searchButton);
		stockInjt = new JTextField("?");
		stockInjt.setOpaque(false);
		stockInjt.setEditable(false);
		stockInjt.setFont(new Font("华文细黑", Font.PLAIN, 15));
		stockInjt.setBorder(null);
		stockInjt.setSize(100,20);
		stockInjt.setLocation(130,50);
		this.add(stockInjt);
		stockOutjt = new JTextField("?");
		stockOutjt.setOpaque(false);
		stockOutjt.setEditable(false);
		stockOutjt.setFont(new Font("华文细黑", Font.PLAIN, 15));
		stockOutjt.setBorder(null);
		stockOutjt.setSize(100,20);
		stockOutjt.setLocation(130,80);
		this.add(stockOutjt);
		expjt = new JTextField("?");
		expjt.setOpaque(false);
		expjt.setEditable(false);
		expjt.setFont(new Font("华文细黑", Font.PLAIN, 15));
		expjt.setBorder(null);
		expjt.setSize(100,20);
		expjt.setLocation(520,50);
		this.add(expjt);
		impjt = new JTextField("?");
		impjt.setOpaque(false);
		impjt.setEditable(false);
		impjt.setFont(new Font("华文细黑", Font.PLAIN, 15));
		impjt.setBorder(null);
		impjt.setSize(100,20);
		impjt.setLocation(520,80);
		this.add(impjt);
		stockInjt2 = new JTextField("?");
		stockInjt2.setOpaque(false);
		stockInjt2.setEditable(false);
		stockInjt2.setFont(new Font("华文细黑", Font.PLAIN, 15));
		stockInjt2.setBorder(null);
		stockInjt2.setSize(100,20);
		stockInjt2.setLocation(210,50);
		this.add(stockInjt2);
		stockOutjt2 = new JTextField("?");
		stockOutjt2.setOpaque(false);
		stockOutjt2.setEditable(false);
		stockOutjt2.setFont(new Font("华文细黑", Font.PLAIN, 15));
		stockOutjt2.setBorder(null);
		stockOutjt2.setSize(100,20);
		stockOutjt2.setLocation(210,80);
		this.add(stockOutjt2);
		expjt2 = new JTextField("?");
		expjt2.setOpaque(false);
		expjt2.setEditable(false);
		expjt2.setFont(new Font("华文细黑", Font.PLAIN, 15));
		expjt2.setBorder(null);
		expjt2.setSize(100,20);
		expjt2.setLocation(600,50);
		this.add(expjt2);
		impjt2 = new JTextField("?");
		impjt2.setOpaque(false);
		impjt2.setEditable(false);
		impjt2.setFont(new Font("华文细黑", Font.PLAIN, 15));
		impjt2.setBorder(null);
		impjt2.setSize(100,20);
		impjt2.setLocation(600,80);
		this.add(impjt2);
		
		amountjt1 = new JTextField(" 数量      金额");
		amountjt1.setOpaque(false);
		amountjt1.setEditable(false);
		amountjt1.setFont(new Font("幼圆", Font.PLAIN, 15));
		amountjt1.setBorder(null);
		amountjt1.setSize(300,20);
		amountjt1.setLocation(120,30);
		this.add(amountjt1);
		
		
		amountjt2 = new JTextField(" 数量      金额");
		amountjt2.setOpaque(false);
		amountjt2.setEditable(false);
		amountjt2.setFont(new Font("幼圆", Font.PLAIN, 15));
		amountjt2.setBorder(null);
		amountjt2.setSize(300,20);
		amountjt2.setLocation(510,30);
		this.add(amountjt2);
		
		jt1 = new JTextField("出库");
		jt1.setOpaque(false);
		jt1.setEditable(false);
		jt1.setFont(new Font("华文细黑", Font.PLAIN, 15));
		jt1.setBorder(null);
		jt1.setSize(100,20);
		jt1.setLocation(60,50);
		this.add(jt1);
		jt2 = new JTextField("入库");
		jt2.setOpaque(false);
		jt2.setEditable(false);
		jt2.setFont(new Font("华文细黑", Font.PLAIN, 15));
		jt2.setBorder(null);
		jt2.setSize(100,20);
		jt2.setLocation(60,80);
		this.add(jt2);
		jt3 = new JTextField("销售");
		jt3.setOpaque(false);
		jt3.setEditable(false);
		jt3.setFont(new Font("华文细黑", Font.PLAIN, 15));
		jt3.setBorder(null);
		jt3.setSize(100,20);
		jt3.setLocation(450,50);
		this.add(jt3);
		jt4 = new JTextField("进货");
		jt4.setOpaque(false);
		jt4.setEditable(false);
		jt4.setFont(new Font("华文细黑", Font.PLAIN, 15));
		jt4.setBorder(null);
		jt4.setSize(100,20);
		jt4.setLocation(450,80);
		this.add(jt4);
		//======================initial table
		stockTableModel = new StockTableModel();
		stockTable = new JTable(stockTableModel);
//		stockTable.getColumnModel().getColumn(0).setPreferredWidth(120);
//		stockTable.getModel().addTableModelListener(this);
		DateHelper dh = new DateHelper();
		ArrayList<StockPO> poList = sbs.showStock("0" , dh.getDate());
//		System.out.println(poList.size());
		if(poList!=null){
			for(StockPO po:poList){
				Vector v=new Vector();
				v.add(po.getOperateType());
				v.add(po.getId());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getBatch());
				v.add(po.getBatchID());
				v.add(po.getLeaveDate());
				stockTableModel.addRow(v);
			}
		}
			stoScrollPane = new JScrollPane(stockTable);
			stockTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//			selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
//		 	selectionModel.addListSelectionListener(this);
			
		
		stoScrollPane.setSize(780,400);
		stoScrollPane.setLocation(40,150);
		stoScrollPane.setVisible(true);
		
		this.add(stoScrollPane);
		this.setLayout(null);
		this.setSize(850, 600);
		this.setLocation(0, 0);
	}

	public class searchListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if((!isNumber(startDate))||(!isNumber(endDate))||
					!(isDateString(startDate))||
					!(isDateString(endDate))||
					(startDate.equals(""))||
					endDate.equals("")
					){
				new ShowMessageFrame("请输入正确的日期");
				return ;
				}
			for(int i = stockTableModel.getRowCount(); i>0 ; i--){
//				System.out.println(i+" "+comTableModel.getRowCount());
				stockTableModel.removeRow(0);
				}
			ArrayList<StockPO> voList = sbs.showStock(startDate, endDate);
			if(voList!=null){
				for(StockPO po:voList){
					Vector v=new Vector();
					v.add(po.getOperateType());
					v.add(po.getId());
					v.add(po.getName());
					v.add(po.getType());
					v.add(po.getAmount());
					v.add(po.getBatch());
					v.add(po.getBatchID());
					v.add(po.getLeaveDate());
					stockTableModel.addRow(v);
				}
			}
			stockTable.revalidate();
			ArrayList<String> calList = sbs.calculate(voList);
			stockInjt.setText(calList.get(0));
			stockOutjt.setText(calList.get(2));
			expjt.setText(calList.get(4));
			impjt.setText(calList.get(6));
			stockInjt2.setText(calList.get(1));
			stockOutjt2.setText(calList.get(3));
			expjt2.setText(calList.get(5));
			impjt2.setText(calList.get(7));
			repaint();
			
			
		}
		
	}
	public class endAdapter implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (endDateText.getText().equals("终止日期")) {
				endDateText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (endDateText.getText().equals("")) {
        		endDateText.setText("终止日期");
            }
        }
		
	}
	public class startAdapter implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (startDateText.getText().equals("起始日期")) {
				startDateText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (startDateText.getText().equals("")) {
        		startDateText.setText("起始日期");
            }
        }
		
	}
	public class StartDateTextListener implements DocumentListener{
		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			startDate = startDateText.getText().trim();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			startDate = startDateText.getText().trim();
		}
	}
	public class EndDateTextListener implements DocumentListener{
		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			endDate = endDateText.getText().trim();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			endDate = endDateText.getText().trim();
		}
	}
	public static boolean isInteger(String value) {
		  try {
		   Integer.parseInt(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }

		 /**
		  * 判断字符串是否是浮点数
		  */
   public static boolean isDouble(String value) {
		  try {
		   Double.parseDouble(value);
		   if (value.contains("."))
		    return true;
		   return false;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
   public static boolean isDateString(String date){
	   int n = Integer.parseInt(date);
	   if((n>10000)&&(n<99999999))
		   return true;
	   else
		   return false;
	   
   }
   public static boolean isNumber(String value) {
  	  return isInteger(value) || isDouble(value);
  	 }

}
