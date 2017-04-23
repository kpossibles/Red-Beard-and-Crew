//import java.util.*;

public abstract class Event{

	
	/**
	 * Adds the racer.
	 *
	 * @param r the racer
	 */
	abstract public void addRacer(int r);
	
	/**
	 * Triggers a channel.
	 *
	 * @param id channel ID
	 */
	abstract public void trigger(int id);
	
	/**
	 * For CANCEL: Start isn't valid, competitor still in queue to start.
	 */
	abstract public void discard();
	
	/**
	 * Removes the racer.
	 *
	 * @param index the position of the racer in the queue
	 */
	abstract public void removeRacer(int index);
	
	/**
	 * For DNF: The next competitor to finish did not finish.
	 */
	abstract public void dnf();
	
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
}
