package gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

import chronotimer.Console;
import chronotimer.Printer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * The Class ChronoGui.
 *
 * @author Red Beard & Crew
 */
@SuppressWarnings("serial")
public class ChronoGui extends JFrame {
	private Console c;
	private Printer p;
	private JRadioButton radioChannel1, radioChannel3, radioChannel5, radioChannel7, radioChannel2, radioChannel4,
			radioChannel6, radioChannel8;
	private JTextArea displayText, printerText;
	private JLabel labelLegend, enable01, enable02, lblStart, lblFinish;
	private JButton buttonPower, buttonFunction, buttonEndRun, buttonSwap, printPower, button1, button2, button3,
			button4, button5, button6, button7, button8, button9, buttonStar, button0, buttonPound, buttonTrigger1,
			buttonTrigger3, buttonTrigger5, buttonTrigger7, buttonTrigger2, buttonTrigger6, buttonTrigger4,
			buttonTrigger8;
	private JPanel lPanel, mPanel, mPanel1, mPanel1a, mPanel1b, mPanel1c, mPanel1d, mPanel2, panel_1, panel_2, rPanel,
			rPanel1, rPanel2, keypad, navPanel, powerStatus;
	private String tempRacer = "";
	private JScrollPane scroll, scroll2;
//	private JComboBox<String> eventType;
	private String offWarning = "The Chronotimer is currently off.\nTry 'POWER' to turn it on.";
	private BackPanel backpanel;
	private JButton up, down, left, right;
	private boolean fcnBtnOn;
	private int rowSelected=0;
	private static final int WIN_HEIGHT = 650;

	/**
	 * Instantiates a new Chronotimer GUI.
	 */
	public ChronoGui() {
		getContentPane().setBackground(new Color(51, 153, 204));
		c = new Console();
		p = new Printer();
		setTitle("ChronoTimer");
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}
	
	

	/**
	 * Sets the ActionListener for sensor.
	 *
	 * @param i the i
	 * @param num the panel number
	 */
//	public void setSensorListener(JPanel i, int num) {
//		i.addMouseListener(new MouseListener() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO - implement sensor type selection
//				Object[] options = { "EYE", "GATE", "PAD" };
//				ImageIcon icon = null;
//				String s = (String) JOptionPane.showInputDialog(null, "Select a sensor below.", "Sensor Selection",
//						JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
//
//				// If a string was returned, set a sensor.
//				if ((s != null) && (s.length() > 0)) {
//					backpanel.toggle(num);
//					c.setSensor(s, num);
//				}
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//			}
//		});
//	}

	/**
	 * Sets up the GUI
	 */
	void setupGUI() {
		setMinimumSize(new Dimension(850, WIN_HEIGHT));
		setSize(850, WIN_HEIGHT);
		getContentPane().setLayout(null);
		
		// back panel
		backpanel = new BackPanel();
		backpanel.setBounds(0, 500, 850, 130);
		getContentPane().add(backpanel);
		
		// left
		setupLPanel();
		
		// middle
		setupMPanel();

		// right
		setupRPanel();
	}

	private void setupLPanel(){
		lPanel = new JPanel();
		lPanel.setBackground(new Color(51, 153, 204));
		lPanel.setBounds(0, 0, 200, 500);
		getContentPane().add(lPanel);
		lPanel.setLayout(null);
		
		Power = new JPanel();
		Power.setBackground(new Color(51, 153, 204));
		Power.setBounds(10, 18, 180, 74);
		lPanel.add(Power);
		Power.setLayout(null);
		
				buttonPower = new JButton("Power");
				buttonPower.setBounds(10, 12, 125, 50);
				Power.add(buttonPower);
				
				powerStatus = new JPanel();
				powerStatus.setBounds(145, 24, 25, 25);
				Power.add(powerStatus);
				powerStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				powerStatus.setBackground(Color.RED);
				buttonPower.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sendCommand("POWER");
						if (c.isOn()) {
							sendCommand("NEWRUN");
							powerStatus.setBackground(Color.GREEN);
						}
						else{
							powerStatus.setBackground(Color.RED);
						}
						displayText.setText("");
						fcnBtnOn=false;
					}
				});

		// eventType.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e)
		// {
		// if(c.isOn()){
		// sendCommand("EVENT "+eventType.getSelectedItem().toString());
		// sendCommand("NEWRUN");
		// } else {
		// offWarning();
		// }
		// }
		// });

		buttonEndRun = new JButton("End Run");
		buttonEndRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendCommand("ENDRUN");
			}
		});

		buttonFunction = new JButton("Function");
		buttonFunction.setBounds(12, 197, 175, 50);
		lPanel.add(buttonFunction);
		buttonFunction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO - import fcn list to displayText
				fcnBtnOn = !fcnBtnOn;
				if(fcnBtnOn && c.isOn()){
					String fcnList = "> RESET\n" + "SET TIME\n" + "TOGGLE CHANNEL\n" + "CONNECT SENSOR\n"
							+ "DISCONNECT SENSOR\n" + "SET EVENT\n" + "NEWRUN\n" + "ENDRUN\n" + "PRINT\n" + "EXPORT\n"
							+ "ADD A RACER\n" + "CLEAR\n" + "SWAP\n" + "MARK AS DNF\n" + "TRIGGER A CHANNEL\n" + "START\n"
							+ "FINISH";
					displayText.setText(fcnList);
				}
				else{
					if(c.isOn())
						displayText.setText("");
					else
						offWarning();
				}
			}
		});

		buttonSwap = new JButton("SWAP");
		buttonSwap.setLocation(12, 327);
		buttonSwap.setSize(175, 50);
		buttonSwap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("SWAP");
			}
		});

		setNavPanel();
		lPanel.add(navPanel);
//		navPanel.setActionListener(up, "up", displayText);
		lPanel.add(buttonSwap);
		buttonEndRun.setBounds(12, 397, 175, 50);
		lPanel.add(buttonEndRun);

	}
	
	private void setupMPanel() {
		mPanel = new JPanel();
		mPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mPanel.setBackground(new Color(51, 153, 204));
		getContentPane().add(mPanel);
		mPanel.setBounds(200, 0, 400, 500);
		
		// top middle
		mPanel1 = new JPanel();
		mPanel1.setBackground(new Color(51, 153, 204));
		mPanel1.setBounds(0, 0, 400, 170);
		mPanel1.setLayout(null);
	
		panel_1 = new JPanel();
		panel_1.setBounds(0, 10, 400, 80);
		panel_1.setBackground(new Color(51, 153, 204));
		mPanel1.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
	
		// m1: split into 4 panels
		lblStart = new JLabel("Start");
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblStart);
		lblStart.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		
		mPanel1a = new JPanel();
		mPanel1a.setBackground(new Color(51, 153, 204));
		mPanel1a.setLayout(new GridLayout(1, 4, 0, 0));
		panel_1.add(mPanel1a);
	
			buttonTrigger1 = new JButton();
			buttonTrigger1.setBounds(0, 5, 40, 30);
			buttonTrigger1.setText("1");
			mPanel1a.add(buttonTrigger1);
			setActionListener(buttonTrigger1, "trig 1");
			buttonTrigger3 = new JButton();
			buttonTrigger3.setBounds(32, 5, 40, 30);
			buttonTrigger3.setText("3");
			mPanel1a.add(buttonTrigger3);
			setActionListener(buttonTrigger3, "trig 3");
			buttonTrigger5 = new JButton();
			buttonTrigger5.setBounds(68, 5, 40, 30);
			buttonTrigger5.setText("5");
			mPanel1a.add(buttonTrigger5);
			setActionListener(buttonTrigger5, "trig 5");
			buttonTrigger7 = new JButton();
			buttonTrigger7.setBounds(104, 5, 40, 30);
			buttonTrigger7.setText("7");
			mPanel1a.add(buttonTrigger7);
			setActionListener(buttonTrigger7, "trig 7");
		
		enable01 = new JLabel("Enable/Disable");
		enable01.setHorizontalAlignment(SwingConstants.RIGHT);
	
		panel_1.add(enable01);
		mPanel1b = new JPanel();
		mPanel1b.setBackground(new Color(51, 153, 204));
		mPanel1b.setLayout(new GridLayout(1, 4, 0, 0));
		panel_1.add(mPanel1b);
	
			radioChannel1 = new JRadioButton();
			radioChannel1.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel1.setBounds(5, 8, 28, 23);
			radioChannel1.setSelected(false);
			setActionListener(radioChannel1, "tog 1");
			mPanel1b.add(radioChannel1);
		
			radioChannel3 = new JRadioButton();
			radioChannel3.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel3.setBounds(38, 8, 28, 23);
			radioChannel3.setSelected(false);
			setActionListener(radioChannel3, "tog 3");
			mPanel1b.add(radioChannel3);
		
			radioChannel5 = new JRadioButton();
			radioChannel5.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel5.setBounds(71, 8, 28, 23);
			radioChannel5.setSelected(false);
			setActionListener(radioChannel5, "tog 5");
			mPanel1b.add(radioChannel5);
		
			radioChannel7 = new JRadioButton();
			radioChannel7.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel7.setBounds(104, 8, 28, 23);
			radioChannel7.setSelected(false);
			setActionListener(radioChannel7, "tog 7");
			mPanel1b.add(radioChannel7);
		
		// m2: split into 4 panels
		panel_2 = new JPanel();
		panel_2.setBounds(0, 90, 400, 80);
		panel_2.setBackground(new Color(51, 153, 204));
		mPanel1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblFinish = new JLabel("Finish");
		lblFinish.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFinish.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		panel_2.add(lblFinish);
		
		mPanel1c = new JPanel();
		mPanel1c.setBackground(new Color(51, 153, 204));
		mPanel1c.setLayout(new GridLayout(1, 4, 0, 0));
		panel_2.add(mPanel1c);
	
			buttonTrigger2 = new JButton();
			buttonTrigger2.setBounds(-4, 5, 40, 30);
			buttonTrigger2.setText("2");
			mPanel1c.add(buttonTrigger2);
			setActionListener(buttonTrigger2, "trig 2");
			buttonTrigger4 = new JButton();
			buttonTrigger4.setBounds(32, 5, 40, 30);
			buttonTrigger4.setText("4");
			mPanel1c.add(buttonTrigger4);
			setActionListener(buttonTrigger4, "trig 4");
			buttonTrigger6 = new JButton();
			buttonTrigger6.setBounds(68, 5, 40, 30);
			buttonTrigger6.setText("6");
			mPanel1c.add(buttonTrigger6);
			setActionListener(buttonTrigger6, "trig 6");
			buttonTrigger8 = new JButton();
			buttonTrigger8.setBounds(104, 5, 40, 29);
			buttonTrigger8.setText("8");
			setActionListener(buttonTrigger8, "trig 8");
			mPanel1c.add(buttonTrigger8);
		
		enable02 = new JLabel("Enable/Disable");
		enable02.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(enable02);
		
		mPanel1d = new JPanel();
		mPanel1d.setBackground(new Color(51, 153, 204));
		mPanel1d.setLayout(new GridLayout(1, 4, 0, 0));
		panel_2.add(mPanel1d);
		
			radioChannel2 = new JRadioButton();
			radioChannel2.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel2.setBounds(4, 8, 28, 23);
			mPanel1d.add(radioChannel2);
			radioChannel2.setSelected(false);
			setActionListener(radioChannel2, "tog 2");
			
			radioChannel4 = new JRadioButton();
			radioChannel4.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel4.setBounds(37, 8, 28, 23);
			mPanel1d.add(radioChannel4);
			radioChannel4.setSelected(false);
			setActionListener(radioChannel4, "tog 4");
			
			radioChannel6 = new JRadioButton();
			radioChannel6.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel6.setBounds(70, 8, 28, 23);
			mPanel1d.add(radioChannel6);
			radioChannel6.setSelected(false);
			setActionListener(radioChannel6, "tog 6");
			
			radioChannel8 = new JRadioButton();
			radioChannel8.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel8.setBounds(103, 8, 28, 23);
			mPanel1d.add(radioChannel8);
			radioChannel8.setSelected(false);
			setActionListener(radioChannel8, "tog 8");
	
		// bottom middle panel
		mPanel2 = new JPanel();
		mPanel2.setBackground(new Color(51, 153, 204));
		mPanel2.setBounds(0, 170, 400, 330);
		mPanel2.setLayout(null);
	
		displayText = new JTextArea();
		displayText.setEditable(false);
		scroll = new JScrollPane(displayText);
		scroll.setBounds(20, 0, 360, 300);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mPanel2.add(scroll);
		displayText.setFocusable(true);
		labelLegend = new JLabel("Queue/Running/Final");
		labelLegend.setBounds(132, 305, 150, 16);
		mPanel2.add(labelLegend);
		mPanel.setLayout(null);
		mPanel.add(mPanel1);
		mPanel.add(mPanel2);
		
	}

	private void setupRPanel() {		
		rPanel = new JPanel();
		rPanel.setBackground(new Color(51, 153, 204));
		rPanel.setBounds(600, 0, 250, 500);
		getContentPane().add(rPanel);
		// top right
		rPanel1 = new JPanel();
		rPanel1.setBackground(new Color(51, 153, 204));
		rPanel1.setLocation(0, 0);
		rPanel1.setSize(254, 225);
	
		// mid right
		rPanel2 = new JPanel();
		rPanel2.setBackground(new Color(51, 153, 204));
		rPanel2.setLocation(0, 225);
		rPanel2.setSize(254, 275);
		displayText.setRows(10);
		displayText.setColumns(15);
		rPanel2.setLayout(null);
		rPanel.setLayout(null);
	
		rPanel.add(rPanel1);
	
		printPower = new JButton("Printer Pwr");
		printPower.setBackground(new Color(100, 149, 237));
		printPower.setBounds(71, 8, 111, 50);
		printPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (p.isActive())
					p.setActive(false);
				else
					p.setActive(true);
			}
		});
		rPanel1.setLayout(null);
		rPanel1.add(printPower);
	
		printerText = new JTextArea();
		printerText.setEditable(false);
		scroll2 = new JScrollPane(printerText);
		rPanel1.add(scroll2);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(10, 66, 230, 150);

		rPanel.add(rPanel2);
		keypad = new JPanel();
		keypad.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		keypad.setBackground(new Color(51, 153, 204));
		rPanel2.add(keypad);
		keypad.setLayout(new GridLayout(4, 3, 0, 1));
		keypad.setBounds(10, 10, 230, 258);
	
		button1 = new JButton("1");
		button1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button2 = new JButton("2");
		button2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button3 = new JButton("3");
		button3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button4 = new JButton("4");
		button4.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button5 = new JButton("5");
		button5.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button6 = new JButton("6");
		button6.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button7 = new JButton("7");
		button7.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button8 = new JButton("8");
		button8.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		button9 = new JButton("9");
		button9.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		buttonStar = new JButton("*");
		buttonStar.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
	
		// setActionListener(buttonStar, "");
		button0 = new JButton("0");
		button0.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		buttonPound = new JButton("#");
		buttonPound.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
	
		buttonPound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!c.isOn()) {
					offWarning();
				} else if (tempRacer.length() > 0) {
					sendCommand("num " + tempRacer);
					tempRacer = "";
				} else {
					System.out.println("Enter a number, then press #.");
				}
			}
		});
	
		buttonAction(button1, 1);
		buttonAction(button2, 2);
		buttonAction(button3, 3);
		buttonAction(button4, 4);
		buttonAction(button5, 5);
		buttonAction(button6, 6);
		buttonAction(button7, 7);
		buttonAction(button8, 8);
		buttonAction(button9, 9);
		buttonAction(button0, 0);
		buttonAction(buttonStar, -1);
		keypad.add(button1);
		keypad.add(button2);
		keypad.add(button3);
		keypad.add(button4);
		keypad.add(button5);
		keypad.add(button6);
		keypad.add(button7);
		keypad.add(button8);
		keypad.add(button9);
		keypad.add(buttonStar);
		keypad.add(button0);
		keypad.add(buttonPound);
	}

	private void setNavPanel(){
		navPanel = new JPanel();
		navPanel.setLocation(12, 259);
		navPanel.setLayout(new GridLayout(1,4,0,0));
		navPanel.setBackground(new Color(51, 153, 204));
		navPanel.setSize(175, 50);
		
		up = new JButton("\u25B2");
		up.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setNavListener(up, "up");
		navPanel.add(up);
		
		down = new JButton("\u25BC");
		down.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setNavListener(down, "down");
		navPanel.add(down);
		
		left = new JButton("\u25C0");
		left.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setNavListener(left, "left");
		navPanel.add(left);
		
		right = new JButton("\u25BA");
		right.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setNavListener(right, "right");
		navPanel.add(right);	
		
	}
	
	/**
		 * Sends command to Console
		 * 
		 * @param command
		 */
		void sendCommand(String command) {
			String formatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + "\t" + command;
			if (c.isOn() || command == "POWER") {
				c.input(formatted);
				c.display(displayText);
			}
			if (p.isActive())
				p.printGUI(formatted, printerText);
			if (!c.isOn()) {
				offWarning();
				radioChannel1.setSelected(false);
				radioChannel2.setSelected(false);
				radioChannel3.setSelected(false);
				radioChannel4.setSelected(false);
				radioChannel5.setSelected(false);
				radioChannel6.setSelected(false);
				radioChannel7.setSelected(false);
				radioChannel8.setSelected(false);
			}
		}

	/**
	 * Sets ActionListener for button
	 * 
	 * @param i
	 * @param command
	 */
	void setActionListener(AbstractButton i, String command) {
		i.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand(command);
			}
		});
	}

	/**
	 * Sets ActionListener for navigation button
	 * 
	 * @param i
	 * @param command
	 */
	void setNavListener(AbstractButton i, String arrow){
		i.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {// TODO - implement arrow navigation
            	if(c.isOn() && fcnBtnOn){
	            	String original = displayText.getText();
	            	String[]strArr = original.split("\n");
	                if(arrow=="up"){
	                	if(rowSelected>0){
	                		strArr[rowSelected]=strArr[rowSelected].substring(2);
	                		strArr[rowSelected-1]="> "+strArr[rowSelected-1];
	                		rowSelected--;
	                	}
	                } else if(arrow=="down"){
	                	if(rowSelected<strArr.length-1){
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
	            	
	                displayText.setText(newString);
            	}
            	else {
            		if(!c.isOn())
            			offWarning();
            		else
            			displayText.setText("Press function button to use arrow keys.");
            	}
            }
        });
	}
	
	Action actionListener = new AbstractAction() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        JButton source = (JButton) actionEvent.getSource();
	        System.out.println("Activated: " + source.getText());
	        String arrow = source.getText();
	        if(c.isOn() && fcnBtnOn){
	        	String original = displayText.getText();
            	String[]strArr = original.split("\n");
                if(arrow.equalsIgnoreCase("up")){
                	if(rowSelected>0){
                		strArr[rowSelected]=strArr[rowSelected].substring(2);
                		strArr[rowSelected-1]="> "+strArr[rowSelected-1];
                		rowSelected--;
                	}
                } else if(arrow.equalsIgnoreCase("down")){
                	if(rowSelected<strArr.length-1){
                		strArr[rowSelected]=strArr[rowSelected].substring(2);
                		strArr[rowSelected+1]="> "+strArr[rowSelected+1];
                		rowSelected++;
                	}
                } else if(arrow.equalsIgnoreCase("left")){
                	// you selected that function
                } else if(arrow.equalsIgnoreCase("right")){
                	// go back
                } else {
                	System.out.println("illegal key entry");
                }
                //rebuild string
                StringBuilder strBuilder = new StringBuilder();
                for (int i = 0; i < strArr.length; i++) {
                   strBuilder.append(strArr[i]+'\n');
                }
                String newString = strBuilder.toString();
            	
                displayText.setText(newString);
	        }
	        else {
        		if(!c.isOn())
        			offWarning();
        		else
        			displayText.setText("Press function button to use arrow keys.");
        	}
	      }
    };
	private JPanel Power;
    
	/**
	 * If console is off, print off warning
	 */
	private void offWarning() {
		displayText.setText(offWarning);
	}

	void debug(String str) {
		System.out.println("DEBUG: " + str);
	}

	/**
	 * Sets up actionPerformed for Runner button
	 * 
	 * @param b
	 * @param i
	 */
	private void buttonAction(JButton b, int i) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (c.isOn() && i > 0)
					tempRacer += i;
				else if (!c.isOn())
					offWarning();
			}
		});
	}
	
	

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		ChronoGui frame = new ChronoGui();
		frame.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        frame.displayText.requestFocusInWindow();
		    }
		});
	}
}
