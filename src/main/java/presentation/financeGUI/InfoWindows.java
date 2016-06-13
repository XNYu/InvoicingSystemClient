package presentation.financeGUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoWindows {
	public InfoWindows(String s){
		Toolkit toolkit=Toolkit.getDefaultToolkit();
	    Dimension screen=toolkit.getScreenSize();
		final JFrame f  = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400,300);
		f.setResizable(false);
		int x=(screen.width -f.getWidth())/2;
	    int y=(screen.height -f.getHeight())/2;
	    f.setLocation(x,y);
	    f.setLayout(null);
        f.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setBackground(new Color(230,190,140));
        panel.setVisible(true);


        JLabel nameLabel = new JLabel(s,JLabel.CENTER);
        nameLabel.setSize(400,60);
        nameLabel.setLocation(0,100);
        nameLabel.setFont(new Font("黑体", Font.BOLD, 30));
        panel.add(nameLabel);


        JButton confirmButton = new JButton();
		confirmButton.setSize(100,50);
		confirmButton.setLocation(150,220);
		confirmButton.setIcon(new ImageIcon("GUI/confirm.png"));
		confirmButton.setRolloverIcon(new ImageIcon("GUI/confirm2.png"));
		confirmButton.setFocusPainted(false);
		confirmButton.setBorderPainted(false);
		confirmButton.setBackground(new Color(80,100,220));
		panel.add(confirmButton);
		confirmButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根

				f.setVisible(false);
			}
			}
		);

		f.add(panel);
		f.setVisible(true);
	}


}
