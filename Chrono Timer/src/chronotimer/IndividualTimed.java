package chronotimer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class IndividualTimed.
 *
 * @author Red Beard & Crew
 */
public class IndividualTimed extends Event {
	//racers queue for single runs of a race.  Each racer has a start event and end event.  
	//Start is on one channel and end is on another channel.
	//a start event is associated with next racer in queue.	
	//end event is fifo for racers association.  
	//for each event handle the response in the Chronotimer.  
	//Should be able to detect a start, a finish (with times) and calculate the total time for a race, DNF is possible.
		/** This is a list of the racers who are actively racing. */
		Queue<Racer> racing;
		/** The run stores all racers who have ever raced and who are planned to race. */
		Run currentRun; 
		String channelMode[];
		Timer timer;
		Printer printer;
		Racer started = null, racer;
		
		/**
		 * Instantiates a new individual timed event.
		 */
		public IndividualTimed(){
			racing = new LinkedList<Racer>();
			channelMode = new String[8];
			channelMode[0] = "START";
			channelMode[1] = "FINISH";
			for(int i=2;i<8;i++)
				channelMode[i] = "";
			timer = new Timer();
			printer = new Printer();
		}
		
		/**
		 * Instantiates a new individual timed.
		 *
		 * @param _timer the timer
		 * @param _print the printer
		 */
		public IndividualTimed(Timer _timer, Printer _print){
			racing = new LinkedList<Racer>();
			channelMode = new String[8];
			channelMode[0] = "START";
			channelMode[1] = "FINISH";
			for(int i=2;i<8;i++)
				channelMode[i] = "";
			timer = _timer;
			printer = _print;
		}
		
		public IndividualTimed(Timer _timer, Printer _print, Run run){
			currentRun = run;
			racing = new LinkedList<Racer>();
			channelMode = new String[8];
			channelMode[0] = "START";
			channelMode[1] = "FINISH";
			for(int i=2;i<8;i++)
				channelMode[i] = "";
			timer = _timer;
			printer = _print;
		}

		public void addRacer(int r){
			if(currentRun!=null && currentRun.isActive()){
				Racer racer = new Racer(r);
				currentRun.add(racer);
				racing.add(racer);
				printer.feedback(String.format("Racer %d added.", r));
			}
			else {
				printer.feedback("Could not add racer.  Either the current run is not active, or there is currently no run. ");
			}
		}
		
		public void removeRacer(int index){
			if(currentRun.isActive()){
				for(Racer r : racing){
					if(r.getId() == index && racing.size()>0){
						printer.feedback(String.format("Racer %d removed.", r.getId()));
						racing.remove(index); 
						currentRun.remove(index);
					}
				}
			}
		}
		
		public void setTimer(Timer _timer){
			timer = _timer;
		}
		
		public void setRun(Run _run){
			currentRun = _run;
			racing = new LinkedList<Racer>();
		}
		
		@Override
		public Run getRun() {
			return currentRun;
		}

		@Override
		public String getType() {
			return "IND";
		}

		// for CANCEL
		public void cancel(){
			racing.peek().reset();
			printer.feedback("Start was not valid. Racer will retry.");
		}
		
		// for DNF
		public void didNotFinish(){
			Racer racer = racing.poll();
			if (racer != null && racer.getStart() != 0){
				racer.didNotFinish();
				printer.feedback(String.format("Racer %d marked as Did Not Finish. ", racer.getId()));
			}
			else
				printer.feedback("No racer queued to finish.");
		}

		@Override
		public void swap(){
			if(racing.size()>=2){
				Object[] temp = racing.toArray();
				Racer racer1 = (Racer) temp[1];
				temp[1] = temp[0];
				temp[0] = racer1;
				racing = new LinkedList<Racer>();
				for(Object r: temp){
					racing.add((Racer)r);
				}
				printer.feedback("Swapped 2 racers at front of queue.");
				
			}
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
				printer.feedback(String.format("Sorry, Channel %d is not active", id));
				if(racing.size() == 0)
					printer.feedback("No racer queued to start.");
			}
		}

		/**
		 * Start.
		 */
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
				printer.feedback(String.format("Racer %d started at %s", started.getId(), started.getStartTime()));
			else
				printer.feedback("No racer queued to start.");
		}

		/**
		 * Finish.
		 */
		public void finish(){
			racer = racing.poll();
			if (racer != null && racer.getStart() != 0){
				racer.setFinish(timer.getTime());
				printer.feedback(String.format("Racer %d finished at %s", racer.getId(), timer.getTimeString()));
				printer.feedback(String.format("Racer %d's time was %s", racer.getId(), racer.getTime()));
			}
			else
				printer.feedback("No racer queued to finish.");
		}
}
