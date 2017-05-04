package gui;

import java.awt.*;

import javax.swing.*;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

/**
 * The Class BackPanel.
 */
@SuppressWarnings("serial")
public class BackPanel extends JPanel {
	private static final int RECT_WIDTH = 25;
	private static final int RECT_HEIGHT = RECT_WIDTH;
	@SuppressWarnings("rawtypes")
	private HashMap componentMap;
	private JPanel panel_1, panel_2, panel_3, panel_4, panel_5, panel_6, panel_7, panel_8;
	private boolean[] active;

	/**
	 * Instantiates a new back panel.
	 */
	public BackPanel() {
		active = new boolean[8];
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setBackground(new Color(65, 105, 225));
		setSize(850, 154);
		setLayout(null);

		JLabel lblChannel = new JLabel("Channel");
		lblChannel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblChannel.setBounds(20, 67, 64, 16);
		add(lblChannel);

		JPanel channels = new JPanel();
		channels.setBackground(new Color(100, 149, 237));
		channels.setBounds(100, 2, 394, 150);
		add(channels);
		channels.setLayout(null);

		JLabel label_1 = new JLabel("1");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(40, 6, RECT_WIDTH, RECT_HEIGHT);
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		channels.add(label_1);

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBackground(new Color(255, 0, 0));
		panel_1.setBounds(40, 41, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_1);

		JLabel label_3 = new JLabel("3");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_3.setBounds(130, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_3);

		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_3.setBackground(Color.RED);
		panel_3.setBounds(130, 41, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_3);

		JLabel label_5 = new JLabel("5");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_5.setBounds(220, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_5);

		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_5.setBackground(Color.RED);
		panel_5.setBounds(220, 41, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_5);

		JLabel label_7 = new JLabel("7");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_7.setBounds(310, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_7);

		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_7.setBackground(Color.RED);
		panel_7.setBounds(310, 41, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_7);

		JLabel label_2 = new JLabel("2");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_2.setBounds(40, 76, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_2);

		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBackground(Color.RED);
		panel_2.setBounds(40, 111, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_2);

		JLabel label_4 = new JLabel("4");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_4.setBounds(130, 76, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_4);

		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_4.setBackground(Color.RED);
		panel_4.setBounds(130, 111, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_4);

		JLabel label_6 = new JLabel("6");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_6.setBounds(220, 76, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_6);

		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_6.setBackground(Color.RED);
		panel_6.setBounds(220, 111, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_6);

		JLabel label_8 = new JLabel("8");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		label_8.setBounds(310, 76, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_8);

		panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_8.setBackground(Color.RED);
		panel_8.setBounds(310, 111, RECT_WIDTH, RECT_HEIGHT);
		channels.add(panel_8);

		JPanel usb = new JPanel();
		usb.setBackground(new Color(100, 149, 237));
		usb.setBounds(500, 2, 350, 150);
		add(usb);
		usb.setLayout(null);

		JLabel lblNewLabel = new JLabel("USB PORT");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		lblNewLabel.setBounds(175, 62, 99, 26);
		usb.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(70, 62, 60, 26);
		usb.add(panel);

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(100, 149, 237));
		panel_9.setBounds(0, 2, 93, 150);
		add(panel_9);
		createComponentMap();
	}

	/**
	 * Toggle the color of the sensor light.
	 *
	 * @param i the index
	 */
	public void toggle(int i) {
		String str = "panel_" + i;
		if (!active[i - 1])
			getComponentByName(str).setBackground(new Color(0, 255, 0));
		else
			getComponentByName(str).setBackground(Color.RED);
		active[i - 1] = !active[i - 1];
	}

	/**
	 * Creates the component map.
	 */
	@SuppressWarnings("unchecked")
	private void createComponentMap() {
		componentMap = new HashMap<String, Component>();
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			componentMap.put(components[i].getName(), components[i]);
		}
	}

	/**
	 * Gets the component by name.
	 *
	 * @param name the name
	 * @return the component by name
	 */
	public Component getComponentByName(String name) {
		if (componentMap.containsKey(name)) {
			return (Component) componentMap.get(name);
		} else
			return null;
	}

	/**
	 * Gets the panel.
	 *
	 * @param i the i
	 * @return the panel
	 */
	public JPanel getPanel(int i) {
		String str = "panel_" + i;
		return (JPanel) getComponentByName(str);
	}

}
