import java.util.ArrayList;

public class Run {
	int number;
	ArrayList<Racer> racers;
	boolean active; //Whether a run has been ended
	
	/**
	 * Instantiates a new run.
	 *
	 * @param _number the number
	 */
	public Run(int _number){
		number = _number;
		racers = new ArrayList<Racer>();
		active = true;
	}

	/**
	 * Adds the racer.
	 *
	 * @param racer the racer
	 */
	public void add(Racer racer) {
		if (active)
			racers.add(racer);
	}
	 
	/**
	 * Ends the run.
	 */
	public void end(){
		active = false;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Removes the racer in the queue.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean remove(int id) {
		if(active){
			for(Racer r : racers){
				if(r.getId() == id){
					return racers.remove(r);
				}
			}
		}
		return false;
	}
	
	public Racer getRacer(){
		return racers.get(0);
	}
}
