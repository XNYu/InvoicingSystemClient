package presentation.financeGUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;

import presentation.commonGUI.mainFrame;
import presentation.initialGUI.InitialPanel;
import presentation.logGUI.LogPanel;
import presentation.loginGUI.LoginFrame;
import presentation.tableGUI.CostBillPanel;
import presentation.tableGUI.DamagePanel;
import presentation.tableGUI.DetailPanel;
import presentation.tableGUI.ImportPanel;
import presentation.tableGUI.ImportReturnPanel;
import presentation.tableGUI.OverflowPanel;
import presentation.tableGUI.PaymentBillPanel;
import presentation.tableGUI.ReceivingBillPanel;
import presentation.tableGUI.SalesPanel;
import presentation.tableGUI.SalesReturnPanel;
import VO.AccountVO;
import VO.CostBillVO;
import VO.CustomerVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.UserVO;
import businesslogic.accountbl.AccountController;
import businesslogic.billbl.BillController;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.TransferList;
import businesslogic.utilitybl.CheckChinese;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.accountBLService.AccountBLService;
import businesslogicservice.billBLService.BillBLService;

public class FinanceFrame extends mainFrame {
	/**
	 * 财务人员操作界面
	 */
	private static final long serialVersionUID = -3092944004405860636L;
	JButton accButton,billButton,iniButton,logButton,courseButton,situationButoon,exchangeButton;
	AccountBLService acc = null;
	BillBLService bill = null;
	UserVO user = null;


	JPanel addpanel;

	public FinanceFrame(UserVO user1){
		super();
		this.user = user1;
		bill = new BillController(user);
		acc = new AccountController(user);

		if(user.getRank().equals("最高权限")){
			accButton= new JButton(" 账户管理", new ImageIcon("GUI/commodity.png"));
			accButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
			accButton.setSize(200,50);
			accButton.setLocation(0,120);
			accButton.setForeground(Color.white);
			accButton.setBackground(new Color(41,48,62));
			accButton.setFocusPainted(false);
			accButton.setBorderPainted(false);
			accButton.setContentAreaFilled(false);
			functionPanel.add(accButton);
			accButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					ArrayList<AccountVO> list = showOfFrame();


					account(0,list);

				}
			});
		}



		billButton= new JButton(" 账单制定", new ImageIcon("GUI/examine.png"));
		billButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		billButton.setSize(200,50);
		billButton.setLocation(0,180);
		billButton.setForeground(Color.white);
		billButton.setBackground(new Color(41,48,62));
		billButton.setFocusPainted(false);
		billButton.setBorderPainted(false);
		billButton.setContentAreaFilled(false);
		functionPanel.add(billButton);
		billButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				bill();

			}
		});

		iniButton= new JButton(" 期初建账", new ImageIcon("GUI/detail.png"));
		iniButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		iniButton.setSize(200,50);
		iniButton.setLocation(0,240);
		iniButton.setForeground(Color.white);
		iniButton.setBackground(new Color(41,48,62));
		iniButton.setFocusPainted(false);
		iniButton.setBorderPainted(false);
		iniButton.setContentAreaFilled(false);
		functionPanel.add(iniButton);
		iniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containPanel.removeAll();
				InitialPanel ini = new InitialPanel(user);
				ini.start();
				ini.repaint();
				containPanel.add(ini);
				containPanel.repaint();

			}
		});

		logButton= new JButton(" 日志查看", new ImageIcon("GUI/log.png"));
		logButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		logButton.setSize(200,50);
		logButton.setLocation(0,300);
		logButton.setForeground(Color.white);
		logButton.setBackground(new Color(41,48,62));
		logButton.setFocusPainted(false);
		logButton.setBorderPainted(false);
		logButton.setContentAreaFilled(false);
		functionPanel.add(logButton);
		logButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containPanel.removeAll();
				containPanel.add(new LogPanel());
				containPanel.repaint();

			}
		});

		courseButton= new JButton(" 经营情况", new ImageIcon("GUI/document.png"));
		courseButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		courseButton.setSize(200,50);
		courseButton.setLocation(0,360);
		courseButton.setForeground(Color.white);
		courseButton.setBackground(new Color(41,48,62));
		courseButton.setFocusPainted(false);
		courseButton.setBorderPainted(false);
		courseButton.setContentAreaFilled(false);
		functionPanel.add(courseButton);
		courseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containPanel.removeAll();
				DetailPanel d=new DetailPanel();
				containPanel.add(d);
				containPanel.paintComponents(containPanel.getGraphics());
				containPanel.repaint();

			}
		});

		situationButoon= new JButton(" 经营历程", new ImageIcon("GUI/table.png"));
		situationButoon.setFont(new Font("华文细黑", Font.PLAIN, 18));
		situationButoon.setSize(200,50);
		situationButoon.setLocation(0,420);
		situationButoon.setForeground(Color.white);
		situationButoon.setBackground(new Color(41,48,62));
		situationButoon.setFocusPainted(false);
		situationButoon.setBorderPainted(false);
		situationButoon.setContentAreaFilled(false);
		functionPanel.add(situationButoon);
		situationButoon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});

		exchangeButton= new JButton(" 切换用户",new ImageIcon("GUI/exchange.png"));
		exchangeButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		exchangeButton.setSize(200,50);
		exchangeButton.setLocation(0,480);
		exchangeButton.setForeground(Color.white);
		exchangeButton.setBackground(new Color(41,48,62));
		exchangeButton.setFocusPainted(false);
		exchangeButton.setBorderPainted(false);
		exchangeButton.setContentAreaFilled(false);
		functionPanel.add(exchangeButton);
		exchangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
					new LoginFrame("1");

			}
		});

		functionPanel.repaint();
		this.repaint();
	}

	void account(int num,ArrayList<AccountVO> list2){
		containPanel.removeAll();

		final ArrayList<AccountVO> list = list2;
		final int n = num;

		JButton search = new JButton();
		ImageIcon searchicon = new ImageIcon("GUI/search.png");
		search.setIcon(searchicon);
		search.setLocation(805, 10);
		search.setSize(40,40);
		search.setBorderPainted(false);
		search.setRolloverIcon(new ImageIcon("GUI/search2.png"));
		containPanel.add(search);
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				//accountController add()

				search();

			}
		});

		JButton back = new JButton(new ImageIcon("GUI/back.png"));
		back.setLocation(5, 210);
		back.setSize(40,180);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
		back.setOpaque(true);
		back.setContentAreaFilled(false);
		back.setRolloverIcon(new ImageIcon("GUI/back2.png"));
		containPanel.add(back);
		back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if(n-1<0){
							new InfoWindows("已是最前");

						}

						else
							account(n-1,list);

					}
		});

		JButton forward = new JButton(new ImageIcon("GUI/forward.png"));
		forward.setLocation(805, 210);
		forward.setSize(40,180);
		forward.setFocusPainted(false);
		forward.setBorderPainted(false);
		forward.setOpaque(true);
		forward.setContentAreaFilled(false);
		forward.setRolloverIcon(new ImageIcon("GUI/forward2.png"));
		containPanel.add(forward);
		forward.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if(n+1>(list.size()-1)/8){
							new InfoWindows("已是最后");

						}

						else
							account(n+1,list);

					}
		});
		int j = -1;

		for(int i = 8*n;i<8*(n+1)&&i<list.size();i++){
			JPanel p = showAccount(list.get(i).getName(),list.get(i).getMoney(),i);
			p.setVisible(true);
			p.setLocation(55+(i%8%3)*250,10+(i%8/3)*200);
			containPanel.add(p);
			j=i;

		}
		j = j%8;
		j++;

		addpanel = new JPanel() {
            /**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
            	ImageIcon icon = new ImageIcon("GUI/add.png");
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
            }
        };
        addpanel.setSize(240,180);
        addpanel.setLocation(55+(j%3)*250,10+(j/3)*200);
        addpanel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent   e){
				JPanel p1 = new JPanel() {
		            /**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@Override
		            protected void paintComponent(Graphics g) {
		            	ImageIcon icon = new ImageIcon("GUI/add2.png");
		                Image img = icon.getImage();
		                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
		            }
		        };
		        p1.setSize(240,180);
		        p1.setVisible(true);
		        addpanel.add(p1);
		        repaint();
			}

			public void mouseExited(MouseEvent   e){
				addpanel.removeAll();
		        repaint();
			}


        	public void  mousePressed(MouseEvent   e)  {

					Toolkit toolkit=Toolkit.getDefaultToolkit();
				    Dimension screen=toolkit.getScreenSize();
					final JFrame f  = new JFrame();
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setSize(400,300);
					f.setResizable(false);
					int x=(screen.width -f.getWidth())/2;
				    int y=(screen.height -f.getHeight())/2;
				    f.setLocation(x,y);
				    f.setLayout(null);
			        f.setUndecorated(true);
			        f.setBackground(Color.WHITE);

			        JLabel nameLabel = new JLabel("账户名：");
			        nameLabel.setSize(130,60);
			        nameLabel.setLocation(40,100);
			        nameLabel.setFont(new Font("黑体", Font.BOLD, 30));
			        f.add(nameLabel);


			        final JTextField nameText = new JTextField();
			        nameText.setSize(220,60);
			        nameText.setLocation(150,100);
			        nameText.setFont(new Font("黑体", Font.BOLD, 30));
			        f.add(nameText);

			        JButton confirmButton = new JButton();
					confirmButton.setSize(100,50);
					confirmButton.setLocation(150,220);
					confirmButton.setIcon(new ImageIcon("GUI/confirm.png"));
					confirmButton.setRolloverIcon(new ImageIcon("GUI/confirm2.png"));
					confirmButton.setFocusPainted(false);
					confirmButton.setBorderPainted(false);
					confirmButton.setBackground(new Color(80,100,220));
					f.add(confirmButton);
					confirmButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO 自动生成的方法存根

							if(CheckChinese.isAllChinese(nameText.getText())){
								ResultMessage flag = acc.addAccount(nameText.getText());
								if(flag == ResultMessage.Failure){
									new InfoWindows("已存在该用户");

								}
								account((showOfFrame().size()-1)/8,showOfFrame());//add方法接口

							}
							else {
								new InfoWindows("输入不合法");

							}


							f.setVisible(false);

						}
						}
					);




			        exitButton = new JButton( new ImageIcon("GUI/closeModify.png"));
					exitButton.setForeground(Color.gray);
					exitButton.setBackground(Color.gray);
					exitButton.setSize(20,20);
					exitButton.setLocation(380, 0);
					exitButton.setRolloverIcon( new ImageIcon("GUI/closeModify2.png"));
					exitButton.setBorderPainted(false);
					exitButton.setFocusPainted(false);
					exitButton.setContentAreaFilled(false);
						exitButton.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO 自动生成的方法存根
								f.setVisible(false);
							}


						}
						);


						f.add(exitButton);
						f.setVisible(true);

			}

		});
        containPanel.add(addpanel);
		containPanel.repaint();
		repaint();

	}

	void search(){
		Toolkit toolkit=Toolkit.getDefaultToolkit();
	    Dimension screen=toolkit.getScreenSize();
		final JFrame f  = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400,300);
		f.setResizable(false);
		int x=(screen.width -f.getWidth())/2;
	    int y=(screen.height -f.getHeight())/2;
	    f.setLocation(x,y);
	    f.setLayout(null);
        f.setUndecorated(true);
        f.setBackground(Color.WHITE);

        MyPanel srh = new MyPanel("GUI/select.png");
        srh.setLayout(null);
		srh.setSize(400, 300);
		srh.setLocation(0,0);
		srh.setVisible(true);



		JLabel nameLabel = new JLabel("查找：");
        nameLabel.setSize(130,60);
        nameLabel.setLocation(40,100);
        nameLabel.setFont(new Font("黑体", Font.BOLD, 30));
        nameLabel.setVisible(true);
        srh.add(nameLabel);


        final JTextField nameText = new JTextField();
        nameText.setSize(220,60);
        nameText.setLocation(150,100);
        nameText.setFont(new Font("黑体", Font.BOLD, 30));
        nameText.setVisible(true);
        srh.add(nameText);

        JButton confirmButton = new JButton();
		confirmButton.setSize(100,50);
		confirmButton.setLocation(150,220);
		confirmButton.setIcon(new ImageIcon("GUI/confirm.png"));
		confirmButton.setRolloverIcon(new ImageIcon("GUI/confirm2.png"));
		confirmButton.setFocusPainted(false);
		confirmButton.setBorderPainted(false);
		confirmButton.setBackground(new Color(80,100,220));
		f.add(confirmButton);
		confirmButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				if(CheckChinese.isAllChinese(nameText.getText())){
					account((acc.searchAccount(nameText.getText()).size()-1)/8,acc.searchAccount(nameText.getText()));
					containPanel.remove(addpanel);

					JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
					backButton.setSize(40,40);
					backButton.setLocation(805,555);
					backButton.setFont(new Font("黑体", Font.BOLD,8));
					backButton.setVisible(true);
					backButton.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO 自动生成的方法存根
							account(0,showOfFrame());
						}
					});
			        containPanel.add(backButton);
			        containPanel.repaint();

				}
				else {
					new InfoWindows("输入不合法");

				}

				f.setVisible(false);
			}
			}
		);
		f.add(srh);
		srh.repaint();
		f.repaint();

		f.setVisible(true);


	}

	JPanel showAccount (String name , double money,final int num){


		JPanel p = new JPanel() {
            /**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {

            	int i = num %5;
            	ImageIcon icon = null;
            	switch(i){
            	case 1: icon = new ImageIcon("GUI/bank1.png");break;
            	case 2: icon = new ImageIcon("GUI/bank2.png");break;
            	case 3: icon = new ImageIcon("GUI/bank3.png");break;
            	case 4: icon = new ImageIcon("GUI/bank5.png");break;
            	case 0: icon = new ImageIcon("GUI/bank4.png");break;
            	}

                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
            }
        };
        p.setLayout(null);
		p.setSize(240,180);

		JLabel jname = new JLabel("账户名称:");
		jname.setSize(100, 40);
		jname.setLocation(35, 10);
		jname.setFont(new Font("黑体", Font.BOLD, 18));
		p.add(jname);

		JLabel jname1 = new JLabel(name);
		jname1.setSize(100, 40);
		jname1.setLocation(120, 45);
		jname1.setFont(new Font("黑体", Font.BOLD, 18));
		p.add(jname1);

		JLabel jmoney = new JLabel("金额:"+Double.toString(money));
		jmoney.setSize(100,40);
		jmoney.setLocation(35, 80);
		jmoney.setFont(new Font("黑体", Font.BOLD, 18));
		p.add(jmoney);



		JButton delButton = new JButton(new ImageIcon("GUI/delete.png"));
		delButton.setSize(50,50);
		delButton.setLocation(50,115);
		delButton.setFocusPainted(false);
		delButton.setBorderPainted(false);
		delButton.setOpaque(true);
		delButton.setContentAreaFilled(false);
		delButton.setRolloverIcon(new ImageIcon("GUI/delete2.png"));
		p.add(delButton);
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				delAccount(num);

			}
		});

		JButton modButton = new JButton(new ImageIcon("GUI/modify.png"));
		modButton.setSize(50,50);
		modButton.setLocation(145,115);
		modButton.setFocusPainted(false);
		modButton.setBorderPainted(false);
		modButton.setOpaque(true);
		modButton.setContentAreaFilled(false);
		modButton.setRolloverIcon(new ImageIcon("GUI/modify2.png"));
		p.add(modButton);
		modButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Toolkit toolkit=Toolkit.getDefaultToolkit();
			    Dimension screen=toolkit.getScreenSize();
				final JFrame f  = new JFrame();
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(400,300);
				f.setResizable(false);
				int x=(screen.width -f.getWidth())/2;
			    int y=(screen.height -f.getHeight())/2;
			    f.setLocation(x,y);
			    f.setLayout(null);
		        f.setUndecorated(true);
		        f.setBackground(Color.WHITE);

		        JLabel nameLabel = new JLabel("新名字：");
		        nameLabel.setSize(130,60);
		        nameLabel.setLocation(40,100);
		        nameLabel.setFont(new Font("黑体", Font.BOLD, 30));
		        f.add(nameLabel);


		        final JTextField nameText = new JTextField();
		        nameText.setSize(220,60);
		        nameText.setLocation(150,100);
		        nameText.setFont(new Font("黑体", Font.BOLD, 30));
		        f.add(nameText);

		        JButton confirmButton = new JButton();
				confirmButton.setSize(100,50);
				confirmButton.setLocation(150,220);
				confirmButton.setIcon(new ImageIcon("GUI/confirm.png"));
				confirmButton.setRolloverIcon(new ImageIcon("GUI/confirm2.png"));
				confirmButton.setFocusPainted(false);
				confirmButton.setBorderPainted(false);
				confirmButton.setBackground(new Color(80,100,220));
				f.add(confirmButton);
				confirmButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根

						if(CheckChinese.isAllChinese(nameText.getText())){
							ResultMessage flag = acc.modifyAccount(showOfFrame().get(num).getName(), nameText.getText());
							if(flag == ResultMessage.Failure){
								new InfoWindows("该名字已被使用");

							}
							account((showOfFrame().size()-1)/8,showOfFrame());

						}
						else {
							new InfoWindows("输入不合法");

						}


						f.setVisible(false);
					}
					}
				);




		        exitButton = new JButton( new ImageIcon("GUI/closeModify.png"));
				exitButton.setForeground(Color.gray);
				exitButton.setBackground(Color.gray);
				exitButton.setSize(20,20);
				exitButton.setLocation(380, 0);
				exitButton.setRolloverIcon( new ImageIcon("GUI/closeModify2.png"));
				exitButton.setBorderPainted(false);
				exitButton.setFocusPainted(false);
				exitButton.setContentAreaFilled(false);
				exitButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						f.setVisible(false);
					}


				}
				);


				f.add(exitButton);
				f.setVisible(true);
			}
		});



		p.repaint();
		return p;
	}

	void delAccount(int num){
		Object[] options = {"确定","取消"};
		int response=JOptionPane.showOptionDialog(this, "您确定删除该账户吗？", "删除账户",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if(response==0)
		{
			acc.deleteAccount(showOfFrame().get(num).getName());
			containPanel.removeAll();
			account((showOfFrame().size()-1)/8,showOfFrame());
		}
	}

	void bill(){
		containPanel.removeAll();

		final JButton recButton = new JButton(new ImageIcon("GUI/recButton.png"));
		recButton.setSize(200,50);
		recButton.setLocation(330,200);
		recButton.setFocusPainted(false);
		recButton.setBorderPainted(false);
		recButton.setOpaque(true);
		recButton.setContentAreaFilled(false);
		containPanel.add(recButton);
		recButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				recButton.setIcon(new ImageIcon("GUI/recButton2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showReceiving();
			}
			public void mouseReleased(MouseEvent e){
				recButton.setIcon(new ImageIcon("GUI/recButton.png"));
			}

		}
		);

		final JButton payButton = new JButton(new ImageIcon("GUI/payButton.png"));
		payButton.setSize(200,50);
		payButton.setLocation(330,280);
		payButton.setFocusPainted(false);
		payButton.setBorderPainted(false);
		payButton.setOpaque(true);
		payButton.setContentAreaFilled(false);
		containPanel.add(payButton);
		payButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				payButton.setIcon(new ImageIcon("GUI/payButton2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showPayment();
			}
			public void mouseReleased(MouseEvent e){
				payButton.setIcon(new ImageIcon("GUI/payButton.png"));
			}
		}
		);

		final JButton costButton = new JButton(new ImageIcon("GUI/costButton.png"));
		costButton.setSize(200,50);
		costButton.setLocation(330,360);
		costButton.setBorderPainted(false);
		costButton.setOpaque(true);
		costButton.setContentAreaFilled(false);
		containPanel.add(costButton);
		costButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				costButton.setIcon(new ImageIcon("GUI/costButton2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showCost();
			}
			public void mouseReleased(MouseEvent e){
				costButton.setIcon(new ImageIcon("GUI/costButton.png"));
			}
		}
		);

		final JButton showButton = new JButton(new ImageIcon("GUI/showAll.png"));
		showButton.setSize(200,50);
		showButton.setLocation(330,440);
		showButton.setFocusPainted(false);
		showButton.setBorderPainted(false);
		showButton.setOpaque(true);
		showButton.setContentAreaFilled(false);
		containPanel.add(showButton);
		showButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				showButton.setIcon(new ImageIcon("GUI/showAll2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showAll();
			}
			private void showAll() {
				// TODO 自动生成的方法存根
				containPanel.removeAll();

				JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
				backButton.setSize(40,40);
				backButton.setLocation(805,555);
				backButton.setFont(new Font("黑体", Font.BOLD,8));
				backButton.setVisible(true);
				backButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						containPanel.removeAll();
						bill();
					}
				});

				InfoTable info = new InfoTable(bill.show(),bill,containPanel,true);
				info.create();
				info.setSize(700, 500);
				info.setLocation(75, 50);
				info.repaint();
				containPanel.add(backButton);
				containPanel.add(info);
				containPanel.repaint();
			}

		}
		);

		JButton infoButton= new JButton(new ImageIcon("GUI/info.png"));
		infoButton.setSize(50,50);
		infoButton.setLocation(700,50);
		infoButton.setBorderPainted(false);
		infoButton.setBackground(new Color(200,230,184));
		infoButton.setFocusPainted(false);
		infoButton.setRolloverIcon(new ImageIcon("GUI/info2.png"));
		containPanel.add(infoButton);
		infoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				showInfo();
			}


		}
		);

		containPanel.setBackground(new Color(200,230,184));

		repaint();

	}

	void showReceiving(){
		containPanel.removeAll();

		MyPanel mp = new MyPanel("GUI/receiving.png");

		mp.setLayout(null);

		//add

		final TransferList transferlist = new TransferList();

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(transferlist.total()));//sum 改
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		final BillTable b = new BillTable(sumtext,transferlist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		containPanel.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(bill.getBillID("SKD"));//billid 改
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(this.user.getName());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> customer = new JComboBox<String>();
		ArrayList<CustomerVO> customerlist = bill.showCustomer();
		for(CustomerVO cus:customerlist){
			customer.addItem(cus.getName());

		}

		customer.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);






		//add

		JButton redo = new JButton("重做");
		redo.setSize(100,50);
		redo.setLocation(60,500);
		redo.setFont(new Font("黑体", Font.BOLD, 18));
		redo.setForeground(Color.WHITE);
		redo.setFocusPainted(false);
		redo.setBackground(new Color(80,100,220));
		mp.add(redo);
		redo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				showReceiving();
			}


		}
		);

		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				bill.createbill(new ReceivingBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getSelectedItem()), b.gettransferlist().getRtlist()));
				new InfoWindows("添加完成");
				bill();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		containPanel.add(mp);
		mp.repaint();
		containPanel.repaint();
	}

	void showPayment(){
		containPanel.removeAll();

		MyPanel mp = new MyPanel("GUI/payment.png");
		mp.setLayout(null);

		final TransferList transferlist = new TransferList();;

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(transferlist.total()));//sum 改
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		BillTable b = new BillTable(sumtext,transferlist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		containPanel.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(bill.getBillID("FKD"));//billid 改
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(this.user.getName());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> customer = new JComboBox<String>();
		ArrayList<CustomerVO> customerlist = bill.showCustomer();
		for(CustomerVO cus:customerlist){
			customer.addItem(cus.getName());

		}

		customer.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);





		JButton redo = new JButton("重做");
		redo.setSize(100,50);
		redo.setLocation(60,500);
		redo.setFont(new Font("黑体", Font.BOLD, 18));
		redo.setForeground(Color.WHITE);
		redo.setFocusPainted(false);
		redo.setBackground(new Color(80,100,220));
		mp.add(redo);
		redo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				showPayment();
			}


		}
		);

		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				bill.createbill(new PaymentBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getSelectedItem()), transferlist.getRtlist()));
				new InfoWindows("添加完成");
				bill();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		containPanel.add(mp);
		mp.repaint();
	}

	void showCost(){
		containPanel.removeAll();

		MyPanel mp = new MyPanel("GUI/cost.png");
		mp.setLayout(null);

		final EntryList entrylist = new EntryList();;

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(entrylist.total()));//sum 改
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		CostTable b = new CostTable(sumtext,entrylist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		containPanel.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(bill.getBillID("XJFYD"));//billid 改
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(this.user.getName());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> account = new JComboBox<String>();
		ArrayList<AccountVO> accountlist = bill.showAccount();
		for(AccountVO acc:accountlist){
			account.addItem(acc.getName());

		}

		account.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		account.setFont(new Font("黑体", Font.BOLD, 24));
		account.setSize(120, 30);
		account.setLocation(235, 108);
		mp.add(account);

		JButton redo = new JButton("重做");
		redo.setSize(100,50);
		redo.setLocation(60,500);
		redo.setFont(new Font("黑体", Font.BOLD, 18));
		redo.setForeground(Color.WHITE);
		redo.setFocusPainted(false);
		redo.setBackground(new Color(80,100,220));
		mp.add(redo);
		redo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				showCost();
			}


		}
		);

		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				bill.createbill(new CostBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getAccount((String)account.getSelectedItem()), entrylist.getRtlist()));
				new InfoWindows("添加完成");
				bill();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		containPanel.add(mp);
		mp.repaint();
	}

	void showInfo(){
		containPanel.removeAll();

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				containPanel.removeAll();
				bill();
			}
		});

		InfoTable info = new InfoTable(bill.getBillExamined(),bill,containPanel,false);
		info.create();
		info.setSize(700, 500);
		info.setLocation(75, 50);
		info.repaint();
		containPanel.add(backButton);
		containPanel.add(info);
		containPanel.repaint();

	}

	ArrayList<AccountVO> showOfFrame(){
		ArrayList<AccountVO> list = acc.show();

		return list;
	}

	public static void main(String[] args) {
		UserVO user = new UserVO("123", "财务人员", "asd", "asd", "最高权限");

		new FinanceFrame(user);

	}
}
