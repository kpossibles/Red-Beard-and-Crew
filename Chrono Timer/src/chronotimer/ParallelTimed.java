package chronotimer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class ParallelTimed.
 *
 * @author Red Beard & Crew
 */
public class ParallelTimed extends Event {

	/**
	 * Instantiates a new parallel timed with a new Timer and Printer
	 *
	 * @param _timer the timer
	 * @param _print the printer
	 */
	public ParallelTimed() {
		racing = new LinkedList<>();
		timer = new Timer();
		printer = new Printer();
		channelMode = new String[8];
		channelMode[0] = "START1";
		channelMode[1] = "FINISH1";
		channelMode[2] = "START2";
		channelMode[3] = "FINISH2";
	}

	/**
	 * Instantiates a new parallel timed.
	 *
	 * @param _timer the timer
	 * @param _print the print
	 */
	public ParallelTimed(Timer _timer, Printer _print){
		racing = new LinkedList<>();
		timer = _timer;
		printer = _print;
		channelMode = new String[8];
		channelMode[0] = "START1";
		channelMode[1] = "FINISH1";
		channelMode[2] = "START2";
		channelMode[3] = "FINISH2";
	}

	@Override
	public void addRacer(int r) {
		if(currentRun!=null && currentRun.isActive()){
			Racer racer = new Racer(r);
			currentRun.add(racer);
			racing.add(racer);
			printer.feedback(String.format("Racer %d added.", r));
		}
		else {
			printer.feedback("Could not add racer. Either the current run is not active, or there is currently no run. ");
		}
	}

	@Override
	public void cancel() {
		racing.peek().reset();
		printer.feedback("Start was not valid. Racer will retry.");
	}

	@Override
	public void didNotFinish() {
		Racer racer = racing.poll();
		if (racer != null && racer.getStart() != 0){
			racer.didNotFinish();
			if(ChronoTimer.consoleMode)
				printer.feedback(String.format("Racer %d marked as Did Not Finish.", racer.getId()));
		}
		else
			printer.feedback("No racer queued to finish.");
	}

	@Override
	public String getType() {
		return "PARIND";
	}

	@Override
	public void removeRacer(int racerId) {
		int size = currentRun.size();
		if(currentRun.isActive()){
			for(Racer r : racing){
				if(r.getId() == racerId && racing.size()>0){
					printer.feedback(String.format("Racer %d removed.", r.getId()));
					racing.remove(racerId);
					currentRun.remove(racerId);
				}
			}
		}
		if(size==currentRun.size()){//couldn't find racer id
			if(currentRun.size()==0)
				printer.feedback("WARNING: Queue is empty.");
			else
				printer.feedback("WARNING: "+racerId+" is not in the queue.");
		}
	}

	@Override
	public void setRun(Run _run) {
		currentRun = _run;
	}

	@Override
	public void setTimer(Timer _timer) {
		timer = _timer;
	}

	@Override
	public void swap() {
		printer.feedback("WARNING: Cannot swap for this type of event.");
	}

	@Override
	public void trigger(int id) {
		// Figures out what the channel type is, and then does the relevant function.
		if(id<=channelMode.length){
			if (channelMode[id-1].startsWith("START") && !currentRun.isEmpty() )
				start(Integer.parseInt(channelMode[id-1].substring(5,6))); // trig 3 => START2 start(2)
			else if (channelMode[id-1].startsWith("FINISH") && racing.size()>0)
				finish(Integer.parseInt(channelMode[id-1].substring(6,7))); // trig 4 => FINISH2 finish(2)
		}
		else{
			printer.feedback(String.format("Sorry, Channel %d is not active", id));
			if(racing.size() == 0)
				printer.feedback("No racer queued to start.");
		}
	}

	@Override
	public Run getRun() {
		return currentRun;
	}

	/**
	 * Start.
	 *
	 * @param lane the lane
	 */
	private void start(int lane){
		if (currentRun != null && currentRun.isActive()) {
			Racer started = null;
			for (Racer r : racing) {
				if (r.getStart() == 0) {
					r.setLane(lane);
					r.setStart(timer.getTime());
					started = r;
					break;
				}
			}
			if (started != null)
				printer.feedback(String.format("Racer %d\t%s", started.getId(), timer.getTimeString()));
			else
				printer.feedback("No racer queued to start.");
		}
		else
			printer.feedback("Current Run is not active.");
	}

	/**
		 * Finish.
		 *
		 * @param lane the lane
		 */
		private void finish(int lane) {
			Racer racer = null;
			for (Racer r: racing){
				if (r.getLane() == lane){
					racer = r;
					break;
				}
			}
			if(racer != null && racing.size()>0){
				racer.setFinish(timer.getTime());
	//			print.print(String.format("Racer %d\t%s", racer.getId(), timer.getTimeString()));
				printer.feedback(String.format("Racer %d\t%s", racer.getId(), racer.getTime()));
				racing.remove(racer);
			}
			else
				printer.feedback("No racer queued to finish.");
		}

	/**
	 * Gets the run size.
	 *
	 * @return the run size
	 */
	public int getRunSize(){
		return currentRun.size();
	}

	/**
	 * Gets the racing size.
	 *
	 * @return the racing size
	 */
	public int getRacingSize(){
		return racing.size();
	}
	
	/**
	 * Prints the record so far.
	 */
	public void print() {
		if(currentRun!=null)
			for(Racer r:currentRun.getRacers()){
				System.out.println(r.toString());
			}
	}
}
