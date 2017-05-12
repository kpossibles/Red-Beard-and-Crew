package chronoview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import chronotimer.ChronoTimer;

public class ChronoConsole {
	private ChronoTimer chronotimer = new ChronoTimer();
	
	/**
	 * Input.
	 *
	 * @param str the str
	 * @return true, if successful
	 */
	public boolean input(String str){
		return chronotimer.input(str);
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
				chronotimer.input(nextline);
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
			commands+="WELCOME TO CHRONOTIMER\n"
					+ "Start the system by entering POWER or type HELP for list of commands (EXIT to quit):\n"
					+ "> ";
		else if(promptType.equalsIgnoreCase("help")){
			commands+="\n*** List of all commands ***\n"
					+ "POWER - Turns ChronoTimer on and off\n"
					+ "EXIT - Exits ChronoTimer simulator\n"
					+ "RESET - Resets the system to the initial state\n"
					+ "TIME <hour>:<min>:<sec> - Sets the current time\n"
					+ "TOG <channel> - Toggle the state of the channel <CHANNEL>\n"
					+ "CONN <sensor> <NUM> - Connect a type of sensor to channel <NUM>, <sensor> = {EYE, GATE, PAD}\n"
					+ "DISC <NUM> EVENT <TYPE> - Disconnect a sensor from channel <NUM>\n"
					+ "EVENT <TYPE> - <TYPE> = {IND, PARIND, GRP, PARGRP}\n"
					+ "NEWRUN - Create a new Run (but must end a run first)\n"
					+ "ENDRUN - End a Run\n"
					+ "PRINT <RUN> - Print the run on stdout\n"
					+ "EXPORT <RUN> - Export run in XML to file “RUN<RUN>\n"
					+ "NUM <NUMBER> - Set <NUMBER> as the next competitor to start.\n"
					+ "CLR <NUMBER> - Clear <NUMBER> the competitor from queue\n"
					+ "SWAP - Exchange next two competitors to finish in IND\n"
					+ "DNF - The next competitor to finish will not finish\n"
					+ "TRIG <NUM> - Triggers channel <NUM>\n"
					+ "START - Trigger a start in channel 1\n"
					+ "FINISH - Trigger a finish in channel 2\n";
		}
		else if(promptType.equalsIgnoreCase("waiting for command")){
			commands += "\nPlease enter your command:\n"
					+ "> ";
		}
		System.out.print(commands);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the textfile
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException{
		ChronoConsole simulator = new ChronoConsole();
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
					printCommands("waiting for command");
					nextLine = input.nextLine();
				}
			}
			input.close();
			System.out.println("\nThank you for using ChronoTimer. GOODBYE");
			System.exit(0);
		}
	}
}
