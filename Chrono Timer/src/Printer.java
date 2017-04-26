import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;

public class Printer {
	private Queue<String> record;
	private PrintWriter writer = null;
	boolean active;
	
	/**
	 * Instantiates a new printer.
	 */
	public Printer(){
		record = new LinkedList<String>();
		active = false;
	}
	
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
	 * Prints to the Console and adds to record
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
	
	public void printGUI(String str,JTextArea text){
		text.setText(text.getText()+'\n'+str);
		addToRecord(str);
	}

}
