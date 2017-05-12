package chronotimer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class GroupTimed.
 *
 * @author Red Beard & Crew
 */
public class GroupTimed extends Event {
	
	private long startTime;
	private int raceCount;

	/**
	 * Instantiates a new group timed.
	 *
	 * @param _timer the timer
	 * @param _print the printer
	 */
	public GroupTimed(){
		timer = new Timer();
		printer = new Printer();
		channelMode = new String[8];
		channelMode[0] = "START";
		channelMode[1] = "FINISH";
	}
	
	/**
	 * Instantiates a new group timed.
	 *
	 * @param _timer the timer
	 * @param _print the printer
	 */
	public GroupTimed(Timer _timer, Printer _print){
		timer = _timer;
		printer = _print;
		channelMode = new String[8];
		channelMode[0] = "START";
		channelMode[1] = "FINISH";
	}

	public void trigger(int id) {
		// Figures out what the channel type is, and then does the relevant function.
		if(id<=channelMode.length){
			if (channelMode[id-1].startsWith("START"))
				start();
			else if (channelMode[id-1].startsWith("FINISH"))
				finish();
			else {
				printer.feedback("Channel is inactive.");
			}
		}
		else {
			printer.feedback("Channel is invalid.");
		}
	}

	/**
	 * Starts the event
	 */
	public void start(){
		if (currentRun != null && currentRun.isActive()) {
			if (startTime == 0) {
				startTime = timer.getTime();
				printer.feedback(String.format("Current run started at %s.", timer.getTimeString()));
			} else {
				printer.feedback("Current run already started.");
			}
		}
		else
			printer.feedback("The current run is already complete or no run has been started. ");
	}

	/**
	 * Finishes the event.
	 */
	private void finish() {
		if (currentRun != null && currentRun.isActive()) {
			if (startTime != 0) {
				raceCount++;
				Racer finishedRacer = new Racer(raceCount);
				finishedRacer.setStart(startTime);
				finishedRacer.setFinish(timer.getTime());
				currentRun.add(finishedRacer);
				racing.add(finishedRacer);
				printer.feedback(String.format("Racer %05d has finished at %s.", raceCount, timer.getTimeString()));
				printer.feedback(String.format("Racer %05d's time was %s. ", raceCount, finishedRacer.getTime()));
			} else {
				printer.feedback("Current run has not been started. ");
			}
		}
		else {
			printer.feedback("The current run is already complete or no run has been started. ");
		}
	}

	@Override
	public void cancel() {
		if(currentRun != null && currentRun.isActive()) {
			if (!currentRun.isEmpty())
				currentRun.removeLast();
			else
				printer.feedback("No racer has finished yet!");
		}
		else {
			printer.feedback("The current run is already complete or no run has been started. ");
		}
	}

	public void removeRacer(int index){
		printer.feedback("CLEAR is not a valid command for this type of race");
	}

	@Override
	public void didNotFinish() {
		if (startTime != 0){
			raceCount++;
			Racer finishedRacer = new Racer();
			finishedRacer.setStart(startTime);
			finishedRacer.didNotFinish();
			currentRun.add(finishedRacer);
			racing.add(finishedRacer);
			printer.feedback(String.format("Racer %1$05d did not finish.", raceCount));
		} else {
			printer.feedback("The current run is already complete or no run has been started.");
		}
	}

	@Override
	public void setRun(Run _run) {
		startTime = 0;
		raceCount = 0;
		racing = new LinkedList<>();
		currentRun = _run;
		printer.feedback(String.format("Run %1$02d started.", _run.getId()));
	}

	@Override
	public void addRacer(int r) {
		if (racing.size() > 0){
			Racer toUpdate = racing.poll();
			int oldNumber = toUpdate.getId();
			toUpdate.setId(r);
			printer.feedback(String.format("Racer %1$05d was renamed to %2$05d.", oldNumber, r));
		}
		else{
			printer.feedback("No racer has finished that is unnamed.");
		}
	}

	@Override
	public void setTimer(Timer _timer) {
		timer = _timer;
	}

	@Override
	public String getType() {
		return "GRP";
	}

	@Override
	public void swap() {
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

	@Override
	public Run getRun() {
		return currentRun;
	}

}
