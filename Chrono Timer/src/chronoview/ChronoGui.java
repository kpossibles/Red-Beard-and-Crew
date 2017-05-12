package chronoview;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import chronotimer.ChronoTimer;
import chronotimer.Printer;
import chronotimer.Racer;

/**
 * The Class ChronoGui.
 *
 * @author Red Beard & Crew
 */
@SuppressWarnings("serial")
public class ChronoGui extends JFrame{
	private Map<String, ArrayList<String>> nameCommandMap;
	private ChronoTimer console;
	private Printer printer;
	private Menu menu;
	private SensorGui sensorGui;
	private Timer timer;
	private JRadioButton radioChannel1, radioChannel3, radioChannel5, radioChannel7, radioChannel2, radioChannel4,
			radioChannel6, radioChannel8, s1, s2, s3, s4, s5, s6, s7, s8;
	private JTextArea displayView, printerView;
	private JLabel labelLegend, enable01, enable02, lblStart, lblFinish;
	private JButton buttonPower, buttonFunction, buttonEndRun, buttonSwap, printPower, button1, button2, button3,
			button4, button5, button6, button7, button8, button9, buttonStar, button0, buttonPound, buttonTrigger1,
			buttonTrigger3, buttonTrigger5, buttonTrigger7, buttonTrigger2, buttonTrigger6, buttonTrigger4,
			buttonTrigger8, up, down, left, right;
	private JPanel lPanel, mPanel, mPanel1, mPanel1_trig, mPanel1_tog, mPanel1_trig2, mPanel1_tog2, mPanel2, panel_1, panel_2, rPanel,
			rPanel1, rPanel2, keypad, navPanel, powerStatus, sensorChannels, backPanel;
	private String tempRacer = "", offWarning = "The Chronotimer is currently off.\nTry 'POWER' to turn it on.";
	private JScrollPane scroll, scroll2;
	private boolean isfcnBtnOn, isClearOn, isTimeOn, isPrintOn;
	private boolean[] sensorActive;
	private static final int WIN_HEIGHT = 650;
	
	
	/**
	 * Instantiates a new ChronoTimer GUI.
	 */
	public ChronoGui() {
		getContentPane().setBackground(new Color(51, 153, 204));
		
		setTitle("ChronoTimer 1009");
		setupGUI();
		setVisible(true);
		try {
			printer = new Printer(displayView, printerView);
			console = new ChronoTimer(printer, displayView);
		} catch (IOException e) {
			debug("CHRONOTIMER COULD NOT BE CREATED");
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();

		System.out.println("ChronoTimer 1009 Application has started.");
	}
	
	/**
	 * Sets up the GUI
	 */
	public void setupGUI() {
		setMinimumSize(new Dimension(850, WIN_HEIGHT));
		setSize(850, WIN_HEIGHT);
		getContentPane().setLayout(null);
		
		setupLPanel();
		setupMPanel();
		setupRPanel();
		setupBackPanel();
		addBindings();
		setHashMap();
	}

	private void setHashMap() {
		// TODO Finish generating submenu commands for Menu
		nameCommandMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> value = new ArrayList<>();
		for(int i=1; i<9;i++)
			value.add(i+"");
		nameCommandMap.put("TOG", value);
		nameCommandMap.put("TRIG", value);
		nameCommandMap.put("EYE", value);
		nameCommandMap.put("PAD", value);
		nameCommandMap.put("GATE", value);
		nameCommandMap.put("DISC", value);
		
		value = new ArrayList<>();
		value.add("EYE");
		value.add("GATE");
		value.add("PAD");
		nameCommandMap.put("CONN", value);
		
		value = new ArrayList<>();
		value.add("IND");
		value.add("PARIND");
		value.add("GRP");
		value.add("PARGRP");
		nameCommandMap.put("EVENT", value);
		
		value = new ArrayList<>();
		value.add("<hour>:<min>:<sec>");
		nameCommandMap.put("TIME", value);
	}

	/**
	 * Setup Left panel.
	 */
	private void setupLPanel(){
		lPanel = new JPanel();
		lPanel.setBackground(new Color(51, 153, 204));
		lPanel.setBounds(0, 0, 200, 500);
		getContentPane().add(lPanel);
		lPanel.setLayout(null);

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
				menu = new Menu();
				setFcnListener();
			}
		});

		buttonSwap = new JButton("SWAP");
		buttonSwap.setLocation(12, 327);
		buttonSwap.setSize(175, 50);
		buttonSwap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(console.getEventType()!="IND"){
					displayView.setText("ERROR: WRONG EVENT TYPE.\nCAN ONLY SWAP WITH IND.");
				}
				if(!console.runExist() && console.getEventType()=="IND"){
					displayView.setText("ERROR: CANNOT SWAP.");
				}else{
					sendCommand("SWAP");
				}
			}
		});

		setNavPanel();
		lPanel.add(navPanel);
		lPanel.add(buttonSwap);
		buttonEndRun.setBounds(12, 397, 175, 50);
		lPanel.add(buttonEndRun);
		
		buttonPower = new JButton("Power");
		buttonPower.setBounds(12, 26, 125, 50);
		lPanel.add(buttonPower);
		
		powerStatus = new JPanel();
		powerStatus.setBounds(155, 38, 25, 25);
		lPanel.add(powerStatus);
		powerStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		powerStatus.setBackground(Color.RED);
		buttonPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("POWER");
				if (console.isOn()) {
					enableAll(true);
					powerStatus.setBackground(Color.GREEN);
					// custom welcome screen
					String temp="WELCOME TO CHRONOTIMER 1009!";
					displayView.setText(temp);
					
					//set action listener for display text box to update at set time.
					// TODO - James needs to check that this works!!!
					ActionListener task = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (!isfcnBtnOn)
							{
								boolean check=false;
								if(console.getCurrentRacers()!=null){
									for(Racer r: console.getCurrentRacers()){
										if(r.getStart()>0 && (!r.getDNF() || r.getFinish()!=0))
											check=true;
											break;
									}
								}
								if(check)
									console.display();
							}
						}
					};
					Timer timer = new Timer(100 ,task); // Execute task each 1000 miliseconds
					timer.setRepeats(true);
					timer.start();
				}
				else{
					debug("off");
					enableAll(false);
					menuGuiUpdate("RESET");
					powerStatus.setBackground(Color.RED);
				}
			}
		});
	}
	
	/**
	 * Setup Middle panel.
	 */
	private void setupMPanel() {
		mPanel = new JPanel();
		mPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		mPanel.setBackground(new Color(51, 153, 204));
		getContentPane().add(mPanel);
		mPanel.setBounds(200, 0, 400, 500);
		
		// top middle
		mPanel1 = new JPanel();
		mPanel1.setBackground(new Color(51, 153, 204,0));
		mPanel1.setBounds(0, 0, 400, 170);
		mPanel1.setLayout(null);
	
		panel_1 = new JPanel();
		panel_1.setBounds(20, 10, 360, 80);
		panel_1.setBackground(new Color(51, 153, 204));
		mPanel1.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 2, 10, 0));
	
		// m1: split into 4 panels
		lblStart = new JLabel("Start");
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblStart);
		lblStart.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		
		mPanel1_trig = new JPanel();
		mPanel1_trig.setBackground(new Color(51, 153, 204));
		mPanel1_trig.setLayout(new GridLayout(1, 4, 0, 0));
		panel_1.add(mPanel1_trig);
	
			buttonTrigger1 = new JButton();
			buttonTrigger1.setBounds(0, 5, 40, 30);
			buttonTrigger1.setText("1");
			mPanel1_trig.add(buttonTrigger1);
			setBtnListener(buttonTrigger1, "TRIG 1", "trigger");
			buttonTrigger3 = new JButton();
			buttonTrigger3.setBounds(32, 5, 40, 30);
			buttonTrigger3.setText("3");
			mPanel1_trig.add(buttonTrigger3);
			setBtnListener(buttonTrigger3, "TRIG 3", "trigger");
			buttonTrigger5 = new JButton();
			buttonTrigger5.setBounds(68, 5, 40, 30);
			buttonTrigger5.setText("5");
			mPanel1_trig.add(buttonTrigger5);
			setBtnListener(buttonTrigger5, "TRIG 5", "trigger");
			buttonTrigger7 = new JButton();
			buttonTrigger7.setBounds(104, 5, 40, 30);
			buttonTrigger7.setText("7");
			mPanel1_trig.add(buttonTrigger7);
			setBtnListener(buttonTrigger7, "TRIG 7", "trigger");
		
		enable01 = new JLabel("Enable/Disable");
		enable01.setHorizontalAlignment(SwingConstants.RIGHT);
	
		panel_1.add(enable01);
		mPanel1_tog = new JPanel();
		mPanel1_tog.setBackground(new Color(51, 153, 204));
		mPanel1_tog.setLayout(new GridLayout(1, 4, 0, 0));
		panel_1.add(mPanel1_tog);
	
			radioChannel1 = new JRadioButton();
			radioChannel1.setEnabled(false);
			radioChannel1.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel1.setBounds(5, 8, 28, 23);
			radioChannel1.setSelected(false);
			setBtnListener(radioChannel1, "TOG 1", "toggle");
			mPanel1_tog.add(radioChannel1);
		
			radioChannel3 = new JRadioButton();
			radioChannel3.setEnabled(false);
			radioChannel3.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel3.setBounds(38, 8, 28, 23);
			radioChannel3.setSelected(false);
			setBtnListener(radioChannel3, "TOG 3", "toggle");
			mPanel1_tog.add(radioChannel3);
		
			radioChannel5 = new JRadioButton();
			radioChannel5.setEnabled(false);
			radioChannel5.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel5.setBounds(71, 8, 28, 23);
			radioChannel5.setSelected(false);
			setBtnListener(radioChannel5, "TOG 5", "toggle");
			mPanel1_tog.add(radioChannel5);
		
			radioChannel7 = new JRadioButton();
			radioChannel7.setEnabled(false);
			radioChannel7.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel7.setBounds(104, 8, 28, 23);
			radioChannel7.setSelected(false);
			setBtnListener(radioChannel7, "TOG 7", "toggle");
			mPanel1_tog.add(radioChannel7);
		
		// m2: split into 4 panels
		panel_2 = new JPanel();
		panel_2.setBounds(20, 90, 360, 80);
		panel_2.setBackground(new Color(51, 153, 204));
		mPanel1.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 2, 10, 0));
		
		lblFinish = new JLabel("Finish");
		lblFinish.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFinish.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		panel_2.add(lblFinish);
		
		mPanel1_trig2 = new JPanel();
		mPanel1_trig2.setBackground(new Color(51, 153, 204));
		mPanel1_trig2.setLayout(new GridLayout(1, 4, 0, 0));
		panel_2.add(mPanel1_trig2);
	
			buttonTrigger2 = new JButton();
			buttonTrigger2.setBounds(-4, 5, 40, 30);
			buttonTrigger2.setText("2");
			mPanel1_trig2.add(buttonTrigger2);
			setBtnListener(buttonTrigger2, "TRIG 2", "trigger");
			buttonTrigger4 = new JButton();
			buttonTrigger4.setBounds(32, 5, 40, 30);
			buttonTrigger4.setText("4");
			mPanel1_trig2.add(buttonTrigger4);
			setBtnListener(buttonTrigger4, "TRIG 4", "trigger");
			buttonTrigger6 = new JButton();
			buttonTrigger6.setBounds(68, 5, 40, 30);
			buttonTrigger6.setText("6");
			mPanel1_trig2.add(buttonTrigger6);
			setBtnListener(buttonTrigger6, "TRIG 6", "trigger");
			buttonTrigger8 = new JButton();
			buttonTrigger8.setBounds(104, 5, 40, 29);
			buttonTrigger8.setText("8");
			setBtnListener(buttonTrigger8, "TRIG 8", "trigger");
			mPanel1_trig2.add(buttonTrigger8);
		
		enable02 = new JLabel("Enable/Disable");
		enable02.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(enable02);
		
		mPanel1_tog2 = new JPanel();
		mPanel1_tog2.setBackground(new Color(51, 153, 204));
		mPanel1_tog2.setLayout(new GridLayout(1, 4, 0, 0));
		panel_2.add(mPanel1_tog2);
		
			radioChannel2 = new JRadioButton();
			radioChannel2.setEnabled(false);
			radioChannel2.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel2.setBounds(4, 8, 28, 23);
			mPanel1_tog2.add(radioChannel2);
			radioChannel2.setSelected(false);
			setBtnListener(radioChannel2, "TOG 2", "toggle");
			
			radioChannel4 = new JRadioButton();
			radioChannel4.setEnabled(false);
			radioChannel4.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel4.setBounds(37, 8, 28, 23);
			mPanel1_tog2.add(radioChannel4);
			radioChannel4.setSelected(false);
			setBtnListener(radioChannel4, "TOG 4", "toggle");
			
			radioChannel6 = new JRadioButton();
			radioChannel6.setEnabled(false);
			radioChannel6.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel6.setBounds(70, 8, 28, 23);
			mPanel1_tog2.add(radioChannel6);
			radioChannel6.setSelected(false);
			setBtnListener(radioChannel6, "TOG 6", "toggle");
			
			radioChannel8 = new JRadioButton();
			radioChannel8.setEnabled(false);
			radioChannel8.setHorizontalAlignment(SwingConstants.CENTER);
			radioChannel8.setBounds(103, 8, 28, 23);
			mPanel1_tog2.add(radioChannel8);
			radioChannel8.setSelected(false);
			setBtnListener(radioChannel8, "TOG 8", "toggle");
	
		// bottom middle panel
		mPanel2 = new JPanel();
		mPanel2.setBackground(new Color(51, 153, 204,0));
		mPanel2.setBounds(0, 170, 400, 330);
		mPanel2.setLayout(null);
	
		displayView = new JTextArea();
		displayView.setLineWrap(true);
		displayView.setEditable(false);
		displayView.setMargin(new Insets(10,10,10,10));
		scroll = new JScrollPane(displayView);
		scroll.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scroll.setBounds(20, 0, 360, 300);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mPanel2.add(scroll);
		displayView.setFocusable(true);
		labelLegend = new JLabel("Queue/Running/Final");
		labelLegend.setBounds(132, 305, 150, 16);
		mPanel2.add(labelLegend);
		mPanel.setLayout(null);
		mPanel.add(mPanel1);
		mPanel.add(mPanel2);
	}

	/**
	 * Setup Right panel.
	 */
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
		displayView.setRows(10);
		rPanel2.setLayout(null);
		rPanel.setLayout(null);
	
		rPanel.add(rPanel1);
	
		printPower = new JButton("Printer Pwr");
		printPower.setBackground(new Color(100, 149, 237));
		printPower.setBounds(71, 8, 111, 50);
		printPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (printer.isOn()){
					printer.setActive(false);
					scroll2.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				} else{
					printer.setActive(true);
					scroll2.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				}
					
			}
		});
		rPanel1.setLayout(null);
		rPanel1.add(printPower);
	
		printerView = new JTextArea();
		printerView.setColumns(15);
		printerView.setWrapStyleWord(true);
		printerView.setEditable(false);
		scroll2 = new JScrollPane(printerView);
		scroll2.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
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
				if(console.isOn() && tempRacer.length()>0){
					debug(String.format("is time command on? %s\nis print command on? %s", isTimeOn, isPrintOn));
					if(isPrintOn){
						sendCommand("PRINT "+tempRacer);
					} else if(isTimeOn){
						boolean checkLetter = false;
						char[]temp = tempRacer.toCharArray();
						String newTime = String.format("%s%s:%s%s:%s%s.0", 
										temp.length>0?tempRacer.charAt(0):"H",
										temp.length>1?tempRacer.charAt(1):"H",
										temp.length>2?tempRacer.charAt(2):"M",
										temp.length>3?tempRacer.charAt(3):"M",
										temp.length>4?tempRacer.charAt(4):"S",
										temp.length>5?tempRacer.charAt(5):"S");
						for(char c:newTime.toCharArray()){
							if(Character.isLetter(c)){//illegal character
								checkLetter = true;
								break;
							}
						}
						debug("sending time: " + newTime);
						if(!checkLetter)
							sendCommand("TIME " + newTime);
						else{
//							debug("YOU NEED TO SUBMIT A COMPLETE TIME! YOU SENT: "+newTime);
							displayView.setText("YOU NEED TO SUBMIT A COMPLETE TIME!\nYOU SENT: "+newTime);
						}
						isTimeOn = false;
					}else{
						sendCommand("NUM "+tempRacer);
						console.display();
					}
					tempRacer = "";
					
				}
			}
		});
	
		setBtnListener(button1, "1", "keypad");
		setBtnListener(button2, "2", "keypad");
		setBtnListener(button3, "3", "keypad");
		setBtnListener(button4, "4", "keypad");
		setBtnListener(button5, "5", "keypad");
		setBtnListener(button6, "6", "keypad");
		setBtnListener(button7, "7", "keypad");
		setBtnListener(button8, "8", "keypad");
		setBtnListener(button9, "9", "keypad");
		setBtnListener(button0, "0", "keypad");
		setBtnListener(buttonStar, "-1", "keypad");
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

	/**
	 * Sets the navigation buttons panel.
	 */
	private void setNavPanel(){
		menu = new Menu();
		
		navPanel = new JPanel();
		navPanel.setLocation(12, 259);
		navPanel.setLayout(new GridLayout(1,4,0,0));
		navPanel.setBackground(new Color(51, 153, 204));
		navPanel.setSize(175, 50);
		
		up = new JButton("\u25B2");
		up.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setBtnListener(up, ""+KeyEvent.VK_UP, "nav");
		navPanel.add(up);
		
		down = new JButton("\u25BC");
		down.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setBtnListener(down, ""+KeyEvent.VK_DOWN, "nav");
		navPanel.add(down);
		
		left = new JButton("\u25C0");
		left.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setBtnListener(left, ""+KeyEvent.VK_LEFT, "nav");
		navPanel.add(left);
		
		right = new JButton("\u25BA");
		right.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		setBtnListener(right, ""+KeyEvent.VK_RIGHT, "nav");
		navPanel.add(right);			
	}
	
	/**
	 * Instantiates a new back panel.
	 */
	private void setupBackPanel() {
		backPanel=new JPanel();
		backPanel.setBounds(0, 500, 850, 130);
		backPanel.setBackground(new Color(25, 25, 112));
		getContentPane().add(backPanel);
		
		sensorActive = new boolean[8];
		backPanel.setLayout(null);

		JLabel lblChannel = new JLabel("CHANNEL");
		lblChannel.setForeground(new Color(255, 255, 255));
		lblChannel.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		lblChannel.setBounds(18, 52, 103, 26);
		backPanel.add(lblChannel);

		sensorChannels = new JPanel();
		sensorChannels.setBackground(new Color(25, 25, 112));
		sensorChannels.setBounds(130, 5, 370, 118);
		backPanel.add(sensorChannels);
		sensorChannels.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel label_1 = new JLabel("1");
		label_1.setForeground(new Color(255, 255, 255));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_1);

		JLabel label_3 = new JLabel("3");
		label_3.setForeground(new Color(255, 255, 255));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_3);

		JLabel label_5 = new JLabel("5");
		label_5.setForeground(new Color(255, 255, 255));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_5);

		JLabel label_7 = new JLabel("7");
		label_7.setForeground(new Color(255, 255, 255));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_7);
		
		s1 = new JRadioButton("");
		s1.setEnabled(false);
		s1.setForeground(new Color(25, 25, 112));
		s1.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s1);
		s3 = new JRadioButton("");
		s3.setEnabled(false);
		s3.setForeground(new Color(25, 25, 112));
		s3.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s3);
		s5 = new JRadioButton("");
		s5.setEnabled(false);
		s5.setForeground(new Color(25, 25, 112));
		s5.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s5);
		s7 = new JRadioButton("");
		s7.setEnabled(false);
		s7.setForeground(new Color(25, 25, 112));
		s7.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s7);
		
		JLabel label_2 = new JLabel("2");
		label_2.setForeground(new Color(255, 255, 255));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_2);

		JLabel label_4 = new JLabel("4");
		label_4.setForeground(new Color(255, 255, 255));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_4);

		JLabel label_6 = new JLabel("6");
		label_6.setForeground(new Color(255, 255, 255));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_6);

		JLabel label_8 = new JLabel("8");
		label_8.setForeground(new Color(255, 255, 255));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		sensorChannels.add(label_8);
		
		s2 = new JRadioButton("");
		s2.setEnabled(false);
		s2.setForeground(new Color(25, 25, 112));
		s2.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s2);
		s4 = new JRadioButton("");
		s4.setEnabled(false);
		s4.setForeground(new Color(25, 25, 112));
		s4.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s4);
		s6 = new JRadioButton("");
		s6.setEnabled(false);
		s6.setForeground(new Color(25, 25, 112));
		s6.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s6);
		s8 = new JRadioButton("");
		s8.setEnabled(false);
		s8.setForeground(new Color(25, 25, 112));
		s8.setHorizontalAlignment(SwingConstants.CENTER);
		sensorChannels.add(s8);

		JPanel usb = new JPanel();
		usb.setBackground(new Color(0,0,0,0));
		usb.setBounds(500, 0, 350, 130);
		backPanel.add(usb);
		usb.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(70, 52, 60, 26);
		usb.add(panel);
		
		JLabel lblNewLabel = new JLabel("USB PORT");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		lblNewLabel.setBounds(175, 52, 105, 26);
		usb.add(lblNewLabel);

		
		setSensorListener(s1,1);
		setSensorListener(s2,2);
		setSensorListener(s3,3);
		setSensorListener(s4,4);
		setSensorListener(s5,5);
		setSensorListener(s6,6);
		setSensorListener(s7,7);
		setSensorListener(s8,8);
	}
	
	/**
	 * Adds the key bindings.
	 */
	private void addBindings(){
		InputMap inputMap = displayView.getInputMap();
		ActionMap actionMap = displayView.getActionMap();
		
		KeyStroke key = KeyStroke.getKeyStroke("UP");
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_UP));
		key = KeyStroke.getKeyStroke("DOWN");
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_DOWN));
		key = KeyStroke.getKeyStroke("LEFT");
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_LEFT));
		key = KeyStroke.getKeyStroke("RIGHT");
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_RIGHT));	
		
		//keyboard & numpad bindings
		for(int i=48, loop=0, numpad=96; loop<10; i++,loop++,numpad++){
			key = KeyStroke.getKeyStroke(i,0);
			actionMap.put(inputMap.get(key), setKeyAction(i));
			key = KeyStroke.getKeyStroke(numpad,0);
			actionMap.put(inputMap.get(key), setKeyAction(numpad));
		} 
		
		key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_ENTER));
		key = KeyStroke.getKeyStroke(KeyEvent.VK_NUMBER_SIGN,0);
		actionMap.put(inputMap.get(key), setKeyAction(KeyEvent.VK_NUMBER_SIGN));
	}

	/**
	 * Sets the key binding for menu navigation & keypad.
	 *
	 * @param key the key
	 * @return the action
	 */
	private Action setKeyAction(int key) {
		Action action = new AbstractAction(){
	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO implement this for R key in private method
				if(key==KeyEvent.VK_LEFT || key==KeyEvent.VK_RIGHT 
						|| key==KeyEvent.VK_UP || key==KeyEvent.VK_DOWN){
					menuResponse(key);
				} 
				for(int i=48, actualNum=0, numpad=96; actualNum<10; i++, actualNum++, numpad++){
					if(key == i || key == numpad)
						keyPadAction(actualNum);
				} 
				if(key == KeyEvent.VK_ENTER){
					keyPadAction(10);
				} if(key == KeyEvent.VK_BACK_SPACE){
					if(!isfcnBtnOn && tempRacer.length()>0)
						tempRacer.substring(0, tempRacer.length()-1);
				}
				
			}
			
		};
		
		return action;
	}

	/**
	 * Sends command to Console
	 * 
	 * @param command
	 */
	private void sendCommand(String command) {
		//debug(command);
		String formatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) + "\t" + command;
		if (console.isOn() || command == "POWER") {
			formatted = console.getTime()+ "\t" + command;
			debug(formatted);
			console.input(formatted);
			console.display();
		}
		if (printer.isOn() && console.isOn()){
			// update the printerView
			String temp = printerView.getText();
			printerView.setText(temp+"\n"+formatted);
		}
		if (!console.isOn()) {
			offWarning();
			if(timer!=null)
				timer.stop();
			if(sensorGui!=null)
				sensorGui.close();
			radioChannel1.setSelected(false);
			radioChannel2.setSelected(false);
			radioChannel3.setSelected(false);
			radioChannel4.setSelected(false);
			radioChannel5.setSelected(false);
			radioChannel6.setSelected(false);
			radioChannel7.setSelected(false);
			radioChannel8.setSelected(false);
			sensorActive = new boolean[8];
			s1.setSelected(false);
			s2.setSelected(false);
			s3.setSelected(false);
			s4.setSelected(false);
			s5.setSelected(false);
			s6.setSelected(false);
			s7.setSelected(false);
			s8.setSelected(false);
		}
	}

	/**
	 * Sets ActionListener for trigger button
	 * 
	 * @param i
	 * @param command
	 */
	private void setBtnListener(AbstractButton i, String command, String buttontype) {
		i.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String className = e.getSource().getClass().getSimpleName();
				if(!console.isOn()){
	            	offWarning();
	            	if(className=="JRadioButton"){
	            		((JRadioButton)e.getSource()).setSelected(false);
	            	}
	            }else {
	            	if(buttontype=="trigger"){
	        			debug("trigger");
	        			trigListener(command);
	        			menu = null;
	        			isfcnBtnOn = false;
	        		} if(buttontype=="toggle"){
        				debug("toggle");
        				sendCommand(command);
        				((JRadioButton)e.getSource()).setSelected(true);
        			} if (command.split(" ").length ==1){
	        			int num = Integer.valueOf(command);
	        			debug(command+" "+buttontype);
	        			if(buttontype =="nav"){
	        				debug("nav");
	        				menuResponse(num);
	        			} if(buttontype=="keypad"){
	        				keyPadAction(num);
	        			} if(buttontype=="sensor"){
	        				if(console.isOn() && !sensorActive[num - 1]){
	        					Object[] options = { "EYE", "GATE", "PAD" };
	        					ImageIcon icon = null;
	        					String s = (String) JOptionPane.showInputDialog(null, "Select a sensor below.", "Sensor Selection",
	        							JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
	        		
	        					// If a string was returned, set a sensor.
	        					if ((s != null) && (s.length() > 0)) {
	        						sendSensor(s,num,true);
	        					}
	        				}else if(console.isOn()){
	        					sendSensor("",num,false);
	        				}
	        			}
	        		}
	            }
			}
			
		});
	}

//	/**
//	 * Sets ActionListener for navigation button
//	 * 
//	 * @param i
//	 * @param command
//	 */
//	private void setNavListener(AbstractButton i, int key){
//		i.addActionListener(new ActionListener()
//        {
//            @Override
//			public void actionPerformed(ActionEvent e)
//            {// TODO - implement arrow navigation for right in private method
//            	menuResponse(key);
//            }
//        });
//	}
//	
//	/**
//	 * Sets up actionPerformed for keypad button.
//	 *
//	 * @param b the b
//	 * @param i the i
//	 */
//	private void setKeypadListener(JButton b, int i) {
//		b.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				keyPadAction(i);
//			}
//		});
//	}

	private void keyPadAction(int realNum) {
		// TODO Auto-generated method stub
				if(!console.isOn()){
					offWarning();
				} else if(console.isOn()){
					if(realNum >= 0 && realNum<10){
						isfcnBtnOn=false;
						tempRacer += realNum;
						String intro="SETTING RACER NAME: " + tempRacer;
						if(isClearOn){
							intro="RACER TO BE CLEARED: " + tempRacer;
						} else if(isTimeOn){
							char[]temp = tempRacer.toCharArray();
							intro = String.format("SETTING <%s%s:%s%s:%s%s>", 
											temp.length>0?tempRacer.charAt(0):"H",
											temp.length>1?tempRacer.charAt(1):"H",
											temp.length>2?tempRacer.charAt(2):"M",
											temp.length>3?tempRacer.charAt(3):"M",
											temp.length>4?tempRacer.charAt(4):"S",
											temp.length>5?tempRacer.charAt(5):"S");
						}
						displayView.setText(intro+"\n\nPRESS # BUTTON TO SUBMIT!");
					} else if (tempRacer.length() > 0 && realNum==10) {
						if(isClearOn){
					//		debug("clr " + tempRacer);
							sendCommand("clr " + tempRacer);
							isClearOn = false;
						} else{
							debug("is time on? "+isTimeOn);
							debug("sending: NUM " + tempRacer);
							sendCommand("NUM " + tempRacer);
							tempRacer = "";
						}
							
						tempRacer = "";
					}
				} else {
					System.out.println("Enter a number, then press #.");
				}
	}

	/**
	 * Sets the sensor listener.
	 *
	 * @param i the JRadiobutton
	 * @param num the num
	 */
	private void setSensorListener(AbstractButton i, int num) {
		i.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(console.isOn() && !sensorActive[num - 1]){
					Object[] options = { "EYE", "GATE", "PAD" };
					ImageIcon icon = null;
					String s = (String) JOptionPane.showInputDialog(null, "Select a sensor below.", "Sensor Selection",
							JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
		
					// If a string was returned, set a sensor.
					if ((s != null) && (s.length() > 0)) {
						sendSensor(s,num,true);
					}
				}else if(console.isOn()){
					sendSensor("",num,false);
				}else{
					offWarning();
					((JRadioButton)e.getSource()).setSelected(false);
				}
			}
		});
	}

	private void sendSensor(String s, int num, boolean connect) {
		if(connect==true){
			if(!sensorActive[num - 1]){
				sensorActive[num - 1] = !sensorActive[num - 1];
				sendCommand(String.format("CONN %s %d", s,num));
				if(sensorGui==null){
					sensorGui=new SensorGui(console,displayView);
					System.out.println("adding "+num+" "+s);
					sensorGui.addSensorButton(s,num);
				}else{
					System.out.println("adding "+num);
					sensorGui.setVisible(false);
					sensorGui.addSensorButton(s, num);
					sensorGui.setVisible(true);
				}
			}else {
				System.out.println("WARNING: A SENSOR IS ALREADY CONNECTED.");
			}
		} else { //disconnect
			if(sensorActive[num - 1] && sensorGui!=null){
			//	debug("disconnecting sensor...");
				sensorActive[num - 1] = !sensorActive[num - 1];
				sendCommand(String.format("DISC %d", num));
				System.out.println("disconnecting "+num);
				sensorGui.setVisible(false);
				sensorGui.removeSensorButton(num);
				boolean checkIfAnyLeft=false;
				for(boolean b:sensorActive){
					if(b==true){
						checkIfAnyLeft=true;
						break;
					}
				}
				if(checkIfAnyLeft)
					sensorGui.setVisible(true);
				else
					sensorGui.close();
				
			}else {
				System.out.println("WARNING: SENSOR IS NOT CONNECTED. CANNOT DISCONNECT.");
			}
		}
	}

	/**
	 * Sets the buttonFunction listener.
	 */
	private void setFcnListener() {
		isfcnBtnOn = !isfcnBtnOn;
		if(isfcnBtnOn && console.isOn()){
			displayView.setText(menu.getMenu());
		}
		else{
			if(console.isOn()){
				boolean check=false;
				if(console.getCurrentRacers()!=null){
					for(Racer r: console.getCurrentRacers()){
						if(r.getStart()>0)
							check=true;
							break;
					}
				}
				if(check)
					console.display();
				else
					displayView.setText("");
			}else
				offWarning();
		}
	}
	
	/**
	 * Trigger listener.
	 *
	 * @param command the command
	 */
	private void trigListener(String command) {
		isfcnBtnOn=false;
		sendCommand(command);
		console.display();
	}

	/**
	 * Menu response.
	 *
	 * @param key the key pressed
	 */
	private void menuResponse(int key){
		// TODO implement this for R key
		if(console.isOn() && isfcnBtnOn && menu!=null){
			String selected = menu.getSelected();
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
				menu.setSelected(key);
			}
			if(key == KeyEvent.VK_LEFT){
				tempRacer="";
				if(menu.getPrevious()!="")
					menu.pressLeft();
				else{
					displayView.setText("");
					isfcnBtnOn = false;
					menu = null;
				}
			}
			if(key == KeyEvent.VK_RIGHT){
				//debug("pressed right key");
				
				if(nameCommandMap.containsKey(selected)){
					//debug("contains "+selected);
					String previous = "Main Menu";
					menu = new Menu(previous, selected, nameCommandMap.get(selected));
					displayView.setText(menu.getMenu());
					menuGuiUpdate(selected);
				}else{
					// TODO implement for every command & GUI response!!!
					// doesn't contain the command key in nameCommandMap
					// sendCommand() to console
					String menuName = menu.getName();
					String command;
					if(menuName=="Main Menu"){
						command = selected;
					} else {
						command = menuName + " " + selected;
					}
					if(selected!="NUM" && selected!="START" && selected!="FINISH" && selected!="CLR"
							&& selected!="EYE"&& selected!="PAD"&& selected!="GATE"&& selected!="PRINT"){
						sendCommand(command);
					}
					menuGuiUpdate(command);
				}
			}
			else if(menu!=null)//set the menu after pressing fcn button!
				displayView.setText(menu.getMenu());
		}
		else {
    		if(!console.isOn())
    			offWarning();
    		else
    			displayView.setText("Press function button to use arrow keys.");
    	}
		
	}
	public void menuGuiUpdate(String command) {
		// TODO implement export
		//debug("menuGuiUpdate");
		
		String[]split = null;
		if(command.contains(" ")){
			split=command.split(" ");
		}
		if(command == "RESET"){
			isfcnBtnOn=false;
			menu = null;
			if(sensorGui!=null){
				sensorGui.close();
				sensorGui=null;
				sensorActive=new boolean[8];
			}
			radioChannel1.setSelected(false);
			radioChannel2.setSelected(false);
			radioChannel3.setSelected(false);
			radioChannel4.setSelected(false);
			radioChannel5.setSelected(false);
			radioChannel6.setSelected(false);
			radioChannel7.setSelected(false);
			radioChannel8.setSelected(false);
			s1.setSelected(false);
			s2.setSelected(false);
			s3.setSelected(false);
			s4.setSelected(false);
			s5.setSelected(false);
			s6.setSelected(false);
			s7.setSelected(false);
			s8.setSelected(false);
			displayView.setText("");
		}if(command == "CLOSE"){
			isfcnBtnOn=false;
			setFcnListener();
			menu = null;
		}if(command == "NUM"){
			isfcnBtnOn=false;
			String temp="PRESS NUMBERS ON THE KEYPAD TO ENTER YOUR RACER NAME! PRESS # TO SUBMIT!";
			//debug(temp);
			menu = null;
			displayView.setText(temp);
		}if(command == "START"){
			isfcnBtnOn=false;
			trigListener("TRIG 1");
			displayView.setText("");
		}if(command == "FINISH"){
			isfcnBtnOn=false;
			trigListener("TRIG 2");
			displayView.setText("");
		}if(split!=null){
			if(split[0].equals("TRIG")){
				trigListener(command);
			} if(split[0].equals("TOG")){
				int index = Integer.valueOf(split[1]);
				menuToggleRadio(index);
			} if(split[0].equals("EYE")||split[0].equals("PAD")||split[0].equals("GATE")){
				int num = Integer.valueOf(split[1]);
				if(!sensorActive[num-1]){
					sendSensor(split[0], num, true);
					menuToggleSensorRadio(num, true);
				}else{
					displayView.setText(num + " is already connected!");
				}
			} if(split[0].equals("DISC")){
				int num = Integer.valueOf(split[1]);
				if(sensorActive[num-1]){
					sendSensor(split[0], num, false);
					menuToggleSensorRadio(num, false);
				}else{
					displayView.setText(num + " is already disconnected!");
				}if(command.equals("NEWRUN")){
					displayView.setText("NEW RUN CREATED");
				}if(command.equals("ENDRUN")){
					displayView.setText("RUN ENDED");
				}
			}
		}if(command == "CLR"){
			isfcnBtnOn=false;
			String temp="PRESS NUMBERS ON THE KEYPAD TO CLEAR RACER! PRESS # TO SUBMIT!";
			menu = null;
			isClearOn = true;
			displayView.setText(temp);
		}if(command == "SWAP"){
			isfcnBtnOn=false;
			menu = null;
		}if(command == "TIME"){
			isfcnBtnOn=false;
			String temp="SETTING: <HH:MM:SS>";
			menu = null;
			isTimeOn = true;
			displayView.setText(temp);
		}if(command == "PRINT"){
			isfcnBtnOn=false;
			if(!console.runExist()){
				displayView.setText("Cannot print. A run does not exist.");
			}else{
				String temp="ENTER THE RUN NUMBER ON THE KEYPAD AND PRESS # TO SUBMIT!";
				displayView.setText(temp);
			}
			menu = null;
			isPrintOn=true;
		}
	}

	/**
	 * Menu toggle radio button.
	 *
	 * @param num the num
	 */
	private void menuToggleRadio(int num) {
		switch(num){
			case 1:
				if(radioChannel1.isSelected())
					radioChannel1.setSelected(false);
				else
					radioChannel1.setSelected(true);
				break;
			case 2:
				if(radioChannel2.isSelected())
					radioChannel2.setSelected(false);
				else
					radioChannel2.setSelected(true);
				break;
			case 3:
				if(radioChannel3.isSelected())
					radioChannel3.setSelected(false);
				else
					radioChannel3.setSelected(true);
				break;
			case 4:
				if(radioChannel4.isSelected())
					radioChannel4.setSelected(false);
				else
					radioChannel4.setSelected(true);
				break;
			case 5:
				if(radioChannel5.isSelected())
					radioChannel5.setSelected(false);
				else
					radioChannel5.setSelected(true);
				break;
			case 6:
				if(radioChannel6.isSelected())
					radioChannel6.setSelected(false);
				else
					radioChannel6.setSelected(true);
				break;
			case 7:
				if(radioChannel7.isSelected())
					radioChannel7.setSelected(false);
				else
					radioChannel7.setSelected(true);
				break;
			case 8:
				if(radioChannel8.isSelected())
					radioChannel8.setSelected(false);
				else
					radioChannel8.setSelected(true);
				break;
		}
	}
	
	/**
	 * Menu toggle sensor radio.
	 *
	 * @param num the num
	 * @param on the on
	 */
	private void menuToggleSensorRadio(int num, boolean on) {
		switch(num){
			case 1:
				if(on)
					s1.setSelected(true);
				else
					s1.setSelected(false);
				break;
			case 2:
				if(on)
					s2.setSelected(true);
				else
					s2.setSelected(false);
				break;
			case 3:
				if(on)
					s3.setSelected(true);
				else
					s3.setSelected(false);
				break;
			case 4:
				if(on)
					s4.setSelected(true);
				else
					s4.setSelected(false);
				break;
			case 5:
				if(on)
					s5.setSelected(true);
				else
					s5.setSelected(true);
				break;
			case 6:
				if(on)
					s6.setSelected(true);
				else
					s6.setSelected(false);
				break;
			case 7:
				if(on)
					s7.setSelected(true);
				else
					s7.setSelected(false);
				break;
			case 8:
				if(on)
					s8.setSelected(true);
				else
					s8.setSelected(false);
				break;
		}
	}
	
	/**
	 * Enable/disable all. Done at startup/shutdown.
	 *
	 * @param enable whether to enable or disable
	 */
	private void enableAll(boolean enable){
		if(enable){
			radioChannel1.setEnabled(true);
			radioChannel3.setEnabled(true);
			radioChannel5.setEnabled(true);
			radioChannel7.setEnabled(true);
			radioChannel2.setEnabled(true);
			radioChannel4.setEnabled(true);
			radioChannel6.setEnabled(true);
			radioChannel8.setEnabled(true);
			s1.setEnabled(true);
			s2.setEnabled(true);
			s3.setEnabled(true);
			s4.setEnabled(true);
			s5.setEnabled(true);
			s6.setEnabled(true);
			s7.setEnabled(true);
			s8.setEnabled(true);
		}else{
			radioChannel1.setEnabled(false);
			radioChannel3.setEnabled(false);
			radioChannel5.setEnabled(false);
			radioChannel7.setEnabled(false);
			radioChannel2.setEnabled(false);
			radioChannel4.setEnabled(false);
			radioChannel6.setEnabled(false);
			radioChannel8.setEnabled(false);
			s1.setEnabled(false);
			s2.setEnabled(false);
			s3.setEnabled(false);
			s4.setEnabled(false);
			s5.setEnabled(false);
			s6.setEnabled(false);
			s7.setEnabled(false);
			s8.setEnabled(false);
		}
	}
	/**
	 * If console is off, print off warning
	 */
	private void offWarning() {
		displayView.setText(offWarning);
	}

	private void debug(String str) {
		System.out.println("DEBUG: " + str);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		new ChronoGui();
	}
}
