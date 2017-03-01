import java.util.*;

public class ChronoTimer {
	private Event event;
	private Channel[] channels;
	private Timer timer;
	private Run run;
	private LinkedList<Object> record;
	boolean on;

	public ChronoTimer(){
		reset();
	}
	public void power() {
		// TODO
	}

	public void reset() {
		// This resets the ChronoTimer to initial state
		event = new IndividualTimed();
		channels = new Channel[8];
		for(int i = 0; i < 8; i++){
			channels[i] = new Channel(this, i);
		}
		run = null;
		record = new LinkedList<>();
	}

	public void setTime(String number) {
		timer.setTime(number);
	}
	public void setEvent(String type) {
		event = new Event(type);
	}

	public void toggle(String number) {
		int index = Integer.valueOf(number);
		channels[index].toggle();
	}

	public void clear(String number) {
		int index = Integer.valueOf(number);
		event.removeRacer(index);
	}

	public void didNotFinish() {
		event.dnf();
	}
	
	public void trigger(String id){
		int index = Integer.valueOf(id);
		trigger(index);
	}

	public void trigger(int id) {
		event.trigger(id);
	}
	public void discard() {
		event.discard();
	}
	public boolean runExist() {
		return run != null;
	}
	public void endRun() {
		run = null;
	}
	public void createRun() {
		run = new Run();
	}

}
