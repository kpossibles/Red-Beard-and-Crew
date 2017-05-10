package chronotimer;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JTextArea;

/**
 * The Class Console.
 *
 * @author Red Beard & Crew
 */
public class Console
{
	private ChronoTimer chronotimer;
	private Printer printer;
	private boolean on;
	
	/**
	 * Instantiates a new console.
	 */
	public Console(){
		printer = new Printer();
		on = false;
	}
	
	/**
	 * Debug.
	 *
	 * @param str the str
	 */
	public void debug(String str){
		System.out.println("***DEBUG: "+str);
	}
	
	/**
	 * Adds the timestamp for JUnit testing.
	 *
	 * @param s the s
	 * @return the string
	 */
	public String addTimestamp(String s){
		if(s.equalsIgnoreCase("POWER"))
			s= LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S"))+"\t"+s;
		else
			s = chronotimer.getTimer().getTimeString() +"\t"+s;
		System.out.println(s);
		return s;
	}
	
	/**
	 * Adds the timestamp for JUnit testing.
	 *
	 * @param s the s
	 * @param i the i
	 * @return the string
	 */
	public String addTimestamp(String s, int i){
		String temp = chronotimer.getTimer().getTimeString();
		int sec = Integer.valueOf(temp.substring(6,temp.length()-2))+i;
		int min = Integer.valueOf(temp.substring(3,5));
		int hour = Integer.valueOf(temp.substring(0,2));
		if(sec>60){
			sec-=60;
			min++;
			if(min>60){
				min-=60;
				hour++;
			}
		}
		s= String.format("%02d:%02d:%02d.0\t%s", hour, min,sec,s);
		return s;
	}
	
	/**
	 * Parses the input and converts the lines into commands for Chronotimer to execute.
	 *
	 * @param line the line
	 * @return true, if successful
	 */
	public boolean input(String line) {
		if (line.indexOf('\t') == -1)
			line = "DIS \"INVALID INPUT\"";
		String timestamp = line.substring(0, line.indexOf('\t'));
		String command, argument = "";
		if(line.indexOf(' ') == -1){
			command = line.substring(line.indexOf('\t')+1,line.length());
		}
		else{
			command = line.substring(line.indexOf('\t')+1,line.indexOf(' '));
			argument = line.substring(line.indexOf(' ') + 1);
		}
		// POWER(if off) Create ChronoTimer, which should set to default state
		// POWER(if on) Delete ChronoTimer
		if (command.equalsIgnoreCase("POWER")){
			if (chronotimer != null){
				chronotimer.reset();
				setOn(false);
				System.out.println("POWER OFF");
			} else {
				try {
					chronotimer = new ChronoTimer(printer);
					setOn(true);
					System.out.println("POWER ON");
				}
				catch(Exception e){
					System.out.println("Could not start Chronotimer. Is port 8000 already bound?");
				}
			}
		}
		else if (isOn()) {
			chronotimer.setTime(timestamp);
			// RESET Resets the System to initial state
			if (command.equalsIgnoreCase("RESET")){
				chronotimer.reset();
			}
			// TIME <hour>:<min>:<sec> Set the current time
			else if (command.equalsIgnoreCase("TIME")){
				if(argument!="" && argument.length()==10){
					String []split = argument.split(":");
					if(Integer.valueOf(split[0])>23 || Integer.valueOf(split[0])<0)
						printer.print("ERROR: HOURS - enter numbers from 00 to 23");
					else if(Integer.valueOf(split[1])>59 || Integer.valueOf(split[1])<0)
						printer.print("ERROR: MIN - enter numbers from 00 to 59");
					else if(Double.valueOf(split[2])>59 || Double.valueOf(split[2])<0)
						printer.print("ERROR: SEC - enter numbers from 00 to 59");
					else
						chronotimer.setTime(argument);
				}else
					printer.print("ERROR: ENTER NEW TIME AS ##:##:##.#");
			}
			// TOG <channel> Toggle the state of the channel <channel>
			else if (command.equalsIgnoreCase("TOG")){
				chronotimer.toggle(argument);
//				System.out.println("Toggled "+argument);
			}
			// CONN <sensor> <num> Connect a type of sensor to channel <num>
			// <sensor> = {EYE, GATE, PAD}
			else if (command.equalsIgnoreCase("CONN")){
				chronotimer.connect(argument, true);
			}
			// DISC <num> Disconnect a sensor from channel <num>
			else if (command.equalsIgnoreCase("DISC")){
				debug(argument);
				chronotimer.connect(argument, false);
			} 
			// EVENT <type> IND | PARIND | GRP | PARGRP
			else if (command.equalsIgnoreCase("EVENT")){
				chronotimer.setEvent(argument);
			} 
			// NEWRUN Create a new Run (must end a run first)
			else if (command.equalsIgnoreCase("NEWRUN")){
				chronotimer.createRun();
//				System.out.println("New run created.");
			} 
			// ENDRUN Done with a Run
			else if (command.equalsIgnoreCase("ENDRUN")){
				int runNum = chronotimer.endRun();
				printer.saveData(runNum);
//				System.out.println("Run ended.");
			}
			// PRINT <RUN> Print the run on stdout
			else if (command.equalsIgnoreCase("PRINT")){
				System.out.println(printer.getRecord());
			}
			// EXPORT <RUN> Export run in XML to file "RUN<RUN>"
			else if (command.equalsIgnoreCase("EXPORT")){
				int runNum = Integer.parseInt(argument);
				printer.saveData(runNum);
			}
			// NUM <number> Set <number> as the next competitor to start.
			else if (command.equalsIgnoreCase("NUM")){
				chronotimer.addToQueue(Integer.valueOf(argument));
//				System.out.println("Adding "+argument);
			}
			// CLR <number> Clear <number> the competitor from queue
			else if (command.equalsIgnoreCase("CLR")){
				chronotimer.clear(argument);
			}
			// SWAP exchange next two competitors to finish in IND
			else if (command.equalsIgnoreCase("SWAP")){
				// TODO - double check if working correctly
				chronotimer.swap();
			}
			// DNF The next competitor to finish will not finish
			else if (command.equalsIgnoreCase("DNF")){
				chronotimer.didNotFinish();
//				System.out.println("Did not finish.");
			}
			// CANCEL Start isn't valid, competitor still in queue to start
			else if (command.equalsIgnoreCase("CANCEL")){
				chronotimer.discard();
				System.out.println("Cancelled start.");
			}
			// TRIG <num> Trigger channel <num>
			else if (command.equalsIgnoreCase("TRIG")){
				chronotimer.trigger(argument);
			}
			// START Start trigger channel 1 (shorthand for trig 1)
			else if (command.equalsIgnoreCase("START")){
				input(timestamp+"\tTRIG 1");
			}
			// FINISH Finish trigger channel 2 (shorthand for trig 2)
			else if (command.equalsIgnoreCase("FINISH")){
				input(timestamp+"\tTRIG 2");
//				System.out.println("Finish!");
			} 
			else
				return false;
		}
			else {
				System.out.println("The Chronotimer is currently off.  Try 'POWER' to turn it on.");
			}
		
		return true;
	}
	
	
	
	/**
	 * Reads from text file.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	public boolean readFromText(String path){
		try {
			File f = new File(path);
			Scanner file_in = new Scanner(f);
			while(file_in.hasNextLine()){
				String nextline = file_in.nextLine();
				if(nextline.equals("EXIT"))
					break;
				input(nextline);
				addToRecord(nextline);
			}
			file_in.close();
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Prints the prompt onto console.
	 *
	 * @param promptType the prompt type
	 */
	public static void printCommands(String promptType){
		String commands="";
		
		if(promptType.equalsIgnoreCase("welcome"))
			commands+=">WELCOME TO CHRONOTIMER\nStart the system by entering POWER or type HELP for list of commands (EXIT to quit): ";
		else if(promptType.equalsIgnoreCase("help")){
			commands+="\n*** List of all commands ***\n"
					+ "POWER\n - Turns ChronoTimer on and off\n"
					+ "EXIT\n - Exits ChronoTimer simulator\n"
					+ "RESET\n - Resets the system to the initial state\n"
					+ "TIME <hour>:<min>:<sec>\n - Sets the current time\n"
					+ "TOG <channel>\n - Toggle the state of the channel <CHANNEL>\n"
					+ "CONN <sensor> <NUM>\n - Connect a type of sensor to channel <NUM>, <sensor> = {EYE, GATE, PAD}\n"
					+ "DISC <NUM> EVENT <TYPE>\n - Disconnect a sensor from channel <NUM>\n"
					+ "EVENT <TYPE>\n - <TYPE> = {IND, PARIND, GRP, PARGRP}\n"
					+ "NEWRUN\n - Create a new Run (but must end a run first)\n"
					+ "ENDRUN\n - End a Run\n"
					+ "PRINT <RUN>\n - Print the run on stdout\n"
					+ "EXPORT <RUN>\n - Export run in XML to file “RUN<RUN>\n"
					+ "NUM <NUMBER>\n - Set <NUMBER> as the next competitor to start.\n"
					+ "CLR <NUMBER>\n - Clear <NUMBER> the competitor from queue\n"
					+ "SWAP\n - Exchange next two competitors to finish in IND\n"
					+ "DNF\n - The next competitor to finish will not finish\n"
					+ "TRIG <NUM>\n - Triggers channel <NUM>\n"
					+ "START\n - Trigger a start in channel 1\n"
					+ "FINISH\n - Trigger a finish in channel 2";
		}
		else if(promptType.equalsIgnoreCase("waiting for command")){
			commands += "\n>Please enter your command:";
		}
		System.out.println(commands);
	}
	
	/**
	 * Adds the event changes to record.
	 *
	 * @param str the str
	 */
	public void addToRecord(String str){
		printer.addToRecord(str);
	}
	
	/**
	 * Gets the record for printing.
	 *
	 * @return the record
	 */
	public String getRecord(){
		return printer.getRecord();
	}
	
	/**
	 * Display.
	 *
	 * @param textbox the display
	 */
	public void display(JTextArea textbox){
		if(chronotimer!=null)
			chronotimer.display(textbox);
	}
	
	/**
	 * Check chronotimer.
	 *
	 * @return true, if successful
	 */
	public boolean checkChronotimer(){
		return chronotimer!=null;
	}
	
	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String getEventType(){
		return chronotimer.getEventType();
	}
	
	/**
	 * Gets the event.
	 *
	 * @return the event
	 */
	public Event getEvent(){
		return chronotimer.getEvent();
	}
	
	/**
	 * Gets the racers currently running.
	 *
	 * @return the racers
	 */
	public ArrayList<Racer> getRacers(){
		return chronotimer.getRacers();
	}
	
	/**
	 * Checks if is on.
	 *
	 * @return the on
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * Sets the on.
	 *
	 * @param on the on to set
	 */
	public void setOn(boolean on) {
		this.on = on;
	}

	/**
	 * Sets the sensor.
	 *
	 * @param sensorType the sensor type
	 * @param num the num
	 */
	public void setSensor(String sensorType, int num) {
		input(String.format("CONN %s %d", sensorType, num));		
	}
	
	/**
	 * Checks if is channel active.
	 *
	 * @param i the i
	 * @return true, if is channel active
	 */
	public boolean isChannelActive(int i){
		return chronotimer.isChannelActive(i);
	}

	/**
		 * The main method.
		 *
		 * @param args the textfile
		 */
		public static void main(String[] args){
			Console simulator = new Console();
			if (args.length>0){
				simulator.readFromText(args[0]);
			}
			else {
				String nextLine = "";
				printCommands("welcome");
				Scanner input = new Scanner(System.in);
				nextLine = input.nextLine();
				while(!nextLine.equalsIgnoreCase("EXIT")){
					if(nextLine.equalsIgnoreCase("help")){
						printCommands("help");
						printCommands("waiting for command");
						nextLine = input.nextLine();
					}
					else {
						nextLine = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) +"\t"+ nextLine;
						if(!simulator.input(nextLine)){
							System.out.println("Sorry, cannot accept that command.");
						}
	//					else
	//						simulator.addToRecord(nextLine);
						printCommands("waiting for command");
						nextLine = input.nextLine();
					}
				}
				input.close();
				System.out.println("\nThank you for using ChronoTimer. GOODBYE");
				System.exit(0);
			}
		}

	/**
	 * Gets the racer list size.
	 *
	 * @return the racer list size
	 */
	public int getRacerListSize() {
		return chronotimer.getRacers().size();
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return chronotimer.getTimer().getTimeString();
	}

	/**
	 * Gets the display text.
	 *
	 * @return the display text
	 */
	public String getDisplayText() {
		return chronotimer.getDisplayText();
	}
	
	/**
	 * Reset.
	 */
	public void reset(){
		chronotimer.reset();
	}
	
}