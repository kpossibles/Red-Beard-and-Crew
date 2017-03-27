import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Printer {
	private Queue<String> record;
	private PrintWriter writer = null;
	
	/**
	 * Instantiates a new printer.
	 */
	public Printer(){
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
	
	public void saveData(){
		// TODO: Might have to add timestamp?
		String data = getRecord();
		try{
			File saveFile = new File("saved.txt");
		    writer = new PrintWriter(saveFile);
		    writer.println(data);
		    writer.flush();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
}
