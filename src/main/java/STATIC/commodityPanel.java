package STATIC;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import java.util.Comparator;  

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import presentation.promotionGUI.*;
import PO.CommodityPO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityController;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.promotionbl.Promotion;
import businesslogic.promotionbl.PromotionController;
import businesslogic.salesbl.SalesController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.promotionBLService.PromotionBLService;
import businesslogicservice.salesBLService.SalesBLService;
import VO.PromotionVO.types;

import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class commodityPanel extends JPanel implements TableModelListener{
	JTable table;
	comModel tableModel;
	ComTableModel tableModel2;
	PromotionBLService pbs;
	commodityPanel panel=this;
	Promotion pro;
	ArrayList<CommodityPO> list,tempList;
	ArrayList<SalesVO> salelist;
	CommodityBLService cbs=new CommodityController();
	SalesBLService sbs=new SalesController();
	final DateChooser mp1;
	final DateChooser mp2;
	boolean commodityShowed=false;
	UserVO user;
	JPanel imagePanel;
	ChartPanel chartPanel ;
	boolean tableSelected=false;
	
	public commodityPanel(UserVO user){
		list=cbs.showCommodity();
		salelist = sbs.showSales();
		
		setSize(850, 600);
		tableModel=new comModel();
		table=new JTable(tableModel);
		this.user=user;
		tableModel2=new ComTableModel();
		
		TableColumn rankColumn = table.getColumnModel().getColumn(1);
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
		
		JButton modifyButton = new JButton("\u9500\u552E\u8D8B\u52BF");
		modifyButton.setBounds(10, 315, 216, 30);
		modifyButton.addActionListener(new TrendListener());
		add(modifyButton);
		
		JButton delButton = new JButton("\u9500\u552E\u6BD4\u4F8B");
		delButton.setBounds(10, 355, 216, 30);
		add(delButton);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 21, 216, 190);
		add(scrollPane);
		
		delButton.addActionListener(new RatioListener());
		
		
		if(list!=null){
			for(CommodityPO vo:list){
				if(!vo.getName().equals("赠品")){
				Vector v=new Vector();
				v.add(vo.getName());
				v.add(vo.getType());
				tableModel.addRow(v);
				}
		}}
		
		JLabel label_2 = new JLabel("起始日期");
		label_2.setBounds(10, 221, 72, 27);
		add(label_2);
		
		mp1 = new DateChooser("yyyy-MM-dd");
		mp1.setBounds(125, 221, 100, 30);
		add(mp1);
		
		JLabel label_3 = new JLabel("终止日期");
		label_3.setBounds(10, 266, 72, 27);
		add(label_3);
		
				mp2 = new DateChooser("yyyy-MM-dd");
				mp2.setBounds(125, 261, 100, 30);
				add(mp2);
				
				JButton addButton = new JButton("\u5237\u65B0");
				addButton.setBounds(10, 405, 98, 30);
				add(addButton);
				
				imagePanel = new JPanel();
				imagePanel.setBounds(236, 21, 589, 546);
				add(imagePanel);
				addButton.addActionListener(new AddListener());
				mp2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						mp2.showPanel(mp2.showDate);
					}
				});
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});
		panel.setVisible(true);
		panel.repaint();
		
	}
	
	public void updateTable(){
		ArrayList<PromotionVO> voList=pbs.getAllPromotion();
		if(voList!=null){
			for(PromotionVO vo:voList)
				if(vo.getType()==types.g){
				Vector v=new Vector();
				v.add(vo.getID());
				v.add(vo.getDiscount());
				v.add(vo.getRank());
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
	public void updateTableTwo(){
		while(tableModel2.getRowCount()>0){
			 tableModel2.removeRow(tableModel2.getRowCount()-1);
		}
		panel.repaint();
	}
	
	class TrendListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String text;
			imagePanel.removeAll();
			int row =table.getSelectedRow();
		
			
//			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
			ArrayList<com> comList= new ArrayList<com>();
			if(row==-1||table.getRowCount()==0)
				tableSelected=false;
			if(row==-1||table.getRowCount()==0){
				comList= new ArrayList<com>();
			for(SalesVO sv:salelist){
				tempList=sv.getCommodityList();
				String time = sv.getID();
				int num=0;
				for(CommodityPO cp:tempList){
					com c = new com(cp.getType(),cp.getAmount(),time);
					boolean has =false;
					int index=0;
					for(com co:comList){
						if(co.getName().equals(c.getName())&&co.time.equals(c.time)){
							has = true;
							index=comList.indexOf(co);
						}
					}
					if(has){
						comList.get(index).addNum(c.num);
					}
					else
						comList.add(c);
//					dataset.addValue(cp.getAmount(), cp.getType(), time);
//					System.out.println(cp.getType()+":"+cp.getAmount());
//					if(cp.getName().equals(name))
//						num+=cp.getAmount();
				}
//				dataset.addValue(num, name, time);
			}
			}
			else{
				comList= new ArrayList<com>();
				String name = (String)table.getValueAt(row, 1);
				for(SalesVO sv:salelist){
					tempList=sv.getCommodityList();
					String time = sv.getID();
					for(CommodityPO cp:tempList){
						if(cp.getType().equals(name))
						{
						com c = new com(cp.getType(),cp.getAmount(),time);
						boolean has =false;
						int index=0;
						for(com co:comList){
							if(co.getName().equals(c.getName())&&co.time.equals(c.time)){
								has = true;
								index=comList.indexOf(co);
							}
						}
						if(has){
							comList.get(index).addNum(c.num);
						}
						else
							comList.add(c);
//						dataset.addValue(cp.getAmount(), cp.getType(), time);
//						System.out.println(cp.getType()+":"+cp.getAmount());
//						if(cp.getName().equals(name))
//							num+=cp.getAmount();
					}
				}
//					dataset.addValue(num, name, time);
			}
				}
			Comparator<com> comparator = new Comparator<com>(){
				public int compare(com s1, com s2){
					if(s1.year!=s2.year)
						return s1.year-s2.year;
					else{
						if(s1.month!=s2.month)
							return s1.month-s2.month;
						else{
							return s1.day-s2.day;
						}
					}
				}
			};
			Collections.sort(comList,comparator);
			
			for(com c:comList){
				if(dateTrue(c.time))
					dataset.addValue(c.num, c.getName(),c.time);
			}
			
			JFreeChart lineChart = ChartFactory.createLineChart(
			         " Sale Trend" ,
			         "Month","Number of Sales",
			         dataset,
			         PlotOrientation.VERTICAL,
			         true,true,false);
			         
			chartPanel = new ChartPanel( lineChart );
			chartPanel.setBounds(30, 30,550,500);
			imagePanel.setVisible(true);
			chartPanel.setVisible(true);
			chartPanel.repaint();
			imagePanel.add(chartPanel);
			imagePanel.repaint();
			imagePanel.paintComponents(imagePanel.getGraphics());
			panel.setVisible(true);
			panel.repaint();
			
		}
		
	}
	class RatioListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			imagePanel.removeAll();
						int row =table.getSelectedRow();
						if(row==-1||table.getRowCount()==0)
							return;
//						String name = (String)table.getValueAt(row, 0);
						DefaultPieDataset dataset = new DefaultPieDataset();
						ArrayList<com> comList=new ArrayList<com>(); 
						for(SalesVO sv:salelist){
							tempList=sv.getCommodityList();
							String time = sv.getID();
							for(CommodityPO cp:tempList){
								com c = new com(cp.getType(),cp.getAmount(),time);
								boolean has =false;
								int index=0;
								for(com co:comList){
									if(co.getName().equals(c.getName())){
										has = true;
										index=comList.indexOf(co);
									}
								}
								if(has){
									comList.get(index).addNum(c.num);
								}
								else
									comList.add(c);
								
//								System.out.println(cp.getType()+":"+cp.getAmount());
//								if(cp.getName().equals(name))
//									num+=cp.getAmount();
							}
							
//							dataset.addValue(num, name, time);
						}
						for(com c:comList){
							if(dateTrue(c.time))
								dataset.setValue(c.getName(), c.num);
						}
						JFreeChart lineChart = ChartFactory.createPieChart(
						         "Sale Ratio" ,
						         dataset,
						         true,true,false);
						         
						chartPanel = new ChartPanel( lineChart );
						chartPanel.setBounds(30, 30,550,500);
						imagePanel.setVisible(true);
						chartPanel.setVisible(true);
						chartPanel.repaint();
						imagePanel.add(chartPanel);
						imagePanel.repaint();
						imagePanel.paintComponents(imagePanel.getGraphics());
						panel.setVisible(true);
						panel.repaint();
		}
		
	}
	class AddListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			table.clearSelection();
			tableSelected=false;
			
//			int price=Integer.parseInt(totalPriceField.getText());
//			int rank=Integer.parseInt((String)rankComboBox.getSelectedItem());
//			String ID=pbs.getID();
//			
//			
//			ArrayList<CommodityPO> tempList = new ArrayList<CommodityPO>();
//			int[] rows=table2.getSelectedRows();
//			for (int i=0;i< rows.length;i++){
//				ArrayList<CommodityPO> tList=cbs.findCommodity((String)table2.getValueAt(rows[i],0));
//				if(!judgeNum((String)table2.getValueAt(rows[i], 3))){
//					utility.setInfo("请输入正确的数量",panel);
//					return;
//				}
//				int num=Integer.parseInt((String)table2.getValueAt(rows[i], 3));
//				for(int j=0;j<num;j++){
//					CommodityPO po =tList.get(0);
//					tempList.add(po);
//				}
//			}
			
			//得到策略内容信息
//			ResultMessage result=pbs.addPromotion(new PromotionVO(ID, rank, price, types.g, tempList , 0, 0, null,0, d1,d2));
//			if(1){
//				text="添加成功";
//				Vector v=new Vector();
//				v.add(utility.dateToString(d1));
//				v.add(utility.dateToString(d2));
//				tableModel.addRow(v);
				table.repaint();
				panel.setVisible(true);
				panel.repaint();
//			}else{
//				text="添加失败";
				
//			}
			commodityShowed=false;
//			utility.setInfo("", panel);
//			updateTableTwo();
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
	//日期是否在范围内
	boolean dateTrue(String dstr){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");//小写的mm表示的是分钟  
		Date date=new Date();
		try {
			date=sdf.parse(dstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d1=mp1.select.getTime();
		Date d2=mp2.select.getTime();
		if(date.after(d1)&&date.before(d2)){
			return true;
		}
		return false;
	}
}


