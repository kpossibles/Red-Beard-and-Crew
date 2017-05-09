package chronotimer;
import chronoserver.ChronoServer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Class ChronoTimer.
 */
public class ChronoTimer  {

	private Event event;
	private Channel[] channels;
	private Timer timer;
	private Printer print;
	private LinkedList<Run> runs;
	private HttpServer server;

	/**
	 * Instantiates a new ChronoTimer.
	 *
	 * @param _print the print
	 */
	public ChronoTimer (Printer _print) throws IOException{
		print = _print;
		reset();

		// set up a simple HTTP server on our local host
//		server = null; 
		server= HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/results", new PostHandler());	
		server.start();

	}

	/**
	 * This resets the ChronoTimer to initial state.
	 */
	public void reset() {
		timer = new Timer();
		event = new IndividualTimed(timer, print);
		channels = new Channel[8];
		for(int i = 0; i < 8; i++){
			channels[i] = new Channel(this, i+1);
		}
		runs = new LinkedList<Run>();
		runs.add(new Run(1));
		event.setRun(runs.getFirst());
	}

	/**
	 * Sets the time.
	 *
	 * @param number the new time
	 */
	public void setTime(String number) {
		timer.setTime(number);
		event.setTimer(timer);
	}
	
	/**
	 * Sets the event.
	 *
	 * @param type the new event
	 */
	public void setEvent(String type) {
		//IND | PARIND | GRP | PARGRP
		if (type.equalsIgnoreCase("IND")) {
			event = new IndividualTimed(timer, print);
			print.print("Event set to Individual");
		} else if (type.equalsIgnoreCase("PARIND")) {
			event = new ParallelTimed(timer, print);
			print.print("Event set to Parallel");
		} else if (type.equalsIgnoreCase("GRP")) {
			event = new GroupTimed(timer, print);
			print.print("Event set to Group");
		}
		else if(type.equalsIgnoreCase("PARGRP")){
			event = new ParallelGroupTimed(timer, print);
			print.print("Event Set to Parallel Group");
		}
		createRun();
	}

	/**
	 * Toggle.
	 *
	 * @param number the number
	 */
	public void toggle(String number) {
		int index = Integer.valueOf(number);
		if (index < channels.length)
			channels[index-1].toggle();
		else
			print.print("Invalid port to toggle");
	}

	/**
	 * Clear.
	 *
	 * @param number the number
	 */
	public void clear(String number) {
		int index = Integer.valueOf(number);
		event.removeRacer(index);
	}

	/**
	 * Runner did not finish.
	 */
	public void didNotFinish() {
		event.dnf();
	}
	
	/**
	 * Triggers a certain channel.
	 *
	 * @param id the id
	 */
	public void trigger(String id){
		int index = Integer.valueOf(id);
		if (index < channels.length && channels[index-1].isOn()){
			channels[index-1].trigger();
		}else{
			print.print("WARNING: Invalid port to trigger.");
		}
	}
	/**
	 * Triggers a certain channel.
	 *
	 * @param id the id
	 */
	public void trigger(int id) {
		if(channels[id-1].isOn()){
			event.trigger(id);
		}else{ // TODO check if PARGRP check implemented correctly -KP
			System.out.println("is it on? "+channels[id-1].isOn());
			print.print(String.format("Channel %d is not active.", id));
			// for PARGRP, marks runner in queue as DNF since channel isn't active
			if(event.getType()=="PARGRP")
				event.dnf();
		}
	}
	
	/**
	 * Discard.
	 */
	public void discard() {
		event.discard();
	}
	
	/**
	 * End run.
	 *
	 * @return the int
	 */
	public int endRun() {
		return runs.getLast().end();
	}
	
	/**
	 * Creates the run.
	 */
	public void createRun() {
		runs.add(new Run(runs.size() + 1));
		event.setRun(runs.getFirst());
	}
	
	/**
	 * Checks if a run exists.
	 *
	 * @return true, if successful
	 */
	public boolean runExist() {
		if(runs.getLast().isActive())
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
	 * Swap 1st and 2nd runner on individual event.
	 */
	public void swap() {
		if(event.getType()=="IND"){
			event.swap();
		} else {
			print.print("WARNING: Cannot swap for this type of event.");
		}
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getEventType() {
		return event.getType();
	}
	
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
	public void connect(String i, boolean on) {
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
	 * Display.
	 *
	 * @param textbox the display
	 */
	public void display(JTextArea textbox) {
		// TODO double check to see if display is working correctly
		String q = "", r="", f="";
		Run run = runs.peek();
		if(!run.isEmpty()){
			int j=0;
			for(int i=0; i<run.size(); i++){
				if(i>run.size()-1)
					break;
				Racer racer = run.getRacers().get(i);
				
				if(racer.getStart()==0 && j<3){
					q+=(racer.getId()+" Q\n");
					j++;
				}
				if(racer.getStart()>0 && racer.getFinish()==0){
					if(racer.DNF())
						f+=(racer.getId()+"  "+" DNF\n");
					else
						r+=(racer.getId()+"  "+racer.getStartTime()+" R\n");
				}
			}
			if(run.getLast()!=null)
				f+=(run.getLast().getId()+"  "+run.getLast().getTime()+" F\n");
			
			// update display textbox with queue, racing, and finish
			if(q==""){
				if(r=="")
					textbox.setText(f);
				else
					textbox.setText(r+"\n"+f);
			}
			else
				textbox.setText(q+"\n"+r+"\n"+f);
		}
	}

	/**
	 * Gets the racers currently running.
	 *
	 * @return the racers
	 */
	public ArrayList<Racer> getRacers(){
		if(runExist())
			return runs.getLast().getRacers();
		return new ArrayList<Racer>();
	}

	/**
	 * Checks if is channel active.
	 *
	 * @param i the i
	 * @return true, if is channel active
	 */
	public boolean isChannelActive(int i) {
		return channels[i-1].isOn();
	}

	public class PostHandler implements HttpHandler {
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
				postResponse = g.toJson(getRacers());
			}

			transmission.sendResponseHeaders(300, postResponse.length());
			outputStream.write(postResponse.getBytes());
			outputStream.close();
		}
	}

	public void stopServer() {
		if(server!=null)
			server.stop(1);
	}

	public Timer getTimer() {
		return timer;
	}

}
