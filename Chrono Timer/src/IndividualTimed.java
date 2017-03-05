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
			channelMode = new String[2];
			channelMode[0] = "START";
			channelMode[1] = "FINISH";
			timer = _timer;
			currentRun = new Run(1);
		}

		public void addRacer(){
			if(currentRun.isActive()){
				Racer racer = new Racer(0);
				currentRun.add(racer);
				racing.add(racer);
			}
		}
		public void addRacer(int r){
			if(currentRun!=null && currentRun.isActive()){
				Racer racer = new Racer(r);
				currentRun.add(racer);
				racing.add(racer);
				System.out.println(String.format("Racer %d added.", r));
			}
			else {
				System.out.println("Could not add racer.  Either the current run is not active, or there is currently no run. ");
			}
		}
		
		public void removeRacer(int index){
			if(currentRun.isActive()){
				for(Racer r : racing){
					if(r.getId() == index && racing.size()>0){
						System.out.println(String.format("Racer %d removed.", r.getId()));
						racing.remove(index); 
						currentRun.remove(index);
					}
				}
			}
		}
		
		public void setTimer(Timer timer){
			timer = this.timer;
		}
		
		public void setRun(Run _run){
			currentRun = _run;
			racing = new LinkedList<Racer>();
		}
		
		public void start(){
			Racer started = null;
			for (Racer r: racing){
				if(r.getStart() == 0) {
					r.setStart(timer.getTime());
					started = r;
					break;
				}
			}
			if (started != null)
					System.out.println(String.format("Racer %d\t%s", started.getId(), timer.getTimeString()));
			else
				System.out.println("No racer queued to start.");
		}
		
		public void finish(){
			Racer racer = racing.poll();
			if (racer != null && racer.getStart() != 0){
				racer.setFinish(timer.getTime());
				System.out.println(String.format("Racer %d\t%s", racer.getId(), timer.getTimeString()));
				System.out.println(String.format("Racer %d\t%s", racer.getId(), racer.getTime()));
			}
			else
				System.out.println("No racer queued to finish.");
		}
		
		// for CANCEL
		public void discard(){
			racing.peek().reset();
			System.out.println("Start was not valid. Racer will retry.");
		}
		
		// for DNF
		public void dnf(){
			Racer racer = racing.poll();
			if (racer != null && racer.getStart() != 0){
				racer.didNotFinish();;
				System.out.println(String.format("Racer %d marked as Did Not Finish. ", racer.getId()));
			}
			else
				System.out.println("No racer queued to finish.");
		}

		public void trigger(int id) {
			// Figures out what the channel type is, and then does the relevant function. 
			if(id<=channelMode.length){
				if (channelMode[id-1].equals("START"))
					start();
				else if (channelMode[id-1].equals("FINISH"))
					finish();
			}
			else{
				System.out.println(String.format("Sorry, Channel %d is not active", id));
				if(racing.size() == 0)
					System.out.println("No racer queued to start.");
			}
		}
}
