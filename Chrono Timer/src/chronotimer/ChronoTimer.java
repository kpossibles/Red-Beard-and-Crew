package chronotimer;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Class ChronoTimer.
 */
public class ChronoTimer  {
	private Map<Integer, String> NumNameMap;
	private Event event;
	private Channel[] channels;
	private Timer timer;
	private Printer print;
	private LinkedList<Run> runs;

	/**
	 * Instantiates a new ChronoTimer.
	 *
	 * @param _print the print
	 */
	public ChronoTimer (Printer _print) throws IOException{
		print = _print;
		reset();

		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to display the results
		server.createContext("/displayresults", new DisplayHandler());

		// create a context to get the request to display the results
		server.createContext("/style.css", new cssHandler());

		server.start();

		buildMap();
	}

	private void buildMap(){
		NumNameMap = new HashMap<Integer, String>();

		NumNameMap.put(100, "Evan");
		NumNameMap.put(200, "Kim");
		NumNameMap.put(300, "James");
		NumNameMap.put(400, "Biscuit");

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
		createRun();
//		else if(type.equalsIgnoreCase("PARGRP")){
//			event = new ParallelGroupTimed(timer, print);
//			print.print("Event Set to Parallel Group");
//		}
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
		if (index < channels.length)
			channels[index-1].trigger();
		else
			print.print("Invalid port to trigger. ");
	}

	/**
	 * Triggers a certain channel.
	 *
	 * @param id the id
	 */
	public void trigger(int id) {
		// 4/30/17: added a check if channel activated -KP
		if(channels[id-1].isOn()){
			event.trigger(id);
		}else{ // TODO check if PARGRP check implemented correctly -KP
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
		}		
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return event.getType();
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
		int id = Integer.valueOf(args[1]);
		
		if (id < channels.length){
			if(on){
				channels[id-1].setOn(args[0]);
			} else{
				channels[id-1].setOff(args[0]);
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

	public ArrayList<Racer> getRacers(){
		if(runExist())
			return runs.getLast().getRacers();
		return new ArrayList<Racer>();
	}

	public class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			String response = "";
//			System.out.println("\nBeginning of response\n");

			//define a HTML String Builder
			StringBuilder htmlStringBuilder=new StringBuilder();

			//set up content
			htmlStringBuilder.append("<html><head><title>Race Results</title>");
			htmlStringBuilder.append("<meta http-equiv=\"refresh\" content=\"1\">");
			htmlStringBuilder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head>");
			htmlStringBuilder.append("<body>");
			htmlStringBuilder.append("<h1>Race Results</h1>");
			htmlStringBuilder.append("<table>");
			//append row: Attributes
			htmlStringBuilder.append("<tr class=\"header\"><th><b>Place</b></th>"
					+ "<th><b>Number</b></th>"
					+ "<th><b>Name</b></th>"
					+ "<th><b>Time</b></th>");
			//append with content from directory
			racerToHtml(htmlStringBuilder);

			//close html file
			htmlStringBuilder.append("</table></body></html>");
			response+=htmlStringBuilder.toString();

//			System.out.println(response);
//			System.out.println("\nEnd of response\n");

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

		private void racerToHtml(StringBuilder htmlStringBuilder) {
			ArrayList<Racer> racers = getRacers();
			// First, sort the racers in a new list based on place.
			// Separate into two lists. One for those that have finished and one for DNF and unfinished
			ArrayList<Racer> finishedRacers = new ArrayList<Racer>();
			ArrayList<Racer> DNFandIncompleteRacers = new ArrayList<Racer>();
			for(Racer r: racers){
				if (r.getFinish() > 0){
					int i;
					for(i = 0; i<finishedRacers.size(); i++){
						if(r.getTimeAsLong() < finishedRacers.get(i).getTimeAsLong()){
							break;
						}

					}
					finishedRacers.add(i, r);
				} else {
					if (r.DNF()) { // Put the racers that have not finished before the racers that DNF!
						DNFandIncompleteRacers.add(r);
					} else{
						DNFandIncompleteRacers.add(0, r);
					}
				}
			}
			finishedRacers.addAll(DNFandIncompleteRacers);

			racers = finishedRacers;

			// Finally, display the results
			if(racers.size() > 0) {
				String res = "";

				for(int i=0;i<racers.size();i++){
					Racer r= racers.get(i);
					if(i%2==0)
						res += "<tr class=\"light\">";
					else
						res += "<tr class=\"dark\">"; //Place, Number, Name, Time
					res    += "<td>" + (i+1) + "</td>"
							+ "<td>" + r.getId() + "</td>"
							+ "<td>" + (NumNameMap.containsKey(r.getId())?NumNameMap.get(r.getId()):"") + "</td>"
							+ "<td>" + (r.getDNF()?"DNF":r.getTime()=="-2"?"":r.getTime()) + "</td>";
					res += "</tr>";
				}

				htmlStringBuilder.append(res);
			}
		}
	}

	public class cssHandler implements HttpHandler{
		@Override
		public void handle(HttpExchange t) throws IOException {
			String response=".tg {border-collapse:collapse;border-spacing:0;}"
					+"table, th, td {border: 1px solid black;font-family:Arial, sans-serif;font-size:14px;}"
					+".header{background-color:#333333;color:white;}"
					+".dark{background-color: #E0E0E0;color:black;}"
					+".light{background-color: white;color:black;}";

			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	public boolean isChannelActive(int i) {
		return channels[i-1].isOn();
	}

}
