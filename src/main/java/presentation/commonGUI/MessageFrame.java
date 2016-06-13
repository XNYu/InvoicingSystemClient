package presentation.commonGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MessageFrame extends JFrame{
	Toolkit toolkit=Toolkit.getDefaultToolkit();
    Dimension screen=toolkit.getScreenSize();
	private int frameWidth = 350;
	private int frameHeight = 250;
	int LocationX;
	int LocationY;
	Color background = new Color(0,0,0,0.0f);
	Color transparent = new Color(0,0,0,0.3f);
	Color white = new Color(245,245,245);
	Color deepGray = new Color(26,32,45);
	Color gray = new Color(41,48,62);
	Color blue = new Color(16,133,253);
	JPanel messagePanel ;
	String message="";
	JTextField messageText;
	
	public MessageFrame(String message){
		//下面为居中
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
        
		messageText = new JTextField(message);
		messageText.setSize(350,100);
		messageText.setLocation(0, 70);
		messageText.setHorizontalAlignment(JTextField.CENTER);
		messageText.setForeground(Color.BLACK);
//		messageText.setBackground();
		messageText.setEditable(false);
		messageText.setBackground(white);
		messageText.setFont(new Font("华文琥珀", Font.BOLD, 29));
		this.add(messageText);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(frameWidth,frameHeight);
		this.setResizable(false);
		this.setTitle("系统提示");
		//下面为居中
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
	    //居中完毕
        this.setLayout(null);
//        this.setUndecorated(true);
//        this.setBackground(white);
        //设置图标
        try {
      			Image icon= ImageIO.read(new File("GUI/foxLogo50.png"));
      			this.setIconImage(icon);
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
        this.setVisible(true);
	}
	public static void main(String[] args){
//		new JOptionPane();
	}
}
