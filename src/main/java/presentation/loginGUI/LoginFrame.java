package presentation.loginGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;





















import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import presentation.commonGUI.mainFrame;
import presentation.financeGUI.FinanceFrame;
import presentation.promotionGUI.PromotionFrame;
import presentation.salesGUI.SalesFrame;
import presentation.stockGUI.commodity.StockFrame;
import presentation.userGUI.UserFrame;
import PO.CommodityPO;
import VO.UserVO;
import businesslogic.userbl.UserController;
import businesslogic.utilitybl.Datafactory;
import businesslogicservice.userBLService.UserBLService;


public class LoginFrame extends JFrame {
	LoginData loginData ;
	UserBLService ubs;
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	private int frameWidth = 450;
	private int frameHeight = 350;
	int LocationX;
	int LocationY;
	int xOld,yOld;
	Dimension screen=toolkit.getScreenSize();
	JPanel decoratePanel ;
	JButton loginButton,exitButton,vanishButton,setButton,confirmButton;
	Color backGround = new Color(243,243,243);
	Color bg2 = new Color(245,245,245);
	Color defaultColor = new Color(180,180,180);
	Color gray = new Color(41,48,62);
	JTextField idText,passwordHint;
	JPasswordField passwordText;
	String id="",password="";
	JCheckBox rememberPassword,autoLogin;
	LoginFrame frame=this;
	// ==========================IP
	JLabel ipLabel,portLabel;
	String ip = "",port="";
	JTextField ipText,portText;
	public LoginFrame(String s){
		this.setTitle("进销存登陆");
		try {
			File fvck = new File("ClientData/LoginData.ser");
			if(fvck.exists()){
	        ObjectInputStream in;
			in = new ObjectInputStream(
					new FileInputStream("ClientData/LoginData.ser"));
			loginData = (LoginData) in.readObject();  
	        in.close();
	        
			}else{
	        	loginData=new LoginData();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		ipLabel = new JLabel("IP :");
		ipLabel.setSize(30,30);
		ipLabel.setLocation(125,220);
		ipLabel.setVisible(false);
		this.add(ipLabel);
		portLabel = new JLabel("端口:");
		portLabel.setSize(30,30);
		portLabel.setLocation(125,250);
		portLabel.setVisible(false);
		this.add(portLabel);
		rememberPassword = new JCheckBox(" 记住密码");
		rememberPassword.setSize(100,50);
		rememberPassword.setLocation(122,259);
		rememberPassword.setFont(new Font("幼圆", Font.PLAIN, 12));
		rememberPassword.setBackground(backGround);
		rememberPassword.setBorderPainted(false);
		rememberPassword.setContentAreaFilled(false);
		//rememberPassword.addActionListener();
		rememberPassword.setSelected(loginData.isRememberPassword());

		this.add(rememberPassword);
		autoLogin = new JCheckBox(" 自动登录");
		autoLogin.setSize(100,50);
		autoLogin.setLocation(248,259);
		autoLogin.setFont(new Font("幼圆", Font.PLAIN, 12));
		autoLogin.setBackground(backGround);
		autoLogin.setBorderPainted(false);
		autoLogin.setContentAreaFilled(false);
		autoLogin.setSelected(loginData.isAutoLogin);

		this.add(autoLogin);
		idText = new JTextField(" 登陆账号");
		idText.setBackground(bg2);
//		idText.setForeground(defaultColor);
		idText.setSize(200,30);
		idText.setLocation(125,210);
		idText.getDocument().addDocumentListener(new idTextListener());
		idText.addFocusListener(new idAdapter());
		if(!loginData.getLoginName().equals(""))
			idText.setText(loginData.getLoginName());
		this.add(idText);
		
		
		ipText = new JTextField("");
		ipText.setBackground(bg2);
		ipText.setSize(160,30);
		ipText.setLocation(165,220);
		ipText.getDocument().addDocumentListener(new ipTextListener());
		if(!loginData.IP.equals(""))
			ipText.setText(loginData.IP);
		ipText.setVisible(false);
		this.add(ipText);
		portText = new JTextField("");
		portText.setBackground(bg2);
		portText.setSize(160,30);
		portText.setLocation(165,250);
		portText.getDocument().addDocumentListener(new portTextListener());
		if(!loginData.port.equals(""))
			portText.setText(loginData.port);
		portText.setVisible(false);
		this.add(portText);
		passwordHint = new JTextField("密码");
		passwordHint.setForeground(defaultColor);
		passwordHint.setEditable(false);
		passwordHint.setOpaque(true);
		passwordHint.setSize(30,26);
		passwordHint.setBorder(null);
		passwordHint.setBackground(bg2);
		passwordHint.setLocation(131,242);
		this.add(passwordHint);
		passwordText = new JPasswordField("");
		passwordText.setBackground(bg2);
		passwordText.setForeground(gray);
		passwordText.setSize(200,30);
		passwordText.setLocation(125,240);
		passwordText.getDocument().addDocumentListener(new passwordTextListener());
		passwordText.addFocusListener(new passwordAdapter());
		if(loginData.isRememberPassword()){
			passwordText.setText(loginData.password);
			passwordHint.setVisible(false);
			}
		this.add(passwordText);
		loginButton = new JButton("登录");
		loginButton.setSize(200,34);
		loginButton.setLocation(125,300);
		loginButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		loginButton.setBackground(new Color(41,48,62));
		loginButton.setForeground(Color.white);
		loginButton.setFocusPainted(false);
		loginButton.setBorderPainted(false);
		loginButton.addActionListener(new LoginListener());
//		loginButton.setContentAreaFilled(false);
		this.add(loginButton);
		confirmButton = new JButton("确认");
		confirmButton.setSize(200,34);
		confirmButton.setLocation(125,300);
		confirmButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		confirmButton.setBackground(new Color(41,48,62));
		confirmButton.setForeground(Color.white);
		confirmButton.setFocusPainted(false);
		confirmButton.setBorderPainted(false);
		confirmButton.addActionListener(new confirmListener());
		confirmButton.setVisible(false);
		this.add(confirmButton);
		vanishButton = new JButton("",new ImageIcon("GUI/vanish-10.png"));
		vanishButton.setSize(12,12);
		vanishButton.setLocation(360,15);
		vanishButton.setFocusPainted(false);
		vanishButton.setBorderPainted(false);
		vanishButton.setContentAreaFilled(false);
		vanishButton.addActionListener(new vanishButtonListener());
		this.add(vanishButton);
		setButton = new JButton("",new ImageIcon("GUI/set-10.png"));
		setButton.setSize(12,12);
		setButton.setLocation(390,15);
		setButton.setFocusPainted(false);
		setButton.setBorderPainted(false);
		setButton.setContentAreaFilled(false);
		setButton.addActionListener(new setButtonListener());
		this.add(setButton);
		exitButton = new JButton("",new ImageIcon("GUI/exit-10.png"));
		exitButton.setSize(12,12);
		exitButton.setLocation(420,15);
		exitButton.setFocusPainted(false);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.addActionListener(new exitButtonListener());
		this.add(exitButton);
		
		//下面为居中
        this.setSize(frameWidth, frameHeight);
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
	    //居中完毕
        this.setLayout(null);
        this.setUndecorated(true);
        //设置图标
        try {
      			Image icon= ImageIO.read(new File("GUI/foxLogo50.png"));
      			this.setIconImage(icon);
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
        
        this.setVisible(true);
		decoratePanel = new JPanel() {  
            @Override  
            protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("GUI/loginBack3.jpg");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
                
                // 细致渲染、绘制背景，可控制截取图片，显示于指定的JPanel位置  
              g.drawImage(img, 0, 0, 0, 0,   
                          0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
            }  
        }; 
        decoratePanel.setLayout(null);
        decoratePanel.setSize(450,200);
        decoratePanel.setVisible(true);
        this.add(decoratePanel);
        Datafactory.setIP(ipText.getText());
        Datafactory.setPort(portText.getText());
        if(loginData.isAutoLogin)
			if(!s.equals("1")){
				login(id,password);
			}
		//can drag
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			xOld = e.getX();
			yOld = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
			int xOnScreen = e.getXOnScreen();
			int yOnScreen = e.getYOnScreen();
			int xx = xOnScreen - xOld;
			int yy = yOnScreen - yOld;
			LoginFrame.this.setLocation(xx, yy);
		}
		});
	}
	public void login(String id,String password){
		Datafactory.setIP(ipText.getText());
        Datafactory.setPort(portText.getText());
		ubs=new UserController();
		UserVO uvo=ubs.login(id , password);
		if(uvo==null){
			passwordText.setText("");
			repaint();
			return ;
		}
		
		if(uvo.getType().equals("销售经理")||uvo.getType().equals("销售员")){
			new SalesFrame(uvo);
			frame.setVisible(false);
		}else if(uvo.getType().equals("库存管理人员")){
			try {
				new StockFrame(uvo);
				frame.setVisible(false);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(uvo.getType().equals("总经理")){
			new PromotionFrame(uvo);
			frame.setVisible(false);
		}else if(uvo.getType().equals("财务人员")){
			new FinanceFrame(uvo);
			frame.setVisible(false);
		}else if(uvo.getType().equals("管理员")){
			new UserFrame(uvo);
			frame.setVisible(false);
		}
		saveLoginData();
	}
	class LoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Datafactory.setIP(ipText.getText());
	        Datafactory.setPort(portText.getText());
			ubs=new UserController();
			// TODO Auto-generated method stub
			if(id.equals("")||id.equals(" 登陆账号")){
				return ;
			} 
			if(password==""){
				return ;
			}
			
			ubs=new UserController();
			
			UserVO uvo=ubs.login(id, password);
			if(uvo==null){
				passwordText.setText("");
				repaint();
				return ;
			}
			
			
			
			if(uvo.getType().equals("销售经理")||uvo.getType().equals("销售员")){
				new SalesFrame(uvo);
				frame.setVisible(false);
			}else if(uvo.getType().equals("库存管理人员")){
				try {
					new StockFrame(uvo);
					frame.setVisible(false);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(uvo.getType().equals("总经理")){
				new PromotionFrame(uvo);
				frame.setVisible(false);
			}else if(uvo.getType().equals("财务人员")){
				new FinanceFrame(uvo);
				frame.setVisible(false);
			}else if(uvo.getType().equals("管理员")){
				new UserFrame(uvo);
				frame.setVisible(false);
			}
			saveLoginData();

		}
		
	}
	class setButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
//			JTextField idText,passwordHint;
//			JPasswordField passwordText;
//			String id="",password="";
//			JCheckBox rememberPassword,autoLogin;
			idText.setVisible(false);
			passwordHint.setVisible(false);
			passwordText.setVisible(false);
			rememberPassword.setVisible(false);
			autoLogin.setVisible(false);
			loginButton.setVisible(false);
			confirmButton.setVisible(true);
			ipText.setVisible(true);
			ipLabel.setVisible(true);
			portText.setVisible(true);
			portLabel.setVisible(true);
		}
		
	}
	class exitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			saveLoginData();
			System.exit(0);
		}

	}
	class vanishButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setExtendedState(JFrame.ICONIFIED);
			
		}
		
	}
	public class passwordAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (passwordText.getText().equals("")) {
				passwordHint.setVisible(false);
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (passwordText.getText().equals("")) {
        		passwordHint.setVisible(true);
            }
        }
	}

	public class idAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (idText.getText().equals(" 登陆账号")||idText.getText().equals("登陆账号")) {
				idText.setForeground(gray);
	            idText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (idText.getText().equals("")) {
        		idText.setForeground(defaultColor);
        		idText.setText(" 登陆账号");
            }
        }
	}
	public class passwordTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			password = passwordText.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			password = passwordText.getText().trim();
			
		}
	    
	}
	public class ipTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			ip = ipText.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			ip = ipText.getText().trim();
			
		}
	    
	}
	public class portTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			port = portText.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			port = portText.getText().trim();
			
		}
	    
	}
	public class idTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			id = idText.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			id = idText.getText().trim();
			
		}
	    
	}

	public class confirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			idText.setVisible(true);
			passwordHint.setVisible(true);
			passwordText.setVisible(true);
			rememberPassword.setVisible(true);
			autoLogin.setVisible(true);
			loginButton.setVisible(true);
			confirmButton.setVisible(false);
			ipText.setVisible(false);
			ipLabel.setVisible(false);
			portText.setVisible(false);
			portLabel.setVisible(false);
		}
		
	}
	public void saveLoginData(){
		//rememberPassword,autoLogin;
		loginData.setRememberPassword(rememberPassword.isSelected());
		loginData.setAutoLogin(autoLogin.isSelected());
		loginData.setLoginName(id);
		if(!port.equals(""))
		     loginData.setPort(port);
		if(!ip.equals(""))
		     loginData.setIP(ip);
		if(rememberPassword.isSelected())
			loginData.password = this.password;
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream("ClientData/LoginData.ser"));
			out.writeObject(loginData); 
	        out.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static void main(String[] args){
		System.out.println("!");
		new LoginFrame("qwerty");
	}

	
}