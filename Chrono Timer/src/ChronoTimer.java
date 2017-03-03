import java.util.*;

public class ChronoTimer {
	
	private Event event;
	private Channel[] channels;
	private Timer timer;
	private LinkedList<Run> runs;

	/**
	 * Instantiates a new ChronoTimer.
	 */
	public ChronoTimer(){
		reset();
	}

	/**
	 * This resets the ChronoTimer to initial state.
	 */
	public void reset() {
		timer = new Timer();
		event = new IndividualTimed(timer);
		channels = new Channel[8];
		for(int i = 0; i < 8; i++){
			channels[i] = new Channel(this, i);
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
		if(type.equals("IND"))
			event = new IndividualTimed(timer);
//		else if(type.equals("PARIND"))
//			event = new ParallelTimed(timer);
//		else if(type.equals("GRP"))
//			event = new GroupTimed(timer);
//		else if(type.equals("PARGRP"))
//			event = new ParallelGroupTimed(timer);
	}

	/**
	 * Toggle.
	 *
	 * @param number the number
	 */
	public void toggle(String number) {
		int index = Integer.valueOf(number);
		channels[index].toggle();
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
		trigger(index);
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
	public void endRun() {
		runs.getLast().end();
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
		if(runs.getLast().isActive())
			return true;
		return false;
	}

}
