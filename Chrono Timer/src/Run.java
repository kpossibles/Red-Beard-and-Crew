import java.util.ArrayList;

public class Run {
	int number;
	ArrayList<Racer> racers;
	boolean active; //Whether a run has been ended
	public Run(int _number){
		number = _number;
		racers = new ArrayList<Racer>();
		active = true;
	}

	public void add(Racer racer) {
		if (active)
			racers.add(racer);
	}
	 
	public void end(){
		active = false;
	}

	public boolean isActive() {
		return active;
	}

	public boolean remove(int id) {
		// TODO Auto-generated method stub
		if(active){
			for(Racer r : racers){
				if(r.getId() == id){
					return racers.remove(r);
				}
			}
		}
		return false;
	}
}
