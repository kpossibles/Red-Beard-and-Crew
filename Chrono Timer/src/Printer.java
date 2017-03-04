import java.util.*;

public class Printer {
	private Queue<String> record;
	
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
}
