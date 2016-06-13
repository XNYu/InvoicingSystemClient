package presentation.stockGUI.document;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import PO.CommodityPO;
import PO.PresentPO;
import businesslogic.document.DocumentController;
import businesslogicservice.documentBLService.DocumentBLService;

public class PresentPanel extends JPanel{
	DocumentBLService dbs = (DocumentBLService) new DocumentController();
	JScrollPane presentPane,comPane;
	PresentTableModel presentTableModel = new PresentTableModel();
	DocComTableModel presentComTableModel = new DocComTableModel();
	JTable presentTable ,presentComTable;
	ListSelectionModel selectionModel = null;
	int[] selectedRows = null;
	String presentID = "";
	public PresentPanel(){
		
		presentTable = new JTable(presentTableModel);
		
		ArrayList<PresentPO> docList=dbs.showPresent();
		if(docList!=null){
			for(PresentPO po: docList){
				Vector v = new Vector();
				v.add(po.getID());
				v.add(po.getCustomer());
				v.add(po.getCreatedDate());
				v.add(po.getExamine());
				presentTableModel.addRow(v);
			}
		}
		presentPane = new JScrollPane(presentTable);
		presentPane.setVisible(true);
		presentPane.setLocation(0,20);
		presentPane.setSize(380,430);
		selectionModel=presentTable.getSelectionModel();//取得table的ListSelectionModel.
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	 	selectionModel.addListSelectionListener(new presentListener());
	 	presentTable.addMouseListener(new MouseHandle() );
	 	
		presentComTable = new JTable(presentComTableModel);
		comPane = new JScrollPane(presentComTable);
		comPane.setVisible(true);
		comPane.setLocation(400,20);
		comPane.setSize(380,430);
		this.add(comPane);
		this.add(presentPane);
		this.setLayout(null);
		this.setSize(780,450);
		this.setLocation(40,100);
	}
	class MouseHandle extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			
			if(e.getSource()!=null||selectedRows.length!=0){
				System.out.println(selectedRows[0]);
				for(int i = presentComTableModel.getRowCount(); i>0 ; i--){
					presentComTableModel.removeRow(0);
					}
				presentID = (String) presentTableModel.getValueAt(selectedRows[0], 0);
				ArrayList<PresentPO> docList=dbs.showPresent();
				if(docList!=null){
					for(PresentPO po: docList){
						if(po.getID().equals(presentID)){
							ArrayList<CommodityPO> presentList = po.getCommoditylist();
							for(int i = 0;i<presentList.size();i++){
								CommodityPO tmpPO = presentList.get(i);
								Vector v = new Vector();
								v.add(tmpPO.getID());
								v.add(tmpPO.getName());
								v.add(tmpPO.getType());
								v.add(tmpPO.getAmount());
								v.add(tmpPO.getReportAmount());
								presentComTableModel.addRow(v);
							}
							
						}
						
					}
				}
				presentComTable.revalidate();
				presentComTable.repaint();
			}
		
		}
	}
	class presentListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			
			selectedRows = presentTable.getSelectedRows();
		}
		
	}
	
}
