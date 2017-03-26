import java.util.LinkedList;
import java.util.Queue;

public class ParallelTimed extends Event {
	Queue<Racer> racing;
	Run currentRun;
	String channelMode[];
	Timer timer;

	public ParallelTimed(Timer _timer){
		racing = new LinkedList<Racer>();
		timer = _timer;
		channelMode = new String[8];
		channelMode[0] = "START1";
		channelMode[1] = "FINISH1";
		channelMode[2] = "START2";
		channelMode[3] = "FINISH2";
	}

	@Override
	public void addRacer() {
		if(currentRun.isActive()){
			Racer racer = new Racer(0);
			currentRun.add(racer);
			racing.add(racer);
		}
	}

	@Override
	public void addRacer(int r) {
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

	public void start(int lane){
		if (currentRun.isActive()) {
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
				System.out.println(String.format("Racer %d\t%s", started.getId(), timer.getTimeString()));
			else
				System.out.println("No racer queued to start.");
		}
		else
			System.out.println("Current Run is not active. ");
	}

	public void finish(int lane) {
		Racer racer = null;
		for (Racer r: racing){
			if (r.getLane() == lane){
				racer = r;
				break;
			}
		}
		if(racer != null){
			racer.setFinish(timer.getTime());
			System.out.println(String.format("Racer %d\t%s", racer.getId(), timer.getTimeString()));
			System.out.println(String.format("Racer %d\t%s", racer.getId(), racer.getTime()));
			racing.remove(racer);
		}
		else
			System.out.println("No racer queued to finish.");
	}

	@Override
	public void trigger(int id) {
		// Figures out what the channel type is, and then does the relevant function.
		if(id<=channelMode.length){
			if (channelMode[id-1].startsWith("START"))
				start(Integer.getInteger(channelMode[id-1].substring(6)));
			else if (channelMode[id-1].startsWith("FINISH"))
				finish(Integer.getInteger(channelMode[id-1].substring(7)));
		}
		else{
			System.out.println(String.format("Sorry, Channel %d is not active", id));
			if(racing.size() == 0)
				System.out.println("No racer queued to start.");
		}
	}

	@Override
	public void discard() {
		racing.peek().reset();
		System.out.println("Start was not valid. Racer will retry.");
	}

	@Override
	public void removeRacer(int index) {
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

	@Override
	public void dnf() {
		Racer racer = racing.poll();
		if (racer != null && racer.getStart() != 0){
			racer.didNotFinish();;
			System.out.println(String.format("Racer %d marked as Did Not Finish. ", racer.getId()));
		}
		else
			System.out.println("No racer queued to finish.");
	}

	@Override
	public void setRun(Run _run) {
		currentRun = _run;
	}

	@Override
	public void setTimer(Timer _timer) {
		timer = _timer;
	}
}
