	import javax.swing.*;
	
public class ClientGui extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel firstNameLabel, lastNameLabel, departmentLabel, phoneLabel, genderLabel;
	JTextArea firstNameText, lastNameText, departmentText, phoneText;
	JRadioButton maleRadio, femaleRadio, otherRadio;
	JList<String> titleList;
	JButton submitButton, clrButton, printButton;
	ButtonGroup radiobuttons = new ButtonGroup();
	  
	public ClientGui() {
	  getContentPane().setLayout(null);
	  setupGUI();
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Sets the JLabel.
	 *
	 * @param label the label
	 * @param lx the location x
	 * @param ly the location y
	 * @param width the width
	 * @param height the height
	 * @param name the name
	 */
	private void setJLabel(JLabel label, int lx, int ly, int width, int height, String name){
		label = new JLabel();
		label.setLocation(lx,ly);
		label.setSize(width, height);
		label.setText(name);
		getContentPane().add(label);
	}
	
	/**
	 * Sets the JTextArea.
	 *
	 * @param textbox the textbox
	 * @param lx the location x
	 * @param ly the location y
	 * @param sx the size x
	 * @param sy the size y
	 * @param row the row
	 * @param column the column
	 */
		private void setJText(JTextArea textbox, int lx, int ly, int width, int height, int row, int col) {
			textbox = new JTextArea();
			textbox.setText("");
			textbox.setLocation(lx,ly);
			textbox.setSize(width, height);
			textbox.setRows(row);
			textbox.setColumns(col);
			
			getContentPane().add(textbox);
	}
	/**
	 * Sets the JLabel.
	 *
	 * @param label the label
	 * @param lx the location x
	 * @param ly the location y
	 * @param width the width
	 * @param height the height
	 * @param name the name
	 */
	private void setJRadio(JRadioButton button, int lx, int ly, int width, int height, String name, boolean selected) {
		button = new JRadioButton(name);
		button.setLocation(lx,ly);
		button.setSize(width, height);
		button.setSelected(selected);
		getContentPane().add(button);
		radiobuttons.add(button);
	}

	public void setupGUI() {
		setJLabel(firstNameLabel, 20, 20, 100, 20, "First Name");
		setJLabel(lastNameLabel, 20, 50, 100, 20, "Last Name");
		setJLabel(departmentLabel, 20, 80, 100, 20, "Department");
		setJLabel(phoneLabel, 20, 110, 100, 20, "Phone");
		setJLabel(phoneLabel, 20, 140, 100, 20, "Gender");
		
		setJText(firstNameText, 110, 20, 150, 20, 1, 1);
		setJText(lastNameText, 110, 50, 150, 20, 5, 5);
		setJText(departmentText, 110, 80, 150, 20, 5, 5);
		setJText(phoneText, 110, 110, 150, 20, 5, 5);
		
		setJRadio(maleRadio, 110, 140, 100, 20, "Male", true);
		setJRadio(femaleRadio, 210, 140, 100, 20, "Female", false);
		setJRadio(otherRadio, 310, 140, 100, 20, "Other", false);
			
		//TODO Review List Code
			
		String[] data = {"Mr.","Ms.","Mrs.","Dr.","Col.","Prof."};
		titleList = new JList<String>(data);
		titleList.setLocation(291,19);
		titleList.setSize(100,100);
		titleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(titleList);
		
		submitButton = new JButton();
		submitButton.setLocation(20,200);
		submitButton.setSize(100,50);
		submitButton.setText("SUBMIT");
		getContentPane().add(submitButton);
		
		clrButton = new JButton();
		clrButton.setLocation(140,200);
		clrButton.setSize(100,50);
		clrButton.setText("CLR");
		getContentPane().add(clrButton);
		
		printButton = new JButton();
		printButton.setText("PRINT");
		printButton.setLocation(260,200);
		printButton.setSize(100,50);
		getContentPane().add(printButton);
		
		setTitle("Lab 8");
		setSize(500,350);
		setVisible(true);
		setResizable(true);			
	}
	
	public static void main( String args[] ) {
		new ClientGui();
	}
}  

