package presentation.stockGUI.commodity;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import PO.*;
import VO.UserVO;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import businesslogic.stockbl.StockController;
import businesslogic.utilitybl.DateHelper;
import businesslogic.utilitybl.ExportExcel;
import businesslogic.utilitybl.ShowMessageFrame;
import businesslogicservice.stockBLService.StockBLService;

public class StockCheckPanel extends JPanel implements Serializable{
	private static final long serialVersionUID = 1L;
	JButton outputButton,fileChooserButton;
	JScrollPane stoScrollPane;
	JTable stockTable;
	StockCheckTableModel stockCheckTableModel;
	StockBLService sbs = new StockController();
	int rowNum=0;
	String filePath = "";

	
	public StockCheckPanel(UserVO uvo){
		fileChooserButton= new JButton("选择路径");
		fileChooserButton.setSize(110,20);
		fileChooserButton.setLocation(40, 40);
		fileChooserButton.addActionListener(new fileListener());
		this.add(fileChooserButton);
		outputButton= new JButton("导出表格");
		outputButton.setSize(110,20);
		outputButton.setLocation(140, 40);
		outputButton.addActionListener(new OutputListener());
		this.add(outputButton);
		//======================initial table
		
			stockCheckTableModel = new StockCheckTableModel();
			stockTable = new JTable(stockCheckTableModel);
			ArrayList<StockPO> poList = sbs.init();
			if(poList!=null){
				for(StockPO po:poList){
					Vector v=new Vector();
					v.add(++rowNum);
					v.add(po.getName());
					v.add(po.getType());
					v.add(po.getAmount());
					v.add(po.getPrice());
					v.add(po.getBatch());
					v.add(po.getBatchID());
					v.add(po.getLeaveDate());
					stockCheckTableModel.addRow(v);
				}
			}
			stockTable.getColumnModel().getColumn(0).setPreferredWidth(30);
//			stockTable.getModel().addTableModelListener(this);
			stoScrollPane = new JScrollPane(stockTable);
			stockTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//			selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
//		 	selectionModel.addListSelectionListener(this);

		stoScrollPane.setSize(780,450);
		stoScrollPane.setLocation(40,100);
		stoScrollPane.setVisible(true);
		
		this.add(stoScrollPane);
		this.setLayout(null);
		this.setSize(850, 600);
		this.setLocation(0, 0);
	}
	public class fileListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.showOpenDialog(null);
			filePath = chooser.getSelectedFile().getPath();
			
		}
		
	}
	public class OutputListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			 // 测试学生  
	        ExportExcel<StockPO> ex = new ExportExcel<StockPO>();  
	        String[] headers =  
	        { "行号", "操作类型", "商品名称", "商品型号", "商品编号" ,"操作日期","库存数量"
	        		,"出厂日期","是否审批"," "};  
	        List<StockPO> dataset = new ArrayList<StockPO>();  
//	        dataset.add(new Student(10000001, "张三", 20, true, new Date()));  
//	        dataset.add(new Student(20000002, "李四", 24, false, new Date()));  
//	        dataset.add(new Student(30000003, "王五", 22, true, new Date()));  
	        dataset=sbs.showStock(String.valueOf(0), String.valueOf(30000000));
	        try  
	        {
//	            BufferedInputStream bis = new BufferedInputStream(  
//	                    new FileInputStream("GUI/commodity.png"));  
//	            byte[] buf = new byte[bis.available()];  
//	            while ((bis.read(buf)) != -1)  
//	            {  
//	                //  
//	            }
	  
	        	DateHelper dh = new DateHelper();
	        	String outputName = dh.getDate();
	        	outputName = "StockData-"+outputName+".xls";
	            OutputStream out = new FileOutputStream(filePath +"/"+outputName); 
	            ex.exportExcel(headers, dataset, out);  
	            out.close();  
	            new ShowMessageFrame("导出表格成功");
//	            JOptionPane.showMessageDialog(null, "导出成功!");  
//	            System.out.println("excel导出成功！");  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
		}
		
	}
}
