package presentation.salesGUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import presentation.promotionGUI.ComTableModel;
import presentation.promotionGUI.discountModel;
import presentation.promotionGUI.giftModel;
import presentation.promotionGUI.packModel;
import presentation.promotionGUI.utility;
import presentation.promotionGUI.voucherModel;
import presentation.salesGUI.CommodityPanel.AddListener;
import presentation.salesGUI.CommodityPanel.GiftFrame;
import presentation.salesGUI.CommodityPanel.PackFrame;
import presentation.salesGUI.CommodityPanel.TableListener;
import PO.CommodityPO;
import VO.PromotionVO;
import VO.SalesVO;
import VO.PromotionVO.types;
public class CommodityShow extends JFrame{
	ArrayList<CommodityPO> commoditylist;
	ArrayList<PromotionVO> promotionlist;
	JTable table;
	CommodityModel tablemodel;
	private JPanel vpanel;
	private JPanel gpanel;
	private JPanel ppanel;
	private JPanel dpanel;
	private JTextField discountField;
	private JTextField voucherField;
	JTable discountTable;
	JTable voucherTable;
	JTable packTable;
	JTable giftTable;
	giftModel gmodel;
	voucherModel vmodel;
	packModel pmodel;
	discountModel dmodel;
	
	public CommodityShow(ArrayList<CommodityPO> poList,ArrayList<PromotionVO> promotionlist){
		setBounds(400,200,500,400);
		setVisible(true);
		commoditylist=poList;
		this.promotionlist=promotionlist;
		tablemodel=new CommodityModel();
		getContentPane().setLayout(null);
		table=new JTable(tablemodel);
		JScrollPane scrollpane=new JScrollPane(table);
		scrollpane.setBounds(0, 0, 484, 218);
		getContentPane().add(scrollpane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 219, 484, 103);
		getContentPane().add(tabbedPane);
		
		
		JPanel dpanel = new JPanel();
		tabbedPane.addTab("折让", null, dpanel, null);
		dmodel=new discountModel();
		dpanel.setLayout(null);
		discountTable=new JTable(dmodel);
		JScrollPane scrollpane0=new JScrollPane(discountTable);
		scrollpane0.setBounds(0, 0, 479, 74);
		dpanel.add(scrollpane0);
		
		vpanel = new JPanel();
		tabbedPane.addTab("代金券", null, vpanel, null);
		vmodel=new voucherModel();
		vpanel.setLayout(null);
		voucherTable=new JTable(vmodel);
		JScrollPane scrollpane1=new JScrollPane(voucherTable);
		scrollpane1.setBounds(0, 0, 479, 74);
		vpanel.add(scrollpane1);
		
		gpanel = new JPanel();
		tabbedPane.addTab("赠品", null, gpanel, null);
		gmodel=new giftModel();
		gpanel.setLayout(null);
		giftTable=new JTable(gmodel);
		JScrollPane scrollpane12=new JScrollPane(giftTable);
		scrollpane12.setBounds(0, 0, 479, 74);
		gpanel.add(scrollpane12);
		giftTable.addMouseListener(new TableListener(giftTable));
		
		ppanel = new JPanel();
		tabbedPane.addTab("特价包", null, ppanel, null);
		pmodel=new packModel();
		ppanel.setLayout(null);
		packTable=new JTable(pmodel);
		JScrollPane scrollpane3=new JScrollPane(packTable);
		scrollpane3.setBounds(0, 0, 479, 74);
		ppanel.add(scrollpane3);
		packTable.addMouseListener(new TableListener(packTable));
		
		JLabel discountlabel = new JLabel("折让");
		discountlabel.setBounds(133, 332, 50, 20);
		discountlabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(discountlabel);
		
		discountField = new JTextField();
		discountField.setBounds(183, 332, 50, 20);
		getContentPane().add(discountField);
		discountField.setColumns(10);
		
		JLabel voucherlabel = new JLabel("代金券");
		voucherlabel.setBounds(233, 332, 50, 20);
		getContentPane().add(voucherlabel);
		
		voucherField = new JTextField();
		voucherField.setBounds(283, 332, 50, 20);
		getContentPane().add(voucherField);
		voucherField.setColumns(10);
		this.setVisible(false);
		
		
		if(commoditylist!=null){
			for(CommodityPO po:commoditylist){
				Vector v=new Vector();
				System.out.println(po.getID());
				v.add(po.getID());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getAmount());
				v.add(po.getImpPrice());
				tablemodel.addRow(v);
			}
		}
		
		table.revalidate();
		repaint();
		setPromotionTable();
		setVisible(true);
	}
	
	public void setPromotionTable(){
		double sum=0;
		for(CommodityPO po:commoditylist){
			sum=sum+po.getAmount()*po.getImpPrice();
		}

		
		while(gmodel.getRowCount()!=0){
			gmodel.removeRow(0);
		}
		while(dmodel.getRowCount()!=0){
			dmodel.removeRow(0);
		}
		while(vmodel.getRowCount()!=0){
			vmodel.removeRow(0);
		}
		while(pmodel.getRowCount()!=0){
			pmodel.removeRow(0);
		}
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
				for(CommodityPO po:commoditylist){
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
						v.add(cpo.getAmount());
						v.add("1");
						tablemodel.addRow(v);
						ID=cpo.getID();
						num=1;
						
					}
					else{
						System.out.println("giftframe"+cpo.getID());
						num++;
						table.setValueAt(Integer.toString(num), table.getRowCount()-1, 4);
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
						v.add(cvo.getAmount());
						v.add("1");
						tablemodel.addRow(v);
						ID=cvo.getID();
						num=1;
					}
					else{
						num++;
						table.setValueAt(Integer.toString(num), table.getRowCount()-1, 4);
					}
				}
			}
			table.revalidate();
			setSize(500,500);
			setVisible(true);
		}
	}
	
}
