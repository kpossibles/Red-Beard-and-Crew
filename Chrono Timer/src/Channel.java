import java.util.*;

public class Channel {
	private ChronoTimer chronotimer;
	public int id;
	private boolean on;
	
	public Channel(ChronoTimer t, int i){
		on = false;
		id = i;
		chronotimer = t;
	}
	public void setSensor(String type){
		new Sensor(this, type);
	}
	public void toggle(){
		on = !on;
	}
	public boolean isOn(){
		return on;
	}
	public void trigger(){
		if(on){
			chronotimer.trigger(id);
		}
	}
	
}
