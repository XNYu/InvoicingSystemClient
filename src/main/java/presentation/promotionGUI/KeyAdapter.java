package presentation.promotionGUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyAdapter implements KeyListener{  
    public void keyTyped(KeyEvent e) {  
        int keyChar = e.getKeyChar();                 
        if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar==KeyEvent.VK_PERIOD)){  
              
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
