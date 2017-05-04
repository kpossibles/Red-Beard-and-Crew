package gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import chronotimer.Console;
import chronotimer.Printer;

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
			rPanel1, rPanel2, keypad;
	private String tempRacer = "";
	private JScrollPane scroll, scroll2;
	private JComboBox<String> eventType;
	private String offWarning = "The Chronotimer is currently off.\nTry 'POWER' to turn it on.";
	private BackPanel backpanel;
	private NavPanel navPanel;
	private static final int WIN_HEIGHT = 650;

	/**
	 * Instantiates a new Chronotimer GUI.
	 */
	public ChronoGui() {
		getContentPane().setBackground(new Color(100, 149, 237));
		c = new Console();
		p = new Printer();
		setTitle("ChronoTimer");
		setupGUI();
		setMinimumSize(new Dimension(850, WIN_HEIGHT));
		setSize(850, WIN_HEIGHT);
		setVisible(true);
		resizeGUI();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	/**
	 * Making the GUI respond dynamically to JFrame size
	 */
	private void resizeGUI() {
		// Left Panel
		int lpanelwidth = lPanel.getWidth() - 40;
		lPanel.setBounds(0, 0, getWidth() / 4, WIN_HEIGHT - 200);
		buttonPower.setBounds(20, buttonPower.getLocation().y, lpanelwidth, 60);
		eventType.setBounds(20, eventType.getLocation().y, lpanelwidth, 60);
		navPanel.setBounds(20, navPanel.getLocation().y, lpanelwidth, 60);
		buttonFunction.setBounds(20, buttonFunction.getLocation().y, lpanelwidth, 60);
		buttonSwap.setBounds(20, buttonSwap.getLocation().y, lpanelwidth, 60);
		buttonEndRun.setBounds(20, buttonEndRun.getLocation().y, lpanelwidth, 60);

		// Mid Panel

		mPanel.setBounds(getContentPane().getWidth() / 4, 0, 5 * getContentPane().getWidth() / 12, WIN_HEIGHT - 200);
		mPanel1.setBounds(0, 0, mPanel.getWidth(), mPanel.getHeight() / 2);
		panel_1.setBounds(0, 0, mPanel.getWidth(), mPanel1.getHeight() / 2 - 20);
		int splitwidth = mPanel.getWidth() / 2 - 10;
		int splitheight = panel_1.getHeight() / 2 - 10;
		lblStart.setBounds(0, 10, splitwidth, splitheight);
		enable01.setBounds(0, panel_1.getHeight() / 2, splitwidth, splitheight);
		mPanel1a.setBounds(mPanel.getWidth() / 2, 10, splitwidth, splitheight);
		mPanel1b.setBounds(mPanel.getWidth() / 2, panel_1.getHeight() / 2, splitwidth, splitheight);

		panel_2.setBounds(0, panel_1.getHeight() + 20, mPanel.getWidth(), mPanel.getHeight() / 2 - 20);
		lblFinish.setBounds(0, 10, splitwidth, splitheight);
		enable02.setBounds(0, panel_1.getHeight() / 2, splitwidth, splitheight);
		mPanel1c.setBounds(mPanel.getWidth() / 2, 10, splitwidth, splitheight);
		mPanel1d.setBounds(mPanel.getWidth() / 2, panel_1.getHeight() / 2, splitwidth, splitheight);
		scroll.setBounds(20, 20, mPanel.getWidth() - 40, 2 * (mPanel2.getHeight() / 3));
		labelLegend.setBounds((mPanel.getWidth() / 2) - 70, scroll.getHeight() + 50, 150, 16);

		// Right Panel
		rPanel.setBounds(getContentPane().getWidth() - getContentPane().getWidth() / 3, 0,
				getContentPane().getWidth() / 3, WIN_HEIGHT - 200);
		rPanel1.setBounds(0, 0, rPanel.getWidth(), rPanel.getHeight() / 2);
		rPanel2.setBounds(0, rPanel.getHeight() / 2, rPanel.getWidth(), rPanel.getHeight() / 2);
		printPower.setLocation((rPanel.getWidth() / 2) - 50, 10);
		scroll2.setBounds(10, printPower.getHeight() + 20, rPanel.getWidth() - 20,
				rPanel1.getHeight() - printPower.getHeight() - 20);
		keypad.setBounds(10, 10, rPanel2.getWidth() - 20, rPanel2.getHeight() - 20);
		revalidate();

		// back panel
		backpanel.setSize(getContentPane().getWidth(), backpanel.getHeight());
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
			eventType.setSelectedIndex(0);
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
	 * Sets the ActionListener for sensor.
	 *
	 * @param i the i
	 * @param num the panel number
	 */
	public void setSensorListener(AbstractButton i, int num) {
		i.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO - implement sensor type selection
				Object[] options = { "EYE", "GATE", "PAD" };
				ImageIcon icon = null;
				String s = (String) JOptionPane.showInputDialog(null, "Select a sensor below.", "Sensor Selection",
						JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);

				// If a string was returned, set a sensor.
				if ((s != null) && (s.length() > 0)) {
					backpanel.toggle(num);
					c.setSensor(s, num);
				}
			}
		});
	}

	/**
	 * Sets up the GUI
	 */
	void setupGUI() {
		// back panel
		backpanel = new BackPanel();
		backpanel.setLocation(0, 474);
		getContentPane().add(backpanel);

		getContentPane().setLayout(null);
		// left
		lPanel = new JPanel();
		lPanel.setBackground(new Color(100, 149, 237));
		lPanel.setBounds(0, 0, 150, 377);
		getContentPane().add(lPanel);

		buttonPower = new JButton("Power");
		buttonPower.setBounds(52, 45, 100, 50);
		buttonPower.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("POWER");
				if (c.isOn()) {
					sendCommand("NEWRUN");
				}
			}
		});
		lPanel.setLayout(null);
		lPanel.add(buttonPower);

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
		buttonFunction.setBounds(20, 140, 100, 40);
		lPanel.add(buttonFunction);
		buttonFunction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO - import fcn list to displayText
				String fcnList = "> RESET\n" + "SET TIME\n" + "TOGGLE CHANNEL\n" + "CONNECT SENSOR\n"
						+ "DISCONNECT SENSOR\n" + "SET EVENT\n" + "NEWRUN\n" + "ENDRUN\n" + "PRINT\n" + "EXPORT\n"
						+ "ADD A RACER\n" + "CLEAR\n" + "SWAP\n" + "MARK AS DNF\n" + "TRIGGER A CHANNEL\n" + "START\n"
						+ "FINISH";
				displayText.setText(fcnList);
			}
		});

		buttonSwap = new JButton("SWAP");
		buttonSwap.setLocation(25, 273);
		buttonSwap.setSize(100, 40);
		buttonSwap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("SWAP");
			}
		});

		navPanel = new NavPanel();
		lPanel.add(navPanel);
		lPanel.add(buttonSwap);
		buttonEndRun.setBounds(20, 360, 100, 40);
		lPanel.add(buttonEndRun);

		// middle
		mPanel = new JPanel();
		mPanel.setBackground(new Color(100, 149, 237));
		mPanel.setLocation(150, 0);
		mPanel.setSize(260, 377);
		getContentPane().add(mPanel);
		mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));

		// top middle
		mPanel1 = new JPanel();
		mPanel1.setBackground(new Color(100, 149, 237));
		mPanel1.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(8, 9, 240, 80);
		mPanel1.add(panel_1);
		panel_1.setLayout(null);

		// split into 4 rows
		lblStart = new JLabel("Start");
		lblStart.setBounds(2, 2, 96, 38);
		lblStart.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(lblStart);
		lblStart.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		mPanel1a = new JPanel();
		mPanel1a.setBackground(new Color(100, 149, 237));
		mPanel1a.setLayout(new GridLayout(1, 4, 0, 0));
		mPanel1a.setBounds(100, 2, 140, 38);
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
		enable01.setBounds(3, 40, 96, 38);
		enable01.setHorizontalAlignment(SwingConstants.RIGHT);

		panel_1.add(enable01);
		mPanel1b = new JPanel();
		mPanel1b.setBackground(new Color(100, 149, 237));
		mPanel1b.setLayout(new GridLayout(1, 4, 0, 0));
		mPanel1b.setBounds(100, 40, 140, 38);
		panel_1.add(mPanel1b);

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

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(100, 149, 237));
		panel_2.setBounds(8, 98, 240, 80);
		mPanel1.add(panel_2);
		panel_2.setLayout(null);
		lblFinish = new JLabel("Finish");
		lblFinish.setBounds(2, 2, 96, 38);
		lblFinish.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFinish.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		panel_2.add(lblFinish);
		mPanel1c = new JPanel();
		mPanel1c.setBackground(new Color(100, 149, 237));
		mPanel1c.setLayout(new GridLayout(1, 4, 0, 0));
		mPanel1c.setBounds(100, 2, 140, 38);
		panel_2.add(mPanel1c);

		buttonTrigger2 = new JButton();
		buttonTrigger2.setBounds(-4, 5, 40, 30);
		buttonTrigger2.setText("2");
		mPanel1c.add(buttonTrigger2);
		buttonTrigger2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("trig 2");
			}
		});
		buttonTrigger4 = new JButton();
		buttonTrigger4.setBounds(32, 5, 40, 30);
		buttonTrigger4.setText("4");
		mPanel1c.add(buttonTrigger4);
		buttonTrigger4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("trig 4");
			}
		});
		buttonTrigger6 = new JButton();
		buttonTrigger6.setBounds(68, 5, 40, 30);
		buttonTrigger6.setText("6");
		mPanel1c.add(buttonTrigger6);
		buttonTrigger6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendCommand("trig 6");
			}
		});
		buttonTrigger8 = new JButton();
		buttonTrigger8.setBounds(104, 5, 40, 29);
		buttonTrigger8.setText("8");
		mPanel1c.add(buttonTrigger8);
		enable02 = new JLabel("Enable/Disable");
		enable02.setBounds(3, 40, 96, 38);
		enable02.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(enable02);

		mPanel1d = new JPanel();
		mPanel1d.setBackground(new Color(100, 149, 237));
		mPanel1d.setLayout(new GridLayout(1, 4, 0, 0));
		mPanel1d.setBounds(100, 40, 140, 38);
		panel_2.add(mPanel1d);

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
			public void actionPerformed(ActionEvent e) {
				sendCommand("trig 8");
			}
		});

		// bottom middle panel
		mPanel2 = new JPanel();
		mPanel2.setBackground(new Color(100, 149, 237));
		mPanel2.setSize(mPanel.getWidth(), mPanel.getHeight() / 3);
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
		rPanel.setBackground(new Color(100, 149, 237));
		rPanel.setLocation(getContentPane().getWidth() - getContentPane().getWidth() / 3, 0);
		rPanel.setSize(getContentPane().getWidth() / 3, getContentPane().getHeight());
		getContentPane().add(rPanel);
		// top right
		rPanel1 = new JPanel();
		rPanel1.setBackground(new Color(100, 149, 237));
		rPanel1.setLocation(0, 0);
		rPanel1.setSize(254, 190);

		// mid right
		rPanel2 = new JPanel();
		rPanel2.setBackground(new Color(100, 149, 237));
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

		rPanel.add(rPanel2);
		keypad = new JPanel();
		keypad.setBackground(new Color(100, 149, 237, 0));
		rPanel2.add(keypad);
		keypad.setLayout(new GridLayout(4, 3, 0, 1));

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

		// setActionListener(buttonStar, "");
		button0 = new JButton("0");
		buttonPound = new JButton("#");

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

	/**
	 * If printer is off, print off warning
	 */
	private void offWarning() {
		// System.out.println(offWarning);
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
