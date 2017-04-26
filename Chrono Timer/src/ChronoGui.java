import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class ChronoGui extends JFrame
{
	Console c;
	Printer p;
	JRadioButton radioChannel1, radioChannel3, radioChannel5, 
    radioChannel7, radioChannel2, radioChannel4, 
    radioChannel6, radioChannel8;
	JTextArea displayText, printerText;
	JLabel lblEventType, labelLegend, label_1, label_2, label_3, label_4, lblFinish;
	JButton buttonPower, buttonDNF, buttonEndRun, buttonSwap, printPower, 
    button1, button2, button3, button4, 
    button5, button6, button7, button8, button9, 
    buttonStar, button0, buttonPound, buttonTrigger1, 
    buttonTrigger3, buttonTrigger5, buttonTrigger7,
//    left, right, up, down, 
    buttonTrigger2, buttonTrigger6, buttonTrigger4, buttonTrigger8;
    JPanel lPanel, mPanel, mPanel1, mPanel1a, mPanel1b, mPanel1c, mPanel1d, mPanel2, panel_1, panel_2, panel_3,
    rPanel, rPanel1, rPanel2, keypad;
    String tempRacer="";
    JScrollPane scroll, scroll2;
    JComboBox<String> eventType;

    public ChronoGui()
    {
        c = new Console();
        p = new Printer();
        getContentPane().setLayout(null);
		setTitle("ChronoTimer");
        setupGUI();
        setMinimumSize(new Dimension(850, 550));
        setSize(850, 550);
        setVisible(true);
        resizeGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        
    }

    private void resizeGUI() {
		// TODO Auto-generated method stub
    	//Left Panel
    	int lpanelwidth = lPanel.getWidth()-40;
    	lPanel.setBounds(0, 0, getWidth()/4, getHeight());
    	buttonPower.setBounds(20, buttonPower.getLocation().y, lpanelwidth, 60);
    	lblEventType.setLocation(lpanelwidth/2, lblEventType.getLocation().y);
    	eventType.setBounds(20, eventType.getLocation().y, lpanelwidth, 60);
    	buttonDNF.setBounds(20, buttonDNF.getLocation().y, lpanelwidth, 60);
    	buttonSwap.setBounds(20, buttonSwap.getLocation().y, lpanelwidth, 60);
    	buttonEndRun.setBounds(20, buttonEndRun.getLocation().y, lpanelwidth, 60);
    	
    	// Mid Panel
    	mPanel.setBounds(getContentPane().getWidth()/4, 0, 5*getContentPane().getWidth()/12, getContentPane().getHeight());
    	mPanel1.setBounds(0, 0, mPanel.getWidth(), mPanel.getHeight()/2);
    	panel_1.setBounds(0, 0, mPanel.getWidth(), mPanel1.getHeight()/2-20);
    	panel_2.setBounds(0, panel_1.getHeight()+20, mPanel.getWidth(), mPanel1.getHeight()/2-20);
    	scroll.setBounds(20,20, mPanel.getWidth()-40, 2*(mPanel2.getHeight()/3));
    	labelLegend.setBounds((mPanel.getWidth()/2)-70, scroll.getHeight()+50, 150, 16);
		
    	// Right Panel
    	rPanel.setBounds(getContentPane().getWidth()-getContentPane().getWidth()/3, 0, getContentPane().getWidth()/3, getContentPane().getHeight());
		rPanel1.setBounds(0,0,rPanel.getWidth(), rPanel.getHeight()/2);
		rPanel2.setBounds(0,rPanel.getHeight()/2,rPanel.getWidth(), rPanel.getHeight()/2);
    	printPower.setLocation((rPanel.getWidth()/2)-50, 10);
		scroll2.setBounds(10,printPower.getHeight()+20,rPanel.getWidth()-20, rPanel1.getHeight()-printPower.getHeight()-20);
		keypad.setBounds(10, 10, rPanel2.getWidth()-20, rPanel2.getHeight()-20);
		revalidate();
	}

	/**
     * Sends command to Console
     * 
     * @param command
     */
    void sendCommand(String command){
    	String formatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) +"\t"+command;
        c.input(formatted);
        c.display(displayText);
        if(p.active)
        	p.printGUI(formatted, printerText);
        if(!c.checkChronotimer()){
        	radioChannel1.setSelected(false);
        	radioChannel2.setSelected(false);
        	radioChannel3.setSelected(false);
        	radioChannel4.setSelected(false);
        	radioChannel5.setSelected(false);
        	radioChannel6.setSelected(false);
        	radioChannel7.setSelected(false);
        	radioChannel8.setSelected(false);
        	displayText.setText("");
        	eventType.setSelectedIndex(0);
        }
    }
    
	/**
	 * Sets ActionListener for button
	 * 
	 * @param i
	 * @param command
	 */
	void setActionListener(AbstractButton i, String command){
		i.addActionListener(new ActionListener()
        {
            @Override
			public void actionPerformed(ActionEvent e)
            {
                sendCommand(command);
            }
        });
	}

    /**
     * Sets up the GUI
     */
    void setupGUI() {
		getContentPane().setLayout(null);
		// left
		lPanel = new JPanel();
		lPanel.setBounds(0, 0, 150, 377);
		getContentPane().add(lPanel);
			
			buttonPower = new JButton("Power"); 	
			buttonPower.setBounds(52, 45, 100, 50);
			buttonPower.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	                sendCommand("POWER");
	                if(c.checkChronotimer())
	                	sendCommand("NEWRUN");
	            }
	        });
			lPanel.setLayout(null);
			lPanel.add(buttonPower);
			
			lblEventType = new JLabel("Event Type");
			lblEventType.setBounds(0, 120, 73, 16);
			lPanel.add(lblEventType);
			
			eventType = new JComboBox<>();
			eventType.setBounds(53, 142, 100, 30);
			lPanel.add(eventType);
			eventType.addItem("IND");
			eventType.addItem("PARIND");
			eventType.addItem("GRP");
			eventType.addItem("PARGRP");
			eventType.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	            	// TODO - implement swap in chronotimer
	                sendCommand("EVENT "+eventType.getSelectedItem().toString());
	                sendCommand("NEWRUN");
	            }
	        });
			
			buttonEndRun = new JButton("End Run");
			buttonEndRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			
			buttonDNF = new JButton("DNF");
			buttonDNF.setBounds(53, 200, 100, 40);
			lPanel.add(buttonDNF);
			buttonDNF.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	            	sendCommand("DNF");
	            }
	        });
			
			buttonSwap = new JButton("SWAP"); 
			buttonSwap.setLocation(25, 273);
			buttonSwap.setSize(100, 40);
			buttonSwap.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e)
	            {
	            	// TODO - implement swap in chronotimer
	               sendCommand("SWAP");
	            }
	        });
			lPanel.add(buttonSwap);
			buttonEndRun.setBounds(53, 400, 100, 40);
			lPanel.add(buttonEndRun);
			
		
		// middle
		mPanel = new JPanel();
		mPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		mPanel.setLocation(150, 0);
		mPanel.setSize(260, 377);
		getContentPane().add(mPanel);
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
			
			// top middle
			mPanel1 = new JPanel();
			mPanel1.setLayout(null);
			
			panel_1 = new JPanel();
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel_1.setBounds(8, 9, 240, 80);
			mPanel1.add(panel_1);
			panel_1.setLayout(null);
			// split into 4 rows
			JLabel label = new JLabel("Start");
			label.setBounds(2, 2, 96, 38);
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_1.add(label);
			label.setFont(new Font("Lucida Grande", Font.BOLD, 21));
			mPanel1a = new JPanel();
			mPanel1a.setBounds(100, 2, 140, 38);
			panel_1.add(mPanel1a);
			mPanel1a.setLayout(null);
			
			buttonTrigger1 = new JButton();
			buttonTrigger1.setBounds(-4, 5, 40, 30);
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
			label_1 = new JLabel("Enable/Disable");
			label_1.setBounds(3, 40, 96, 38);
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_1.add(label_1);
			mPanel1b = new JPanel();
			mPanel1b.setBounds(100, 40, 140, 38);
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
			panel_2.setBounds(8, 98, 240, 80);
			mPanel1.add(panel_2);
			panel_2.setLayout(null);
			lblFinish = new JLabel("Finish");
			lblFinish.setBounds(2, 2, 96, 38);
			lblFinish.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFinish.setFont(new Font("Lucida Grande", Font.BOLD, 21));
			panel_2.add(lblFinish);
			mPanel1c = new JPanel();
			mPanel1c.setBounds(100, 2, 140, 38);
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
			label_3 = new JLabel("Enable/Disable");
			label_3.setBounds(3, 40, 96, 38);
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			panel_2.add(label_3);
			
			mPanel1d = new JPanel();
			mPanel1d.setBounds(100, 40, 140, 38);
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
			mPanel2 = new JPanel();
			mPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			mPanel2.setSize(mPanel.getWidth(), mPanel.getHeight()/3);
			mPanel2.setLayout(null);
			
			displayText = new JTextArea();
			displayText.setEditable(false);
			scroll = new JScrollPane(displayText);
		    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		    scroll.setBounds(38, 15, 180, 125);
			mPanel2.add(scroll);
			labelLegend = new JLabel("Queue/Running/Final");
			labelLegend.setBounds(60, 155, 136, 16);
			mPanel2.add(labelLegend);
			mPanel.add(mPanel1);
			mPanel.add(mPanel2);
		
		// right
		rPanel = new JPanel();
		rPanel.setLocation(getContentPane().getWidth()-getContentPane().getWidth()/3, 0);
		rPanel.setSize(getContentPane().getWidth()/3, getContentPane().getHeight());
		getContentPane().add(rPanel);
			// top right
			rPanel1 = new JPanel();
			rPanel1.setLocation(0, 0);
			rPanel1.setSize(254, 190);
			
			// mid right
			rPanel2 = new JPanel();
			rPanel2.setLocation(0, 188);
			rPanel2.setSize(254, 278);
			displayText.setRows(10);
			displayText.setColumns(15);
			rPanel2.setLayout(null);
			rPanel.setLayout(null);
			
			
			rPanel.add(rPanel1);
			
			printPower = new JButton("Printer Pwr");
			printPower.setBounds(74, 6, 111, 50);
			printPower.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(p.active)
						p.active = false;
					else
						p.active = true;
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
			
			rPanel.add(rPanel2);			
			keypad = new JPanel();
			rPanel2.add(keypad);
			keypad.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			keypad.setLayout(new GridLayout(4,3,0,1));
			
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
			
//			setActionListener(buttonStar, "");
	        button0 = new JButton("0");
	        buttonPound = new JButton("#");
	        
	        buttonPound.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent e) {
	            	if(tempRacer.length()>0){
		            	sendCommand("num "+tempRacer);
		            	tempRacer="";
	            	}
	            	else {
	            		System.out.println("Enter a number, then press #.");
	            	}
	            }});
	        
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
	 * Sets up actionPerformed for Runner button
	 * @param b
	 * @param i
	 */
	private void buttonAction(JButton b, int i){
		b.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e)
            {
            	tempRacer+=i;
            }
        });
	}
    public static void main( String args[] )
    {
        ChronoGui frame = new ChronoGui();
        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                frame.resizeGUI();
            }

			@Override
			public void componentMoved(ComponentEvent e) {
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				
			}
        });
    }
}
