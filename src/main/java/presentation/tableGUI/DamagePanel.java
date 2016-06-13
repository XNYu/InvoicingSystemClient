package presentation.tableGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import presentation.promotionGUI.DateChooser;
import presentation.stockGUI.document.*;
import PO.CommodityPO;
import PO.DocumentPO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.document.DocumentController;
import businesslogic.utilitybl.ResultMessage;
import businesslogicservice.commodityBLService.CommodityBLService;
import businesslogicservice.documentBLService.DocumentBLService;

public class DamagePanel extends JPanel implements TableModelListener,ListSelectionListener{

	CommodityBLService cbs = new CommodityController();
	DocumentBLService dbs = (DocumentBLService) new DocumentController();
	JScrollPane overflowPane;
	DocDamageTableModel damageTableModel;
	int rowNum = 0;
	JTable damageTable;
	DamagePanel panel=this;
	int[] selectedRows = null;
	ListSelectionModel selectionModel = null;
	DateChooser mp1;
	DateChooser mp2;
	
	public DamagePanel(){
		setSize(850, 460);
		damageTableModel=new DocDamageTableModel();	
		setLayout(null);
		
	 	JPanel searchPanel = new JPanel();
	 	searchPanel.setBounds(0, 0, 820, 36);
	 	add(searchPanel);
	 	searchPanel.setLayout(null);
	 	
	 	JLabel label = new JLabel("起始日期");
		label.setBounds(14, 6, 72, 18);
		searchPanel.add(label);
		
		JLabel label_1 = new JLabel("终止日期");
		label_1.setBounds(208, 5, 72, 18);
		searchPanel.add(label_1);
		
		JButton btnNewButton_1 = new JButton("检索");
	 	btnNewButton_1.addActionListener(new searchListener());
	 	btnNewButton_1.setBounds(707, 0, 113, 30);
	 	searchPanel.add(btnNewButton_1);
		//====================================================================
		damageTable=new JTable(damageTableModel);
		damageTable.getModel().addTableModelListener(this);
		damageTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
		
		overflowPane = new JScrollPane(damageTable);
		overflowPane.setVisible(true);
		overflowPane.setLocation(0,44);
		overflowPane.setSize(820,416);
		
		mp1 = new DateChooser("yyyy-MM-dd");  
		mp1.setBounds(81, 0, 113, 30);
		searchPanel.add(mp1);
		mp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mp1.showPanel(mp1.showDate);
			}
		});
		
		mp2 = new DateChooser("yyyy-MM-dd");  
		mp2.setBounds(275, 0, 113, 30);
		searchPanel.add(mp2);

		add(overflowPane);
		setLayout(null);
		setSize(850,460);
		setLocation(40,100);
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateTable(){
		damageTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		while(damageTableModel.getRowCount()>0){
			 damageTableModel.removeRow(damageTableModel.getRowCount()-1);
		}
		ArrayList<DocumentPO> docList=dbs.showDocument("DAMAGE");
		if(docList!=null){
			for(DocumentPO po:docList){
				Vector v = new Vector();
				v.add(po.getDocType());
				v.add(po.getName());
				v.add(po.getType());
				v.add(po.getId());
				v.add(po.getSystemAmount());
				v.add(po.getRealAmount());
				v.add(po.getCreatedDate());
				damageTableModel.addRow(v);
			}
		}
		
		damageTable.revalidate();
		repaint();
	}
	
	class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Date d1=mp1.getDate();
			Date d2=mp2.getDate();
			
			updateTable();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}

