import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Console
{
	private ChronoTimer timer;
	public Console(){
		timer = new ChronoTimer();
	}
	
	public boolean input(String line){
		if (line.indexOf(' ') == -1)
			line = "DIS \"INVALID INPUT\"";
		String timestamp = line.substring(0, line.indexOf('\t'));
		String command = line.substring(line.indexOf('\t')+1,line.indexOf(' '));
		String argument = "";
		if (line.indexOf(' ') != -1)
			argument = line.substring(line.indexOf(' ') + 1);
		// POWER(if off) Turn system on, enter quiescent state
		// POWER(if on) Turn system off (but stay in simulator)
		if (command.equalsIgnoreCase("POWER")){
			timer.power();
		}
		// RESET Resets the System to initial state
		else if (command.equalsIgnoreCase("RESET")){
			timer.reset();
		}
		// TIME <hour>:<min>:<sec> Set the current time
		else if (command.equalsIgnoreCase("TIME")){
			timer.setTime(argument);
		}
		// TOG <channel> Toggle the state of the channel <channel>
		else if (command.equalsIgnoreCase("TOG")){
			timer.toggle(argument);
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
			timer.setEvent(argument);
		} 
		// NEWRUN Create a new Run (must end a run first)
		else if (command.equalsIgnoreCase("NEWRUN")){
			if(timer.runExist()){
				timer.endRun();
			}
			timer.createRun();
			
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
			timer.clear(argument);
		}
		// SWAP exchange next two competitors to finish in IND
		else if (command.equalsIgnoreCase("SWAP")){
			// NOT IMPLEMENTED IN SPRINT 1
		}
		// DNF The next competitor to finish will not finish
		else if (command.equalsIgnoreCase("DNF")){
			timer.didNotFinish();
		}
		// CANCEL Start isn't valid, competitor still in queue to start
		else if (command.equalsIgnoreCase("CANCEL")){
			// TODO
			timer.discard();
		}
		// TRIG <num> Trigger channel <num>
		else if (command.equalsIgnoreCase("TRIG")){
			timer.trigger(argument);
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
		//TODO: If an argument is passed in with a file name, read from file, otherwise loop through receiving input until cancel. 
		Console simulator = new Console();
		if (args.length>0){
			simulator.readFromText(args[0]);
		}
		else {
			Scanner input = new Scanner(System.in);
			System.out.print(": ");
			String nextLine = input.nextLine();
			while(!nextLine.equals("EXIT")){
				//TODO Append the time before the command
				simulator.input(nextLine);
				System.out.print(": ");
				nextLine = input.nextLine();
			}
			input.close();
		}
	}
	
	
}