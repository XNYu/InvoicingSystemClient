package presentation.initialGUI;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentation.financeGUI.InfoWindows;
import presentation.financeGUI.MyPanel;
import VO.AccountVO;
import VO.UserVO;
import businesslogic.accountbl.Account;
import businesslogic.accountbl.AccountInterfaceForInitial;
import businesslogic.utilitybl.CheckChinese;
import businesslogic.utilitybl.ResultMessage;

public class AccountShow extends MyPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	JPanel containPanel = this;
	JPanel addpanel;
	JButton exitButton;
	AccountInterfaceForInitial acc = null;

	AccountShow(UserVO user){
		super("GUI//background.png");

		acc =new Account(user);

		this.setLayout(null);
		this.setSize(805, 600);
		this.setLocation(0, 0);

	}


	void account(int num,ArrayList<AccountVO> list2){
		containPanel.removeAll();

		final ArrayList<AccountVO> list = list2;
		final int n = num;



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
			JPanel p = show(list.get(i).getName(),list.get(i).getMoney(),i);
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

	JPanel show (String name , double money,final int num){


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







		p.repaint();
		return p;
	}



	ArrayList<AccountVO> showOfFrame(){
		ArrayList<AccountVO> list = acc.showAccount();

		return list;
	}
}
