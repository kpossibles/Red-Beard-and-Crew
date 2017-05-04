package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class NavPanel extends JPanel{
	private JButton up, down, left, right;
	private int rowSelected=0;
	
	public NavPanel(){
		setLayout(new GridLayout(1,4,0,0));
		setBackground(new Color(0,0,0,0));
		setSize(175, 60);
		
		up = new JButton("▲");
		up.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(up);
		
		down = new JButton("▼");
		down.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(down);
		
		left = new JButton("◄");
		left.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(left);
		
		right = new JButton("►");
		right.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		add(right);
	}
	
	/**
	 * Sets ActionListener for button
	 * 
	 * @param i
	 * @param command
	 */
	void setActionListener(AbstractButton i, String arrow, JTextArea textbox){
		i.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {// TODO - implement arrow navigation
            	String original = textbox.getText();
            	String[]strArr = original.split(original, '\n');
                if(arrow=="up"){
                	if(rowSelected>0){
                		strArr[rowSelected]=strArr[rowSelected].substring(2);
                		strArr[rowSelected-1]="> "+strArr[rowSelected-1];
                		rowSelected--;
                	}
                } else if(arrow=="down"){
                	if(rowSelected<strArr.length){
                		strArr[rowSelected]=strArr[rowSelected].substring(2);
                		strArr[rowSelected+1]="> "+strArr[rowSelected+1];
                		rowSelected++;
                	}
                }else if(arrow=="left"){
                	// you selected that function
                }else {
                	// go back
                }
                //rebuild string
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < strArr.length; i++) {
                   strBuilder.append(strArr[i]+'\n');
                }
                String newString = strBuilder.toString();
            	
                textbox.setText(newString);
            }
        });
	}
}
