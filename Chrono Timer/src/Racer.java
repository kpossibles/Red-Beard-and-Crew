import java.util.concurrent.TimeUnit;

//import java.time.LocalTime;

public class Racer {
	private long start, finish;
	private int id; 
	private boolean didNotFinish;
	
	/**
	 * Instantiates a new racer.
	 */
	public Racer(){
		didNotFinish = false;
		start = finish = 0;
		id = 0;
	}
	
	/**
	 * Instantiates a new racer.
	 *
	 * @param _id the id
	 */
	public Racer(int _id){
		start = finish = 0;
		id = _id;
	}
	
	/**
	 * Sets the start.
	 *
	 * @param time the new start
	 */
	public void setStart(long time) {
		start = time;
	}
	
	/**
	 * Sets the finish.
	 *
	 * @param time the new finish
	 */
	public void setFinish(long time) {
		finish = time;
	}
	
	/**
	 * Gets the time by converting millisecond time to HH:mm:ss.S format.
	 *
	 * @return the time
	 */
	public String getTime(){
		if(didNotFinish)
			return "-1";
		long sec = TimeUnit.MILLISECONDS.toSeconds(finish - start);
		long min = TimeUnit.SECONDS.toMinutes(sec);
		long hours = TimeUnit.MINUTES.toHours(min);
		long remainMinute = min - TimeUnit.HOURS.toMinutes(min);
		long remainSec = sec - TimeUnit.MINUTES.toSeconds(remainMinute);
		long remainMilli = (finish - start) - TimeUnit.SECONDS.toMillis(remainSec);
		
		String result = String.format("%02d:%02d:%02d.%d", hours,remainMinute, remainSec, remainMilli/100);
		
		return result;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	
	/**
	 * Gets the finish.
	 *
	 * @return the finish
	 */
	public long getFinish() {
		return finish;
	}
	
	/**
	 * Reset.
	 */
	public void reset() {
		start = finish = 0;		
	}
	
	/**
	 * Did not finish.
	 */
	public void didNotFinish() {
		didNotFinish = true;
		finish = 0;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
}
