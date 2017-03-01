import java.time.LocalTime;

public class Racer {
	private long start, finish;
	private int id; 
	
	public Racer(){
		start = finish = 0;
		id = 0;
	}
	public Racer(int _id){
		start = finish = 0;
		id = _id;
	}
	
	public void setStart(long time) {
		// TODO Auto-generated method stub
		start = time;
	}
	
	public void setFinish(long time) {
		// TODO Auto-generated method stub
		finish = time;
	}
	
	public long getTime(){
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
}
