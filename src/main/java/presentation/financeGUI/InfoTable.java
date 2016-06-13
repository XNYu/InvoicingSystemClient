package presentation.financeGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.TableColumn;

import PO.EntryPO;
import PO.TransferPO;
import VO.AccountVO;
import VO.BillVO;
import VO.CostBillVO;
import VO.CustomerVO;
import VO.PaymentBillVO;
import VO.ReceivingBillVO;
import VO.TransferVO;
import businesslogic.billbl.EntryList;
import businesslogic.billbl.TransferList;
import businesslogicservice.billBLService.BillBLService;

public class InfoTable extends JPanel implements TableModelListener, ListSelectionListener{
	/**
	 *
	 */
	private static final long serialVersionUID = -8778186646620959183L;
	JTable t;
	ListSelectionModel selectionModel = null;
	int[] selectedRows = null;
	InfoModel b=null;
	ArrayList<BillVO> list = new ArrayList<BillVO>();
	BillBLService bill;
	JPanel contain;
	boolean flag = false;
	Thread create = null;


	public InfoTable(ArrayList<BillVO> arrayList, BillBLService bill, JPanel containPanel,boolean isShowAll){
		if(arrayList!=null)
			this.list = arrayList;
		this.bill = bill;
		this.contain = containPanel;
		this.flag = isShowAll;
	}

	void create(){
		createTable();
		create = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					while(true){
						Thread.sleep(2000);
						
						createTable();
						}

					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}

			}
		}
			);
		create.start();

	}

	void createTable(){
		this.removeAll();
		b=new InfoModel();
		if(flag)
			list = bill.show();
		else
			list = bill.getBillExamined();
		
		for(int i =0;i<list.size();i++){
			Vector<String> r = new Vector<String>();
			r.add(list.get(i).getNumberID());
			if(list.get(i).getNumberID().substring(0, 3).equals("SKD")){
				r.add("收款单");
			}
			else if(list.get(i).getNumberID().substring(0, 3).equals("FKD")){
				r.add("付款单");
			}
			else if(list.get(i).getNumberID().substring(0, 3).equals("XJF")){
				r.add("现金费用单");
			}
			r.add(list.get(i).getUsername());

			String state = "";
			if(list.get(i).isExamined()){
				state +="已审查";
				if(!list.get(i).isModified()){
					state +="；通过";
				}
				else if(list.get(i).isModified()){
					state +="；未通过";
				}
				if(list.get(i).isRead()){
					state +="；已读";
				}
				else if(!list.get(i).isRead()){
					state +="；未读";
				}
			}
			else if(!list.get(i).isExamined()){
				state +="未审查";
			}
			r.add(state);
			b.addRow(r);

		}

		t = new JTable(b){

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row,int col){
				   return false;
			}
		};
		t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    t.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getButton() == MouseEvent.BUTTON1) {
	                    if (t.rowAtPoint(e.getPoint()) == t.getSelectedRow()) {
	                    	create.stop();
	                    	if(list.get(t.getSelectedRow()).getNumberID().substring(0, 3).equals("SKD")){
	                    		if(!list.get(t.getSelectedRow()).isModified()||flag)
	                    			showReceiving1((ReceivingBillVO)list.get(t.getSelectedRow()));
	                    		else
	                    			showReceiving2((ReceivingBillVO)list.get(t.getSelectedRow()));
	            			}
	            			else if(list.get(t.getSelectedRow()).getNumberID().substring(0, 3).equals("FKD")){

	            				if(!list.get(t.getSelectedRow()).isModified()||flag)
	            					showPayment1((PaymentBillVO)list.get(t.getSelectedRow()));
	                    		else
	                    			showPayment2((PaymentBillVO)list.get(t.getSelectedRow()));
	            			}
	            			else if(list.get(t.getSelectedRow()).getNumberID().substring(0, 3).equals("XJF")){

	            				if(!list.get(t.getSelectedRow()).isModified()||flag)
	            					showCost1((CostBillVO)list.get(t.getSelectedRow()));
		                    	else
		                    		showCost2((CostBillVO)list.get(t.getSelectedRow()));
	            			}
	                    }
	                }
	            }
	        });

		this.setBackground(new Color(236,245,209));
		this.setLayout(null);
		TableColumn firsetColumn = t.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(250);
		firsetColumn.setMaxWidth(250);
		firsetColumn.setMinWidth(250);
		TableColumn secondColumn = t.getColumnModel().getColumn(1);
		secondColumn.setPreferredWidth(100);
		secondColumn.setMaxWidth(100);
		secondColumn.setMinWidth(100);
		TableColumn thirdColumn = t.getColumnModel().getColumn(2);
		thirdColumn.setPreferredWidth(100);
		thirdColumn.setMaxWidth(100);
		thirdColumn.setMinWidth(100);
		TableColumn fourthColumn = t.getColumnModel().getColumn(3);
		fourthColumn.setPreferredWidth(250);
		fourthColumn.setMaxWidth(250);
		fourthColumn.setMinWidth(250);
		JScrollPane scrollPane = new JScrollPane(t);

		scrollPane.setSize(700, 500);
		scrollPane.setLocation(0,0);




		this.add(scrollPane);
		this.repaint();
	}

	void showReceiving1(ReceivingBillVO receivingBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/receiving.png");

		mp.setLayout(null);



		final TransferList transferlist = new TransferList();
		for(TransferPO po:receivingBillVO.getTransferList()){

			transferlist.addTransfer(transferlist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(receivingBillVO.getSum()));
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		BillTable b = new BillTable(sumtext,transferlist,bill,false);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		contain.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(receivingBillVO.getNumberID());
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(receivingBillVO.getUsername());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JLabel customer = new JLabel(receivingBillVO.getCus().getName());
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);


		if(!flag){
			JButton commit = new JButton("确认");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill的确认审批成功的方法

					bill.changeReceive((String)customer.getText(), -Double.parseDouble(sumtext.getText()));
					for(TransferVO vo:transferlist.getlist()){
						bill.changeMoney(vo.acc.getName(), vo.getMoney());
					}

					bill.modifyBill(new ReceivingBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getText()), transferlist.getRtlist(),true,true,false));
					new InfoWindows("确认成功");
					contain.removeAll();
					InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();
				}


			}
			);
		}

		else{
			JButton commit = new JButton("返回");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill的确认审批成功的方法
					contain.removeAll();
					InfoTable info = new InfoTable(bill.show(),bill,contain,true);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();
				}


			}
			);

		}


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
		contain.repaint();
	}

	void showReceiving2(ReceivingBillVO receivingBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/receiving.png");

		mp.setLayout(null);



		final TransferList transferlist = new TransferList();;
		for(TransferPO po:receivingBillVO.getTransferList()){
			transferlist.addTransfer(transferlist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(receivingBillVO.getSum()));
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		BillTable b = new BillTable(sumtext,transferlist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		contain.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(receivingBillVO.getNumberID());
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(receivingBillVO.getUsername());
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> customer = new JComboBox<String>();
		ArrayList<CustomerVO> customerlist = bill.showCustomer();
		for(CustomerVO cus:customerlist){
			customer.addItem(cus.getName());

		}

		customer.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		customer.setSelectedItem(receivingBillVO.getCus().getName());
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);





		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				//修改的方法
				bill.modifyBill(new ReceivingBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getSelectedItem()), transferlist.getRtlist()));
				new InfoWindows("提交成功");
				contain.removeAll();
				InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
				info.create();
				info.setSize(700, 500);
				info.setLocation(75, 50);
				info.repaint();
				contain.add(info);
				contain.repaint();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
		contain.repaint();
	}

	void showPayment1(PaymentBillVO paymentBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/payment.png");
		mp.setLayout(null);

		final TransferList transferlist = new TransferList();;
		for(TransferPO po:paymentBillVO.getTransferList()){
			transferlist.addTransfer(transferlist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(paymentBillVO.getSum()));
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		BillTable b = new BillTable(sumtext,transferlist,bill,false);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		this.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(paymentBillVO.getNumberID());//billid 改
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(paymentBillVO.getUsername());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JLabel customer = new JLabel(paymentBillVO.getCus().getName());
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);



		if(!flag){
			JButton commit = new JButton("确认");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill审批通过单据更改方法
					bill.changePay((String)customer.getText(), Double.parseDouble(sumtext.getText()));
					bill.changeReceive((String)customer.getText(), Double.parseDouble(sumtext.getText()));
					for(TransferVO vo:transferlist.getlist()){
						bill.changeMoney(vo.acc.getName(), -vo.getMoney());
					}

					bill.modifyBill(new PaymentBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getText()), transferlist.getRtlist(),true,true,false));
					new InfoWindows("确认成功");
					contain.removeAll();
					InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();

				}


			}
			);
		}

		else{
			JButton commit = new JButton("返回");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill的确认审批成功的方法
					contain.removeAll();
					InfoTable info = new InfoTable(bill.show(),bill,contain,true);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();
				}


			}
			);

		}
		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
	}

	void showPayment2(PaymentBillVO paymentBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/payment.png");
		mp.setLayout(null);

		final TransferList transferlist = new TransferList();;
		for(TransferPO po:paymentBillVO.getTransferList()){
			transferlist.addTransfer(transferlist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(paymentBillVO.getSum()));//sum 改
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		BillTable b = new BillTable(sumtext,transferlist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		this.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(paymentBillVO.getNumberID());//billid 改
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(paymentBillVO.getUsername());//userinfo 改
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> customer = new JComboBox<String>();
		ArrayList<CustomerVO> customerlist = bill.showCustomer();
		for(CustomerVO cus:customerlist){
			customer.addItem(cus.getName());

		}

		customer.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		customer.setSelectedItem(paymentBillVO.getCus().getName());
		customer.setFont(new Font("黑体", Font.BOLD, 24));
		customer.setSize(120, 30);
		customer.setLocation(205, 108);
		mp.add(customer);



		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				//保存修改的方法
				bill.modifyBill(new PaymentBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getCustomer((String)customer.getSelectedItem()), transferlist.getRtlist()));
				new InfoWindows("提交成功");
				contain.removeAll();
				InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
				info.create();
				info.setSize(700, 500);
				info.setLocation(75, 50);
				info.repaint();
				contain.add(info);
				contain.repaint();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
	}

	void showCost1(CostBillVO costBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/cost.png");
		mp.setLayout(null);

		final EntryList entrylist = new EntryList();
		for(EntryPO po:costBillVO.getEntryList()){
			entrylist.addEntry(entrylist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(costBillVO.getSum()));
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		CostTable b = new CostTable(sumtext,entrylist,bill,false);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		contain.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(costBillVO.getNumberID());
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(costBillVO.getUsername());
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JLabel account = new JLabel(costBillVO.getAcc().getName());
		account.setFont(new Font("黑体", Font.BOLD, 24));
		account.setSize(120, 30);
		account.setLocation(235, 108);
		mp.add(account);

		if(!flag){
			JButton commit = new JButton("确认");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill审查通过账单保存
					bill.changeMoney((String)account.getText(), -Double.parseDouble(sumtext.getText()));

					bill.modifyBill(new CostBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getAccount((String)account.getText()), entrylist.getRtlist(),true,true,false));
					new InfoWindows("确认成功");
					contain.removeAll();
					InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();
				}


			}
			);
		}

		else{
			JButton commit = new JButton("返回");
			commit.setSize(100,50);
			commit.setLocation(690,500);
			commit.setFont(new Font("黑体", Font.BOLD, 18));
			commit.setForeground(Color.WHITE);
			commit.setFocusPainted(false);
			commit.setBackground(new Color(80,100,220));
			mp.add(commit);
			commit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					//bill的确认审批成功的方法
					contain.removeAll();
					InfoTable info = new InfoTable(bill.show(),bill,contain,true);
					info.create();
					info.setSize(700, 500);
					info.setLocation(75, 50);
					info.repaint();
					contain.add(info);
					contain.repaint();
				}


			}
			);

		}
		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
	}

	void showCost2(CostBillVO costBillVO){
		contain.removeAll();

		MyPanel mp = new MyPanel("GUI/cost.png");
		mp.setLayout(null);

		final EntryList entrylist = new EntryList();
		for(EntryPO po:costBillVO.getEntryList()){
			entrylist.addEntry(entrylist.transform(po));
		}

		final JLabel sumtext = new JLabel();
		sumtext .setText(Double.toString(costBillVO.getSum()));//sum 改
		sumtext .setSize(80,30);
		sumtext .setLocation(650,402);
		sumtext .setFont(new Font("黑体", Font.BOLD, 24));
		mp.add(sumtext);


		CostTable b = new CostTable(sumtext,entrylist,bill,true);
		b.setLayout(null);
		b.setSize(460, 260);
		b.setLocation(200, 140);
		b.repaint();
		b.paintComponents(b.getGraphics());
		mp.add(b);
		mp.repaint();
		contain.repaint();


		final JLabel idtext = new JLabel();
		idtext.setText(costBillVO.getNumberID());
		idtext.setSize(500,30);
		idtext.setLocation(170,42);
		idtext.setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(idtext);


		final JLabel usertext = new JLabel();
		usertext .setText(costBillVO.getUsername());
		usertext .setSize(80,30);
		usertext .setLocation(700,42);
		usertext .setFont(new Font("黑体", Font.BOLD, 18));
		mp.add(usertext);

		final JComboBox<String> account = new JComboBox<String>();
		ArrayList<AccountVO> accountlist = bill.showAccount();
		for(AccountVO acc:accountlist){
			account.addItem(acc.getName());

		}

		account.setUI(new BasicComboBoxUI() {
            public void installUI(JComponent comboBox) {
                super.installUI(comboBox);
                listBox.setForeground(Color.BLACK);
                listBox.setSelectionBackground(new Color(236,245,209));
                listBox.setSelectionForeground(Color.BLUE);
            }

		});
		account.setSelectedItem(costBillVO.getAcc().getName());
		account.setFont(new Font("黑体", Font.BOLD, 24));
		account.setSize(120, 30);
		account.setLocation(235, 108);
		mp.add(account);

		JButton commit = new JButton("提交");
		commit.setSize(100,50);
		commit.setLocation(690,500);
		commit.setFont(new Font("黑体", Font.BOLD, 18));
		commit.setForeground(Color.WHITE);
		commit.setFocusPainted(false);
		commit.setBackground(new Color(80,100,220));
		mp.add(commit);
		commit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				//更改后提交
				bill.modifyBill(new CostBillVO(idtext.getText(), usertext.getText(), Double.parseDouble(sumtext.getText()), bill.getAccount((String)account.getSelectedItem()), entrylist.getRtlist()));
				new InfoWindows("提交成功");
				contain.removeAll();
				InfoTable info = new InfoTable(bill.getBillExamined(),bill,contain,false);
				info.create();
				info.setSize(700, 500);
				info.setLocation(75, 50);
				info.repaint();
				contain.add(info);
				contain.repaint();
			}


		}
		);


		mp.setSize(850, 600);
		mp.setLocation(0, 0);
		mp.setVisible(true);
		contain.add(mp);
		mp.repaint();
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub


	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO 自动生成的方法存根
		selectedRows=t.getSelectedRows();
	}
}

