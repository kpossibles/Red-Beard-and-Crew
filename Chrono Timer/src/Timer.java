//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
import java.time.Instant; // EB - Solution for epoch (linux) time in millis
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timer {
	//private Date timer;
	//private SimpleDateFormat sdf = new SimpleDateFormat("sssss.SS");
	private long unixTimestamp;
	
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
		String tempDate = "Jul 7 1996 " + number + " UTC"; // Set to random day for math reasons, must be constant.
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss.SSS zzz");
        ZonedDateTime     zdt  = ZonedDateTime.parse(tempDate,dtf);        
        unixTimestamp = zdt.toInstant().toEpochMilli();
	}
	
	/**
	 * 
	 * @return current time
	 */
	public long getTime(){
		return unixTimestamp;
	}
}
