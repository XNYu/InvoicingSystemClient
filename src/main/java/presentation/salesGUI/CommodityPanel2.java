package presentation.salesGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import PO.CommodityPO;
import businesslogic.commoditybl.CommodityController;
import businesslogicservice.commodityBLService.CommodityBLService;






public class CommodityPanel2 extends JPanel implements  Serializable, ListSelectionListener,TreeModelListener,TableModelListener{
	
	private static final long serialVersionUID = 1L;
	CommodityBLService cbs = new CommodityController();
	JTree tree;
	DefaultTreeModel treeModel = null;
	String nodeName = null;//原有节点名称
	JTable comTable;
	JTable comTable2;
	TreeNode pressedTreeNode;
	CommodityModel2 comTableModel;
	CommodityModel2 comTableModel2;
	ListSelectionModel selectionModel = null;
	ListSelectionModel selectionModel2 = null;
	int[] selectedRows = null;
	int[] selectedRows2 = null;
	JScrollPane comScrollPane ,comScrollPane2,typeScrollPane ;
	String comName,comType,comImpStr,comExpStr,searchStr;
	double comImp,comExp;
	ArrayList<CommodityPO> commodityList;
	
	public CommodityPanel2(ArrayList<CommodityPO> commodityList) throws IOException, ClassNotFoundException{
		this.commodityList=commodityList;
		this.setSize(880, 350);
		this.setLocation(0, 0);
		if(this.commodityList==null){
			this.commodityList=new ArrayList<CommodityPO>();
		}

		//===============================initial Com Table================
		
			comTableModel = new CommodityModel2();
			comTableModel2 = new CommodityModel2();
			comTable = new JTable(comTableModel);
			comTable.getColumnModel().getColumn(0).setPreferredWidth(120);
			comTable.getModel().addTableModelListener(this);
			ArrayList<CommodityPO> poList=cbs.showCommodity();
			if(poList!=null){
				for(CommodityPO po:poList){
					Vector v=new Vector();
					v.add(po.getID());
					v.add(po.getName());
					v.add(po.getType());
					v.add(po.getAmount());
					v.add(po.getImpPrice());
					v.add("");
					comTableModel.addRow(v);
				}
			}
			comScrollPane = new JScrollPane(comTable);
			comTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
		 	selectionModel.addListSelectionListener(this);
		
		comScrollPane.setSize(320,250);
		comScrollPane.setLocation(135,30);
		comScrollPane.setVisible(true);
		
		
		comTable2 = new JTable(comTableModel2);
		comTable2.getColumnModel().getColumn(0).setPreferredWidth(120);
		comTable2.getModel().addTableModelListener(this);
		
		comScrollPane2 = new JScrollPane(comTable2);
		comTable2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectionModel2=comTable2.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel2.addListSelectionListener(this);
	 	
	 	comScrollPane2.setSize(320,250);
		comScrollPane2.setLocation(500,30);
		comScrollPane2.setVisible(true);
	 	
		//-------------------------initial Tree------------------------

		tree = cbs.showTree();
		tree.addMouseListener(new MouseHandle());
		tree.setEditable(true);
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(this);
		
		typeScrollPane = new JScrollPane(tree);
		typeScrollPane.setSize(100,250);
		typeScrollPane.setLocation(30,30);
		typeScrollPane.setVisible(true);
		
		
		JButton forward = new JButton(">");
		forward.setLocation(455, 131);
		forward.setSize(45,20);
		forward.setFocusPainted(false);
		forward.setBorderPainted(false);
		forward.setOpaque(false);
		forward.setContentAreaFilled(false);
		add(forward);
		forward.addActionListener(new ForwardListener());
		
		this.setLayout(null);
		this.add(typeScrollPane);
		this.add(comScrollPane);
		this.add(comScrollPane2);
		
		JButton delButton = new JButton("DEL");
		delButton.setBounds(631, 282, 93, 23);
		add(delButton);
		delButton.addActionListener(new DelListener());
		
		JButton addButton = new JButton("ADD");
		addButton.setBounds(727, 282, 93, 23);
		add(addButton);
		addButton.addActionListener(new AddListener());
		this.setVisible(false);
		updateComTable2();
	}

public void updateComTable2(){
		
		for(int i=0;i<comTable2.getRowCount();){
			comTableModel2.removeRow(0);
		}
		comTable2.revalidate();
		if(commodityList!=null){
			for(CommodityPO po:commodityList){
				Vector v=new Vector();
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getImpPrice());
				v.add(po.getLeaveDate());
				
				comTableModel2.addRow(v);
			}
		}
		comTable.revalidate();
		repaint();
	}
	
	class MouseHandle extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			try {
				JTree tree = (JTree) e.getSource();
				int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
				TreePath treepath = tree.getPathForRow(rowLocation);
				pressedTreeNode = (TreeNode) treepath.getLastPathComponent();
				nodeName = pressedTreeNode.toString();
				
			} catch (NullPointerException ne) {
			}
		}
	}
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		
	}
	public void treeNodesInserted(TreeModelEvent e) {
		//System.out.println("new node insered");
	}

	public void treeNodesRemoved(TreeModelEvent e) {
		//System.out.println("node deleted");
	}

	public void treeStructureChanged(TreeModelEvent e) {
		//System.out.println("Structrue changed");
	}

	public void tableChanged(TableModelEvent e) {
		// TODO 自动生成的方法存根
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO 自动生成的方法存根
		selectedRows=comTable.getSelectedRows();
		selectedRows2=comTable2.getSelectedRows();
	}
	
	class ForwardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(selectedRows==null){
				
				return;
			}
			for(int i=0;i<selectedRows.length;++i){
				boolean flag=true;
				for(int j=0;j<comTable2.getRowCount();++j){
					if(comTable.getValueAt(selectedRows[i], 0).equals(comTable2.getValueAt(j, 0))){
						
						flag=false;
					}
					
				}
				if(flag){
					comTableModel2.addRow((Vector)comTableModel.rows.get(selectedRows[i]));
					comTable2.revalidate();
				}
				//System.out.println(selectedRows[i]);
				
			}
			
		}
		
	}
	class AddListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			
			for(int i=0;i<comTable2.getRowCount();++i){
				boolean flag=true;
				CommodityPO po=new CommodityPO((String)comTable2.getValueAt(i, 1),(String)comTable2.getValueAt(i, 2),
						(Integer)comTable2.getValueAt(i, 3),(Double)comTable2.getValueAt(i, 4),0,null);
				po.setLeaveDate((String)comTable2.getValueAt(i, 5));
				po.setID((String)comTable2.getValueAt(i, 0));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
			    try{
			    	Date date=sdf.parse(po.getLeaveDate());
			    }catch(Exception e){
			    	flag=false;
			    	System.out.println(e);
			    	JDialog infoDialog=new JDialog();
					infoDialog.setSize(100,80);
					infoDialog.setLocationRelativeTo(null);
					infoDialog.setVisible(true);
					JLabel infoLabel=new JLabel("日期格式不合法");
					infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
					infoLabel.setVisible(true);
					infoDialog.getContentPane().add(infoLabel);
					return;
			    }
				
				if(po.getAmount()<=0){
					commodityList.clear();
					JDialog infoDialog=new JDialog();
					infoDialog.setSize(100,80);
					infoDialog.setLocationRelativeTo(null);
					infoDialog.setVisible(true);
					JLabel infoLabel=new JLabel("输入数量不可小于0");
					infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
					infoLabel.setVisible(true);
					infoDialog.getContentPane().add(infoLabel);
					return;
				}
				for(CommodityPO cpo:commodityList){
					
					if(po.getName().equals(cpo.getName())&&po.getType().equals(cpo.getType())){
						cpo.setAmount(po.getAmount());
						cpo.setImpPrice(po.getImpPrice());
						flag=false;
						break;
					}
				}
				if(flag){
					commodityList.add(po);
				}
			}
			
			
		}
		
	}
	
	class DelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
		    for(int i = 0 ; i < selectedRows2.length ;i++){
		    	comTableModel2.removeRow(selectedRows2[i]-i);
		    	if(commodityList.size()>selectedRows2[i]-i){
		    		commodityList.remove(selectedRows2[i]-i);
		    	}
		    }
		    comTable2.revalidate();
		}
		
	}
}
