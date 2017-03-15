import java.util.LinkedList;
import java.util.Queue;

public class ParallelTimed extends Event {
	Queue<Racer> racing;
	Run currentRun;
	String channelMode[];
	Timer timer;

	public ParallelTimed(Timer _timer){
		timer = _timer;
		channelMode = new String[8];
		channelMode[0] = "START1";
		channelMode[1] = "FINISH1";
		channelMode[2] = "START2";
		channelMode[3] = "START3";
	}

	@Override
	public void addRacer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRacer(int r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trigger(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRacer(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dnf() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRun(Run _run) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimer(Timer timer) {
		// TODO Auto-generated method stub
		
	}
}
