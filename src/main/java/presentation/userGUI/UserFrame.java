package presentation.userGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import presentation.commonGUI.mainFrame;
import presentation.loginGUI.LoginFrame;

import VO.UserVO;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import businesslogic.userbl.UserController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.userBLService.UserBLService;

import javax.swing.JPanel;

import java.awt.FlowLayout;

public class UserFrame extends mainFrame{
	UserVO uvo;
	UserPanel panel;
	
	public UserFrame(UserVO uvo){
		this.uvo=uvo;
		panel=new UserPanel(uvo);
		JButton userButton= new JButton(" 用户管理", new ImageIcon("GUI/commodity.png"));
		userButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		userButton.setSize(200,50);
		userButton.setLocation(0,120);
		userButton.setForeground(Color.white);
		userButton.setBackground(new Color(41,48,62));
		userButton.setFocusPainted(false);
		userButton.setBorderPainted(false);
		userButton.setContentAreaFilled(false);
		userButton.addActionListener(new UserListener());
		functionPanel.add(userButton);
		functionPanel.add(userButton);
		functionPanel.setVisible(true);
		functionPanel.repaint();
		userButton.addActionListener(new UserListener());
		
		JButton exchangeButton= new JButton(" 切换用户",new ImageIcon("GUI/exchange.png"));
		exchangeButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		exchangeButton.setSize(200,50);
		exchangeButton.setLocation(0,200);
		exchangeButton.setForeground(Color.white);
		exchangeButton.setBackground(new Color(41,48,62));
		exchangeButton.setFocusPainted(false);
		exchangeButton.setBorderPainted(false);
		exchangeButton.setContentAreaFilled(false);
		exchangeButton.addActionListener(new exchangeButtonListener());
		functionPanel.add(exchangeButton);
		
		
		setVisible(true);
		repaint();
		
	}
	
	class exchangeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new LoginFrame("1");
			
			
		}
		
	}
	
	class UserListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			containPanel.add(panel);
			panel.setLocation(20,130);
			panel.repaint();
			
		}
		
	}
	public static void main(String [] args){
		new UserFrame(new UserVO("admin","管理员","马昕","123456","普通权限"));
	}
		
}
