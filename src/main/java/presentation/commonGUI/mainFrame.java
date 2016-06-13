package presentation.commonGUI;

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
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class mainFrame extends JFrame{
	Toolkit toolkit=Toolkit.getDefaultToolkit();
    Dimension screen=toolkit.getScreenSize();
	private int frameWidth = 1100;
	private int frameHeight = 600;
	int LocationX;
	int LocationY;
	protected JPanel functionPanel,containPanel,systemPanel,shadow;
	protected JButton logoButton,exitButton,vanishButton;
	int xOld,yOld;
	
	public mainFrame(){
		Color background = new Color(0,0,0,0.0f);
		Color white = new Color(245,245,245);
		Color gray = new Color(41,48,62);
		Color blue = new Color(16,133,253);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(frameWidth,frameHeight);
		this.setResizable(false);
		this.setTitle("QWERTY");
		//下面为居中
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
	    //居中完毕
        this.setLayout(null);
        this.setUndecorated(true);
        this.setBackground(background);
        //设置图标
        try {
      			Image icon= ImageIO.read(new File("GUI/foxLogo50.png"));
      			this.setIconImage(icon);
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
//        shadow = new JPanel();
//        shadow.setSize(200,5);
//        shadow.setLocation(0,590);
//        shadow.setBackground(transparent);
//        this.add(shadow);
        functionPanel = new JPanel();
        functionPanel.setSize(200, 580);
        functionPanel.setLocation(0,10);
        functionPanel.setLayout(null);
        functionPanel.setBackground(gray);
        
        
        containPanel = new JPanel() {  
            @Override  
            protected void paintComponent(Graphics g) {  
            	ImageIcon icon = new ImageIcon("GUI/background.png");
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
            }
        };
        containPanel.setSize(850, 600);
        containPanel.setLocation(200,0);
        containPanel.setLayout(null);
        containPanel.setBackground(white);
        
        
        systemPanel = new JPanel();
        systemPanel.setSize(50, 580);
        systemPanel.setLocation(1050,10);
        systemPanel.setLayout(null);
        systemPanel.setBackground(gray);
        

        //-----------------------FunctionPanel--------------------------
        logoButton = new JButton(
        		"", new ImageIcon("GUI/foxLogo50.png")
        		);
        logoButton.setFont(new Font("华文琥珀", Font.BOLD, 29));
		logoButton.setForeground(white);
		logoButton.setBackground(blue);
		logoButton.setSize(200,100);
        logoButton.setLocation(0, 0);
		logoButton.setFocusPainted(false);
		logoButton.setBorderPainted(false);
//		logoButton.setContentAreaFilled(false);
		functionPanel.add(logoButton);
		//-----------------------SystemPanel--------------------------
        exitButton = new JButton(
        		"", new ImageIcon("GUI/exit.png")
        		);
		exitButton.setForeground(gray);
		exitButton.setBackground(gray);
		exitButton.setSize(20,20);
		exitButton.setLocation(15, 20);
		exitButton.setBorderPainted(false);
		exitButton.setFocusPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.addActionListener(new exitButtonListener());
		systemPanel.add(exitButton);
		
		vanishButton = new JButton(
        		"", new ImageIcon("GUI/vanish.png")
        		);
		vanishButton.setForeground(gray);
		vanishButton.setBackground(gray);
		vanishButton.setSize(20,20);
		vanishButton.setLocation(15, 60);
		vanishButton.setBorderPainted(false);
		vanishButton.setFocusPainted(false);
		vanishButton.setContentAreaFilled(false);
		vanishButton.addActionListener(new vanishButtonListener());
		systemPanel.add(vanishButton);
		
		JTextArea jta=new JTextArea("版本号 1.0.0 Beta ");
		jta.setFont(new Font("华文细黑", Font.PLAIN, 13));
		jta.setSize(200,30);
		jta.setLocation(40,560);
		jta.setEditable(false);
		jta.setForeground(new Color(94,99,109));
		jta.setBackground(gray);
		functionPanel.add(jta);
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
			mainFrame.this.setLocation(xx, yy);
		}
		});


		this.add(containPanel);
		this.add(systemPanel);
		this.add(functionPanel);
		this.setVisible(true);
	}
	class exitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}
	class vanishButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setExtendedState(JFrame.ICONIFIED);
		}

	}
	
	public static void main(String[] args){
		mainFrame mF = new mainFrame();
	}
}
