package chronotimer;
//import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant; // EB - Solution for epoch (linux) time in millis
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;

/**
 * The Class Timed.
 *
 * @author Red Beard & Crew
 */
public class Timer implements Runnable {
	//private Date timer;
	//private SimpleDateFormat sdf = new SimpleDateFormat("sssss.SS");
	private long unixTimestamp;
	private String time="";
	private final ScheduledExecutorService scheduler =
		     Executors.newScheduledThreadPool(1);
	final Runnable addMillis = new Runnable() {
	       public void run() { 
	    	   unixTimestamp++;
	       }
	     };

	/**
	 * Timer() will start the time now and set it to unix time
	 */
	public Timer(){ 
		unixTimestamp = Instant.now().getEpochSecond();
	}
	
	public Timer(long time){ 
		unixTimestamp = time;
	}
	
	/**
	 * Sets time using a string in the format "HH:MM:SS.X"
	 * @param number in format "HH:MM:SS.X"
	 * @return unixTimestamp time equivalent on arbitrary date  
	 */
	public void setTime(String number){
		time = number;
		LocalDateTime ldf = LocalDateTime.now();
		String str = String.format("%s %02d %d %s CDT", ldf.getMonth().toString().substring(0, 1)+ldf.getMonth().toString().substring(1, 3).toLowerCase(), ldf.getDayOfMonth(), ldf.getYear(), number);
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss.S zzz");

//		String tempDate = "Jul 07 1996 " + number + " UTC"; // Set to arbitrary day (my birthday :]) for math reasons, must be constant.
        
//      ZonedDateTime     zdt  = ZonedDateTime.parse(tempDate,dtf);
        ZonedDateTime     zdt  = ZonedDateTime.parse(str,dtf);
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
		Date date = new Date(unixTimestamp);
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss.S");
		String time = localDateFormat.format(date);
		return time.substring(0,10);
//		return time;
	}
	
	public String convertToTime(long timeDifference){
		Date date = new Date(timeDifference);
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss.S");
		String time = localDateFormat.format(date);
		return time.substring(0,10);
	}

	@Override
	public void run() {
		scheduler.scheduleAtFixedRate(addMillis, 1, 1, TimeUnit.MILLISECONDS);
		Runnable updating = new Runnable(){
			public void run() { 
				String temp = getTimeString();
//				System.out.println(temp);		    	   
			}};
		scheduler.scheduleAtFixedRate(updating, 0, 100, TimeUnit.MILLISECONDS);
	}
	//testing that it's working
//	public static void main(String[] args){
//		Timed test = new Timed();
//		test.run();
//		
//		
//	}
}
