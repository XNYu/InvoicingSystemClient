package STATIC;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import STATIC.customer.customerModel;
import STATIC.customer.customerPanel;
import VO.UserVO;
import presentation.commonGUI.mainFrame;
import presentation.logGUI.LogPanel;
import presentation.loginGUI.LoginFrame;
import presentation.tableGUI.*;

public class StatisticFrame extends mainFrame{
	//PromotionTablePanel panel = new PromotionTablePanel();
	static String title=null;
	static UserVO user=new UserVO(title, "财务人员", title, title, title);
	/*giftTablePanel panel1=new giftTablePanel(user);
	discountTablePanel panel2=new discountTablePanel(user);
	voucherTablePanel panel3=new voucherTablePanel(user);
	packTablePanel panel4=new packTablePanel(user);
	*/
	public StatisticFrame(UserVO uvo){
	
	JButton giftButton= new JButton("促销趋势",new ImageIcon("GUI/promotion.png"));
	giftButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
	giftButton.setSize(200,50);
	giftButton.setLocation(0,120);
	giftButton.setForeground(Color.white);
	giftButton.setBackground(new Color(41,48,62));
	giftButton.setFocusPainted(false);
	giftButton.setBorderPainted(false);
	giftButton.setContentAreaFilled(false);
	giftButton.addActionListener(new promotionListener());
	functionPanel.add(giftButton);
	
	JButton discountButton= new JButton("客户分析",new ImageIcon("GUI/examine.png"));
	discountButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
	discountButton.setSize(200,50);
	discountButton.setLocation(0,185);
	discountButton.setForeground(Color.white);
	discountButton.setBackground(new Color(41,48,62));
	discountButton.setFocusPainted(false);
	discountButton.setBorderPainted(false);
	discountButton.setContentAreaFilled(false);
	discountButton.addActionListener(new detailListener());
	functionPanel.add(discountButton);
	
	JButton voucherButton= new JButton("销售分析",new ImageIcon("GUI/qingkuang.png"));
	voucherButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
	voucherButton.setSize(200,50);
	voucherButton.setLocation(0,250);
	voucherButton.setForeground(Color.white);
	voucherButton.setBackground(new Color(41,48,62));
	voucherButton.setFocusPainted(false);
	voucherButton.setBorderPainted(false);
	voucherButton.setContentAreaFilled(false);
	voucherButton.addActionListener(new infoListener());
	functionPanel.add(voucherButton);
	
	JButton showButton= new JButton("经营历程", new ImageIcon("GUI/table.png"));
	showButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
	showButton.setSize(200,50);
	showButton.setLocation(0,315);
	showButton.setForeground(Color.white);
	showButton.setBackground(new Color(41,48,62));
	showButton.setFocusPainted(false);
	showButton.setBorderPainted(false);
	showButton.setContentAreaFilled(false);
	showButton.addActionListener(new showListener());
	functionPanel.add(showButton);
	
//	JButton exButton= new JButton("审批单据", new ImageIcon("GUI/detail.png"));
//	exButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
//	exButton.setSize(200,50);
//	exButton.setLocation(0,380);
//	exButton.setForeground(Color.white);
//	exButton.setBackground(new Color(41,48,62));
//	exButton.setFocusPainted(false);
//	exButton.setBorderPainted(false);
//	exButton.setContentAreaFilled(false);
//	exButton.addActionListener(new examineListener());
//	functionPanel.add(exButton);
//	
//	JButton logButton= new JButton("查看日志", new ImageIcon("GUI/log.png"));
//	logButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
//	logButton.setSize(200,50);
//	logButton.setLocation(0,445);
//	logButton.setForeground(Color.white);
//	logButton.setBackground(new Color(41,48,62));
//	logButton.setFocusPainted(false);
//	logButton.setBorderPainted(false);
//	logButton.setContentAreaFilled(false);
//	logButton.addActionListener(new logListener());
//	functionPanel.add(logButton);
	
	JButton switchButton= new JButton("切换用户", new ImageIcon("GUI/exchange.png"));
	switchButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
	switchButton.setSize(200,50);
	switchButton.setLocation(0,510);
	switchButton.setForeground(Color.white);
	switchButton.setBackground(new Color(41,48,62));
	switchButton.setFocusPainted(false);
	switchButton.setBorderPainted(false);
	switchButton.setContentAreaFilled(false);
	switchButton.addActionListener(new switchListener());
	functionPanel.add(switchButton);
	
	functionPanel.setVisible(true);
	functionPanel.repaint();
	containPanel.setLocation(200,0);
	containPanel.setSize(850,1000);
	containPanel.repaint();
	setVisible(true);
	repaint();
	
	
	}
	
	class promotionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
//			containPanel.removeAll();
//			JTabbedPane tabbedPane = new JTabbedPane();
//			tabbedPane.setBounds(10, 10, 840, 550);
//			giftTablePanel panel1 = new giftTablePanel(user);
//			discountTablePanel panel2 = new discountTablePanel(user);
//			voucherTablePanel panel3 = new voucherTablePanel(user);
//			packTablePanel panel4 = new packTablePanel(user);
//			
//			tabbedPane.add("赠送赠品", panel1);
//			tabbedPane.add("价格折让", panel2);
//			tabbedPane.add("代金券", panel3);
//			tabbedPane.add("特价包", panel4);
//			
//			containPanel.add(tabbedPane);
//			containPanel.paintComponents(containPanel.getGraphics());
//			tabbedPane.setVisible(true);
//			containPanel.repaint();
		}
		
	}
	
	class infoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			commodityPanel d=new commodityPanel(null);
			containPanel.add(d);
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}
	
	class detailListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			customerPanel s=new customerPanel(null);
			//s.setLocation(0,0);
			containPanel.add(s);
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
	}
	
	class logListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			LogPanel l=new LogPanel();
			containPanel.add(l);
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}
	
	class showListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 80, 840, 480);
			
			SalesPanel spanel=new SalesPanel();
			SalesReturnPanel srpanel=new SalesReturnPanel();
			ImportPanel ipanel=new ImportPanel();
			ImportReturnPanel irpanel=new ImportReturnPanel();
			PaymentBillPanel panel5 =new PaymentBillPanel(user);
			ReceivingBillPanel panel6 =new ReceivingBillPanel(user);
			CostBillPanel panel7 =new CostBillPanel(user);
			DamagePanel panel8 =new DamagePanel();
 			OverflowPanel panel9 =new OverflowPanel();
			tabbedPane.add("销售单", spanel);
			tabbedPane.add("销售退货单", srpanel);
			tabbedPane.add("进货单", ipanel);
			tabbedPane.add("进货退货单", irpanel);
			tabbedPane.add("付款单", panel5);
			tabbedPane.add("收款单", panel6);
			tabbedPane.add("现金费用单", panel7);
			tabbedPane.add("库存报损单", panel8);
			tabbedPane.add("库存报溢单", panel9);
			
			containPanel.add(tabbedPane);
	
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}
	class examineListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(10, 80, 840, 480);
			
			presentation.examineGUI.SalesPanel spanel=new presentation.examineGUI.SalesPanel();
			presentation.examineGUI.SalesReturnPanel srpanel=new presentation.examineGUI.SalesReturnPanel();
			presentation.examineGUI.ImportPanel ipanel=new presentation.examineGUI.ImportPanel();
			presentation.examineGUI.ImportReturnPanel irpanel=new presentation.examineGUI.ImportReturnPanel();
			presentation.examineGUI.PaymentBillPanel panel5 =new presentation.examineGUI.PaymentBillPanel();
			presentation.examineGUI.ReceivingBillPanel panel6 =new presentation.examineGUI.ReceivingBillPanel();
			presentation.examineGUI.CostBillPanel panel7 =new presentation.examineGUI.CostBillPanel();
			presentation.examineGUI.DamagePanel panel8 =new presentation.examineGUI.DamagePanel();
			presentation.examineGUI.OverflowPanel panel9 =new presentation.examineGUI.OverflowPanel();
			presentation.examineGUI.PresentPanel panel10 =new presentation.examineGUI.PresentPanel();
			
			tabbedPane.add("销售单", spanel);
			tabbedPane.add("销售退货单", srpanel);
			tabbedPane.add("进货单", ipanel);
			tabbedPane.add("进货退货单", irpanel);
			tabbedPane.add("付款单", panel5);
			tabbedPane.add("收款单", panel6);
			tabbedPane.add("现金费用单", panel7);
			tabbedPane.add("库存报损单", panel8);
			tabbedPane.add("库存报溢单", panel9);
			tabbedPane.add("库存赠送单", panel10);
			containPanel.add(tabbedPane);
	
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}
	
	class switchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
//			dispose();
//			new LoginFrame("1");
			containPanel.removeAll();
			DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
	        
	        dpd.setValue("MANAGER", 25);  //输入数据
	        dpd.setValue("市场人员", 25);
	        dpd.setValue("开发人员", 45);
	        dpd.setValue("其他人员", 10);

	        JFreeChart chart=ChartFactory.createPieChart("IMAGE",dpd,true,true,false); 
	        ChartPanel cp = new ChartPanel(chart);
	        cp.setBounds(10, 80, 600, 300);
	        
	        containPanel.add(cp);
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}

	public static void main(String [] args){
		new StatisticFrame(user);
	}
	
}
