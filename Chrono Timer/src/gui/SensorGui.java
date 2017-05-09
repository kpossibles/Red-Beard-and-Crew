package gui;
import javax.swing.JButton;
import javax.swing.JFrame;

import chronotimer.Console;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class SensorGui extends JFrame{
	private JPanel p1,p2,p3,p4,p5,p6,p7,p8;
	private Console mainGui;
	
	/**
	 * Instantiates a new sensor gui Jframe (popup).
	 */
	public SensorGui() {
		setSize(500, 500);
		setBackground(new Color(135,206,250));
		setTitle("Sensors");
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public SensorGui(Console c) {
		mainGui = c;
		setSize(500, 500);
		setTitle("Sensors");
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * Setup GUI.
	 */
	private void setupGUI() {
		getContentPane().setLayout(new GridLayout(4, 2, 0, 0));
		
		p1 = new JPanel();
		p1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p1.setBackground(new Color(51, 153, 204));
		getContentPane().add(p1);
		p1.setLayout(new BorderLayout(0, 0));
		
		p2 = new JPanel();
		p2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p2.setBackground(new Color(51, 153, 204));
		getContentPane().add(p2);
		p2.setLayout(new BorderLayout(0, 0));
		
		p3 = new JPanel();
		p3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p3.setBackground(new Color(51, 153, 204));
		getContentPane().add(p3);
		p3.setLayout(new BorderLayout(0, 0));
		
		p4 = new JPanel();
		p4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p4.setBackground(new Color(51, 153, 204));
		getContentPane().add(p4);
		p4.setLayout(new BorderLayout(0, 0));
		
		p5 = new JPanel();
		p5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p5.setBackground(new Color(51, 153, 204));
		getContentPane().add(p5);
		p5.setLayout(new BorderLayout(0, 0));
		
		p6 = new JPanel();
		p6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p6.setBackground(new Color(51, 153, 204));
		getContentPane().add(p6);
		p6.setLayout(new BorderLayout(0, 0));
		
		p7 = new JPanel();
		p7.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p7.setBackground(new Color(51, 153, 204));
		getContentPane().add(p7);
		p7.setLayout(new BorderLayout(0, 0));
		
		p8 = new JPanel();
		p8.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		p8.setBackground(new Color(51, 153, 204));
		getContentPane().add(p8);
		p8.setLayout(new BorderLayout(0, 0));
	}

	/**
	 * Adds the sensor button.
	 *
	 * @param type the type
	 * @param i the i
	 */
	public void addSensorButton(String type, int i){
		JButton sensor = new JButton("Sensor "+i+", "+type);
		sensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sensor "+i+" pressed!");
				mainGui.input(mainGui.addTimestamp("TRIG "+i));
			}
		});
		switch(i){
			case 1:
				p1.add(sensor);
				break;
			case 2:
				p2.add(sensor);
				break;
			case 3:
				p3.add(sensor);
				break;
			case 4:
				p4.add(sensor);
				break;
			case 5:
				p5.add(sensor);
				break;
			case 6:
				p6.add(sensor);
				break;
			case 7:
				p7.add(sensor);
				break;
			case 8:
				p8.add(sensor);
				break;
		}
		revalidate();
		
	}

	public void removeSensorButton(int i){
		System.out.println("SENSOR: Disconnecting sensor "+i);
		switch(i){
			case 1:
				p1.removeAll();
//				p1.setLayout(new BorderLayout(0, 0));
				break;
			case 2:
				p2.removeAll();
				p2.setLayout(new BorderLayout(0, 0));
				break;
			case 3:
				p3.removeAll();
				p4.setLayout(new BorderLayout(0, 0));
				break;
			case 4:
				p4.removeAll();
				p4.setLayout(new BorderLayout(0, 0));
				break;
			case 5:
				p5.removeAll();
				p5.setLayout(new BorderLayout(0, 0));
				break;
			case 6:
				p6.removeAll();
				p6.setLayout(new BorderLayout(0, 0));
				break;
			case 7:
				p7.removeAll();
				p7.setLayout(new BorderLayout(0, 0));
				break;
			case 8:
				p8.removeAll();
				p8.setLayout(new BorderLayout(0, 0));
				break;
		}
	}

	public void close(){
		setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
	}
}
