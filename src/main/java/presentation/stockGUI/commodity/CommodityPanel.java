package presentation.stockGUI.commodity;

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
import java.util.ArrayList;
import java.util.Enumeration;
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

import PO.*;
import VO.UserVO;
import businesslogic.commoditybl.CommodityController;
import businesslogic.logbl.Log;
import businesslogic.logbl.LogInterface;
import businesslogic.utilitybl.ResultMessage;
import businesslogic.utilitybl.ShowMessageFrame;
import businesslogicservice.commodityBLService.CommodityBLService;


public class CommodityPanel extends JPanel implements   Serializable, ListSelectionListener,TableModelListener{
	
	private static final long serialVersionUID = 1L;
	CommodityBLService cbs = new CommodityController();
	LogInterface loginterface= new Log();
	JButton addTypeButton ,modifyTypeButton,delTypeButton,addComButton,delComButton,searchButton,backSearchButton;
	JTree tree;
	DefaultTreeModel treeModel = null;
	String nodeName = null;//原有节点名称
	JTable comTable;
	TreeNode pressedTreeNode;
	DefaultMutableTreeNode modifyTreeNode;
	ComTableModel comTableModel;
	ListSelectionModel selectionModel = null;
	int[] selectedRows = null;
    FindComPanel findPanel;
	JScrollPane comScrollPane ,typeScrollPane ;
	JTextArea searchArea ;
    JTextField comNameText,comTypeText,comImpText,comExpText,typeModifyText;
	String comName="",comType="",comImpStr="",comExpStr="",searchStr="",typeModifyStr="";
	double comImp=0.0,comExp=0.0;
	UserVO uvo;
	public CommodityPanel(UserVO uvo) throws IOException, ClassNotFoundException{
		this.uvo = uvo;
		findPanel = new FindComPanel(new ArrayList<CommodityPO>());
		findPanel.setVisible(false);
		this.add(findPanel);
		searchArea = new JTextArea("search:");
		searchArea.setSize(200,20);
		searchArea.setLocation(610,40);
		searchArea.getDocument().addDocumentListener(new searchTextListener());
		searchArea.addFocusListener(new searchAdapter());
		this.add(searchArea);
		backSearchButton= new JButton("BACK");
		backSearchButton.setSize(200,20);
		backSearchButton.setLocation(610, 61);
		backSearchButton.addActionListener(new backSearchListener());
		backSearchButton.setVisible(false);
		this.add(backSearchButton);
		searchButton= new JButton("GO");
		searchButton.setSize(200,20);
		searchButton.setLocation(610, 61);
		searchButton.addActionListener(new searchListener());
		this.add(searchButton);
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
		
		typeModifyText = new JTextField("新名称");
		typeModifyText.setSize(89,20);
		typeModifyText.setLocation(40	, 510);
		typeModifyText.getDocument().addDocumentListener(new typeModifyTextListener());
		typeModifyText.addFocusListener(new typeModifyAdapter());
		this.add(typeModifyText);
		modifyTypeButton= new JButton("MODIFY");
		modifyTypeButton.setSize(90,20);
		modifyTypeButton.setLocation(130, 510);
		modifyTypeButton.addActionListener(new modifyTypeListener());
		this.add(modifyTypeButton);
		
		addTypeButton= new JButton("ADD");
		addTypeButton.setSize(89,20);
		addTypeButton.setLocation(40, 530);
		addTypeButton.addActionListener(new addTypeListener());
		this.add(addTypeButton);
		
		delTypeButton= new JButton("DEL");
		delTypeButton.addActionListener(new delTypeListener());
		delTypeButton.setSize(90,20);
		delTypeButton.setLocation(130, 530);
		this.add(delTypeButton);
		
		addComButton= new JButton("ADD");
		addComButton.setSize(100,20);
		addComButton.setLocation(620, 530);
		addComButton.addActionListener(new addComListener());
		this.add(addComButton);
		delComButton= new JButton("DEL");
		delComButton.setSize(100,20);
		delComButton.setLocation(720, 530);
		delComButton.addActionListener(new delComListener());
		this.add(delComButton);
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
		tree.addMouseListener(new MouseHandle());
		treeModel = (DefaultTreeModel) tree.getModel();
		treeModel.addTreeModelListener(new treeModelListener());
		tree.setEditable(false);
		expandTree(tree);
		
		typeScrollPane = new JScrollPane(tree);
		typeScrollPane.setSize(180,380);
		typeScrollPane.setLocation(40,130);
		typeScrollPane.setVisible(true);
		
		this.setLayout(null);
		this.add(typeScrollPane);
		this.add(comScrollPane);
		this.setVisible(false);
	}
	public class typeModifyAdapter implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (typeModifyText.getText().equals("新名称")) {
				typeModifyText.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (typeModifyText.getText().equals("")) {
        		typeModifyText.setText("新名称");
            }
        }
		
	}
	public class searchAdapter implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (searchArea.getText().equals("search:")) {
				searchArea.setText("");
	        }
        }

        public void focusLost(FocusEvent e) {
        	if (searchArea.getText().equals("")) {
        		searchArea.setText("search:");
            }
        }
		
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
	public class delComListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
//			Vector r=new Vector();
			if(selectedRows == null)
				return;
			else{
				for(int i = 0 ; i < selectedRows.length ;i++){
					ResultMessage delComRM = cbs.delCommodity((String) comTableModel.getValueAt(selectedRows[i], 0));
					if(delComRM!=ResultMessage.Success){
						new ShowMessageFrame("删除失败");
						break;
						}
//					  System.out.println(selectedRows[i]);
//					System.out.println(comTableModel.getValueAt(selectedRows[i], 0));
					    }
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
				loginterface.buildlog(uvo.getName(), "删除商品");
				comTable.setVisible(true);
				comTable.revalidate();
				repaint();
			}
		}
		
	}
	public class searchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(searchStr!=""){
			ArrayList<CommodityPO> poList=cbs.findCommodity(searchStr);
			findPanel = new FindComPanel(poList);
			add(findPanel);
				comScrollPane.setVisible(false);
				typeScrollPane.setVisible(false);
				searchButton.setVisible(false);
				backSearchButton.setVisible(true);modifyTypeButton.setVisible(false);typeModifyText.setVisible(false);
				addTypeButton.setVisible(false); delTypeButton.setVisible(false);
				addComButton.setVisible(false);delComButton.setVisible(false);
				comNameText.setVisible(false);comTypeText.setVisible(false);
				comImpText.setVisible(false);comExpText.setVisible(false);
				findPanel.setVisible(true);
				repaint();
			}
			
		}
		
	}
	public class backSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			searchStr = searchArea.getText();
			comScrollPane.setVisible(true);
			typeScrollPane.setVisible(true);
			searchButton.setVisible(true);
			backSearchButton.setVisible(false);modifyTypeButton.setVisible(true);typeModifyText.setVisible(true);
			addTypeButton.setVisible(true); delTypeButton.setVisible(true);
			addComButton.setVisible(true);delComButton.setVisible(true);
			comNameText.setVisible(true);comTypeText.setVisible(true);
			comImpText.setVisible(true);comExpText.setVisible(true);
			findPanel.setVisible(false);
			repaint();
		}
		
	}
	
	public class addComListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(comName.equals("")||comName.equals("名称")||comType.equals("")||comType.equals("型号")||
					comImpStr.equals("")||comImpStr.equals("进价")||comExpStr.equals("")||comExpStr.equals("售价"))
			{
				new ShowMessageFrame("输入有误，请检查输入");
				return;
			}
			if(isNumber(comImpStr)&&isNumber(comExpStr)){
				comImp = Double.parseDouble(comImpStr);
				comExp = Double.parseDouble(comExpStr);
				if(comImp>=comExp){
					new ShowMessageFrame("请重新制定进价与售价");
					return;
					}
				if(pressedTreeNode==null){
					new ShowMessageFrame("请选择一个商品分类");
					return ;
				}
				if(pressedTreeNode.toString().equals("全部商品分类")){
					new ShowMessageFrame("不能在此分类下添加商品");
					return;
				}
				ResultMessage addComRM = cbs.addCommodity(comName,comType,0,comImp,comExp,pressedTreeNode.toString());
				if(addComRM!=ResultMessage.Success){
					new ShowMessageFrame("添加商品失败");
					return;
					}
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
				loginterface.buildlog(uvo.getName(), "增加商品: "+comName+comType);
			}else{
				new ShowMessageFrame("请在价格处输入数字");
			}
		}
		
	}

	public class searchTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			searchStr = searchArea.getText().trim();
			
		}
	    
	}
	public class typeModifyTextListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			typeModifyStr = typeModifyText.getText().trim();
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			typeModifyStr = typeModifyText.getText().trim();
			
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
	public class delTypeListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			TreePath treepath = tree.getSelectionPath();
			if (treepath != null) {
				// 下面两行取得选取节点的父节点.
				DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) treepath
						.getLastPathComponent();
				TreeNode parent = (TreeNode) selectionNode.getParent();
				if (parent != null) {
					// 由DefaultTreeModel的removeNodeFromParent()方法删除节点，包含它的子节点。
//					System.out.println(selectionNode+"zzzzzzzzzz");
//					System.out.println(treeModel.getChildCount(parent));
					ResultMessage deltypeRM = cbs.delCommodityType(selectionNode.toString());
					if(deltypeRM==ResultMessage.Success){
						treeModel.removeNodeFromParent(selectionNode);
						loginterface.buildlog(uvo.getName(), "删除商品分类: "+selectionNode.toString());
						}
					else
						new ShowMessageFrame("删除失败");
//					System.out.println("!"+selectionNode.toString());
					
//					cbs.saveTree(tree);
					
				}else
					new ShowMessageFrame("无可删除的分类");
			}
		}
		
	}
	public class modifyTypeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(typeModifyStr.equals("")||typeModifyStr.equals("新名称")){
				new ShowMessageFrame("修改商品分类失败");
				return ;
			}
			ResultMessage modifyTypeRM = cbs.modifyCommodityType(nodeName, typeModifyStr);
			if(modifyTypeRM==ResultMessage.Success){
			modifyTreeNode.setUserObject(typeModifyStr);
			loginterface.buildlog(uvo.getName(), "修改商品分类 : "+nodeName+"改为"+typeModifyStr);
			treeModel.reload();
			expandTree(tree);
			}else
				new ShowMessageFrame("修改商品分类失败");
			
		}
		
	}

	public class addTypeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode parentNode = null;
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("新分类");
			newNode.setAllowsChildren(true);
			TreePath parentPath = tree.getSelectionPath();

			// 取得新节点的父节点
			parentNode = (DefaultMutableTreeNode) (parentPath
					.getLastPathComponent());

			String parentName = (parentNode.toString());
			if(parentName==null){
				new ShowMessageFrame("请选择一个父分类");
				return ;
				}
			ResultMessage resultM = cbs.addCommodityType("新分类",parentName);
			if(resultM==ResultMessage.Success){
				// 由DefaultTreeModel的insertNodeInto（）方法增加新节点
				treeModel.insertNodeInto(newNode, parentNode, parentNode
						.getChildCount());
				// tree的scrollPathToVisible()方法在使Tree会自动展开文件夹以便显示所加入的新节点。若没加这行则加入的新节点
				// 会被 包在文件夹中，你必须自行展开文件夹才看得到。
				tree.scrollPathToVisible(new TreePath(newNode.getPath()));
				loginterface.buildlog(uvo.getName(), "增加商品分类: 新分类");
//				cbs.saveTree(tree);
			}else
				new ShowMessageFrame("添加商品分类失败");
			
            
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
				ResultMessage result = cbs.modifyCommodityType(nodeName , node.toString());
				
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
	

	public static boolean isInteger(String value) {
		  try {
		   Integer.parseInt(value);
		   return true;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }

		 /**
		  * 判断字符串是否是浮点数
		  */
     public static boolean isDouble(String value) {
		  try {
		   Double.parseDouble(value);
		   if (value.contains("."))
		    return true;
		   return false;
		  } catch (NumberFormatException e) {
		   return false;
		  }
		 }
     public static boolean isNumber(String value) {
    	  return isInteger(value) || isDouble(value);
    	 }
	
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();  
        int column = e.getColumn();  
        String newName = (String) comTableModel.getValueAt(row, 1);
        String newType = (String) comTableModel.getValueAt(row, 2);
        String id = (String) comTableModel.getValueAt(row, 0);
        cbs.modifyCommodity(id,newName,newType);
        loginterface.buildlog(uvo.getName(), "修改商品: "+id+"改为 "+newName+newType);
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
	private void expandTree(JTree tree) {
        // 根节点
        TreeNode node = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(node), true);
    }
	private void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();

        if (node.getChildCount() > 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
}
