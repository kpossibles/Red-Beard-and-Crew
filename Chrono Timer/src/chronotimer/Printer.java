package chronotimer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;

import com.google.gson.Gson;

/**
 * The Class Printer.
 *
 * @author Red Beard & Crew
 */
public class Printer {
	private ArrayList<Run> record;
	private PrintWriter writer = null;
	private boolean active;
	private JTextArea displayView, printerView;
	
	/**
	 * Instantiates a new printer without a display.
	 */
	public Printer(){
		record = new ArrayList<Run>();
		setActive(false);
	}
	
	/**
	 * Instantiates a new printer with 2 displays.
	 *
	 * @param _displayView the display view
	 * @param _printerView the printer view
	 */
	public Printer(JTextArea _displayView, JTextArea _printerView){
		record = new ArrayList<Run>();
		displayView  = _displayView;
		printerView  = _printerView;
		
	}
	
	/**
	 * Adds the completed run to record.
	 *
	 * @param newRun the new run
	 */
	public void addToRecord(Run newRun){
		record.add(newRun);
	}

	/**
	 * Convert to record.
	 *
	 * @param run the run
	 * @return the string
	 */
	public String convertToRecord(Run run) {
		String ret = "\nRUN "+run.getId()+" RECORD\n"
				+ "======================================================\n"
				+ "Rank\t|| #\t|| Start time\t|| Finish time\t|| Total time\n"
				+ "------------------------------------------------------\n";
		ret+=run.toString();
		return ret;	
	}
	
	/**
	 * Save data.
	 *
	 * @param runNum the run number
	 */
	public void export(Run r){
		Gson g = new Gson();
		String data = g.toJson(r);
		try{
			File saveFile = new File(filename(r.getId()));
		    writer = new PrintWriter(saveFile);
		    writer.println(data);
		    writer.flush();
		} catch (IOException e) {
		   e.printStackTrace();
		}
	}
	
	/**
	 * Filename.
	 *
	 * @param runNum the run number
	 * @return the string
	 */
	private String filename(int runNum){
		return String.format("Run%03d.txt", runNum);
	}

	/**
	 * Checks if the printer is on.
	 *
	 * @return true, if is on
	 */
	public boolean isOn() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Updates the printer display with newly entered command.
	 *
	 * @param formatted the formatted string
	 */
	public void updatePrinterDisplay(String formatted){
		if(printerView!=null){
			String old = printerView.getText();
			printerView.setText(old+formatted);
		}
	}
	
	/**
	 * Feedback - prints to console or displayView.
	 *
	 * @param message the message
	 */
	public void feedback(String message){
		if(ChronoTimer.consoleMode){
			System.out.println(message);
		}
		if(displayView!=null){
			displayView.setText(message);
		}
	}

}
