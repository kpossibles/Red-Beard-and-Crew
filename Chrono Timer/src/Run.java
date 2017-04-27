import java.util.ArrayList;

/**
 * The Class Run.
 *
 * @author Red Beard & Crew
 */
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
	 *
	 * @return the int
	 */
	public int end(){
		active = false;
		return number;
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
	
	/**
	 * Removes the last racer in queue.
	 */
	public void removeLast(){
		if(active){
			racers.remove(racers.size()-1);
		}
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return racers.size() == 0;
	}
	
	/**
	 * Gets the racer.
	 *
	 * @return the racer
	 */
	public Racer getRacer(){
		return racers.get(0);
	}
	
	/**
	 * Gets the last racer.
	 *
	 * @return the last
	 */
	public Racer getLast(){
		Racer best=new Racer();
		for(Racer r: racers){
			if(r.getFinish()>0 && r.getFinish()>best.getFinish())
				best=r;
		}
		return best.getFinish()>0 ? best : null;
	}
	
	/**
	 * Gets a copy of the racer queue.
	 *
	 * @return the racers
	 */
	public ArrayList<Racer> getRacers(){
		return racers;
	}
	
	/**
	 * Size of the racers queue.
	 *
	 * @return the int
	 */
	public int size(){
		return racers.size();
	}
}
