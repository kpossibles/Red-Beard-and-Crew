import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Timer {
	private Date timer;
	private SimpleDateFormat sdf = new SimpleDateFormat("sssss.SS");
	
	public Timer(){
		timer = new Date();
		
	}
	public void setTime(String number){
		timer = Timestamp.valueOf(number);
	}
	public String getTime(){
		return sdf.format(timer);
	}
}
