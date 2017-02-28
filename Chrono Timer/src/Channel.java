import java.util.*;

public class Channel {
	private Sensor sensor;
	private Timer timer;
	private boolean on;
	private String[] times;
	
	public Channel(Timer t){
		on = false;
		timer = t;
		times = new String[3];
		sensor = new Sensor();
	}
	public void setSensor(String type){
		sensor = new Sensor(type);
	}
	public void toggle(){
		on = !on;
	}
	public boolean isOn(){
		return on;
	}
	public void trigger(){
		// I'm not exactly sure how this should work.
		if(sensor.isActive()){
			if(times[0] == null)
				times[0] = timer.getTime();
			else if (times[1] == null)
				times[1] = timer.getTime();
		}
	}
	
}
