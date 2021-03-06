package chronotimer;

import java.util.Queue;

//import java.util.*;

/**
 * The Class Event.
 *
 * @author Red Beard & Crew
 */
public abstract class Event {
	protected Queue<Racer> racing;
	protected Run currentRun;
	protected String channelMode[];
	protected Timer timer;
	protected Printer printer;
	
	/**
	 * Adds the racer.
	 *
	 * @param r the racer
	 */
	abstract public void addRacer(int r);
	
	/**
	 * Gets the type.
	 *
	 * @return String type of event
	 */
	abstract public String getType();
	
	/**
	 * Triggers a channel.
	 *
	 * @param id channel ID
	 */
	abstract public void trigger(int id);
	
	/**
	 * For CANCEL: Start isn't valid, competitor still in queue to start.
	 */
	abstract public void cancel();
	
	/**
	 * Removes the racer.
	 *
	 * @param index the position of the racer in the queue
	 */
	abstract public void removeRacer(int index);
	
	/**
	 * For DNF: The next competitor to finish did not finish.
	 */
	abstract public void didNotFinish();
	
	/**
	 * Sets the run.
	 *
	 * @param _run the new run
	 */
	abstract public void setRun(Run _run);
	
	/**
	 * Sets the timer when you assign a new timer for an Event.
	 *
	 * @param timer the new timer
	 */
	abstract public void setTimer(Timer timer);

	/**
	 * Swap.
	 */
	abstract public void swap();

	/**
	 * Gets the run to save to record.
	 *
	 * @return the run
	 */
	abstract public Run getRun();
}
