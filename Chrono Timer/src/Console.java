import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
//import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//import java.time.format.FormatStyle;
import java.util.Scanner;


public class Console
{
	private ChronoTimer chronotimer;
	public Console(){
		
	}
	
	/**
	 * Parses the input and converts the lines into commands for Chronotimer to execute.
	 *
	 * @param line the line
	 * @return true, if successful
	 */
	public boolean input(String line){
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
			if (chronotimer != null)
				chronotimer = null;
			else
				chronotimer = new ChronoTimer();
		}
		if (chronotimer != null)
			chronotimer.setTime(timestamp);
			// RESET Resets the System to initial state
			if (command.equalsIgnoreCase("RESET")){
				chronotimer.reset();
			}
			// TIME <hour>:<min>:<sec> Set the current time
			else if (command.equalsIgnoreCase("TIME")){
				chronotimer.setTime(argument);
			}
			// TOG <channel> Toggle the state of the channel <channel>
			else if (command.equalsIgnoreCase("TOG")){
				chronotimer.toggle(argument);
			}
			// CONN <sensor><num> Connect a type of sensor to channel <num>
			// <sensor> = {EYE, GATE, PAD}
			else if (command.equalsIgnoreCase("CONN")){
				// NOT IMPLEMENTED IN SPRINT 1
			}
			// DISC <num> Disconnect a sensor from channel <num>
			else if (command.equalsIgnoreCase("DISC")){
				// NOT IMPLEMENTED IN SPRINT 1
			} 
			// EVENT <type> IND | PARIND | GRP | PARGRP
			else if (command.equalsIgnoreCase("EVENT")){
				chronotimer.setEvent(argument);
			} 
			// NEWRUN Create a new Run (must end a run first)
			else if (command.equalsIgnoreCase("NEWRUN")){
				if(chronotimer.runExist()){
					chronotimer.endRun();
				}
				chronotimer.createRun();
			} 
			// ENDRUN Done with a Run
			else if (command.equalsIgnoreCase("ENDRUN")){
				chronotimer.endRun();
			}
			// PRINT <RUN> Print the run on stdout
			else if (command.equalsIgnoreCase("PRINT")){
				// NOT IMPLEMENTED IN SPRINT 1
			}
			// EXPORT <RUN> Export run in XML to file "RUN<RUN>"
			else if (command.equalsIgnoreCase("EXPORT")){
				// NOT IMPLEMENTED IN SPRINT 1
			}
			// NUM <number> Set <number> as the next competitor to start.
			else if (command.equalsIgnoreCase("NUM")){
				chronotimer.addToQueue(Integer.valueOf(argument));
			}
			// CLR <number> Clear <number> the competitor from queue
			else if (command.equalsIgnoreCase("CLR")){
				chronotimer.clear(argument);
			}
			// SWAP exchange next two competitors to finish in IND
			else if (command.equalsIgnoreCase("SWAP")){
				// NOT IMPLEMENTED IN SPRINT 1
			}
			// DNF The next competitor to finish will not finish
			else if (command.equalsIgnoreCase("DNF")){
				chronotimer.didNotFinish();
			}
			// CANCEL Start isn't valid, competitor still in queue to start
			else if (command.equalsIgnoreCase("CANCEL")){
				chronotimer.discard();
			}
			// TRIG <num> Trigger channel <num>
			else if (command.equalsIgnoreCase("TRIG")){
				chronotimer.trigger(argument);
			}
			// START Start trigger channel 1 (shorthand for trig 1)
			else if (command.equalsIgnoreCase("START")){
				input("TRIG 1");
			}
			// FINISH Finish trigger channel 2 (shorthand for trig 2)
			else if (command.equalsIgnoreCase("FINISH")){
				input("TRIG 2");
			} 
			else {
				return false;
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
				input(file_in.nextLine());
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
//					+ "RESET\n - Resets the system to the initial state\n"
					+ "TIME <hour>:<min>:<sec>\n - Sets the current time\n"
					+ "TOG <channel>\n - Toggle the state of the channel <CHANNEL>\n"
//					+ "CONN <sensor> <NUM>\n - Connect a type of sensor to channel <NUM>, <sensor> = {EYE, GATE, PAD}\n"
//					+ "DISC <NUM> EVENT <TYPE>\n - Disconnect a sensor from channel <NUM>\n"
					+ "EVENT <TYPE>\n - <TYPE> = {IND, PARIND, GRP, PARGRP}\n"
					+ "NEWRUN\n - Create a new Run (but must end a run first)\n"
					+ "ENDRUN\n - End a Run\n"
					+ "PRINT <RUN>\n - Print the run on stdout\n"
//					+ "EXPORT <RUN>\n - Export run in XML to file “RUN<RUN>”\n"
					+ "NUM <NUMBER>\n - Set <NUMBER> as the next competitor to start.\n"
//					+ "CLR <NUMBER>\n - Clear <NUMBER> the competitor from queue\n"
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
					System.out.println(nextLine);
					if(!simulator.input(nextLine)){
						System.out.print("ERROR");
					}
					printCommands("waiting for command");
					nextLine = input.nextLine();
				}
			}
			input.close();
			System.out.println("\nThank you for using ChronoTimer. GOODBYE");
		}
	}
	
	
}