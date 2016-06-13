package presentation.salesGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import businesslogic.customerbl.CustomerController;
import businesslogicservice.customerBLService.CustomerBLService;
import VO.*;
import presentation.commonGUI.mainFrame;
import presentation.customerGUI.CustomerPanel;
import presentation.loginGUI.LoginFrame;



public class SalesFrame extends mainFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SalesFrame frame=this;
	CustomerPanel cpanel;
	JTextArea searchArea;
	String searchStr;
	JButton backSearchButton,searchButton;
	CustomerBLService cbs=new CustomerController();
	UserVO uvo;
	public static void main(String [] args){
		new SalesFrame(new UserVO("123","销售员","马昕","123","最高权限"));
	}
	
	public SalesFrame(UserVO uvo){
		this.uvo=uvo;
		JButton customerButton;
		customerButton= new JButton("客户管理", new ImageIcon("GUI/commodity.png"));
		customerButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		customerButton.setSize(200,50);
		customerButton.setLocation(0,120);
		customerButton.setForeground(Color.white);
		customerButton.setBackground(new Color(41,48,62));
		customerButton.setFocusPainted(false);
		customerButton.setBorderPainted(false);
		customerButton.setContentAreaFilled(false);
		customerButton.addActionListener(new CustomerButtonListener());
		functionPanel.add(customerButton);
		
		JButton exchangeButton= new JButton(" 切换用户",new ImageIcon("GUI/exchange.png"));
		exchangeButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		exchangeButton.setSize(200,50);
		exchangeButton.setLocation(0,320);
		exchangeButton.setForeground(Color.white);
		exchangeButton.setBackground(new Color(41,48,62));
		exchangeButton.setFocusPainted(false);
		exchangeButton.setBorderPainted(false);
		exchangeButton.setContentAreaFilled(false);
		exchangeButton.addActionListener(new exchangeButtonListener());
		functionPanel.add(exchangeButton);
		
		JButton salesButton;
		salesButton= new JButton("销售管理", new ImageIcon("GUI/document.png"));
		salesButton.setFont(new Font("华文细黑", Font.PLAIN, 18));
		salesButton.setSize(200,50);
		salesButton.setLocation(0,220);
		salesButton.setForeground(Color.white);
		salesButton.setBackground(new Color(41,48,62));
		salesButton.setFocusPainted(false);
		salesButton.setBorderPainted(false);
		salesButton.setContentAreaFilled(false);
		salesButton.addActionListener(new SalesButtonListener());
		functionPanel.add(salesButton);
		containPanel.setLayout(null);
		
		
		
		
		
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
	
	public class searchAdapter implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (searchArea.getText().equals("search:")) {
				searchArea.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (searchArea.getText().equals("")) {
        		searchArea.setText("search:");
            }
        }
		
	}
	
	public class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(searchStr!=""){
			ArrayList<CustomerVO> voList=cbs.vagueFind(searchStr);
				searchButton.setVisible(false);
				backSearchButton.setVisible(true);
				repaint();
				while(cpanel.table.getRowCount()!=0){
					cpanel.tablemodel.removeRow(0);
				}
				if(voList!=null){
					for(CustomerVO vo:voList){
						Vector v=new Vector();
						v.add(vo.getID());
						v.add(vo.getType());
						v.add(String.valueOf(vo.getRank()));
						v.add(vo.getName());
						v.add(vo.getPhone());
						v.add(vo.getAddress());
						v.add(vo.getPostcode());
						v.add(vo.getEmail());
						v.add(String.valueOf(vo.getQuota()));
						v.add(String.valueOf(vo.getPayment()));
						v.add(String.valueOf(vo.getReceiving()));
						v.add(vo.getSalesman());
						cpanel.tablemodel.addRow(v);
					}
				}
				cpanel.table.revalidate();
				setVisible(true);
				repaint();
			}
			
		}
		
	}
	public class backSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			searchStr = searchArea.getText();
			searchButton.setVisible(true);
			backSearchButton.setVisible(false);
			repaint();
			ArrayList<CustomerVO> voList=cbs.showCustomer();
			while(cpanel.table.getRowCount()!=0){
				cpanel.tablemodel.removeRow(0);
			}
			if(voList!=null){
				for(CustomerVO vo:voList){
					Vector v=new Vector();
					v.add(vo.getID());
					v.add(vo.getType());
					v.add(String.valueOf(vo.getRank()));
					v.add(vo.getName());
					v.add(vo.getPhone());
					v.add(vo.getAddress());
					v.add(vo.getPostcode());
					v.add(vo.getEmail());
					v.add(String.valueOf(vo.getQuota()));
					v.add(String.valueOf(vo.getPayment()));
					v.add(String.valueOf(vo.getReceiving()));
					v.add(vo.getSalesman());
					cpanel.tablemodel.addRow(v);
				}
			}
		}
		
	}
	
	
	public class searchTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}
	    
	}
	
	class CustomerButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			cpanel=new CustomerPanel(uvo);
			containPanel.add(cpanel);
			cpanel.setLocation(20,130);
			cpanel.repaint();
			
			searchArea = new JTextArea("search:");
			searchArea.setSize(200,20);
			searchArea.setLocation(610,40);
			searchArea.getDocument().addDocumentListener(new searchTextListener());
			searchArea.addFocusListener(new searchAdapter());
			searchArea.setVisible(false);
			containPanel.add(searchArea);
			backSearchButton= new JButton("BACK");
			backSearchButton.setSize(200,20);
			backSearchButton.setLocation(610, 61);
			backSearchButton.addActionListener(new backSearchListener());
			backSearchButton.setVisible(false);
			containPanel.add(backSearchButton);
			searchButton= new JButton("GO");
			searchButton.setSize(200,20);
			searchButton.setLocation(610, 61);
			searchButton.addActionListener(new searchListener());
			searchButton.setVisible(false);
			containPanel.add(searchButton);
			
			
			searchButton.setVisible(true);
			backSearchButton.setVisible(false);
			searchArea.setVisible(true);
			containPanel.repaint();
			
		}
		
	}
	
	class SalesButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			containPanel.removeAll();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(20, 90, 820, 470);
			
			SalesPanel spanel=new SalesPanel(uvo);
			SalesReturnPanel srpanel=new SalesReturnPanel(uvo);
			ImportPanel ipanel=new ImportPanel(uvo);
			ImportReturnPanel irpanel=new ImportReturnPanel(uvo);
			tabbedPane.add("销售单", spanel);
			tabbedPane.add("销售退货单", srpanel);
			tabbedPane.add("进货单", ipanel);
			tabbedPane.add("进货退货单", irpanel);
			
			containPanel.add(tabbedPane);
	
			containPanel.paintComponents(containPanel.getGraphics());
			containPanel.repaint();
		}
		
	}
}
