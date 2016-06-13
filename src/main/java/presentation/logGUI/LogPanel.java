package presentation.logGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import presentation.financeGUI.MyPanel;
import presentation.stockGUI.commodity.DateChooser;
import VO.LogVO;
import businesslogic.logbl.LogController;

public class LogPanel extends MyPanel{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private LogController log = new LogController();
	private JTextArea showArea = null;
	DateChooser mp1,mp2;
	MyPanel panel = null;

	public LogPanel() {
		super("GUI/background.png");
		this.setLayout(null);
		this.setSize(850, 600);
		this.setLocation(0, 0);
		this.panel = this;

		showArea = new JTextArea();
		showArea.setSize(800, 400);
		showArea.setLocation(200, 100);
		showArea.setFont(new Font("黑体", Font.BOLD,12));
		showArea.setForeground(Color.white);
		showArea.setOpaque(false);
		showArea.setEditable(false);
		showArea.append("日期\t\t\t"+"操作员\t\t"+"内容\n");
		showArea.setVisible(true);
		this.add(showArea);

		final JLabel searchtext = new JLabel("请输入要查询的日期");
		searchtext.setSize(200,30);
		searchtext.setLocation(160,60);
		searchtext.setFont(new Font("黑体", Font.BOLD, 15));
		searchtext.setForeground(Color.white);
		this.add(searchtext);


		//此处缺少日期选择
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
		//confirm.setOpaque(true);
		//confirm.setContentAreaFilled(false);
		this.add(confirm);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSearchResult();
				new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							while(true){
								Thread.sleep(3500);
								showSearchResult();
								}

							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}

					}
				}
					).start();



			}
		});
	}


	private void showSearchResult(){
		showArea.setText("日期\t\t\t"+"操作员\t\t"+"内容\n");
 		ArrayList<LogVO> list = new ArrayList<LogVO>();
 		list = log.searchlogs(mp1.showDate.getText(), mp2.showDate.getText()+"a");
 		for(int i=0;i<list.size();i++){
 			showArea.append(list.get(i).getTime()+"\t"+list.get(i).getUsername()+"\t\t"+list.get(i).getIncluding()+"\n");
 		}
 		repaint();
	}



}
