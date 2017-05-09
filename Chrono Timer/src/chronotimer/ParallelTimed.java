package chronotimer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class ParallelTimed.
 *
 * @author Red Beard & Crew
 */
public class ParallelTimed extends Event {
	private Queue<Racer> racing;
	private Run currentRun;
	private String channelMode[];
	private Timer timer;
	private Printer print;

	/**
	 * Instantiates a new parallel timed with a new Timer and Printer
	 *
	 * @param _timer the timer
	 * @param _print the printer
	 */
	public ParallelTimed() {
		racing = new LinkedList<>();
		timer = new Timer();
		print = new Printer();
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
		print = _print;
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
			print.print(String.format("Racer %d added.", r));
		}
		else {
			print.print("Could not add racer. Either the current run is not active, or there is currently no run. ");
		}
	}

	@Override
	public void discard() {
		racing.peek().reset();
		print.print("Start was not valid. Racer will retry.");
	}

	@Override
	public void dnf() {
		Racer racer = racing.poll();
		if (racer != null && racer.getStart() != 0){
			racer.didNotFinish();;
			print.print(String.format("Racer %d marked as Did Not Finish.", racer.getId()));
		}
		else
			print.print("No racer queued to finish.");
	}

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
			print.print(String.format("Racer %d\t%s", racer.getId(), racer.getTime()));
			racing.remove(racer);
		}
		else
			print.print("No racer queued to finish.");
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
					print.print(String.format("Racer %d removed.", r.getId()));
					racing.remove(racerId);
					currentRun.remove(racerId);
				}
			}
		}
		if(size==currentRun.size()){//couldn't find racer id
			if(currentRun.size()==0)
				print.print("WARNING: Queue is empty.");
			else
				print.print("WARNING: "+racerId+" is not in the queue.");
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
				print.print(String.format("Racer %d\t%s", started.getId(), timer.getTimeString()));
			else
				print.print("No racer queued to start.");
		}
		else
			print.print("Current Run is not active. ");
	}

	@Override
	public void swap() {
		print.print("WARNING: Cannot swap for this type of event.");
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
			System.out.println(String.format("Sorry, Channel %d is not active", id));
			if(racing.size() == 0)
				print.print("No racer queued to start.");
		}
	}
	
	public String getRecord(){
		return print.getRecord();
	}
}
