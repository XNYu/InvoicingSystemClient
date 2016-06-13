package presentation.promotionGUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class utility {
	
	public static String dateToString(Date date){
		String sdate;  
		sdate=(new SimpleDateFormat("yyyyMMdd")).format(date);
		return sdate;
	}
	
	public static Date stringToDate(String s){
		DateFormat fmt =new SimpleDateFormat("yyyyMMdd"); 
		Date date=null;
		try {
			date = fmt.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static void setInfo(String text,JPanel panel){
		final JDialog infoDialog=new JDialog();
		infoDialog.setSize(250,80);
		infoDialog.setLocationRelativeTo(panel);
		infoDialog.getContentPane();
		
		JLabel infoLabel=new JLabel(text);
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setFont(new java.awt.Font("",1,18));
		infoLabel.setVisible(true);
		
		/*ImageIcon bg = new ImageIcon("GUI/confirm.png"); 
		infoLabel.setIcon(bg);
		infoDialog.setUndecorated(true);
		*/
		infoDialog.getContentPane().add(infoLabel);
		infoDialog.setVisible(true);
		infoDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	public class KeyAdapter implements KeyListener{  
        public void keyTyped(KeyEvent e) {  
            int keyChar = e.getKeyChar();                 
            if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){  
                  
            }else{  
                e.consume(); //关键，屏蔽掉非法输入  
            }  
        }

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}	
	}
	

}
