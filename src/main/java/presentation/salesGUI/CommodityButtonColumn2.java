package presentation.salesGUI;

import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import PO.CommodityPO;
import VO.SalesVO;

public class CommodityButtonColumn2 extends AbstractCellEditor implements  
       ActionListener,TableCellRenderer,TableCellEditor{  
    CommodityPanel panel;
	JTable table;  
    JButton renderButton;  
    JButton editButton;  
    String text;
    ArrayList<SalesVO> voList;
    int num;
    public CommodityButtonColumn2(JTable table, int column,ArrayList<SalesVO> voList) {  
        super();  
        this.table = table;  
        renderButton = new JButton("入库");  
        editButton = new JButton("入库");  
        editButton.setFocusPainted(false);  
        editButton.addActionListener(this);  
        this.voList=voList;
        TableColumnModel columnModel = table.getColumnModel(); 
        columnModel.getColumn(column).setCellRenderer(this);  
        columnModel.getColumn(column).setCellEditor(this);
    }
  
   
    public void actionPerformed(ActionEvent e) { 
    	
    	new CommodityShow2(voList.get(num).getCommodityList());
      
    }


	public ArrayList<SalesVO> getVoList() {
		return voList;
	}


	public void setVoList(ArrayList<SalesVO> voList) {
		this.voList = voList;
	}


	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return text;
	}


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO Auto-generated method stub
		num=row;
		return renderButton;
	}


	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
		return editButton;  
	}


	
}  