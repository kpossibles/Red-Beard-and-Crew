import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;


public class Console
{
	private ChronoTimer chronotimer;
	public Console(){
		
	}
	
	public boolean input(String line){
		if (line.indexOf(' ') == -1)
			line = "DIS \"INVALID INPUT\"";
		String timestamp = line.substring(0, line.indexOf('\t'));
		String command = line.substring(line.indexOf('\t')+1,line.indexOf(' '));
		String argument = "";
		if (line.indexOf(' ') != -1)
			argument = line.substring(line.indexOf(' ') + 1);
		// POWER(if off) Create ChronoTimer, which should set to default state
		// POWER(if on) Delete ChronoTimer
		if (command.equalsIgnoreCase("POWER")){
			if (chronotimer != null)
				chronotimer = null;
			else
				chronotimer = new ChronoTimer();
		}
		if (chronotimer != null)
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
				// NOT IMPLEMENTED IN SPRINT 1
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
				// TODO
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
	
	public static void main(String[] args){
		Console simulator = new Console();
		if (args.length>0){
			simulator.readFromText(args[0]);
		}
		else {
			Scanner input = new Scanner(System.in);
			System.out.print(": ");
			String nextLine = input.nextLine();
			while(!nextLine.equals("EXIT")){
				nextLine = LocalTime.now().toString() + nextLine;
				simulator.input(nextLine);
				System.out.print(": ");
				nextLine = input.nextLine();
			}
			input.close();
		}
	}
	
	
}