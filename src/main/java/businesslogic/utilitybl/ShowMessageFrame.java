package businesslogic.utilitybl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ShowMessageFrame extends javax.swing.JFrame {
    private JLabel text;
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    Dimension screen=toolkit.getScreenSize();
    int LocationX,LocationY;
    private String str = null;
    Color background = new Color(0,0,0,0.0f);
    JPanel pngPanel ;
	
    public ShowMessageFrame(String str) {
    	 this.setSize(400,75);
    	//下面为居中
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
        this.setLayout(null);
        this.setUndecorated(true);
        this.setBackground(background);
        pngPanel = new JPanel() { 
            @Override  
            protected void paintComponent(Graphics g) {  
            	ImageIcon icon = new ImageIcon("GUI/messageFrame.png");
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
            }
        };
        pngPanel.setSize(400,75);
        pngPanel.setLocation(0, 0);
        pngPanel.setLayout(null);
        this.add(pngPanel);
        this.str = str;
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        {
            text = new JLabel("<html>"  +  str + "</html>", JLabel.CENTER);
            
            text.setLocation(80, 23);
            text.setSize(200,20);
            pngPanel.add(text);
            text.setFont(new Font("黑体", Font.BOLD, 15));
            text.setBackground(new java.awt.Color(0,0,0,0.4f));
            
        }
        setVisible(true);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();
            }
        }).start();
    }

    private void initGUI() {
        try {
//            setUndecorated(true);
//            setLocationRelativeTo(null);
        	
           
//            setBounds(width / 2 - 180, height - 150, 360, 100);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
    	new ShowMessageFrame("输入名称已存在");
    	
    }



}