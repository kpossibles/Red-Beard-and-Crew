import java.util.*;

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
		if (type.equals("IND")) {
			event = new IndividualTimed(timer, print);
			print.print("Event set to Individual");
		} else if (type.equals("PARIND")) {
			event = new ParallelTimed(timer, print);
			print.print("Event set to Parallel");
		} else if (type.equals("GRP")) {
			event = new GroupTimed(timer, print);
			print.print("Event set to Group");
		}
//		else if(type.equals("PARGRP")){
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

	public void swap() {
		// TODO check if working correctly
		if(event.getType()=="IND"){
			event.swap();
		}
		
	}

}
