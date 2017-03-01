import java.util.*;

public class Channel {
	private ChronoTimer chronotimer;
	public int id;
	private boolean on;
	
	public Channel(ChronoTimer t){
		on = false;
		chronotimer = t;
	}
	public boolean setSensor(String type){
		if(type.equalsIgnoreCase("eye") || type.equalsIgnoreCase("gate") || type.equalsIgnoreCase("pad")){
			new Sensor(this, type);
			return true;
		} else return false;
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
