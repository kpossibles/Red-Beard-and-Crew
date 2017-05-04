package chronotimer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class GroupTimed.
 *
 * @author Red Beard & Crew
 */
public class GroupTimed extends Event {
	private Queue<Racer> unnamed;
	private Run currentRun;
	private String channelMode[];
	private Timer timer;
	private Printer print;
	private long startTime;
	private int raceCount;

	/**
	 * Instantiates a new group timed.
	 *
	 * @param _timer the timer
	 * @param _print the printer
	 */
	public GroupTimed(Timer _timer, Printer _print){
		timer = _timer;
		print = _print;
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
				print.print("Channel is inactive.");
			}
		}
		else {
			print.print("Channel is invalid.");
		}
	}

	/**
	 * Starts the event
	 */
	public void start(){
		if (currentRun != null && currentRun.isActive()) {
			if (startTime == 0) {
				startTime = timer.getTime();
				print.print(String.format("Current run started at %s. ", timer.getTimeString()));
			} else {
				print.print("Current run already started. ");
			}
		}
		else
			print.print("The current run is already complete or no run has been started. ");
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
				unnamed.add(finishedRacer);
				print.print(String.format("Racer %05d has finished at %s.", raceCount, timer.getTimeString()));
				print.print(String.format("Racer %05d's time was %s. ", raceCount, finishedRacer.getTime()));
			} else {
				print.print("Current run has not been started. ");
			}
		}
		else {
			print.print("The current run is already complete or no run has been started. ");
		}
	}

	@Override
	public void discard() {
		if(currentRun != null && currentRun.isActive()) {
			if (!currentRun.isEmpty())
				currentRun.removeLast();
			else
				print.print("No racer has finished yet!");
		}
		else {
			print.print("The current run is already complete or no run has been started. ");
		}
	}

	public void removeRacer(int index){
		print.print("CLEAR is not a valid command for this type of race");
	}

	@Override
	public void dnf() {
		if (startTime != 0){
			raceCount++;
			Racer finishedRacer = new Racer();
			finishedRacer.setStart(startTime);
			finishedRacer.didNotFinish();
			currentRun.add(finishedRacer);
			unnamed.add(finishedRacer);
			print.print(String.format("Racer %1$05d did not finish.", raceCount));
		} else {
			print.print("The current run is already complete or no run has been started. ");
		}
	}

	@Override
	public void setRun(Run _run) {
		startTime = 0;
		raceCount = 0;
		unnamed = new LinkedList<>();
		currentRun = _run;
		print.print(String.format("Run %1$02d started. ", _run.getNumber()));
	}

	@Override
	public void addRacer(int r) {
		if (unnamed.size() > 0){
			Racer toUpdate = unnamed.poll();
			int oldNumber = toUpdate.getId();
			toUpdate.setId(r);
			print.print(String.format("Racer %1$05d was renamed to %2$05d.", oldNumber, r));
		}
		else{
			print.print("No racer has finished that is unnamed.");
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
}
