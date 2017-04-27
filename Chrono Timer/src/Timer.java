//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
import java.time.Instant; // EB - Solution for epoch (linux) time in millis
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class Timer.
 *
 * @author Red Beard & Crew
 */
public class Timer {
	//private Date timer;
	//private SimpleDateFormat sdf = new SimpleDateFormat("sssss.SS");
	private long unixTimestamp;
	private String time="";
	
	/**
	 * Timer() will start the time now and set it to unix time
	 */
	public Timer(){ 
		unixTimestamp = Instant.now().getEpochSecond();
	}
	
	/**
	 * Sets time using a string in the format "HH:MM:SS.X"
	 * @param number in format "HH:MM:SS.X"
	 * @return unixTimestamp time equivalent on arbitrary date  
	 */
	public void setTime(String number){
		time = number;
		String tempDate = "Jul 07 1996 " + number + " UTC"; // Set to arbitrary day (my birthday :]) for math reasons, must be constant.
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss.S zzz");
        ZonedDateTime     zdt  = ZonedDateTime.parse(tempDate,dtf);        
        unixTimestamp = zdt.toInstant().toEpochMilli();
	}
	
	/**
	 * Gets the time.
	 *
	 * @return current time
	 */
	public long getTime(){
		return unixTimestamp;
	}
	
	/**
	 * Gets the time string.
	 *
	 * @return current time as string object
	 */
	public String getTimeString(){
		return "" + time;
	}
}
