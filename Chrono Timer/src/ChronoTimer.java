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
		event = new Event();
		channels = new Channel[2];
		channels[0] = new Channel(timer);
		channels[1] = new Channel(timer);
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
		// TODO Parse string to integer, then toggle the related channel
		int index = Integer.valueOf(number);
		channels[index].toggle();		
	}

	public void clear(String number) {
		// TODO parse string to integer, then pass to event
		int index = Integer.valueOf(number);
		event.removeRacer(index);
	}

	public void didNotFinish() {
		// TODO pass on to event
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
		// TODO Auto-generated method stub
		run = new Run();
	}

}
