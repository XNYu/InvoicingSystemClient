package presentation.initialGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import presentation.financeGUI.InfoWindows;
import presentation.financeGUI.MyPanel;
import presentation.stockGUI.commodity.DateChooser;
import PO.CommodityPO;
import VO.AccountVO;
import VO.CustomerVO;
import VO.InitialVO;
import VO.UserVO;
import businesslogic.accountbl.Account;
import businesslogic.accountbl.AccountInterfaceForInitial;
import businesslogic.commoditybl.Commodity;
import businesslogic.commoditybl.CommodityInterfaceForInitial;
import businesslogic.customerbl.Customer;
import businesslogic.customerbl.CustomerInterfaceForInitial;
import businesslogic.initialbl.InitialController;
import businesslogicservice.initialBLService.InitialBLService;

public class InitialPanel extends MyPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JButton search,makeinitial;
	JPanel mainPanel = this;
	InitialVO vo = null;
	InitialBLService iniblservice = null;
	AccountInterfaceForInitial acc = null;
	CustomerInterfaceForInitial cus = null;
	CommodityInterfaceForInitial com = null;
	UserVO user = null;
	Thread account = null,customer = null,commodity = null;


	public InitialPanel(UserVO user1) {
		super("GUI/background.png");

		this.user = user1;
		acc =new Account(user);
		cus = new Customer();
		com = new Commodity();
		iniblservice = new InitialController(user);

		this.setSize(850, 600);
		this.setLocation(0, 0);
		this.setLayout(null);


		// TODO 自动生成的构造函数存根
	}

	public void start(){
		search = new JButton(new ImageIcon("GUI/searchButton.png"));
		search.setLocation(330, 240);
		search.setSize(200,50);
		search.setFocusPainted(false);
		search.setBorderPainted(false);
		search.setOpaque(true);
		search.setContentAreaFilled(false);
		this.add(search);
		search.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				search.setIcon(new ImageIcon("GUI/searchButton2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				search();
			}
			public void mouseReleased(MouseEvent e){
				search.setIcon(new ImageIcon("GUI/searchButton.png"));
			}

		}
		);

		makeinitial = new JButton(new ImageIcon("GUI/initialButton.png"));
		makeinitial.setSize(200,50);
		makeinitial.setLocation(330,340);
		makeinitial.setFocusPainted(false);
		makeinitial.setBorderPainted(false);
		makeinitial.setOpaque(true);
		makeinitial.setContentAreaFilled(false);
		this.add(makeinitial);
		makeinitial.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				makeinitial.setIcon(new ImageIcon("GUI/initialButton2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				initial();
			}
			public void mouseReleased(MouseEvent e){
				makeinitial.setIcon(new ImageIcon("GUI/initialButton.png"));
			}


		}
		);


		this.repaint();
		this.setVisible(true);

	}

	void search(){
		this.removeAll();
		final JLabel searchtext = new JLabel("请输入要查询账目的日期");
		searchtext.setSize(600,30);
		searchtext.setLocation(240,180);
		searchtext.setFont(new Font("黑体", Font.BOLD, 30));
		searchtext.setForeground(Color.white);
		this.add(searchtext);

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		this.add(backButton);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				start();
			}
		});

		final DateChooser date = new DateChooser("yyyy/MM/dd");
		date.setSize(100,30);
		date.setLocation(360,240);
		this.add(date);
		date.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				date.showPanel(date.showDate);
				mainPanel.repaint();
			}
		});



		final JButton confirm = new JButton(new ImageIcon("GUI/iniconfirm.png"));
		confirm.setLocation(310, 380);
		confirm.setSize(200,50);
		confirm.setFocusPainted(false);
		confirm.setBorderPainted(false);
		confirm.setOpaque(true);
		confirm.setContentAreaFilled(false);
		this.add(confirm);
		confirm.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				confirm.setIcon(new ImageIcon("GUI/iniconfirm2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				mainPanel.removeAll();

				if(iniblservice.search(date.showDate.getText())!=null){
					showSearchResult(iniblservice.search(date.showDate.getText()));
				}

				else {
					new InfoWindows("该时间没有进行建账");
					search();
				}

				mainPanel.repaint();
			}
			public void mouseReleased(MouseEvent e){
				confirm.setIcon(new ImageIcon("GUI/iniconfirm.png"));
			}


		}
		);

		this.repaint();

	}


	void showSearchResult(InitialVO inivo){
		this.removeAll();

		final JLabel datetext = new JLabel("日期："+String.valueOf(inivo.getYear()));
		datetext.setSize(250,30);
		datetext.setLocation(330,80);
		datetext.setFont(new Font("黑体", Font.BOLD, 24));
		datetext.setForeground(Color.white);
		this.add(datetext);

		vo = inivo;

		final JButton accButton = new JButton(new ImageIcon("GUI/showAccount.png"));
		accButton.setLocation(330, 180);
		accButton.setSize(200,50);
		accButton.setFocusPainted(false);
		accButton.setBorderPainted(false);
		accButton.setOpaque(true);
		accButton.setContentAreaFilled(false);
		this.add(accButton);
		accButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				accButton.setIcon(new ImageIcon("GUI/showAccount2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showAccount();
			}
			public void mouseReleased(MouseEvent e){
				accButton.setIcon(new ImageIcon("GUI/showAccount.png"));
			}


		}
		);

		final JButton cusButton = new JButton(new ImageIcon("GUI/showCustomer.png"));
		cusButton.setLocation(330, 280);
		cusButton.setSize(200,50);
		cusButton.setFocusPainted(false);
		cusButton.setBorderPainted(false);
		cusButton.setOpaque(true);
		cusButton.setContentAreaFilled(false);
		this.add(cusButton);
		cusButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				cusButton.setIcon(new ImageIcon("GUI/showCustomer2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showCustomer();
			}
			public void mouseReleased(MouseEvent e){
				cusButton.setIcon(new ImageIcon("GUI/showCustomer.png"));
			}


		}
		);

		//戚航
		final JButton comButton = new JButton(new ImageIcon("GUI/showCommodity.png"));
		comButton.setLocation(330, 380);
		comButton.setSize(200,50);
		comButton.setFocusPainted(false);
		comButton.setBorderPainted(false);
		comButton.setOpaque(true);
		comButton.setContentAreaFilled(false);
		this.add(comButton);
		comButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				comButton.setIcon(new ImageIcon("GUI/showCommodity2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				showCommodity();
			}
			public void mouseReleased(MouseEvent e){
				comButton.setIcon(new ImageIcon("GUI/showCommodity.png"));
			}


		}
		);

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		this.add(backButton);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				search();
			}
		});
	}

	private void showAccount(){
		mainPanel.removeAll();

		JTextArea showArea = new JTextArea();
		showArea.setSize(200, 400);
		showArea.setLocation(350, 100);
		showArea.append("账户名"+"\t"+"金额"+"\n");
		for(AccountVO accvo:vo.getAccountList()){
			showArea.append(accvo.getName()+"\t"+accvo.getMoney()+"\n");
		}
		showArea.setFont(new Font("黑体", Font.BOLD,20));
		showArea.setForeground(Color.white);
		showArea.setOpaque(false);
		showArea.setEditable(false);
		showArea.setVisible(true);


		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				showSearchResult(vo);
				mainPanel.repaint();
			}
		});

		mainPanel.add(backButton);
		mainPanel.add(showArea);
		mainPanel.repaint();
	}

	private void showCustomer(){
		mainPanel.removeAll();

		JTextArea showArea = new JTextArea();
		showArea.setSize(650, 400);
		showArea.setLocation(150, 100);
		showArea.append("ID"+"\t"+"类型"+"\t"+"等级"+"\t"+"姓名"+"\t"+"电话"+"\t"+"邮箱"+"\t"+"应收"+"\t"+"应付"+"\n");
		for(CustomerVO cusvo:vo.getCustomerList()){
			showArea.append(cusvo.getID()+"\t"+cusvo.getType()+"\t"+cusvo.getRank()+"\t"+cusvo.getName()+"\t"+cusvo.getPhone()+"\t"+cusvo.getEmail()+"\t"+cusvo.getReceiving()+"\t"+cusvo.getPayment()+"\n");
		}
		showArea.setFont(new Font("黑体", Font.PLAIN,15));
		showArea.setForeground(Color.white);
		showArea.setOpaque(false);
		showArea.setEditable(false);
		showArea.setVisible(true);


		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				showSearchResult(vo);
				mainPanel.repaint();
			}
		});

		mainPanel.add(backButton);
		mainPanel.add(showArea);
		mainPanel.repaint();

	}

	private void showCommodity(){
		mainPanel.removeAll();

		JTextArea showArea = new JTextArea();
		showArea.setSize(650, 400);
		showArea.setLocation(200, 100);
		showArea.append("商品分类"+"\t"+"名称"+"\t\t"+"型号"+"\t"+"进价"+"\t"+"售价"+"\n");
		System.out.println(vo.getcommodityList().size());

		for(CommodityPO comvo:vo.getcommodityList()){
			showArea.append(comvo.getCommodityType()+"\t"+comvo.getName()+"\t"+comvo.getType()+"\t"+comvo.getImpPrice()+"\t"+comvo.getExpPrice()+"\n");
		}
		showArea.setFont(new Font("黑体", Font.PLAIN,15));
		showArea.setForeground(Color.white);
		showArea.setOpaque(false);
		showArea.setEditable(false);
		showArea.setVisible(true);


		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				showSearchResult(vo);
				mainPanel.repaint();
			}
		});

		mainPanel.add(backButton);
		mainPanel.add(showArea);
		mainPanel.repaint();
	}

	void initial(){
		this.removeAll();
		final SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		final JLabel datetext = new JLabel("日期："+df.format(new Date()));
		datetext.setSize(250,30);
		datetext.setLocation(330,80);
		datetext.setFont(new Font("黑体", Font.BOLD, 24));
		datetext.setForeground(Color.white);
		this.add(datetext);


		final JButton accButton = new JButton(new ImageIcon("GUI/addAccount.png"));
		accButton.setLocation(330, 180);
		accButton.setSize(200,50);
		accButton.setFocusPainted(false);
		accButton.setBorderPainted(false);
		accButton.setOpaque(true);
		accButton.setContentAreaFilled(false);
		this.add(accButton);
		accButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				accButton.setIcon(new ImageIcon("GUI/addAccount2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				addAccount();
				account =new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							while(true){
								Thread.sleep(10000);
								addAccount();
								}

							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}

					}
				}
					);
				account.start();
			}
			public void mouseReleased(MouseEvent e){
				accButton.setIcon(new ImageIcon("GUI/addAccount.png"));
			}


		}
		);

		final JButton cusButton = new JButton(new ImageIcon("GUI/addCustomer.png"));
		cusButton.setLocation(330, 280);
		cusButton.setSize(200,50);
		cusButton.setFocusPainted(false);
		cusButton.setBorderPainted(false);
		cusButton.setOpaque(true);
		cusButton.setContentAreaFilled(false);
		this.add(cusButton);
		cusButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				cusButton.setIcon(new ImageIcon("GUI/addCustomer2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				addCustomer();
				customer =new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							while(true){
								Thread.sleep(300000);
								addCustomer();
								}

							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}

					}
				}
					);
				customer.start();

			}
			public void mouseReleased(MouseEvent e){
				cusButton.setIcon(new ImageIcon("GUI/addCustomer.png"));
			}


		}
		);

		final JButton comButton = new JButton(new ImageIcon("GUI/addCommodity.png"));
		comButton.setLocation(330, 380);
		comButton.setSize(200,50);
		comButton.setFocusPainted(false);
		comButton.setBorderPainted(false);
		comButton.setOpaque(true);
		comButton.setContentAreaFilled(false);
		this.add(comButton);
		comButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				comButton.setIcon(new ImageIcon("GUI/addCommodity2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				addCommodity();
				commodity = new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							while(true){
								Thread.sleep(300000);
								addCommodity();
								}

							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}

					}
				}
					);
				commodity.start();

			}
			public void mouseReleased(MouseEvent e){
				comButton.setIcon(new ImageIcon("GUI/addCommodity.png"));
			}


		}
		);

		final JButton confirm = new JButton(new ImageIcon("GUI/iniconfirm.png"));
		confirm.setLocation(330, 480);
		confirm.setSize(200,50);
		confirm.setFocusPainted(false);
		confirm.setBorderPainted(false);
		confirm.setOpaque(true);
		confirm.setContentAreaFilled(false);
		this.add(confirm);
		confirm.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent   e){
				// TODO 自动生成的方法存根
				confirm.setIcon(new ImageIcon("GUI/iniconfirm2.png"));

			}
			public void mouseClicked(MouseEvent   e){
				// TODO 自动生成的方法存根
				try {
					iniblservice.createInitial(new InitialVO(df.format(new Date()), com.showCom(), cus.showCustomer(), acc.showAccount()));
				} catch (NumberFormatException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}

				mainPanel.removeAll();
				new InfoWindows("建账成功");

				start();
			}
			public void mouseReleased(MouseEvent e){
				confirm.setIcon(new ImageIcon("GUI/iniconfirm.png"));
			}


		}
		);

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				start();
			}
		});

		mainPanel.add(confirm);
		mainPanel.add(backButton);


		mainPanel.repaint();
	}

	private void addAccount(){
		mainPanel.removeAll();

		AccountShow accountShow = new AccountShow(user);

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));

		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				account.stop();
				initial();
			}
		});
		ArrayList<AccountVO> list =accountShow.showOfFrame();
		accountShow.account(0,list);


		backButton.setVisible(true);
		mainPanel.add(backButton);
		accountShow.repaint();
		accountShow.setVisible(true);
		mainPanel.add(accountShow);
		mainPanel.repaint();
	}

	private void addCustomer(){
		mainPanel.removeAll();
		CustomerShow cpanel=new CustomerShow();

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				customer.stop();
				initial();
			}
		});

		mainPanel.add(backButton);
		cpanel.setLocation(20,30);
		cpanel.repaint();
		mainPanel.add(cpanel);
		mainPanel.repaint();
	}

	private void addCommodity(){
		mainPanel.removeAll();
		CommodityPanel comp = null;
		try {
			comp = new CommodityPanel();
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		JButton backButton = new JButton( new ImageIcon("GUI/backButton.png"));
		backButton.setSize(40,40);
		backButton.setLocation(805,555);
		backButton.setFont(new Font("黑体", Font.BOLD,8));
		backButton.setVisible(true);
		backButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				mainPanel.removeAll();
				commodity.stop();
				initial();
			}
		});

		mainPanel.add(backButton);

		comp.repaint();
		mainPanel.add(comp);
		comp.setVisible(true);
		mainPanel.repaint();
	}

}
