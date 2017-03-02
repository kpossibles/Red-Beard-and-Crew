import java.util.*;

public abstract class Event{
// for each event handle the response in the Chronotimer.  Should be able to detect a start, a finish (with times) and calculate the total time for a race, DNF is possible.
	String type;
	Queue<Racer> q = new LinkedList<>();
	Racer cur;
	int qlength;
	
	public Event(){
		type = "IND";
		cur = null;
		qlength = -1;
	}
	
	public Event(String str) {
		type = str;
		cur = null;
		qlength = -1;
	}

	public void addRacer(){
		q.add(new Racer());
	}
	public void addRacer(int r){
		q.add(new Racer(r));
	}
	
	public void removeRacer(int index){
		q.remove(index);
	}
	
	public int start(String time){
		if(qlength == -1){
			qlength = q.size();
		}
		q.peek().setStart(time);
		
		return cur.getStart();
	}
	
	public int end(String time){
		q.peek().setFinish(time);
		int totalTime = q.peek().getTime();
		q.remove();
		
		return totalTime;
	}
	
	// for CANCEL
	public void discard(){
		q.peek().reset();
	}
	
	// for DNF
	public void dnf(){
		q.peek().setFinish("0");
		q.remove();
	}

	public void trigger(int id) {
		// Figures out what the channel type is, and then does the relevant function. 
		
	}
}
