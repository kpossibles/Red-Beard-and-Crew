package chronotimer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;

/**
 * The Class Printer.
 *
 * @author Red Beard & Crew
 */
public class Printer {
	private Queue<String> record;
	private PrintWriter writer = null;
	private boolean active;
	
	/**
	 * Instantiates a new printer.
	 */
	public Printer(){
		record = new LinkedList<String>();
		setActive(false);
	}
	
	/**
	 * Instantiates a new printer.
	 *
	 * @param d the d
	 */
	public Printer(JTextArea d){
		record = new LinkedList<String>();
	}
	
	/**
	 * Adds the event changes to record.
	 *
	 * @param str the str
	 */
	public void addToRecord(String str){
		record.add(str);
	}

	/**
	 * Prints to the Console and adds to record.
	 *
	 * @param str the str
	 */
	public void print(String str) {
		System.out.println(str);
		addToRecord(str);
	}

	/**
	 * Gets the record for printing.
	 *
	 * @return the record
	 */
	public String getRecord(){
		String ret = "\n***CHRONOTIMER RECORD:\n";
		for(String str:record){
			ret+=str+'\n';
		}
		ret+="***END RECORD\n";
		return ret;
	}
	
	/**
	 * Save data.
	 *
	 * @param runNum the run number
	 */
	public void saveData(int runNum){
		// TODO: Might have to add timestamp?
		String data = getRecord();
		try{
			File saveFile = new File(filename(runNum));
		    writer = new PrintWriter(saveFile);
		    writer.println(data);
		    writer.flush();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
	
	private String filename(int runNum){
		return String.format("Run%03d.txt", runNum);
	}
	
	/**
	 * Prints to the GUI.
	 *
	 * @param str the str
	 * @param text the text
	 */
	public void printGUI(String str, JTextArea text){
		text.setText(text.getText()+'\n'+str);
		addToRecord(str);
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
