package chronotimer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.JTextArea;

/**
 * The Class ChronoTimer.
 */
public class ChronoTimer  {

	/** The event. */
	protected Event event;
	
	/** The channels. */
	protected Channel[] channels;
	
	/** The timer. */
	protected Timer timer;
	
	/** The printer. */
	protected Printer printer;
	
	/** The runs. */
	protected LinkedList<Run> runs;
	
	/** The server. */
	private HttpServer server;
	
	/** The current status (on/off). */
	private boolean on;

	/** The console mode. */
	protected static boolean consoleMode;
	
	/** The display to print updates. */
	protected JTextArea displayView, printerView;
	
	protected boolean fcnBtnStatus, clearStatus, printCmdStatus;


	/**
	 * Instantiates a new ChronoTimer (Only used in Console mode).
	 *
	 */
	public ChronoTimer() {
		printer = new Printer();
		reset();
		on = false;
		consoleMode = true;
	}

	/**
	 * Instantiates a new ChronoTimer.
	 *
	 * @param _printer the printer
	 */
	public ChronoTimer(Printer _printer) throws IOException {
		printer = _printer;
		reset();
		on = false;
	}
	
	public ChronoTimer(Printer _printer, JTextArea _displayView) throws IOException {
		printer = _printer;
		displayView = _displayView;
		reset();
		on = false;
	}
	
	/**
	 * Feedback: if-else statement shortcut to update via commandline or GUI.
	 *
	 * @param message the message
	 */
	public void feedback(String message){
		if(consoleMode)
			System.out.println(message);
		else
			displayView.setText(message);
	}
	
	/**
	 * This resets the ChronoTimer to initial state.
	 * 
	 * @see #input(String)
	 */
	private void reset() {
		timer = new Timer();
		timer.run();
		event = new IndividualTimed();
		channels = new Channel[8];
		for(int i = 0; i < 8; i++){//TODO double check
			channels[i] = new Channel(this, i+1);
		}
		runs = new LinkedList<Run>();
		
		// set up a simple HTTP server on our local host
		server = null; 
		try {
			server= HttpServer.create(new InetSocketAddress(8000), 0);
		} catch (IOException e) {
//			debug("Localhost:8000 is either already connected");
		}
		if(server!=null){
			server.createContext("/results", new PostHandler());	
			server.start();
		}
		if(consoleMode){
			System.out.println("Reset the system.");
		}
	}

	/**
	 * Sets the time.
	 *
	 * @param number the new time
	 * @see #input(String)
	 */
	private void setTime(String number) {
		timer.setTime(number);
		event.setTimer(timer);
	}
	
	/**
	 * Sets the event.
	 *
	 * @param type the new event
	 * @see #input(String)
	 */
	private void setEvent(String type) {
		//IND | PARIND | GRP | PARGRP
		if (type.equalsIgnoreCase("IND")) {
			event = new IndividualTimed();
			feedback("Event set to Individual");
		} else if (type.equalsIgnoreCase("PARIND")) {
			event = new ParallelTimed();
			feedback("Event set to Parallel");
		} else if (type.equalsIgnoreCase("GRP")) {
			event = new GroupTimed();
			feedback("Event set to Group");
		}
		else if(type.equalsIgnoreCase("PARGRP")){
			event = new ParallelGroupTimed(timer, printer);
			feedback("Event Set to Parallel Group");
		}
		createRun();
	}

	/**
	 * Clear the competitor from the queue.
	 *
	 * @param number the number
	 * @see #input(String)
	 */
	private void clear(String number) {
		int index = Integer.valueOf(number);
		if(event.getRun()!=null){
			event.removeRacer(index);
			feedback("ERROR: COULD NOT FIND RACER "+number);
		} else{
			feedback("ERROR: COULD NOT FIND RACER "+number);
		}
	}

	/**
	 * Runner did not finish.
	 * 
	 * @see #input(String)
	 */
	public void didNotFinish() {
		event.didNotFinish();
	}
	
	/**
	 * Triggers a certain channel.
	 *
	 * @param id the id
	 * @see #input(String)
	 */
	private void trigger(String id){
		if(runExist() && event.getRun().isActive() && getRacerListSize()>0){
			int index = Integer.valueOf(id);
			if (index < channels.length && channels[index-1].isOn()){
				channels[index-1].trigger();
			}else{
				feedback("WARNING: Invalid port "+id+" to trigger. Toggle it on!");
			}
		}else{
			if(getRacerListSize()==0)
				feedback("WARNING: No racers in the run.");
			else
				feedback("WARNING: Run does not exist. Cannot trigger.");
		}
	}
	/**
	 * Triggers a certain channel.
	 *
	 * @param id the id
	 */
	protected void trigger(int id) {
		if(channels[id-1].isOn()){
			event.trigger(id);
		}else{ // TODO check if PARGRP check implemented correctly -KP
			feedback(String.format("Channel %d is not active.", id));
			// for PARGRP, marks runner in queue as DNF since channel isn't active
			if(event.getType()=="PARGRP")
				event.didNotFinish();
		}
	}
	
	/**
	 * Toggle.
	 *
	 * @param number the number
	 * @see #input(String)
	 */
	public void toggle(String number) {
		int index = Integer.valueOf(number);
		if (index>0 && index <= channels.length){
			channels[index-1].toggle();
			if(consoleMode)System.out.println("Toggled "+number);
		}else
			feedback("Invalid port to toggle: "+number);
	}
	
	public boolean endRun() {
		if(!runs.getLast().end()){
			feedback("Could not end run! Run is not active.");
			return false;
		}
		return true;
	}
	
	/**
	 * Creates the run.
	 */
	public void createRun() {
		runs.add(new Run(runs.size() + 1));
		event.setRun(runs.getLast());
	}
	
	/**
	 * Checks if a run exists.
	 *
	 * @return true, if successful
	 */
	public boolean runExist() {
		if(runs.size()>0 && runs.getLast().isActive())
			return true;
		return false;
	}
	
	/**
	 * Adds the racer to queue.
	 *
	 * @param num the racer's ID number
	 */
	public void addToQueue(int num){
		event.addRacer(num);
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getEventType() {
		return event.getType();
	}
	
	/**
	 * Gets the event for JUnit testing.
	 *
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Connect or disconnect a sensor.
	 *
	 * TODO: FIX! This should be to change sensor types, not to toggle them!
	 *
	 * @param i the number of the sensor
	 * @param on the on
	 */
	private void connect(String i, boolean on) {
		String[] args = i.split(" ");
		int id;
		if(i.length()==1)
			id = Integer.valueOf(i);
		else{
			id = Integer.valueOf(args[1]);
		}
		
		if (id < channels.length){
			if(on){
				channels[id-1].setOn(i.length()==1?i:args[0]);
			} else{
				channels[id-1].setOff(i.length()==1?i:args[0]);
			}
		}
	}

	
	/**
	 * Display text from Chronotimer.
	 */
	public void display() {
		// TODO double check to see if display is working correctly
		String q = "", r="", f="";
		Run run = runs.peek();
		
		if(on && run!=null && !run.isEmpty()){
			int j=0;
			for(int i=0; i<run.size(); i++){
				if(i>run.size()-1)
					break;
				Racer racer = run.getRacers().get(i);
				
				if(racer.getStart()==0){
					if((event.getType()=="IND" && j<3) || event.getType()=="PARGRP" || 
							(event.getType()=="PARIND" && j<3) || (event.getType()=="GRP" &&j<1))
						q+=(racer.getId()+" Q\n");
					j++;
				}
				if(racer.getStart()>0 && racer.getFinish()==0){
					if(racer.DNF())
						f+=(racer.getId()+" "+" DNF\n");
					else{
						long timeDifference = timer.getTime()-racer.getStart();
						r+=(racer.getId()+" "+"00:"+timer.convertToTime(timeDifference).substring(3)+" R\n");
					}
						
				}
			}
			if(run!=null && run.getLast()!=null)
				f+=(run.getLast().getId()+" "+run.getLast().getTime()+" F\n");
			
			// update display textbox with queue, racing, and finish
			String updatedStatus = String.format("QUEUE:\n%s\n"
					+ "RUNNING:\n%s\n"
					+ "LAST FINISH TIME:\n%s\n", q, r, f);
			displayView.setText(updatedStatus);
		}
	}

	/**
	 * Gets the racers currently running for server update.
	 *
	 * @return the racers
	 */
	public ArrayList<Racer> getCurrentRacers(){
		if(runExist())
			return runs.getLast().getRacers();
		else
			return null;
	}
	
	/**
	 * Gets the racer list size.
	 *
	 * @return the racer list size
	 */
	public int getRacerListSize() {
		ArrayList<Racer> temp = null;
		if(runExist()){
			temp = runs.getLast().getRacers();
		}
		return temp==null ? 0 : temp.size();
	}

	/**
	 * Checks if is channel active.
	 *
	 * @param i the channel lane number (1-8)
	 * @return true, if is channel active
	 */
	public boolean isChannelActive(int i) {
		return channels[i-1].isOn();
	}

	/**
	 * The Class PostHandler.
	 */
	public class PostHandler implements HttpHandler {
		
		/* (non-Javadoc)
		 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
		 */
		public void handle(HttpExchange transmission) throws IOException {
			// set up a stream to read the body of the request
			InputStream inputStr = transmission.getRequestBody();
			OutputStream outputStream = transmission.getResponseBody();

			StringBuilder sb = new StringBuilder();
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb=sb.append((char)nextChar);
				nextChar=inputStr.read();
			}

			String postResponse = "ROGER COMMAND RECEIVED";
			//The only command that does anything is REQUEST_RUN, which returns the racers as a GSON string.
			if (sb.toString().equals("REQUEST_RUN")){
				Gson g = new Gson();
				ArrayList<Racer> currentRun = getCurrentRacers();
				if(currentRun==null){
					//send empty list
					currentRun = new ArrayList<>();
				}
				postResponse = g.toJson(currentRun);
			}

			transmission.sendResponseHeaders(300, postResponse.length());
			outputStream.write(postResponse.getBytes());
			outputStream.close();
		}
	}

	/**
	 * Stop server.
	 */
	public void stopServer() {
		if(server!=null)
			server.stop(1);
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public String getTime() {
		return timer.getTimeString();
	}
	
	/**
	 * EVERYTHING BELOW THIS LINE IS IMPORTED FROM OLD CONSOLE CLASS.
	 * TODO - Check if everything implemented correctly & see if you can get rid of anything
	 *
	 */
	
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
			s = timer.getTimeString() +"\t"+s;
		feedback(s);
		return s;
	}
	
	/**
	 * Adds the timestamp for JUnit testing.
	 *
	 * @param s the s
	 * @param racingTime the racing time for a racer
	 * @return the string
	 */
	public String addTimestamp(String s, int racingTime){
		String temp = timer.getTimeString();
		int sec = Integer.valueOf(temp.substring(6,temp.length()-2))+racingTime;
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
	 * Checks if is on.
	 *
	 * @return the on
	 */
	public boolean isOn() {
		return on;
	}
	
	/**
	 * Sets on/off.
	 *
	 * @param on whether to turn it on or off
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
	
	/**
	 * Sets the sensor.
	 *
	 * @param sensorType the sensor type
	 * @param num the sensor number
	 */
	public void setSensor(String sensorType, int num) {
		input(String.format("CONN %s %d", sensorType, num));		
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
		
		if (command.equalsIgnoreCase("POWER")){
			if (on){//turn off
				reset();
				on=false;
				stopServer();
				if(consoleMode)
					System.out.println("POWER OFF");
			} else {
				on=true;
				if(consoleMode)
					System.out.println("POWER ON");
			}
		}
		else if (isOn()) {
			setTime(timestamp);
			// RESET Resets the System to initial state
			if (command.equalsIgnoreCase("RESET")){
				reset();
				if(consoleMode)
					System.out.println("Reset the program!");
			}
			// TIME <hour>:<min>:<sec> Set the current time
			else if (command.equalsIgnoreCase("TIME")){
				if(argument!="" && argument.length()==10){
					String []split = argument.split(":");
					if(Integer.valueOf(split[0])>23 || Integer.valueOf(split[0])<0){
						feedback("ERROR: HOURS - enter numbers from 00 to 23; try again...");
					} else if(Integer.valueOf(split[1])>59 || Integer.valueOf(split[1])<0){
						feedback("ERROR: MIN - enter numbers from 00 to 59; try again...");
					} else if(Double.valueOf(split[2])>59 || Double.valueOf(split[2])<0){
						feedback("ERROR: SEC - enter numbers from 00 to 59; try again...");
					} else{
						setTime(argument);
						feedback("SET TIME TO < "+argument+" >.");
					}
						
				}else{
					feedback("ERROR: ENTER NEW TIME AS ##:##:##.#; try again...");
				}
					
			}
			// TOG <channel> Toggle the state of the channel <channel>
			else if (command.equalsIgnoreCase("TOG")){
				if(argument.equalsIgnoreCase("all")){//shortcut for testing
					for(int i=1;i<=8;i++){
						toggle(""+i);
					}
				} else
					toggle(argument);
			}
			// CONN <sensor> <num> Connect a type of sensor to channel <num>
			// <sensor> = {EYE, GATE, PAD}
			else if (command.equalsIgnoreCase("CONN")){
				connect(argument, true);
			}
			// DISC <num> Disconnect a sensor from channel <num>
			else if (command.equalsIgnoreCase("DISC")){
				connect(argument, false);
			} 
			// EVENT <type> IND | PARIND | GRP | PARGRP
			else if (command.equalsIgnoreCase("EVENT")){
				setEvent(argument);
			} 
			// NEWRUN Create a new Run (must end a run first)
			else if (command.equalsIgnoreCase("NEWRUN")){
				createRun();
			} 
			// ENDRUN Done with a Run
			else if (command.equalsIgnoreCase("ENDRUN")){
				if(endRun()){
					printer.addToRecord(event.getRun());
				}
			}
			// PRINT <RUN> Print the run on stdout
			else if (command.equalsIgnoreCase("PRINT")){
				if(argument==""){
					feedback("You need to add a Run # to your function in order to print!");
				} else {
					int runNum = Integer.parseInt(argument);
					Run temp=null;
					for(Run r:runs){
						if(r.getId()==runNum){
							printer.export(r);
							temp = r;
						}
					}
					if(temp!=null){
						feedback(printer.convertToRecord(temp));
					}else{
						feedback("Run "+argument+" does not exist!");
					}
				}	
			}
			// EXPORT <RUN> Export run in XML to file "RUN<RUN>"
			else if (command.equalsIgnoreCase("EXPORT")){
				int runNum = Integer.parseInt(argument);
				for(Run r:runs){
					if(r.getId()==runNum){
						new Exporter(r);
					}
				}
				if(consoleMode) 
					System.out.println("EXPORTED RUN "+runNum);
				
			}
			// NUM <number> Set <number> as the next competitor to start.
			else if (command.equalsIgnoreCase("NUM")){
				if(!argument.startsWith("0") && runExist() && argument.length()<=6){
					addToQueue(Integer.valueOf(argument));
			}else{
					if(!runExist()){
						feedback("A run does not exist. Cannot accept "+argument+".\nTry NEWRUN.");
					}
					else
						feedback("Cannot accept "+argument);
				}
			}
			// CLR <number> Clear <number> the competitor from queue
			else if (command.equalsIgnoreCase("CLR")){
				clear(argument);
			}
			// SWAP exchange next two competitors to finish in IND
			else if (command.equalsIgnoreCase("SWAP")){
				if(event.getType()=="IND" && event.getRun().size()>=2){
					event.swap();
					printer.updatePrinterDisplay(line);
				} else {
					if(event.getRun().size()<=2)
						feedback("WARNING: Not enough racers to swap.");
					else
						feedback("WARNING: Cannot swap for this type of event.");
				}
			}
			// DNF The next competitor to finish will not finish
			else if (command.equalsIgnoreCase("DNF")){
				didNotFinish();
			}
			// CANCEL Start isn't valid, competitor still in queue to start
			else if (command.equalsIgnoreCase("CANCEL")){
				event.cancel();
				System.out.println("Cancelled start.");
			}
			// TRIG <num> Trigger channel <num>
			else if (command.equalsIgnoreCase("TRIG")){
				trigger(argument);
			}
			// START Start trigger channel 1 (shorthand for trig 1)
			else if (command.equalsIgnoreCase("START")){
				input(timestamp+"\tTRIG 1");
			}
			// FINISH Finish trigger channel 2 (shorthand for trig 2)
			else if (command.equalsIgnoreCase("FINISH")){
				input(timestamp+"\tTRIG 2");
			}
			else
				return false;
		}
			else {
				System.out.println("The Chronotimer is currently off.  Try 'POWER' to turn it on.");
			}
		
		return true;
	}

}
