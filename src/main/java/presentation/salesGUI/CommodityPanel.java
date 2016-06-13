package presentation.salesGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import presentation.promotionGUI.ComTableModel;
import presentation.promotionGUI.discountModel;
import presentation.promotionGUI.giftModel;
import presentation.promotionGUI.packModel;
import presentation.promotionGUI.utility;
import presentation.promotionGUI.voucherModel;
import PO.CommodityPO;
import VO.PromotionVO;
import VO.PromotionVO.types;
import VO.SalesVO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.salesbl.SalesController;

import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.salesBLService.SalesBLService;






public class CommodityPanel extends JPanel implements  Serializable, ListSelectionListener,TreeModelListener,TableModelListener{
	
	private static final long serialVersionUID = 1L;
	CommodityBLService cbs = new CommodityController();
	JTree tree;
	DefaultTreeModel treeModel = null;
	String nodeName = null;//原有节点名称
	JTable comTable;
	JTable comTable2;
	TreeNode pressedTreeNode;
	CommodityModel comTableModel;
	CommodityModel comTableModel2;
	ListSelectionModel selectionModel = null;
	ListSelectionModel selectionModel2 = null;
	int[] selectedRows = null;
	int[] selectedRows2 = null;
	JScrollPane comScrollPane ,comScrollPane2,typeScrollPane ;
	String comName,comType,comImpStr,comExpStr,searchStr;
	double comImp,comExp;
	ArrayList<CommodityPO> commodityList;
	private JTextField discountField;
	private JLabel label_1;
	private JTextField voucherField;
	ArrayList<PromotionVO> promotionlist;
	String customer;
	SalesBLService sbs=new SalesController();
	private JPanel vpanel;
	private JPanel gpanel;
	private JPanel ppanel;
	JTable discountTable;
	JTable voucherTable;
	JTable packTable;
	JTable giftTable;
	giftModel gmodel;
	voucherModel vmodel;
	packModel pmodel;
	discountModel dmodel;
	
	public CommodityPanel(ArrayList<CommodityPO> commodityList,ArrayList<PromotionVO> promotionlist,String customer) throws IOException, ClassNotFoundException{
		this.commodityList=commodityList;
		this.promotionlist=promotionlist;
		this.customer=customer;
		this.setSize(880, 430);
		this.setLocation(0, 0);

		//===============================initial Com Table================
		
			comTableModel = new CommodityModel();
			comTableModel2 = new CommodityModel();
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
					v.add(po.getExpPrice());
					comTableModel.addRow(v);
				}
			}
			comScrollPane = new JScrollPane(comTable);
			comScrollPane.setBounds(135, 30, 320, 330);
			comTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
		 	selectionModel.addListSelectionListener(this);
		comScrollPane.setVisible(true);
		
		
		comTable2 = new JTable(comTableModel2);
		comTable2.getColumnModel().getColumn(0).setPreferredWidth(120);
		comTable2.getModel().addTableModelListener(this);
		
		updateComTable2();
		
		comScrollPane2 = new JScrollPane(comTable2);
		comScrollPane2.setBounds(500, 30, 320, 165);
		comTable2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectionModel2=comTable2.getSelectionModel();//取得table的ListSelectionModel.
	 	selectionModel2.addListSelectionListener(this);
		comScrollPane2.setVisible(true);
	 	
		//-------------------------initial Tree------------------------

		tree = cbs.showTree();
		tree.addMouseListener(new MouseHandle());
		tree.setEditable(true);
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(this);
		
		typeScrollPane = new JScrollPane(tree);
		typeScrollPane.setBounds(30, 30, 105, 330);
		typeScrollPane.setVisible(true);
		setLayout(null);
		
		
		JButton forward = new JButton(">");
		forward.setBounds(455, 99, 45, 20);
		forward.setFocusPainted(false);
		forward.setBorderPainted(false);
		forward.setOpaque(false);
		forward.setContentAreaFilled(false);
		add(forward);
		forward.addActionListener(new ForwardListener());
		this.add(typeScrollPane);
		this.add(comScrollPane);
		this.add(comScrollPane2);
		
		JButton delButton = new JButton("DEL");
		delButton.setBounds(640, 200, 90, 20);
		add(delButton);
		delButton.addActionListener(new DelListener());
		
		JButton addButton = new JButton("ADD");
		addButton.setBounds(730, 200, 90, 20);
		add(addButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(500, 225, 320, 105);
		add(tabbedPane);
		
		
		JPanel dpanel = new JPanel();
		tabbedPane.addTab("折让", null, dpanel, null);
		dmodel=new discountModel();
		dpanel.setLayout(null);
		discountTable=new JTable(dmodel);
		JScrollPane scrollpane=new JScrollPane(discountTable);
		scrollpane.setBounds(0, 0, 315, 76);
		dpanel.add(scrollpane);
		
		vpanel = new JPanel();
		tabbedPane.addTab("代金券", null, vpanel, null);
		vmodel=new voucherModel();
		vpanel.setLayout(null);
		voucherTable=new JTable(vmodel);
		JScrollPane scrollpane1=new JScrollPane(voucherTable);
		scrollpane1.setBounds(0, 0, 315, 76);
		vpanel.add(scrollpane1);
		
		gpanel = new JPanel();
		tabbedPane.addTab("赠品", null, gpanel, null);
		gmodel=new giftModel();
		gpanel.setLayout(null);
		giftTable=new JTable(gmodel);
		JScrollPane scrollpane12=new JScrollPane(giftTable);
		scrollpane12.setBounds(0, 0, 315, 76);
		gpanel.add(scrollpane12);
		giftTable.addMouseListener(new TableListener(giftTable));
		
		ppanel = new JPanel();
		tabbedPane.addTab("特价包", null, ppanel, null);
		pmodel=new packModel();
		ppanel.setLayout(null);
		packTable=new JTable(pmodel);
		JScrollPane scrollpane3=new JScrollPane(packTable);
		scrollpane3.setBounds(0, 0, 315, 76);
		ppanel.add(scrollpane3);
		packTable.addMouseListener(new TableListener(packTable));
		
		JLabel label = new JLabel("折让");
		label.setBounds(542, 340, 50, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);
		
		discountField = new JTextField();
		discountField.setBounds(592, 340, 50, 20);
		add(discountField);
		discountField.setColumns(10);
		
		label_1 = new JLabel("代金券");
		label_1.setBounds(662, 340, 50, 20);
		add(label_1);
		
		voucherField = new JTextField();
		voucherField.setBounds(712, 340, 50, 20);
		add(voucherField);
		voucherField.setColumns(10);
		addButton.addActionListener(new AddListener());
		this.setVisible(false);
		setPromotionTable();
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
				po.setID((String)comTable2.getValueAt(i, 0));
				
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
			setPromotionTable();
		}
		
	}
	
	public void setPromotionTable(){
		double sum=0;
		for(CommodityPO po:commodityList){
			sum=sum+po.getAmount()*po.getImpPrice();
		}
		promotionlist.clear();
		ArrayList<PromotionVO> templist=sbs.showAvailablePromotion(new SalesVO("","Sales",customer,"",
				"","",commodityList,sum,0,
				0,0,"",null,"未审批"));
		promotionlist.addAll(templist);

		
		while(gmodel.getRowCount()!=0){
			gmodel.removeRow(0);
		}
		giftTable.revalidate();
		while(dmodel.getRowCount()!=0){
			dmodel.removeRow(0);
		}
		discountTable.revalidate();
		while(vmodel.getRowCount()!=0){
			vmodel.removeRow(0);
		}
		voucherTable.revalidate();
		while(pmodel.getRowCount()!=0){
			pmodel.removeRow(0);
		}
		packTable.revalidate();
		for(PromotionVO pvo:promotionlist){
			if(pvo.getType().equals(types.g)){
					Vector v=new Vector();
					v.add(pvo.getID());
					v.add(pvo.getRank());
					v.add(pvo.getTotalPrice());
					v.add(utility.dateToString(pvo.getStartTime()));
					v.add(utility.dateToString(pvo.getEndTime()));
					gmodel.addRow(v);
					giftTable.revalidate();
				
			}else if(pvo.getType().equals(types.v)){
				Vector v=new Vector();
				v.add(pvo.getID());
				v.add(pvo.getVoucher());
				v.add(pvo.getRank());
				v.add(pvo.getTotalPrice());
				v.add(pvo.getStartTime());
				v.add(pvo.getEndTime());
				vmodel.addRow(v);
				voucherTable.revalidate();
			}else if(pvo.getType().equals(types.d)){
				Vector v=new Vector();
				v.add(pvo.getID());
				v.add(pvo.getDiscount());
				v.add(pvo.getRank());
				v.add(pvo.getStartTime());
				v.add(pvo.getEndTime());
				dmodel.addRow(v);
				discountTable.revalidate();
			}else if(pvo.getType().equals(types.p)){
				Vector v=new Vector();
				v.add(pvo.getID());
				v.add(pvo.getPackDiscount());
				v.add(pvo.getStartTime());
				v.add(pvo.getEndTime());
				pmodel.addRow(v);
				packTable.revalidate();
			}
		}
		double discount=0,voucher=0;
		for(PromotionVO vo:promotionlist){
			if(vo.getType().equals(types.v)){
				voucher=voucher+vo.getVoucher();
			}else if(vo.getType().equals(types.d)){
				discount=discount+sum*(1-vo.getDiscount());
			}else if(vo.getType().equals(types.g)){
				
			}else if(vo.getType().equals(types.p)){
				ArrayList<CommodityPO> packlist=vo.getpackList();
				ArrayList<CommodityPO> list=new ArrayList<CommodityPO>();
				String ID=null;
				int num=0;
				for(CommodityPO po:packlist){
					if(!po.getID().equals(ID)){
						num=1;
						list.add(po);
						po.setImpPrice(num);
						ID=po.getID();
					}else{
						num++;
						list.get(list.size()-1).setImpPrice(num);
					}
				}
				for(CommodityPO po:commodityList){
					for(CommodityPO pack:list){
						if(pack.getID().equals(po.getID())){
							discount=discount+pack.getImpPrice()*po.getImpPrice()*(1-vo.getPackDiscount());
						}
					}
				}
			}
		}
		DecimalFormat df = new DecimalFormat(".00");
		
		discountField.setText(String.valueOf(df.format(discount)));
		voucherField.setText(String.valueOf(df.format(voucher)));
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
	
	class TableListener implements MouseListener{
		JTable t;
		public TableListener(JTable table){
			t = table;
		}

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			ArrayList<PromotionVO> giftlist=new ArrayList<PromotionVO>();
			ArrayList<PromotionVO> packlist=new ArrayList<PromotionVO>();
			int row = t.getSelectedRow();
			
			for(PromotionVO pvo:promotionlist){
				if(pvo.getType().equals(types.g)){
					giftlist.add(pvo);
				}else if(pvo.getType().equals(types.p)){
					packlist.add(pvo);
				}
			}
			if(e.getSource().equals(giftTable)){
				GiftFrame gf= new GiftFrame(giftlist.get(row).getGiftList());
				gf.setLocationRelativeTo(null);
			}else{ 
				System.out.println(row);
				PackFrame pf= new PackFrame(packlist.get(row).getpackList());
				pf.setLocationRelativeTo(null);
			}
			
			
		}

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	
	class GiftFrame extends JFrame{
		ArrayList<CommodityPO> giftlist;
		ComTableModel tablemodel=new ComTableModel();
		JTable table=new JTable(tablemodel);
		
		public GiftFrame(ArrayList<CommodityPO> giftlist){
			this.giftlist=giftlist;
			JScrollPane scrollpane=new JScrollPane(table);
			add(scrollpane);

			if(giftlist!=null){
				int num=1;
				String ID=null;
				for(CommodityPO cpo:giftlist){
					if(!cpo.getID().equals(ID)){
						Vector v=new Vector();
						v.add(cpo.getID());
						v.add(cpo.getName());
						v.add(cpo.getType());
						v.add("1");
						tablemodel.addRow(v);
						ID=cpo.getID();
						num=1;
						
					}
					else{
						
						num++;
						table.setValueAt(Integer.toString(num), table.getRowCount()-1, 3);
					}
			
				}
				table.revalidate();
			}
			setSize(500,500);
			setVisible(true);
		}
	}
	
	class PackFrame extends JFrame{
		ArrayList<CommodityPO> packlist;
		ComTableModel tablemodel=new ComTableModel();
		JTable table=new JTable(tablemodel);
		
		public PackFrame(ArrayList<CommodityPO> packlist){
			this.packlist=packlist;
			JScrollPane scrollpane=new JScrollPane(table);
			add(scrollpane);

			if(packlist!=null){
				int num=1;
				String ID=null;
				for(CommodityPO cvo:packlist){
					if(!cvo.getID().equals(ID)){
						Vector v=new Vector();
						v.add(cvo.getID());
						v.add(cvo.getName());
						v.add(cvo.getType());
						v.add("1");
						tablemodel.addRow(v);
						ID=cvo.getID();
						num=1;
					}
					else{
						num++;
						table.setValueAt(Integer.toString(num), table.getRowCount()-1, 3);
					}
				}
			}
			table.revalidate();
			setSize(500,500);
			setVisible(true);
		}
	}


}
