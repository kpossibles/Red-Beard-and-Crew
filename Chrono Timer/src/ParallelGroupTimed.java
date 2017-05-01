import java.util.LinkedList;
import java.util.Queue;

/**
 * The Class ParallelGroupTimed.
 *
 * @author Red Beard & Crew
 */
public class ParallelGroupTimed extends Event {
	private Queue<Racer> racing;
	private Run currentRun;
	private String channelMode[];
	private Timer timer;
	private Printer print;
	
	public ParallelGroupTimed(Timer _timer, Printer _print){
		racing = new LinkedList<>();
		timer = _timer;
		print = _print;
		channelMode = new String[8];
		for(int i=0;i<8;i++){
			channelMode[i]="START"+i;
		}
	}
	@Override
	public void addRacer(int r) {
		// TODO check if working correctly -KP
		if(currentRun!=null && currentRun.isActive()){
			if(currentRun.size()<=8){
				int tempLane=currentRun.size();
				Racer racer = new Racer(r);
				racer.setLane(tempLane);
				currentRun.add(racer);
				racing.add(racer);
				print.print(String.format("Racer %d added in lane %d.", r, tempLane+1));
			}
			else
				print.print("Only can add maximum 8 racers.");
		}
		else {
			print.print("Could not add racer. Either the current run is not active, or there is currently no run.");
		}
	}

	@Override
	public void discard() {
		// TODO check if working correctly -KP
		racing.peek().reset();
		print.print("Start was not valid. Racer will retry.");
	}

	@Override
	public void dnf() {
		// TODO check if working correctly -KP
		Racer racer = racing.poll();
		if (racer != null && racer.getStart() != 0){
			racer.didNotFinish();;
			print.print(String.format("Racer %d marked as Did Not Finish.", racer.getId()));
		}
		else
			print.print("No racer queued to finish.");
	}

	@Override
	public String getType() {
		return "PARGRP";
	}

	@Override
	public void removeRacer(int index) {
		// TODO check if working correctly -KP
		if(currentRun.isActive()){
			for(Racer r : racing){
				if(r.getId() == index && racing.size()>0){
					print.print(String.format("Racer %d removed.", r.getId()));
					racing.remove(index);
					currentRun.remove(index);
				}
			}
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
		// does nothing in this event type		
	}

	@Override
	public void trigger(int id) {
		// TODO check if implemented correctly -KP
		if(id<=channelMode.length){
			if (channelMode[id-1].startsWith("START")){
				for(int i=0;i<8;i++){
					start();
					channelMode[i] = "FINISH"+i;
				}
			}
			else if (channelMode[id-1].startsWith("FINISH"))
				finish(id); 
		}
		else{
			System.out.println(String.format("Sorry, Channel %d is not active", id));
			if(racing.size() == 0)
				print.print("No racer queued to start.");
		}
	}
	private void finish(int lane) {
		// TODO check if working correctly -KP
		Racer racer = null;
		for (Racer r: racing){
			if (r.getLane() == lane){
				racer = r;
				break;
			}
		}
		if(racer != null){
			racer.setFinish(timer.getTime());
			print.print(String.format("Racer %d\t%s", racer.getId(), racer.getFinishTime()));
			print.print(String.format("Racer %d\t%s", racer.getId(), racer.getTime()));
			racing.remove(racer);
		}
		else
			print.print(String.format("No racer queued to finish in lane %d.", lane));
	}
	private void start() {
		// TODO check if working correctly -KP
		// Upon a start (trigger on channel 1), the times for all racers begin.
		if (currentRun != null && currentRun.isActive()) {
			Racer started = null;
			for (Racer r : racing) {
				if (r.getStart() == 0) {
					r.setStart(timer.getTime());
					started = r;
					if (started != null)
						print.print(String.format("Racer %d\t%s", started.getId(), started.getStartTime()));
				}
			}
			if (started == null)
				print.print("No racer queued to start.");
		}
		else
			print.print("Current Run is not active.");
	}
}
