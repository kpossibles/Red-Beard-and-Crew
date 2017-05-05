package gui;

import java.awt.*;

import javax.swing.*;
import java.util.*;
import javax.swing.border.LineBorder;

/**
 * The Class BackPanel.
 */
@SuppressWarnings("serial")
public class BackPanel extends JPanel {
	private static final int RECT_WIDTH = 25;
	private static final int RECT_HEIGHT = RECT_WIDTH;
	@SuppressWarnings("rawtypes")
	private HashMap componentMap;
	private JButton channel_1, channel_2, channel_3, channel_4, channel_5, channel_6, channel_7, channel_8;
	private boolean[] active;

	/**
	 * Instantiates a new back panel.
	 */
	public BackPanel() {
		active = new boolean[8];
		setBackground(new Color(51, 51, 102));
		setSize(850, 130);
		setLayout(null);

		JLabel lblChannel = new JLabel("CHANNEL");
		lblChannel.setForeground(new Color(255, 255, 255));
		lblChannel.setFont(new Font("Lucida Grande", Font.BOLD, 21));
		lblChannel.setBounds(18, 52, 103, 26);
		add(lblChannel);

		JPanel channels = new JPanel();
		channels.setBackground(new Color(0,0,0,0));
		channels.setBounds(115, 0, 380, 130);
		add(channels);
		channels.setLayout(null);

		JLabel label_1 = new JLabel("1");
		label_1.setForeground(new Color(255, 255, 255));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(40, 6, RECT_WIDTH, RECT_HEIGHT);
		label_1.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		channels.add(label_1);

		channel_1 = new JButton();
		channel_1.setBorderPainted(false);
		channel_1.setBackground(Color.RED);
		channel_1.setOpaque(true);
		channel_1.setBounds(40, 33, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_1);

		JLabel label_3 = new JLabel("3");
		label_3.setForeground(new Color(255, 255, 255));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_3.setBounds(130, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_3);

		channel_3 = new JButton();
		channel_3.setBorderPainted(false);
		channel_3.setBackground(Color.RED);
		channel_3.setOpaque(true);
		channel_3.setBounds(130, 33, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_3);

		JLabel label_5 = new JLabel("5");
		label_5.setForeground(new Color(255, 255, 255));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_5.setBounds(220, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_5);

		channel_5 = new JButton();
		channel_5.setBorderPainted(false);
		channel_5.setBackground(Color.RED);
		channel_5.setOpaque(true);
		channel_5.setBounds(220, 33, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_5);

		JLabel label_7 = new JLabel("7");
		label_7.setForeground(new Color(255, 255, 255));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_7.setBounds(310, 6, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_7);

		channel_7 = new JButton();
		channel_7.setBorderPainted(false);
		channel_7.setBackground(Color.RED);
		channel_7.setOpaque(true);
		channel_7.setBounds(310, 33, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_7);

		JLabel label_2 = new JLabel("2");
		label_2.setForeground(new Color(255, 255, 255));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_2.setBounds(40, 59, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_2);

		channel_2 = new JButton();
		channel_2.setBorderPainted(false);
		channel_2.setBackground(Color.RED);
		channel_2.setOpaque(true);
		channel_2.setBounds(40, 86, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_2);

		JLabel label_4 = new JLabel("4");
		label_4.setForeground(new Color(255, 255, 255));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_4.setBounds(130, 59, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_4);

		channel_4 = new JButton();
		channel_4.setBorderPainted(false);
		channel_4.setBackground(Color.RED);
		channel_4.setOpaque(true);
		channel_4.setBounds(130, 86, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_4);

		JLabel label_6 = new JLabel("6");
		label_6.setForeground(new Color(255, 255, 255));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_6.setBounds(220, 59, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_6);

		channel_6 = new JButton();
		channel_6.setBorderPainted(false);
		channel_6.setBackground(Color.RED);
		channel_6.setOpaque(true);
		channel_6.setBounds(220, 86, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_6);

		JLabel label_8 = new JLabel("8");
		label_8.setForeground(new Color(255, 255, 255));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		label_8.setBounds(310, 59, RECT_WIDTH, RECT_HEIGHT);
		channels.add(label_8);

		channel_8 = new JButton();
		channel_8.setBorderPainted(false);
		channel_8.setBackground(Color.RED);
		channel_8.setOpaque(true);
		channel_8.setBounds(310, 86, RECT_WIDTH, RECT_HEIGHT);
		channels.add(channel_8);

		JPanel usb = new JPanel();
		usb.setBackground(new Color(0,0,0,0));
		usb.setBounds(500, 0, 350, 130);
		add(usb);
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
