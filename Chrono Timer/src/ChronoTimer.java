import java.util.*;

public class ChronoTimer {
	private Event event;
	private Channel[] channels;
	private Timer timer;
	private LinkedList<Run> runs;

	public ChronoTimer(){
		reset();
	}

	public void reset() {
		// This resets the ChronoTimer to initial state
		event = new IndividualTimed(timer);
		channels = new Channel[8];
		for(int i = 0; i < 8; i++){
			channels[i] = new Channel(this, i);
		}
		runs = new LinkedList<Run>();
		runs.add(new Run(1));
		event.setRun(runs.getFirst());
	}

	public void setTime(String number) {
		timer.setTime(number);
	}
	
	public void setEvent(String type) {
		//IND | PARIND | GRP | PARGRP
		if(type.equals("IND"))
			event = new IndividualTimed(timer);
//		else if(type.equals("PARIND"))
//			event = new ParallelTimed(timer);
//		else if(type.equals("GRP"))
//			event = new GroupTimed(timer);
//		else if(type.equals("PARGRP"))
//			event = new ParallelGroupTimed(timer);
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
	public void endRun() {
		runs.getLast().end();
	}
	public void createRun() {
		runs.add(new Run(runs.size() + 1));
		event.setRun(runs.getLast());
	}
	public boolean runExist() {
		if(runs.getLast().isActive())
			return true;
		return false;
	}

}
