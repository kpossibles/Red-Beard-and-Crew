import java.util.*;

import javax.swing.JTextArea;

// TODO: Auto-generated Javadoc
/**
 * The Class ChronoTimer.
 */
public class ChronoTimer {
	
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
	public ChronoTimer(Printer _print){
		print = _print;
		reset();
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
		event.trigger(id);
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
	 * @param i the i
	 * @param on the on
	 */
	public void connect(String i, boolean on) {
		int id = Integer.valueOf(i);
		if (id < channels.length){
			if(on)
				channels[id-1].setOn();
			else
				channels[id-1].setOff();
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

}
