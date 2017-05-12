package chronotimer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class Run.
 *
 * @author Red Beard & Crew
 */
public class Run {
	private int id;
	private ArrayList<Racer> racers;
	private boolean active; //Whether a run has been ended
	
	/**
	 * Instantiates a new run.
	 *
	 * @param _number the number
	 */
	public Run(int _number){
		setId(_number);
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
		else
			System.out.println("ERROR: Could not add Racer"+racer.getId());
	}
	 
	/**
	 * End.
	 *
	 * @return true, if successful
	 */
	public boolean end(){
		if(!active){
			return false;
		}
			
		active = false;
		for(Racer r:racers){
			if(r.getStart()==0){
				r.didNotFinish();
			} else if(r.getFinish()==0 && !r.DNF()){
				r.didNotFinish();
			}
		}
		return true;
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
	 * Gets the first racer.
	 *
	 * @return the racer
	 */
	public Racer getRacer(){
		if(racers.size()>0)
			return racers.get(0);
		else
			return null;
	}
	
	/**
	 * Gets the racer.
	 *
	 * @param index the index
	 * @return the racer
	 */
	public Racer getRacer(int index){
		if(racers.size()>0)
			return racers.get(index);
		else
			return null;
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

	/**
	 * @return the number
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param _id the number to set
	 */
	public void setId(int _id) {
		id = _id;
	}
	
	public String toString(){
		sort();
		String str="";
		int i=1;
		for(Racer r:racers){
			System.out.println("print");
			str+=(i+"\t|| ");
			str+=r.toString();
			str+="\n";
			i++;
		}
		return str;
	}

	public void sort() {
		ArrayList<Racer> finishedRacers = new ArrayList<>();
        ArrayList<Racer> DNFandIncompleteRacers = new ArrayList<>();
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
        racers=finishedRacers;
	}

}
