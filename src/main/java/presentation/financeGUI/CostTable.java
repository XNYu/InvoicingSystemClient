package presentation.financeGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import businesslogic.billbl.EntryList;
import businesslogicservice.billBLService.BillBLService;

public class CostTable extends JPanel implements TableModelListener, ListSelectionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8778186646620959183L;
	JTable t;
	ListSelectionModel selectionModel = null;
	int[] selectedRows = null;
	CostModel b=null;
	boolean isModified = false;
	public CostTable(JLabel sumtext, final EntryList entrylist, BillBLService bill,boolean isModified){
		this.isModified = isModified;
		b=new CostModel(sumtext, entrylist, bill, isModified);
		if(isModified)
			t = new JTable(b);
		else{
			t = new JTable(b){ 
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row,int col){ 
					   return false;
				}
			};
		}
		
		
		this.setBackground(new Color(236,245,209));
		this.setLayout(null);
		TableColumn firsetColumn = t.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(100);
		firsetColumn.setMaxWidth(100);
		firsetColumn.setMinWidth(100);
		TableColumn secondColumn = t.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(100);
		secondColumn.setMaxWidth(100);
		secondColumn.setMinWidth(100);
		TableColumn thirdColumn = t.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(257);
		thirdColumn.setMaxWidth(257);
		thirdColumn.setMinWidth(257);
		JScrollPane scrollPane = new JScrollPane(t);  
		
		scrollPane.setSize(460, 230);
		scrollPane.setLocation(0,0);
		
		
		if(isModified){
			JButton delarow = new JButton(new ImageIcon("GUI/delarow.png"));
			delarow.setSize(60,30);
			delarow.setLocation(200,230);
			delarow.setBorderPainted(false);
			delarow.setFocusPainted(false);
			t.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			selectionModel=t.getSelectionModel();//取得table的ListSelectionModel.
		 	selectionModel.addListSelectionListener(this);delarow.setBackground(new Color(80,100,220));
			
			this.add(delarow);
			delarow.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					if(selectedRows == null)
						return;
					else{
					    for(int i = 0 ; i < selectedRows.length ;i++){
					    	//sumlabel.setText(Double.toString(entrylist.total()));
					    	b.removeRow(selectedRows[i]-i);
					    }
						
					    t.revalidate();
						t.repaint();
					}
					
				}
				
				
			}
			);
		}
		
		this.add(scrollPane);
		this.repaint();
	}
	
	
	
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		/*int row = e.getFirstRow();  
        int column = e.getColumn();  
        BillModel model = (BillModel)e.getSource();  
        String columnName = model.getColumnName(column);  
        Object data = model.getValueAt(row, column); 
        if(!model.getValueAt(model.getRowCount()-1, 0).equals("")&&!model.getValueAt(model.getRowCount()-1, 1).equals("")&&!model.getValueAt(model.getRowCount()-1, 2).equals("")){
        	entrylist.addentry(new entryVO(bill.getAccount((String)model.getValueAt(model.getRowCount()-1, 0)),
        			(Double)model.getValueAt(model.getRowCount()-1, 1),(String)model.getValueAt(model.getRowCount()-1, 2)));
        	sumlabel.setText(Double.toString(entrylist.total()));
        	Vector<String> v=new Vector<String>();
    		v.add("");
    		v.add("");
    		v.add("");
    		model.addRow(v);
        }
        repaint();*/
        
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO 自动生成的方法存根
		selectedRows=t.getSelectedRows();
	}
}

