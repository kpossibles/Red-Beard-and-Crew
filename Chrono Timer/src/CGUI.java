import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class CGUI extends JFrame {

	private JPanel rPanel3= new JPanel();
	private Console c;
	private JRadioButton radioChannel1, radioChannel3, radioChannel5, 
    radioChannel7, radioChannel2, radioChannel4, 
    radioChannel6, radioChannel8;
	private JTextArea printDisplayTex;
	private JLabel labelLegend;
	private JButton buttonPower, buttonFunction, buttonSwap, printPower, 
    button1, button2, button3, button4, 
    button5, button6, button7, button8, button9, 
    buttonStar, button0, buttonPound, buttonTrigger1, 
    buttonTrigger3, buttonTrigger5, buttonTrigger7, 
    buttonTrigger2, buttonTrigger6, buttonTrigger4, buttonTrigger8;
    private String tempRacer="";
    private JPanel mPanel1a, mPanel1d, panel_1, panel_2;

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
		setSize(560, 400);
		setTitle("ChronoTimer");
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
            @Override
			public void actionPerformed(ActionEvent e)
            {
                sendCommand(command);
            }
        });
	}
	
	void setupGUI() {
		getContentPane().setLayout(null);
		// left
		JPanel lPanel = new JPanel();
		lPanel.setLocation(0, 0);
		lPanel.setSize(110, 377);
		getContentPane().add(lPanel);
			
			buttonPower = new JButton("Power"); 	
			buttonPower.setBounds(5, 6, 100, 40);
			buttonPower.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	                sendCommand("POWER");
	            }
	        });
			lPanel.setLayout(null);
			lPanel.add(buttonPower);
			
			buttonFunction = new JButton("Function");
			buttonFunction.setBounds(5, 131, 100, 40);
			lPanel.add(buttonFunction);
			buttonFunction.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	                // TODO
	            }
	        });
			
			// bottom left
			JPanel lPanel3 = new JPanel();
			lPanel3.setSize(lPanel.getWidth(), lPanel.getHeight()/3);
			
			buttonSwap = new JButton("SWAP"); 
			buttonSwap.setLocation(5, 273);
			buttonSwap.setSize(100, 40);
			buttonSwap.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	                // TODO
	            }
	        });
			lPanel.add(buttonSwap);
			JPanel lPanelNav = new JPanel();
			lPanelNav.setBounds(5, 176, 100, 29);
			lPanel.add(lPanelNav);
			lPanelNav.setLayout(null);
			
			JButton left = new JButton("◄");
			left.setBounds(4, 5, 20, 20);
			lPanelNav.add(left);
			left.setHorizontalAlignment(SwingConstants.CENTER);
			JButton right = new JButton("►");
			right.setBounds(28, 5, 20, 20);
			lPanelNav.add(right);
			right.setHorizontalAlignment(SwingConstants.CENTER);
			JButton up = new JButton("▲");
			up.setBounds(52, 5, 20, 20);
			lPanelNav.add(up);
			up.setHorizontalAlignment(SwingConstants.CENTER);
			JButton down = new JButton("▼");
			down.setBounds(76, 5, 20, 20);
			lPanelNav.add(down);
			down.setHorizontalAlignment(SwingConstants.CENTER);
			
			
			
		
		// middle
		JPanel mPanel = new JPanel();
		mPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		mPanel.setLocation(109, 0);
		mPanel.setSize(250, 377);
		getContentPane().add(mPanel);
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
			
			// top middle
			JPanel mPanel1 = new JPanel();
			mPanel1.setLayout(null);
			
			panel_1 = new JPanel();
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_1.setBounds(-39, 9, 290, 80);
			mPanel1.add(panel_1);
			panel_1.setLayout(new GridLayout(2, 2, 5, 0));
			// split into 4 rows
			JLabel label = new JLabel("Start");
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_1.add(label);
			label.setFont(new Font("Lucida Grande", Font.BOLD, 21));
			mPanel1a = new JPanel();
			panel_1.add(mPanel1a);
			mPanel1a.setLayout(null);
			
			buttonTrigger1 = new JButton();
			buttonTrigger1.setBounds(-4, 5, 40, 30);
			buttonTrigger1.setText("1");
			mPanel1a.add(buttonTrigger1);
			buttonTrigger1.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 1");
			    }
			});
			buttonTrigger3 = new JButton();
			buttonTrigger3.setBounds(32, 5, 40, 30);
			buttonTrigger3.setText("3");
			mPanel1a.add(buttonTrigger3);
			buttonTrigger3.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 3");
			    }
			});
			buttonTrigger5 = new JButton();
			buttonTrigger5.setBounds(68, 5, 40, 30);
			buttonTrigger5.setText("5");
			mPanel1a.add(buttonTrigger5);
			buttonTrigger5.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 5");
			    }
			});
			buttonTrigger7 = new JButton();
			buttonTrigger7.setBounds(104, 5, 40, 30);
			buttonTrigger7.setText("7");
			mPanel1a.add(buttonTrigger7);
			JLabel label_1 = new JLabel("Enable/Disable");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_1.add(label_1);
			JPanel mPanel1b = new JPanel();
			panel_1.add(mPanel1b);
			mPanel1b.setLayout(null);
			
			radioChannel1 = new JRadioButton();
			radioChannel1.setBounds(5, 8, 28, 23);
			radioChannel1.setSelected(false);
			setActionListener(radioChannel1, "tog 1");
			mPanel1b.add(radioChannel1);
			
			radioChannel3 = new JRadioButton();
			radioChannel3.setBounds(38, 8, 28, 23);
			radioChannel3.setSelected(false);
			setActionListener(radioChannel3, "tog 3");
			mPanel1b.add(radioChannel3);
			
			radioChannel5 = new JRadioButton();
			radioChannel5.setBounds(71, 8, 28, 23);
			radioChannel5.setSelected(false);
			setActionListener(radioChannel5, "tog 5");
			mPanel1b.add(radioChannel5);
			
			radioChannel7 = new JRadioButton();
			radioChannel7.setBounds(104, 8, 28, 23);
			radioChannel7.setSelected(false);
			setActionListener(radioChannel7, "tog 7");
			mPanel1b.add(radioChannel7);
			buttonTrigger7.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 7");
			    }
			});
			
			panel_2 = new JPanel();
			panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_2.setBounds(-39, 98, 290, 80);
			mPanel1.add(panel_2);
			panel_2.setLayout(new GridLayout(0, 2, 5, 0));
			JLabel lblFinish = new JLabel("Finish");
			lblFinish.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFinish.setFont(new Font("Lucida Grande", Font.BOLD, 21));
			panel_2.add(lblFinish);
			JPanel mPanel1c = new JPanel();
			panel_2.add(mPanel1c);
			mPanel1c.setLayout(null);
			
			buttonTrigger2 = new JButton();
			buttonTrigger2.setBounds(-4, 5, 40, 30);
			buttonTrigger2.setText("2");
			mPanel1c.add(buttonTrigger2);
			buttonTrigger2.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 2");
			    }
			});
			buttonTrigger4 = new JButton();
			buttonTrigger4.setBounds(32, 5, 40, 30);
			buttonTrigger4.setText("4");
			mPanel1c.add(buttonTrigger4);
			buttonTrigger4.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 4");
			    }
			});
			buttonTrigger6 = new JButton();
			buttonTrigger6.setBounds(68, 5, 40, 30);
			buttonTrigger6.setText("6");
			mPanel1c.add(buttonTrigger6);
			buttonTrigger6.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 6");
			    }
			});
			buttonTrigger8 = new JButton();
			buttonTrigger8.setBounds(104, 5, 40, 29);
			buttonTrigger8.setText("8");
			mPanel1c.add(buttonTrigger8);
			JLabel label_3 = new JLabel("Enable/Disable");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_2.add(label_3);
			
			mPanel1d = new JPanel();
			panel_2.add(mPanel1d);
			mPanel1d.setLayout(null);
			
			radioChannel2 = new JRadioButton();
			radioChannel2.setBounds(4, 8, 28, 23);
			mPanel1d.add(radioChannel2);
			radioChannel2.setSelected(false);
			setActionListener(radioChannel2, "tog 2");
			
			radioChannel4 = new JRadioButton();
			radioChannel4.setBounds(37, 8, 28, 23);
			mPanel1d.add(radioChannel4);
			radioChannel4.setSelected(false);
			setActionListener(radioChannel4, "tog 4");
			
			radioChannel6 = new JRadioButton();
			radioChannel6.setBounds(70, 8, 28, 23);
			mPanel1d.add(radioChannel6);
			radioChannel6.setSelected(false);
			setActionListener(radioChannel6, "tog 6");
			
			radioChannel8 = new JRadioButton();
			radioChannel8.setBounds(103, 8, 28, 23);
			mPanel1d.add(radioChannel8);
			radioChannel8.setSelected(false);
			setActionListener(radioChannel8, "tog 8");
			buttonTrigger8.addActionListener(new ActionListener() {
			    @Override
				public void actionPerformed(ActionEvent e)
			    {
			        sendCommand("trig 8");
			    }
			});
			
			// mid middle
			JPanel mPanel2 = new JPanel();
			mPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			mPanel2.setSize(mPanel.getWidth(), mPanel.getHeight()/3);
			mPanel2.setLayout(null);
			
			printDisplayTex = new JTextArea();
			printDisplayTex.setBounds(35, 15, 180, 125);
			printDisplayTex.setRows(10);
			printDisplayTex.setColumns(15);
			mPanel2.add(printDisplayTex);
			
			mPanel.add(mPanel1);
			mPanel.add(mPanel2);
		
		// right
		JPanel rPanel = new JPanel();
		rPanel.setLocation(360, 0);
		rPanel.setSize(200, 377);
		getContentPane().add(rPanel);
			// top right
			JPanel rPanel1 = new JPanel();
			rPanel1.setLocation(0, 0);
			rPanel1.setSize(200, 190);
			
			// mid right
			JPanel rPanel2 = new JPanel();
			rPanel2.setLocation(0, 188);
			rPanel2.setSize(200, 190);
			
			JTextArea printArea = new JTextArea();
			printArea.setBounds(-14, 58, 0, 16);
			printDisplayTex.setRows(10);
			printDisplayTex.setColumns(15);
			labelLegend = new JLabel("Queue/Running/Final");
			labelLegend.setBounds(57, 155, 136, 16);
			mPanel2.add(labelLegend);
			rPanel2.setLayout(null);
			rPanel2.add(printArea);
			rPanel.setLayout(null);
			
			
			rPanel.add(rPanel1);
			
			printPower = new JButton("Printer Pwr");
			rPanel1.add(printPower);
			rPanel.add(rPanel2);
			rPanel3.setBounds(10, 15, 180, 123);
			rPanel2.add(rPanel3);
			rPanel3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			rPanel3.setLayout(new GridLayout(4,3,0,1));
			
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
	            @Override
				public void actionPerformed(ActionEvent e) {
	                sendCommand("num *");
	            }});
	        button0 = new JButton("0");
	        buttonPound = new JButton("#");
	        buttonPound.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e) {
	            	sendCommand("num "+tempRacer);
	            	tempRacer="";
	            }});
	        
	        button1.addActionListener(new ActionListener() {
	            @Override
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
		
		
		
	}
	private void buttonAction(JButton b, int i){
		b.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e)
            {
            	tempRacer+=i;
            }
        });
	}
}
