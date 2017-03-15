	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	
public class ClientGui extends JFrame {
	    JLabel firstNameLabel;
	     JLabel lastNameLabel;
	     JLabel departmentLabel;
	     JLabel phoneLabel;
	     JTextArea firstNameText;
	     JTextArea lastNameText;
	     JTextArea departmentText;
	     JTextArea phoneText;
	     JLabel genderLabel;
	     JRadioButton maleRadio;
	     JRadioButton femaleRadio;
	     JRadioButton otherRadio;
	     JList titleList;
	     JButton submitButton;
	     JButton exitButton;
	     
	   public ClientGui()
	   {
	     getContentPane().setLayout(null);
	     setupGUI();
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   }
	   void setupGUI()
	   {
		   firstNameLabel = new JLabel();
		firstNameLabel.setLocation(10,16);
		firstNameLabel.setSize(100,50);
		firstNameLabel.setText("First Name");
		getContentPane().add(firstNameLabel);

		lastNameLabel = new JLabel();
		lastNameLabel.setLocation(10,70);
		lastNameLabel.setSize(100,50);
		lastNameLabel.setText("Last Name");
		getContentPane().add(lastNameLabel);

		departmentLabel = new JLabel();
		departmentLabel.setLocation(10,123);
		departmentLabel.setSize(100,50);
		departmentLabel.setText("Department");
		getContentPane().add(departmentLabel);

		phoneLabel = new JLabel();
		phoneLabel.setLocation(10,175);
		phoneLabel.setSize(100,50);
		phoneLabel.setText("Phone");
		getContentPane().add(phoneLabel);

		firstNameText = new JTextArea();
		firstNameText.setLocation(117,16);
		firstNameText.setSize(100,50);
		firstNameText.setText("");
		firstNameText.setRows(1);
		firstNameText.setColumns(1);
		getContentPane().add(firstNameText);

		lastNameText = new JTextArea();
		lastNameText.setLocation(117,71);
		lastNameText.setSize(100,50);
		lastNameText.setText("");
		lastNameText.setRows(5);
		lastNameText.setColumns(5);
		getContentPane().add(lastNameText);

		departmentText = new JTextArea();
		departmentText.setLocation(117,124);
		departmentText.setSize(100,50);
		departmentText.setText("");
		departmentText.setRows(5);
		departmentText.setColumns(5);
		getContentPane().add(departmentText);

		phoneText = new JTextArea();
		phoneText.setLocation(117,176);
		phoneText.setSize(100,50);
		phoneText.setText("");
		phoneText.setRows(5);
		phoneText.setColumns(5);
		getContentPane().add(phoneText);

		genderLabel = new JLabel();
		genderLabel.setLocation(11,243);
		genderLabel.setSize(100,50);
		genderLabel.setText("Gender");
		getContentPane().add(genderLabel);

		maleRadio = new JRadioButton();
		maleRadio.setLocation(11,295);
		maleRadio.setSize(100,50);
		maleRadio.setText("Male");
		maleRadio.setSelected(false);
		getContentPane().add(maleRadio);

		femaleRadio = new JRadioButton();
		femaleRadio.setLocation(11,348);
		femaleRadio.setSize(100,50);
		femaleRadio.setText("Female");
		femaleRadio.setSelected(false);
		getContentPane().add(femaleRadio);

		otherRadio = new JRadioButton();
		otherRadio.setLocation(11,400);
		otherRadio.setSize(100,50);
		otherRadio.setText("Other");
		otherRadio.setSelected(false);
		getContentPane().add(otherRadio);
	//TODO Review List Code
		
		
		titleList = new JList();
		titleList.setLocation(291,19);
		titleList.setSize(100,50);
		//String[] data = {"Mr.","Ms.","Mrs.","Dr.","Col.","Prof."};
		//JList<String> myList = new JList<String>(data);
		getContentPane().add(titleList);

		submitButton = new JButton();
		submitButton.setLocation(294,289);
		submitButton.setSize(100,50);
		submitButton.setText("Submit");
		getContentPane().add(submitButton);

		exitButton = new JButton();
		exitButton.setLocation(294,347);
		exitButton.setSize(100,50);
		exitButton.setText("Exit");
		getContentPane().add(exitButton);

		setTitle("Lab 8");
		setSize(564,536);
		setVisible(true);
		setResizable(true);
		
		
	   }
	    public static void main( String args[] )
	   {
	     new ClientGui();
	   }
	}  

