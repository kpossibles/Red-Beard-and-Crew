package chronotimer;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.*;
/**
 * The Class Racer.
 *
 * @author Red Beard & Crew
 */
public class Racer {
	private long start, finish;
	private int id; 
	private int lane;
	private boolean didNotFinish;

	
	/**
	 * Instantiates a new racer.
	 */
	public Racer(){
		didNotFinish = false;
		start = finish = 0;
		id = 0;
		lane = -1;
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
	 * Sets the lane.
	 *
	 * @param _lane the lane number
	 */
	public void setLane(int _lane) {
		lane=_lane;
	}

	/**
	 * Sets the id.
	 *
	 * @param _id the new id
	 */
	public void setId(int _id){
		id = _id;
	}
	/**
	 * Gets the time by converting millisecond time to HH:mm:ss.S format.
	 *
	 * @return the time
	 */

	public String getTime(){
		if(didNotFinish)
			return "-1";
		if(finish == 0)
			return "-2";
		return convertToTime(finish-start);
	}

	public long getTimeAsLong(){
		if(didNotFinish)
			return -1;
		if(finish == 0)
			return -2;
		return finish-start;
	}
	
	private String convertToTime(long time){
		long sec = TimeUnit.MILLISECONDS.toSeconds(time);
		long min = TimeUnit.SECONDS.toMinutes(sec);
		long hours = TimeUnit.MINUTES.toHours(min);
		long remainMinute = min - TimeUnit.HOURS.toMinutes(hours);
		long remainSec = sec - TimeUnit.MINUTES.toSeconds(remainMinute);
		long remainMilli = time - TimeUnit.SECONDS.toMillis(remainSec);
		
		if(remainSec>9)remainSec = Integer.parseInt(Integer.toString((int) remainSec).substring(0, 2));
		remainMilli = Integer.parseInt(Integer.toString((int) remainMilli).substring(0, 1));
		String result = String.format("%02d:%02d:%02d.%d", hours,remainMinute, remainSec, remainMilli);
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
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public String getStartTime(){
		Timer temp = new Timer(start);
		temp.getTimeString();
		String time = temp.getTimeString();
		return time;
	}
	
	/**
	 * Gets the finish time.
	 *
	 * @return the finish time
	 */
	public String getFinishTime(){
		if(didNotFinish)
			return "DNF";
		else{
			Timer temp = new Timer(finish);
			temp.getTimeString();
			String time = temp.getTimeString();
			return time;
		}
	}
	
	/**
	 * Gets the lane.
	 *
	 * @return the lane
	 */
	public long getLane() {
		return lane;
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
	 * * Gets whether the racer is marked as DNF
	 *
	 * @return True if DNF
	 */
	public boolean getDNF(){
		return didNotFinish;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		start = finish = 0;		
	}
	
	/**
	 * Marks racer as did not finish.
	 */
	public void didNotFinish() {
		didNotFinish = true;
		finish = 0;
	}
	
	/**
	 * Checks if racer did not finish.
	 *
	 * @return true, if successful
	 */
	public boolean DNF() {
		return didNotFinish;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Customized - gets the Racer.
	 *
	 * @return the Racer in formatted form
	 */
	public String toString(){
		return String.format("%d\t|| %s\t|| %s\t|| %s\t", id, getStartTime(), getFinishTime(), getTime()=="-2" ? "00:00:00.0" : getTime());
	}
}
