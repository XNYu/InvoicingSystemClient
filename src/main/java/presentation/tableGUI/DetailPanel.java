package presentation.tableGUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import presentation.financeGUI.MyPanel;
import presentation.stockGUI.commodity.DateChooser;
import VO.LogVO;
import businesslogic.logbl.LogController;
import businesslogic.tablebl.Table;
import businesslogic.tablebl.TableController;
import businesslogicservice.tableBLService.TableBLService;


public class DetailPanel extends MyPanel implements TableModelListener,ListSelectionListener{
	
	private static final long serialVersionUID = 1L;

	private JTextArea showArea = null;
	DateChooser mp1,mp2;
	DetailPanel panel = this;
	double Income,Outcome,Profit,Discount;
	Table table=new Table();
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	
	public DetailPanel() {
		
		super("GUI/background.png");
		this.setLayout(null);
		this.setSize(850, 600);
		this.setLocation(0, 0);
		this.panel = this;

		final JLabel searchtext = new JLabel("请输入要查询的日期");
		searchtext.setSize(200,30);
		searchtext.setLocation(160,60);
		searchtext.setFont(new Font("黑体", Font.BOLD, 15));
		searchtext.setForeground(Color.white);
		this.add(searchtext);

		mp1 = new DateChooser("yyyy/MM/dd");
		mp1.setBounds(340, 60, 90, 25);
		this.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
				panel.repaint();
			}
		});

		mp2 = new DateChooser("yyyy/MM/dd");
		mp2.setBounds(450, 60, 90, 25);
		this.add(mp2);
		mp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp2.showPanel(mp2.showDate);
				panel.repaint();
			}
		});



		final JButton confirm = new JButton("确认");
		confirm.setLocation(600,60);
		confirm.setSize(80,25);
		confirm.setFocusPainted(false);
		confirm.setBorderPainted(false);
		this.add(confirm);
		
		label = new JLabel("折让后收入");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label.setBounds(160, 151, 247, 30);
		add(label);
		
		label_1 = new JLabel("折让值");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_1.setBounds(160, 210, 247, 30);
		add(label_1);
		
		label_2 = new JLabel("支出");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_2.setBounds(160, 313, 247, 30);
		add(label_2);
		
		label_3 = new JLabel("利润");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_3.setBounds(160, 413, 247, 30);
		add(label_3);
		
		label_4 = new JLabel("        ");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_4.setBounds(557, 150, 124, 30);
		add(label_4);
		
		label_5 = new JLabel("        ");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_5.setBounds(557, 209, 124, 30);
		add(label_5);
		
		label_6 = new JLabel("        ");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_6.setBounds(557, 312, 124, 30);
		add(label_6);
		
		label_7 = new JLabel("        ");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("微软雅黑", Font.BOLD, 25));
		label_7.setBounds(557, 412, 124, 30);
		add(label_7);
		confirm.addActionListener(new sListener());
		
		
		
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class sListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Date d1=mp1.getDate();
			Date d2=mp2.getDate();
			Income=table.checkIncome(d1, d2);
			Outcome=table.checkOutcome(d1, d2);
			Profit=table.check(d1, d2);
			Discount=table.checkDiscount(d1, d2);
			
			String in,out,pro,dis;
			in=Double.toString(Income);
			out=Double.toString(Outcome);
			pro=Double.toString(Profit);
			dis=Double.toString(Discount);
			label_4.setText(in);
			label_5.setText(dis);
			label_6.setText(out);
			label_7.setText(pro);
			
			panel.repaint();
		}
		
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}
