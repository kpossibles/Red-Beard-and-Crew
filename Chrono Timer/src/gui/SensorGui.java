package gui;
import javax.swing.JButton;
import javax.swing.JFrame;

import chronotimer.Sensor;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class SensorGui extends JFrame{
	private JPanel p1,p2,p3,p4,p5,p6,p7,p8;
	private ChronoGui mainGui;
	
	/**
	 * Instantiates a new sensor gui Jframe (popup).
	 */
	public SensorGui() {
		setSize(500, 500);
		setTitle("Sensors");
		setupGUI();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Setup GUI.
	 */
	private void setupGUI() {
		getContentPane().setLayout(new GridLayout(4, 2, 0, 0));
		
		p1 = new JPanel();
		getContentPane().add(p1);
		p1.setLayout(new BorderLayout(0, 0));
		
		p2 = new JPanel();
		getContentPane().add(p2);
		p2.setLayout(new BorderLayout(0, 0));
		
		p3 = new JPanel();
		getContentPane().add(p3);
		p3.setLayout(new BorderLayout(0, 0));
		
		p4 = new JPanel();
		getContentPane().add(p4);
		p4.setLayout(new BorderLayout(0, 0));
		
		p5 = new JPanel();
		getContentPane().add(p5);
		p5.setLayout(new BorderLayout(0, 0));
		
		p6 = new JPanel();
		getContentPane().add(p6);
		p6.setLayout(new BorderLayout(0, 0));
		
		p7 = new JPanel();
		getContentPane().add(p7);
		p7.setLayout(new BorderLayout(0, 0));
		
		p8 = new JPanel();
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
		System.out.println("Connecting sensor "+i);
		JButton sensor = new JButton("Sensor "+i+", "+type);
		sensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO add implementation
				System.out.println("Test");
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
		System.out.println("Disconnecting sensor "+i);
		switch(i){
			case 1:
				p1= new JPanel();
				p1.setLayout(new BorderLayout(0, 0));
				break;
			case 2:
				p2= new JPanel();
				p2.setLayout(new BorderLayout(0, 0));
				break;
			case 3:
				p3= new JPanel();
				p4.setLayout(new BorderLayout(0, 0));
				break;
			case 4:
				p4= new JPanel();
				p4.setLayout(new BorderLayout(0, 0));
				break;
			case 5:
				p5= new JPanel();
				p5.setLayout(new BorderLayout(0, 0));
				break;
			case 6:
				p6= new JPanel();
				p6.setLayout(new BorderLayout(0, 0));
				break;
			case 7:
				p7= new JPanel();
				p7.setLayout(new BorderLayout(0, 0));
				break;
			case 8:
				p8= new JPanel();
				p8.setLayout(new BorderLayout(0, 0));
				break;
		}
	}
}
