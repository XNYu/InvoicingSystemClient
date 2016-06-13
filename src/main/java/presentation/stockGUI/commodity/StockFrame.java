package presentation.stockGUI.commodity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.*;

import VO.UserVO;
import presentation.commonGUI.mainFrame;
import presentation.loginGUI.LoginFrame;
import presentation.stockGUI.document.DocumentPanel;


public class StockFrame extends mainFrame implements Serializable{
	private static final long serialVersionUID = 1L;
	JButton comButton,stoButton,docButton,exchangeButton,stoCheckButton;
	
	CommodityPanel comPanel ;
	StockPanel stoPanel ;
	StockCheckPanel stoCheckPanel;
	DocumentPanel docPanel ;
	UserVO uvo;

	public StockFrame(UserVO uvo) throws IOException, ClassNotFoundException{
		super();
		this.uvo=uvo;
		exitButton.addActionListener(new exitButtonListener());
		exitButton.setToolTipText(" exit ");
		
		comButton= new JButton(" 商品管理", new ImageIcon("GUI/commodity.png"));
		comButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		comButton.setSize(200,50);
		comButton.setLocation(0,120);
		comButton.setForeground(Color.white);
		comButton.setBackground(new Color(41,48,62));
		comButton.setFocusPainted(false);
		comButton.setBorderPainted(false);
		comButton.setContentAreaFilled(false);
		comButton.addActionListener(new ComButtonListener());
		functionPanel.add(comButton);
		
		stoButton= new JButton(" 库存查看",new ImageIcon("GUI/stock.png"));
		stoButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		stoButton.setSize(200,50);
		stoButton.setLocation(0,200);
		stoButton.setForeground(Color.white);
		stoButton.setBackground(new Color(41,48,62));
		stoButton.addActionListener(new StoButtonListener());
		stoButton.setFocusPainted(false);
		stoButton.setBorderPainted(false);
		stoButton.setContentAreaFilled(false);
		functionPanel.add(stoButton);
		
		stoCheckButton= new JButton(" 库存盘点",new ImageIcon("GUI/stockCheck.png"));
		stoCheckButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		stoCheckButton.setSize(200,50);
		stoCheckButton.setLocation(0,280);
		stoCheckButton.setForeground(Color.white);
		stoCheckButton.setBackground(new Color(41,48,62));
		stoCheckButton.addActionListener(new StoCheckButtonListener());
		stoCheckButton.setFocusPainted(false);
		stoCheckButton.setBorderPainted(false);
		stoCheckButton.setContentAreaFilled(false);
		functionPanel.add(stoCheckButton);
		
		docButton= new JButton(" 单据管理",new ImageIcon("GUI/document.png"));
		docButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		docButton.setSize(200,50);
		docButton.setLocation(0,360);
		docButton.setForeground(Color.white);
		docButton.setBackground(new Color(41,48,62));
		docButton.addActionListener(new DocumentButtonListener());
		docButton.setFocusPainted(false);
		docButton.setBorderPainted(false);
		docButton.setContentAreaFilled(false);
		functionPanel.add(docButton);
		
		exchangeButton= new JButton(" 切换用户",new ImageIcon("GUI/exchange.png"));
		exchangeButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		exchangeButton.setSize(200,50);
		exchangeButton.setLocation(0,440);
		exchangeButton.setForeground(Color.white);
		exchangeButton.setBackground(new Color(41,48,62));
		exchangeButton.setFocusPainted(false);
		exchangeButton.setBorderPainted(false);
		exchangeButton.setContentAreaFilled(false);
		exchangeButton.addActionListener(new exchangeButtonListener());
		functionPanel.add(exchangeButton);
		
		comPanel = new CommodityPanel(uvo);
		stoPanel = new StockPanel(uvo);
		stoCheckPanel = new StockCheckPanel(uvo);
		docPanel = new DocumentPanel(uvo);
		docPanel.setVisible(false);
		stoCheckPanel.setVisible(false);
		comPanel.setVisible(false);
		stoPanel.setVisible(false);
		containPanel.add(docPanel);
		containPanel.add(stoCheckPanel);
		containPanel.add(comPanel);
		containPanel.add(stoPanel);
		this.repaint();
	}
	class exchangeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new LoginFrame("1");
			
			
		}
		
	}
	class exitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			comPanel.finish();
			System.exit(0);
		}
	}
	public class ComButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			docPanel.setVisible(false);
			stoCheckPanel.setVisible(false);
			stoPanel.setVisible(false);
			comPanel.setVisible(true);
			
		}
		
	}
	public class StoButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			docPanel.setVisible(false);
			stoCheckPanel.setVisible(false);
			comPanel.setVisible(false);
			stoPanel.setVisible(true);
		}
		
	}
	public class StoCheckButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			docPanel.setVisible(false);
			comPanel.setVisible(false);
			stoPanel.setVisible(false);
			stoCheckPanel.setVisible(true);
		}
		
	}
	public class DocumentButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			comPanel.setVisible(false);
			stoPanel.setVisible(false);
			stoCheckPanel.setVisible(false);
			docPanel.setVisible(true);
		}
		
	}
	public static void main(String[] args) {
		try {
			StockFrame s = new StockFrame(new UserVO("asd","asd","asd","asd","asd"));
			
		} catch (ClassNotFoundException | IOException e) {
			
			e.printStackTrace();
		}
	}

}
