import java.util.LinkedList;
import java.util.Queue;

public class IndividualTimed extends Event {
//racers queue for single runs of a race.  Each racer has a start event and end event.  Start is on one channel and end is on another channel.
	
	//a start event is associated with next racer in queue.
	
	//end event is fifo for racers association.  
	// for each event handle the response in the Chronotimer.  Should be able to detect a start, a finish (with times) and calculate the total time for a race, DNF is possible.
		/** This is a list of the racers who are actively racing. */
		Queue<Racer> racing;
		/** The run stores all racers who have ever raced and who are planned to race. */
		Run currentRun; 
		String channelMode[];
		Timer timer;
		
		public IndividualTimed(Timer _timer){
			racing = new LinkedList<Racer>();
			channelMode = new String[8];
			channelMode[0] = "START";
			channelMode[1] = "FINISH";
			timer = _timer;
		}

		public void addRacer(){
			if(currentRun.isActive()){
				Racer racer = new Racer(0);
				currentRun.add(racer);
				racing.add(racer);
			}
		}
		public void addRacer(int r){
			if(currentRun.isActive()){
				Racer racer = new Racer(r);
				currentRun.add(racer);
				racing.add(racer);
			}
		}
		
		public void removeRacer(int index){
			//TODO: Implement!
			if(currentRun.isActive()){
				for(Racer r : racing){
					if(r.getId() == index){
						racing.remove(index); 
						currentRun.remove(index);
					}
				}
			}
		}
		
		public void setRun(Run _run){
			currentRun = _run;
			racing = new LinkedList<Racer>();
		}
		
		public void start(){
			for (Racer r: racing){
				if(r.getStart() == 0)
					r.setStart(timer.getTime());
					break;
			}
		}
		
		public void finish(){
			Racer racer = racing.remove();
			racer.setFinish(timer.getTime());
			System.out.println(racer.toString());
		}
		
		// for CANCEL
		public void discard(){
			racing.peek().reset();
		}
		
		// for DNF
		public void dnf(){
			Racer r = racing.remove();
			r.didNotFinish();
		}

		public void trigger(int id) {
			// Figures out what the channel type is, and then does the relevant function. 
			if (channelMode[id].equals("START"))
				start();
			else if (channelMode[id].equals("FINISH"))
				finish();
		}
}
