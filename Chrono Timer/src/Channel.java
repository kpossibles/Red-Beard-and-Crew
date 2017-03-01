import java.util.*;

public class Channel {
	private Sensor sensor;
	private boolean on;
	
	public Channel(Timer t){
		on = false;
		sensor = new Sensor(t);
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
			sensor.trigger();
		}
	}
	
}
