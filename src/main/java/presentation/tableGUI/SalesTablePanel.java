
package presentation.tableGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import presentation.promotionGUI.DateChooser;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import PO.CommodityPO;
import PO.SalesPO;
import VO.CustomerVO;
import VO.SalesVO;
import VO.UserVO;
import businesslogic.commoditybl.Commodity;
import businesslogic.salesbl.Sales;
import businesslogic.salesbl.SalesController;
import businesslogic.userbl.User;
import businesslogic.utilitybl.DateHelper;
import businesslogic.utilitybl.ExportExcel;
import businesslogic.utilitybl.ShowMessageFrame;
import businesslogicservice.salesBLService.SalesBLService;

public class SalesTablePanel extends JPanel{
	DateChooser mp1,mp2;
	JTextField stockField;
	JComboBox commodityBox;
	JComboBox customerBox;
	JComboBox salesmanBox;
	JTable table;
	SalesTableModel tablemodel; 
	JPanel panel=this;
	SalesBLService sales=new SalesController();
	Commodity commodity=new Commodity();
	User user=new User();
	ArrayList<SalesVO> saleslist;
	String filePath = "";
	List<forExport> dataset=new ArrayList<forExport>();
	private JButton button_1;
	ArrayList<UserVO> userlist=new ArrayList<UserVO>();
	
	public SalesTablePanel(){
		//setSize(840,600);
		
		setBounds(10, 80, 840, 480);
		tablemodel=new SalesTableModel();
		table=new JTable(tablemodel);
		
		JLabel startdate = new JLabel("起始日期");
		startdate.setBounds(28, 7, 70, 25);
		panel.add(startdate);
		
		JLabel enddate = new JLabel("终止日期");
		enddate.setBounds(208, 7, 70, 25);
		panel.add(enddate);
		
		mp1 = new DateChooser("yyyy-MM-dd");
		mp1.setBounds(108, 7, 90, 25);
		panel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});	
		
		mp2 = new DateChooser("yyyy-MM-dd");
		mp2.setBounds(288, 7, 90, 25);
		panel.add(mp2);
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
			}
		});		
		
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(28, 48, 802, 363);
		add(scrollpane);
		
		ArrayList<CommodityPO> commoditylist=commodity.showCom();
		commodityBox = new JComboBox();
		commodityBox.setBounds(388, 7, 90, 25);
		panel.add(commodityBox);
		commodityBox.addItem(null);
		for(CommodityPO cpo:commoditylist){
			commodityBox.addItem(cpo.getName());
		}
		
		ArrayList<CustomerVO> customerlist=sales.showSalesCustomer();
		customerBox = new JComboBox();
		customerBox.setBounds(488, 7, 90, 25);
		panel.add(customerBox);
		customerBox.addItem(null);
		for(CustomerVO cvo:customerlist){
			customerBox.addItem(cvo.getName());
		}
		
		userlist=user.showUser();
		salesmanBox = new JComboBox();
		salesmanBox.setBounds(588, 7, 90, 25);
		panel.add(salesmanBox);
		salesmanBox.addItem(null);
		for(UserVO uvo:userlist){
			salesmanBox.addItem(uvo.getName());
		}
		
		stockField=new JTextField("仓库");
		stockField.setHorizontalAlignment(SwingConstants.CENTER);
		stockField.setBounds(688, 7, 70, 25);
		stockField.addFocusListener(new StockAdapter());
		panel.add(stockField);
		
		JButton ensureButton = new JButton("确定");
		ensureButton.setBounds(768, 7, 60, 25);
		ensureButton.addActionListener(new EnsureListener());
		panel.add(ensureButton);
		setLayout(null);
		
		JButton button = new JButton("导出表格");
		button.addActionListener(new OutputListener());
		button.setBounds(709, 424, 121, 25);
		add(button);
		
		button_1 = new JButton("选择路径");
		button_1.setBounds(571, 423, 121, 25);
		button_1.addActionListener(new fileListener());
		add(button_1);
		
	}
	
	public void updateTable(){
		while(table.getRowCount()!=0){
			tablemodel.removeRow(0);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
		String sd=sdf.format(mp1.getDate());
		String ed=sdf.format(mp2.getDate());
		int sdate=Integer.parseInt(sd);
		int edate=Integer.parseInt(ed);
		for(SalesVO svo:saleslist){	
			String[] tmp=svo.getID().split("-");
			String d=tmp[1];
			int date=Integer.parseInt(d);
			
			if(customerBox.getSelectedItem()==null||svo.getCustomer().equals(customerBox.getSelectedItem())){
				if(salesmanBox.getSelectedItem()==null||svo.getSalesman().equals(salesmanBox.getSelectedItem())){
					
					if(stockField.getText().equals("")||stockField.getText().equals("仓库")||svo.getStock().equals(stockField.getText())){
						if(date>=sdate&&date<=edate){
							ArrayList<CommodityPO> clist=svo.getCommodityList();
							for(CommodityPO cpo:clist){
								if(commodityBox.getSelectedItem()==null||commodityBox.getSelectedItem().equals(cpo.getName())){
									dataset.add(new forExport(d,cpo.getName(),cpo.getType(),cpo.getAmount(),
											cpo.getImpPrice(),cpo.getImpPrice()*cpo.getAmount()));
									Vector v=new Vector();
									v.add(d);
									v.add(cpo.getName());
									v.add(cpo.getType());
									v.add(cpo.getAmount());
									v.add(cpo.getImpPrice());
									v.add(cpo.getImpPrice()*cpo.getAmount());
									tablemodel.addRow(v);
									
									
								}
							}
						}
					}
				}
			}
		}
		table.revalidate();
	}
	
	public class EnsureListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			saleslist=sales.showSales();
			updateTable();
			table.revalidate();
			panel.repaint();
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
	
	public class fileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.showOpenDialog(null);
			filePath = chooser.getSelectedFile().getPath();
			
		}
		
	}
	public class OutputListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			 // 测试学生  
	        ExportExcel<forExport> ex = new ExportExcel<forExport>();  
	        String[] headers =  
	        { "日期","商品名","型号","数量","单价","总额"};  
	        try  
	        {
//	            BufferedInputStream bis = new BufferedInputStream(  
//	                    new FileInputStream("GUI/commodity.png"));  
//	            byte[] buf = new byte[bis.available()];  
//	            while ((bis.read(buf)) != -1)  
//	            {  
//	                //  
//	            }
	        	DateHelper dh = new DateHelper();
	        	String outputName = dh.getDate();
	        	outputName = "销售明细表-"+outputName+".xls";
	            OutputStream out = new FileOutputStream(filePath +"/"+outputName); 
	            ex.exportExcel(headers, dataset, out);  
	            out.close();  
	            new ShowMessageFrame("导出表格成功");
//	            JOptionPane.showMessageDialog(null, "导出成功!");  
//	            System.out.println("excel导出成功！");  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
		}
		
	}
	
	public class forExport{
		String date,name,type;
		int num;
		double price,total;
		private static final long serialVersionUID = 1L;
		
		public long getSerialVersionUID() {
			return serialVersionUID;
		}
		
		forExport(String d,String n,String t,int m, double p,double to){
			date=d;
			name=n;
			type=t;
			num=m;
			price=p;
			total=to;
		}
		
		public String getDate(){
			return date;
		}
		public String getType(){
			return type;
		}
		public String getName(){
			return name;
		}
		public int getNum(){
			return num;
		}
		public double getPrice(){
			return price;
		}
		public double getTotal(){
			return total;
		}
		public String getThis$0(){
			return "";
		}
	}
}

