import java.time.LocalTime;

public class Racer {
	private long start, finish;
	private int id; 
	private boolean didNotFinish;
	
	public Racer(){
		didNotFinish = false;
		start = finish = 0;
		id = 0;
	}
	public Racer(int _id){
		start = finish = 0;
		id = _id;
	}
	
	public void setStart(long time) {
		start = time;
	}
	
	public void setFinish(long time) {
		finish = time;
	}
	
	public long getTime(){
		if(didNotFinish)
			return -1;
		return finish - start;
	}

	public long getStart() {
		return start;
	}
	public long getFinish() {
		return finish;
	}
	public void reset() {
		start = finish = 0;		
	}
	public void didNotFinish() {
		didNotFinish = true;
		finish = 0;
	}
}
