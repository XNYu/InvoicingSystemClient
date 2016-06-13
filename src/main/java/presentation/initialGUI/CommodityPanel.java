package presentation.initialGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import PO.CommodityPO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.utilitybl.CheckNumber;
import businesslogicservice.commodityBLService.CommodityBLService;



public class CommodityPanel extends JPanel implements  Serializable, ListSelectionListener,TableModelListener{

	private static final long serialVersionUID = 1L;
	CommodityBLService cbs = new CommodityController();

	JButton addComButton;
	JTree tree;
	DefaultTreeModel treeModel = null;
	String nodeName = null;//原有节点名称
	JTable comTable;
	TreeNode pressedTreeNode;
	DefaultMutableTreeNode modifyTreeNode;
	ComTableModel comTableModel;
	ListSelectionModel selectionModel = null;
	int[] selectedRows = null;
	JScrollPane comScrollPane ,typeScrollPane ;
	JTextArea searchArea ;
    JTextField comNameText,comTypeText,comImpText,comExpText,typeModifyText;
	String comName,comType,comImpStr,comExpStr,searchStr,typeModifyStr;
	double comImp,comExp;
	public CommodityPanel() throws IOException, ClassNotFoundException{



		comNameText = new JTextField("名称");
		comNameText.setSize(100,21);
		comNameText.setLocation(220, 530);
		comNameText.getDocument().addDocumentListener(new comNameTextListener());
		comNameText.addFocusListener(new comNameAdapter());
		this.add(comNameText);
		comTypeText = new JTextField("型号");
		comTypeText.setSize(100,21);
		comTypeText.setLocation(320, 530);
		comTypeText.getDocument().addDocumentListener(new comTypeTextListener());
		comTypeText.addFocusListener(new comTypeAdapter());
		this.add(comTypeText);
		comImpText = new JTextField("进价");
		comImpText.setSize(100,21);
		comImpText.setLocation(420, 530);
		comImpText.getDocument().addDocumentListener(new comImpTextListener());
		comImpText.addFocusListener(new comImpAdapter());
		this.add(comImpText);
		comExpText = new JTextField("售价");
		comExpText.setSize(100,21);
		comExpText.setLocation(520, 530);
		comExpText.getDocument().addDocumentListener(new comExpTextListener());
		comExpText.addFocusListener(new comExpAdapter());
		this.add(comExpText);



		addComButton= new JButton("ADD");
		addComButton.setSize(100,20);
		addComButton.setLocation(718, 530);
		addComButton.addActionListener(new addComListener());
		this.add(addComButton);

		this.setSize(850, 600);
		this.setLocation(0, 0);


		//===============================initial Com Table================

			comTableModel = new ComTableModel();
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
					v.add(po.getExpPrice());
					v.add(po.getRecentImpPrice());
					v.add(po.getRecentExpPrice());
					comTableModel.addRow(v);
				}
			}
			comScrollPane = new JScrollPane(comTable);
			comTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			selectionModel=comTable.getSelectionModel();//取得table的ListSelectionModel.
		 	selectionModel.addListSelectionListener(this);

		comScrollPane.setSize(600,400);
		comScrollPane.setLocation(220,130);
		comScrollPane.setVisible(true);


		//-------------------------initial Tree------------------------

		tree = cbs.showTree();
		System.out.println();
		tree.addMouseListener(new MouseHandle());
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(new treeModelListener());
		tree.setEditable(false);

		typeScrollPane = new JScrollPane(tree);
		typeScrollPane.setSize(180,420);
		typeScrollPane.setLocation(40,130);
		typeScrollPane.setVisible(true);

		this.setLayout(null);
		this.add(typeScrollPane);
		this.add(comScrollPane);
		this.setVisible(false);
	}


	public class comNameAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (comNameText.getText().equals("名称")) {
	            comNameText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (comNameText.getText().equals("")) {
                comNameText.setText("名称");
            }
        }
	}
	public class comTypeAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (comTypeText.getText().equals("型号")) {
	            comTypeText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (comTypeText.getText().equals("")) {
                comTypeText.setText("型号");
            }
        }
	}
	public class comImpAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (comImpText.getText().equals("进价")) {
				comImpText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (comImpText.getText().equals("")) {
        		comImpText.setText("进价");
            }
        }
	}
	public class comExpAdapter implements FocusListener{
		public void focusGained(FocusEvent e) {
			if (comExpText.getText().equals("售价")) {
				comExpText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (comExpText.getText().equals("")) {
        		comExpText.setText("售价");
            }
        }
	}



	public class addComListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(CheckNumber.isNumber(comImpStr)&&CheckNumber.isNumber(comExpStr)){
				comImp = Double.parseDouble(comImpStr);
				comExp = Double.parseDouble(comExpStr);
				cbs.addCommodity(comName,comType,0,comImp,comExp,pressedTreeNode.toString());
				String tmpID;
				tmpID = cbs.getComID(comName, comType);

				Vector v=new Vector();
				v.add(tmpID);
				v.add(comName);
				v.add(comType);
				v.add(0);
				v.add(comImp);
				v.add(comExp);
				v.add(comImp);
				v.add(comExp);
				comTableModel.addRow(v);
				comTable.revalidate();
				comTable.repaint();
			}else{
				System.out.println("input wrong ! ");
			}
		}

	}


	public class comNameTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			comName = comNameText.getText().trim();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			comName = comNameText.getText().trim();

		}

	}
	public class comTypeTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			comType = comTypeText.getText();

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO 自动生成的方法存根

		}

	}
	public class comImpTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			comImpStr =(comImpText.getText()) ;

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO 自动生成的方法存根

		}

	}
	public class comExpTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			comExpStr = (comExpText.getText());

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO 自动生成的方法存根

		}

	}


	class MouseHandle extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			try {
				JTree tree = (JTree) e.getSource();
				int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
				TreePath treepath = tree.getPathForRow(rowLocation);
				pressedTreeNode = (TreeNode) treepath.getLastPathComponent();
				modifyTreeNode = (DefaultMutableTreeNode)  treepath.getLastPathComponent();
				nodeName = pressedTreeNode.toString();
//				System.out.println(comTableModel.getRowCount());
				ArrayList<CommodityPO> imgod = cbs.showCommodity(nodeName);
				for(int i = comTableModel.getRowCount(); i>0 ; i--){
//					System.out.println(i+" "+comTableModel.getRowCount());
					comTableModel.removeRow(0);
					}

				if(imgod!=null){
					for(CommodityPO po:imgod){
						Vector v=new Vector();
						v.add(po.getID());
						v.add(po.getName());
						v.add(po.getType());
						v.add(po.getAmount());
						v.add(po.getImpPrice());
						v.add(po.getExpPrice());
						v.add(po.getRecentImpPrice());
						v.add(po.getRecentExpPrice());
						comTableModel.addRow(v);
					}

				}
				comTable.setVisible(true);
				comTable.revalidate();
				repaint();
			} catch (NullPointerException ne) {
			}
		}
	}




	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        String newName = (String) comTableModel.getValueAt(row, 1);
        String newType = (String) comTableModel.getValueAt(row, 2);
        String id = (String) comTableModel.getValueAt(row, 0);
        cbs.modifyCommodity(id,newName,newType);
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
	}
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO 自动生成的方法存根
		selectedRows=comTable.getSelectedRows();
	}
	public void finish(){

	}

}
class treeModelListener implements TreeModelListener{
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		TreePath treePath = e.getTreePath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
				.getLastPathComponent();
		DefaultMutableTreeNode tmpNode = node;
		System.out.println("test"+node.toString());
		try {
			int[] index = e.getChildIndices();
			node = (DefaultMutableTreeNode) node.getChildAt(index[0]);


		} catch (NullPointerException exc) {
		}

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

}


