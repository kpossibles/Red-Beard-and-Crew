import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class CGUI extends JFrame {

	private JPanel contentPane, rPanel3= new JPanel();
	private Console c;
	private JRadioButton radioChannel1, radioChannel3, radioChannel5, 
    radioChannel7, radioChannel2, radioChannel4, 
    radioChannel6, radioChannel8;
	private JTextArea printDisplayTex;
	private JLabel labelLegend, labelheader;
	private JComboBox<Object> typeSelect1, typeSelect3, typeSelect5, 
    typeSelect7, typeSelect2, typeSelect4, typeSelect6, typeSelect8;
	private JButton buttonPower, buttonFunction, buttonSwap, printPower, 
    button1, button2, button3, button4, 
    button5, button6, button7, button8, button9, 
    buttonStar, button0, buttonPound, buttonTrigger1, 
    buttonTrigger3, buttonTrigger5, buttonTrigger7, 
    buttonTrigger2, buttonTrigger6, buttonTrigger4, buttonTrigger8;
    private String tempRacer="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new CGUI();
	}

	/**
	 * Create the frame.
	 */
	public CGUI() {
		c = new Console();
		setSize(550, 300);
		setTitle("ChronoTimer");
		getContentPane().setLayout(new BorderLayout(0, 0));
		setupGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Sends command to console
	 * 
	 * @param command
	 */
	private void sendCommand(String command){
        c.input(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) +"\t"+command);
    }
	
	/**
	 * Sets ActionListener for button
	 * 
	 * @param i
	 * @param command
	 */
	private void setActionListener(AbstractButton i, String command){
		i.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                sendCommand(command);
            }
        });
	}
	
	public void setupGUI() {
		// left
		JPanel lPanel = new JPanel();
		lPanel.setSize(getWidth()/3, getHeight());
		getContentPane().add(lPanel, BorderLayout.WEST);
		lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.Y_AXIS));
			
			// top left
			JPanel lPanel1 = new JPanel();
			lPanel1.setSize(lPanel.getWidth(), lPanel.getHeight()/3);
			
			buttonPower = new JButton("Power"); 	
			buttonPower.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	            {
	                sendCommand("POWER");
	            }
	        });
			lPanel1.add(buttonPower);
			
			// mid left
			JPanel lPanel2 = new JPanel();
			lPanel2.setSize(lPanel.getWidth(), lPanel.getHeight()/3);
			lPanel2.setLayout(new BoxLayout(lPanel2, BoxLayout.Y_AXIS));
			
			buttonFunction = new JButton("Function"); 
			buttonFunction.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	            {
	                // TODO
	            }
	        });
			JPanel lPanel2a = new JPanel();
			lPanel2a.setSize(lPanel2.getWidth(), 20);
			lPanel2a.add(buttonFunction);
			JPanel lPanel2b = new JPanel();
			lPanel2b.setSize(lPanel2.getWidth(), 20);
			lPanel2b.setLayout(new GridLayout(1, 4, 10, 0));
			JLabel up = new JLabel("▲");
			up.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel down = new JLabel("▼");
			down.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel left = new JLabel("◄");
			left.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel right = new JLabel("►");
			right.setHorizontalAlignment(SwingConstants.CENTER);
	
			lPanel2b.add(left);
			lPanel2b.add(right);
			lPanel2b.add(up);
			lPanel2b.add(down);
			
			lPanel2.add(lPanel2a);
			lPanel2.add(lPanel2b);
			
			// bottom left
			JPanel lPanel3 = new JPanel();
			lPanel3.setSize(lPanel.getWidth(), lPanel.getHeight()/3);
			
			buttonSwap = new JButton("SWAP"); 
			buttonSwap.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	            {
	                // TODO
	            }
	        });
			lPanel3.add(buttonSwap);
			
			lPanel.add(lPanel1);
			lPanel.add(lPanel2);
			lPanel.add(lPanel3);
			
		
		// middle
		JPanel mPanel = new JPanel();
		mPanel.setSize(getWidth()/3, getHeight());
		getContentPane().add(mPanel, BorderLayout.CENTER);
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
			
			// top middle
			JPanel mPanel1 = new JPanel();
			mPanel1.setSize(mPanel.getWidth(), mPanel.getHeight()/3);
			JPanel mPanel1a = new JPanel();
			mPanel1a.setSize(mPanel.getWidth(), 25);
			mPanel1.add(mPanel1a);
			JPanel mPanel1b = new JPanel();
			mPanel1b.setSize(mPanel.getWidth(), 25);
			mPanel1.add(mPanel1b);
			
			// mid middle
			JPanel mPanel2 = new JPanel();
			mPanel2.setSize(mPanel.getWidth(), mPanel.getHeight()/3);
			
			JTextArea textArea = new JTextArea();
			textArea.setRows(10);
			textArea.setColumns(15);
			mPanel2.add(textArea);
			
			// bottom middle
			JPanel mPanel3 = new JPanel();
			mPanel3.setSize(mPanel.getWidth(), mPanel.getHeight()/4);
			labelLegend = new JLabel("Queue/Running/Final");
			mPanel3.add(labelLegend);
			
			mPanel.add(mPanel1);
			// split into 4 rows
			JLabel label = new JLabel("Start");
			mPanel1.add(label);
			JLabel label_1 = new JLabel("Enable/Disable");
			mPanel1b.add(label_1);
			JLabel label_2 = new JLabel("Finish");
			mPanel1.add(label_2);
			JLabel label_3 = new JLabel("Enable/Disable");
			mPanel1.add(label_3);
			
			radioChannel1 = new JRadioButton();
			radioChannel1.setSelected(false);
	        setActionListener(radioChannel1, "tog 1");
	        mPanel1a.add(radioChannel1);
	        
	        radioChannel3 = new JRadioButton();
	        radioChannel3.setSelected(false);
	        setActionListener(radioChannel3, "tog 3");
	        mPanel1a.add(radioChannel3);
	        
	        radioChannel5 = new JRadioButton();
	        radioChannel5.setSelected(false);
	        setActionListener(radioChannel5, "tog 5");
	        mPanel1a.add(radioChannel5);
	        
	        radioChannel7 = new JRadioButton();
	        radioChannel7.setSelected(false);
	        setActionListener(radioChannel7, "tog 7");
	        mPanel1a.add(radioChannel7);

	        radioChannel2 = new JRadioButton();
	        radioChannel2.setSelected(false);
	        setActionListener(radioChannel2, "tog 2");
	        mPanel1.add(radioChannel2);
	        
	        radioChannel4 = new JRadioButton();
	        radioChannel4.setSelected(false);
	        setActionListener(radioChannel4, "tog 4");
	        mPanel1.add(radioChannel4);
	        
	        radioChannel6 = new JRadioButton();
	        radioChannel6.setSelected(false);
	        setActionListener(radioChannel6, "tog 6");
	        mPanel1.add(radioChannel6);
	        
	        radioChannel8 = new JRadioButton();
	        radioChannel8.setSelected(false);
	        setActionListener(radioChannel8, "tog 8");
	        mPanel1.add(radioChannel8);
			
			mPanel.add(mPanel2);
			mPanel.add(mPanel3);
		
		// right
		JPanel rPanel = new JPanel();
		rPanel.setSize(getWidth()/3, getHeight());
		getContentPane().add(rPanel, BorderLayout.EAST);
		rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.Y_AXIS));
			// top right
			JPanel rPanel1 = new JPanel();
			rPanel1.setSize(rPanel.getWidth(), rPanel.getHeight()/3);
			
			printPower = new JButton("Printer Pwr"); 
			rPanel1.add(printPower);
			
			// mid right
			JPanel rPanel2 = new JPanel();
			rPanel2.setSize(rPanel.getWidth(), rPanel.getHeight()/3);
			
			JTextArea printArea = new JTextArea();
			textArea.setRows(10);
			textArea.setColumns(15);
			rPanel2.add(printArea);
			
			// bottom right
			rPanel3.setSize(rPanel.getWidth(), rPanel.getHeight()/3);
			rPanel3.setLayout(new GridLayout(4,3,1,1));
			
			button1 = new JButton("1");
			button2 = new JButton("2");
			button3 = new JButton("3");
			button4 = new JButton("4");
			button5 = new JButton("5");
			button6 = new JButton("6");
			button7 = new JButton("7");
			button8 = new JButton("8");
			button9 = new JButton("9");
			buttonStar = new JButton("*");
			
	        buttonStar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                sendCommand("num *");
	            }});
			button0 = new JButton("0");
			buttonPound = new JButton("#");
			buttonPound.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	sendCommand("num "+tempRacer);
	            	tempRacer="";
	            }});
			
			button1.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e)
	            {
	            	tempRacer+=1;
	            }
	        });
			rPanel3.add(button1);
			rPanel3.add(button2);
			rPanel3.add(button3);
			rPanel3.add(button4);
			rPanel3.add(button5);
			rPanel3.add(button6);
			rPanel3.add(button7);
			rPanel3.add(button8);
			rPanel3.add(button9);
			rPanel3.add(buttonStar);
			rPanel3.add(button0);
			rPanel3.add(buttonPound);
			buttonAction(button2, 2);
			buttonAction(button3, 3);
			buttonAction(button4, 4);
			buttonAction(button5, 5);
			buttonAction(button6, 6);
			buttonAction(button7, 7);
			buttonAction(button8, 8);
			buttonAction(button9, 9);
			buttonAction(button0, 0);
			
			
			rPanel.add(rPanel1);
			rPanel.add(rPanel2);
			rPanel.add(rPanel3);
		
		
		
	}
	private void buttonAction(JButton b, int i){
		b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	tempRacer+=i;
            }
        });
	}

}
